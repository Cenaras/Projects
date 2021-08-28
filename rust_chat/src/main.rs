mod util;

use std::io;
use num_bigint::BigUint;
use std::path::Path;
use std::io::prelude::*;
use std::fs;
use std::fs::File;

use std::thread;
use std::net::{TcpListener, TcpStream, Shutdown};
use std::env;
use std::str::from_utf8;
use std::io::{Read, Write};


// Notes: https://github.com/rsarky/og-rsa/blob/master/src/lib.rs
// Client-Server: https://github.com/tensor-programming/Rust_client-server_chat/blob/master/chat/client/src/main.rs

// NOTE: Project undone - lost interest after TCP part... Too much copy-paste code


/* ########### CLIENT-SERVER PART ###########*/

fn handle_connection(mut conn: TcpStream) //This needs reworking - this is just the template for TCP echo servers (we simply write w/e is in the buffer)
{
    let mut buf = [0 as u8, 50];
    while match conn.read(&mut buf)
    {
        Ok(size) => 
        {
            conn.write(&buf[0..size]).unwrap();
            true
        }

        Err(e) => 
        {
            println!("Error occurred from connection {}: {}", conn.peer_addr().unwrap(), e);
            conn.shutdown(Shutdown::Both).unwrap();
            false
        }
    } {}
}

fn listen_for_connections(port: &String)
{
    let mut address: String = "localhost:".to_owned();
    address.push_str(port);
    let listener = TcpListener::bind(address).unwrap();
    for connections in listener.incoming()
    {
        match connections
        {
            Ok(conn) => 
            {
                println!("Connection inbound from {}", conn.peer_addr().unwrap());
                thread::spawn(move|| {handle_connection(conn);}); //Spawn new thread to handle the TCP connection
            }

            Err(e) =>
            {
                println!("Error occured while listening for connection: {}", e);
            }

        }
    }
    
}

fn connect_to_client(port: &String)
{
    let mut address: String = "localhost:".to_owned();
    address.push_str(port);
    match TcpStream::connect(address)
    {
        Ok(mut conn) => 
        { //get msg, enc and send
            println!("Successfully connected to server in port 3333");

            let msg = b"Hello!";

            conn.write(msg).unwrap();
            println!("Sent Hello, awaiting reply...");

            let mut data = [0 as u8; 6]; // using 6 byte buffer
            match conn.read_exact(&mut data) 
            {
                Ok(_) => 
                {
                    if &data == msg 
                    {
                        println!("Reply is ok!");
                    } 
                    else 
                    {
                        let text = from_utf8(&data).unwrap();
                        println!("Unexpected reply: {}", text);
                    }
                },
                Err(e) => 
                {
                    println!("Failed to receive data: {}", e);
                }
            }
        },
        Err(e) => 
        {
            println!("Failed to connect: {}", e);
        }
    }
    println!("Terminated.");
}



/* ########### RSA PART ###########*/

struct KeySet {
    e: BigUint,
    d: BigUint,
    n: BigUint,
}


impl KeySet
{
    pub fn to_string(&self) -> String
    {
        format!("{} {} {}", self.e.to_str_radix(10), self.d.to_str_radix(10), self.n.to_str_radix(10)) //Return the 
    }
}


//Add error handling if file format is not correct
fn read_key(filename: &str) -> KeySet
{
    let contents = fs::read_to_string(filename).expect("Error while reading key file - if this persists try to delete the file and obtain new keypair");
    let contents: Vec<&[u8]> = contents.split(' ').map(|x| x.as_bytes()).collect();

    let e = BigUint::parse_bytes(contents[0], 10).expect("Error while reading key");
    let d = BigUint::parse_bytes(contents[1], 10).expect("Error while reading key");
    let n = BigUint::parse_bytes(contents[2], 10).expect("Error while reading key");
    KeySet{e, d, n}

}


//Either return the key when generating it and when reading or something else...
fn obtain_key_pair() -> KeySet
{

    /*
        First check if the client already has a key pair stored.
        If he does, read it from the file
        Else, create a key pair and store it in a file
    */


    if Path::new("keys.txt").exists()
    {
        println!("File exists"); //Report that file indeed exists
        return read_key("keys.txt");
    }
    else
    {
        println!("No keys were found - generating your keypair... This may take some time");
        let e = BigUint::from(65537u64); //hard coding e for now...
        
        let mut p = util::generate_primes(512);
        
        //We need that p mod e =/= 1 and same for q - they have to be co-prime
        while &p % &e == BigUint::from(1u64)
        {
            p = util::generate_primes(512)
        }

        let mut q = util::generate_primes(512);
        while &q % &e == BigUint::from(1u64)
        {
            q = util::generate_primes(512)
        }

        let n = &p*&q;
        let euler = (p - BigUint::from(1u32)) * (q - BigUint::from(1u32));
        println!("n as generated: {}", n);
        

        let d: BigUint = util::mult_inverse(&euler, &e);

        let key_set = KeySet {e, d, n}; //Maybe run a validation test on the keyset?
        println!("Current keyset: {}\n{}\n{}", key_set.e, key_set.d, key_set.n);

        /* An implementation of generating files and writing to them in Rust found on the internet */

        let test = key_set.to_string();
        let key_set_string = test.as_bytes();

        let mut file = match File::create("keys.txt") 
        {
            Err(_) => panic!("Error while creating file for storing key pair... Terminating process"),
            Ok(key_file) => key_file
        };
    
        match file.write_all(key_set_string) //before: b"&n.to_bytes_be()" 
        {
            Err(_) => panic!("Error while writing to key pair file... Terminating process"),
            Ok(_) => println!("Key fild was sucessfully created...")
        };

        return key_set;
    }
}


// c = m^e mod n
fn encrypt(m: &BigUint, n: &BigUint, e: &BigUint) -> BigUint
{
    return m.modpow(&e, &n);
}

// m = c^d mod n
fn decrypt(c: &BigUint, n: &BigUint, d: &BigUint) -> BigUint
{
    return c.modpow(&d, &n);
}

/*
    Reads a string from the command line, and converts it to a BigUInt representataion, which then can be passed to the encrypt function
*/

// Convert the raw message to BigUInt prepresentation
fn msg_to_int(msg: String) -> BigUint
{
    return BigUint::from_bytes_be(msg.as_bytes()); 
} 

fn get_raw_msg() -> String
{
    //Creates a string, reads cmd line and stores in string.
    let mut message = String::new();
    io::stdin().read_line(&mut message).expect("Failed to read line");
    return message
}

/*
    Notes: Allow to listen for connections
    Command line argument to say who we want to connect to
    Actually create a TCP Stream Connection
*/

fn main() -> std::io::Result<()> 
{
    
    //Obtain arguments - either listen for incomming connections or connect to server
    let args: Vec<String> = env::args().collect();
    if args[1].trim() == "listen" //args[0] is path, 1 is first argument etc...
    {
        //println!("Listening for connections");
        listen_for_connections(&args[2])
    }
    else if args[1].trim() == "connect" 
    {
        //println!("Connecting to peer");
        connect_to_client(&args[2])
    }
    //println!("Enter a message:");
    //let message = get_msg();
    let client_key_set = obtain_key_pair(); //Obtain / Generate the key set from the client.
    Ok(())

}

#[cfg(test)]
mod tests
{
    use super::*;
    #[test]
    fn test_enc_dec()
    {
        let p = BigUint::from(5u64);
        let q = BigUint::from(11u64);
        let n = &p*&q;
        let e = BigUint::from(7u64);
        let d = BigUint::from(23u64);
        let message = BigUint::from(8u64);

        assert_eq!(encrypt(&message, &n, &e), BigUint::from(2u64));
        assert_eq!(decrypt(&encrypt(&message, &n, &e), &n, &d), message);

    }

}

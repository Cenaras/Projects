mod util;



use std::io;
use num_bigint::BigUint;
use std::path::Path;
use std::io::prelude::*;
use std::fs;
use std::fs::File;


    /*
        let p = BigUint::from(5u64);
        let q = BigUint::from(11u64);
        return(BigUint::from(7u64), BigUint::from(23u64), p*q);
    */

// Notes: https://github.com/rsarky/og-rsa/blob/master/src/lib.rs


struct KeySet {
    e: BigUint,
    d: BigUint,
    n: BigUint,
}


impl KeySet
{
    pub fn to_string(&self) -> String
    {
        format!("{} {} {}", self.e.to_str_radix(10), self.d.to_str_radix(10), self.n.to_str_radix(10))
    }
}

        //This actually restores our n from the file!

        //let test = fs::read("keys.txt")
        //.expect("Couldnt read file.");
        //let test = BigUint::from_bytes_be(&test);
        
        //println!("Keys after reading from file: {}", test);

        //Write to the file: https://doc.rust-lang.org/std/fs/struct.File.html
fn read_key(filename: &str) -> KeySet
{
    let contents = fs::read_to_string(filename).expect("Error while reading key file - if this persists try to delete the file and obtain new keypair");
    let contents: Vec<&[u8]> = contents.split(' ').map(|x| x.as_bytes()).collect();

    let e = BigUint::parse_bytes(contents[0], 10).expect("Error while reading key");
    let d = BigUint::parse_bytes(contents[1], 10).expect("Error while reading key");
    let n = BigUint::parse_bytes(contents[2], 10).expect("Error while reading key");
    KeySet{e, d, n}

}


/* TO DO
    - Store them in readable file if non-existing
    - Read keypair from file, if existing

*/
    /*
        n = p*q
        d = e^-1 mod (p-1 * q-1)
    */  



fn obtain_key_pair() //-> KeySet
{

    /*
        First check if the client already has a key pair stored.
        If he does, read it from the file
        Else, create a key pair and store it in a file
    */


    if Path::new("keys.txt").exists()
    {
        println!("File exists"); //Report that file indeed exists
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

        // Test conversion from struct to bytes and back with writing and reading from file...

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
//Should not take these arguments - just used for testing!
fn get_msg() -> BigUint
{
    //Creates a string, reads cmd line and stores in string.
    let mut message = String::new();
    io::stdin().read_line(&mut message).expect("Failed to read line");
    
    //Converts the message to a byte array, and from that converts it to a biguint representation of the byte array
    return BigUint::from_bytes_be(message.as_bytes()); 
}

fn main() {
    
    //println!("Enter a message:");

    //let message = get_msg();
    obtain_key_pair();


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

mod util;

use std::io;
use num_bigint::BigUint;
use std::path::Path;
use std::io::prelude::*;
use std::fs;
use std::fs::File;

// Notes: https://github.com/rsarky/og-rsa/blob/master/src/lib.rs

//(e, d, n)
type KeySet = (BigUint, BigUint, BigUint);

/* TO DO
    - Generate key pairs
    - Store them in readable file if non-existing
    - Read keypair from file, if existing


*/
    /*
        n = p*q
        d = e^-1 mod (p-1 * q-1)
    */  



//A lot here works, when p and q are the correct type. Just uncomment again... Working on getting correct primes rn.
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
        
        //This BigUInt is not the same as the other for some reason...
        //let p = Generator::new_prime(512); //Returns a biguint. Key size should be 2048 - shorter for testing
        //let q = Generator::new_prime(512);

        //We need that p mod e =/= 1 and same for q - they have to be co-prime
        
        /*if p % e == 1
        {

        }*/

        //let n = &p*&q;
        //println!("n as generated: {}", n);
        
        //Compute the rest of the keypair from here...



        /* An implementation of generating files and writing to them in Rust found on the internet */

        /*let mut file = match File::create("keys.txt") 
        {
            Err(_) => panic!("Error while creating file for storing key pair... Terminating process"),
            Ok(key_file) => key_file
        };
    
        match file.write_all(&n.to_bytes_be()) 
        {
            Err(_) => panic!("Error while writing to key pair file... Terminating process"),
            Ok(_) => println!("Key fild was sucessfully created...")
        };*/


        //This actually restores our n from the file!

        let test = fs::read("keys.txt")
        .expect("Couldnt read file.");
        let test = BigUint::from_bytes_be(&test);
        
        //println!("n after reading from file: {}", test);

        //Write to the file: https://doc.rust-lang.org/std/fs/struct.File.html
    }

    /*
        let p = BigUint::from(5u64);
        let q = BigUint::from(11u64);
        return(BigUint::from(7u64), BigUint::from(23u64), p*q);
    */
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

    //let key: KeySet = createKeyPair();
    //let message = get_msg();
    //obtain_key_pair();
    util::generate_primes(15);

}

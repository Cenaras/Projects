use std::io;
use num_bigint::BigUint;
use std::fs;
// Notes: https://github.com/rsarky/og-rsa/blob/master/src/lib.rs

//(e, d, n)
type KeySet = (BigUint, BigUint, BigUint);

// I THINK THE ERROR COMRES FROM THE FACT THAT THE KEYS ARE SO SMALL THAT WE HAVE TOO MANY COLLISIONS!
// IMPLEMENT A PROPER WAY TO GENERATE KEYS BEFORE ANYTHING


//Hard coded toy example for keypair gen
    /*
        n = p*q
        d = e^-1 mod (p-1 * q-1)
    */  
fn createKeyPair() -> KeySet
{
    let p = BigUint::from(5u64);
    let q = BigUint::from(11u64);
    return(BigUint::from(7u64), BigUint::from(23u64), p*q);
}


// c = m^e mod n
fn encrypt(m: BigUint, n: BigUint, e: BigUint) -> BigUint
{
    return m.modpow(&e, &n);
}

// m = c^d mod n
fn decrypt(c: BigUint, n: BigUint, d: BigUint) -> BigUint
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

fn read_key(file: &str) -> String
{ 
    return fs::read_to_string(file)
        .expect("Something went wrong reading the file");
}




fn main() {


    //println!("Result was: {}", result);
    
    let file = read_key("test.txt");

    //let message = BigUint::from(42u64); //Hard coded message - encrypts to "48"
    //let cipher = encrypt(message, n, e);

    

    //println!("Cipher: {}", cipher);
    //println!("Hard coded result: {}", decrypt(BigUint::from(48u64), key.2, key.1));
 
    /* Something is wrong with enc/dec
        println!("Message entered in BigUInt representation: {} ", message);
        //println!("Message encrypted: {}", encrypt(message, key.2, key.0));
        let test :BigUint = BigUint::from(40u64); 
        //Cause of "moved" error, we compute the encrypted message before: Result was 40 of "hello"
        println!("Encrypted message decrypted: {}", decrypt(test, key.2, key.1));
    */

    println!("Enter a message:");

    let key: KeySet = createKeyPair();
    let message = get_msg(key.2, key.0, key.1);


}

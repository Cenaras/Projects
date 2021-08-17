use std::io;
use num_bigint::BigUint;
use std::fs;
// Notes: https://github.com/rsarky/og-rsa/blob/master/src/lib.rs

//(e, d, n)
type KeySet = (BigUint, BigUint, BigUint);


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
fn int_enc_msg(m: BigUint, n: BigUint, e: BigUint) -> BigUint
{
    return m.modpow(&e, &n);
}

// m = c^d mod n
fn int_dec_msg(c: BigUint, n: BigUint, d: BigUint) -> BigUint
{
    return c.modpow(&d, &n);
}

fn get_msg() -> String
{
    let mut message = String::new();
    io::stdin().read_line(&mut message).expect("Failed to read line");
    return message;
}

fn read_key(file: &str) -> String
{ 
    return fs::read_to_string(file)
        .expect("Something went wrong reading the file");
}




fn main() {

    //println!("Enter a message:");
    //let result = get_msg();
    //println!("Result was: {}", result);
    
    let file = read_key("test.txt");

    let message = BigUint::from(42u64); //Hard coded message - encrypts to "48"
    //let cipher = int_enc_msg(message, n, e);

    let key: KeySet = createKeyPair();

    //println!("Cipher: {}", cipher);
    println!("Hard coded result: {}", int_dec_msg(BigUint::from(48u64), key.2, key.1));

 
}

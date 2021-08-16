use std::io;
use num_bigint::BigInt;
//use std::env;
use std::fs;

type key = (BigInt , BigInt);


fn enc_msg(msg: &str, n: u64, e: u64) -> &str
{
    return msg;
}

// c = m^e mod n
fn int_enc_msg(m: u32, n: u32, e: u32) -> u32
{
    return m.pow(e) % n;
}

// m = c^d mod n
fn int_dec_msg(c: u32, n: u32, d: u32) -> u32
{
    return c.pow(d) % n;
}

fn get_msg() -> String
{
    let mut message = String::new();
    io::stdin().read_line(&mut message).expect("Failed to read line");
    return message;
}

fn readKey(file: &str)
{
    
    let contents = fs::read_to_string(file)
        .expect("Something went wrong reading the file");
    println!("In file was: {}", contents);
}

fn main() {

    println!("Enter a message:");
    let result = get_msg();
    println!("Result was: {}", result);
    let base: i32 = 2;
    let pow = base.pow(2);
    println!("{}", pow%2);
    //println!("Enc and dec: {} : {}", )
    readKey("test.txt");
}

use std::io;
use num_bigint::BigInt;

type key = (BigInt , BigInt );


fn enc_msg(msg: &str) -> &str
{
    return msg;
}

fn get_msg() -> String
{
    let mut message = String::new();
    io::stdin().read_line(&mut message).expect("Failed to read line");
    return message;
}

fn main() {

    println!("Enter a message:");
    let result = get_msg();
    println!("Result was: {}", result);
}

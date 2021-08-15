use std::io;

fn get_number()
{
    let mut message = String::new();
    io::stdin().read_line(&mut message).expect("Failed to read line");
}

fn main() {

    println!("Hello, world!");
    get_number();
}

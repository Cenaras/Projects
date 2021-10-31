#[derive(Debug, Clone, PartialEq)]
enum Token {
    Identifier(String),
    Literal(Literal),
    Symbol(String),
}

enum Literal {
    Integer(i32),
    Str(String),
}
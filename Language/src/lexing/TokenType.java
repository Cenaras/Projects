package lexing;

public enum TokenType {
    INT,
    ID,
    LET,
    PLUS {
        public String toString() {
            return "+";
        }
    },
    MINUS {
        public String toString() {
            return "-";
        }
    },
    TIMES {
        public String toString() {
            return "*";
        }
    },
    DIVIDE {
        public String toString() {
            return "/";
        }
    },
    SEMICOLON,
    ASSIGNEQ,
    EOF
}

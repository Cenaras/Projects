import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        String filePrefix = "D:\\CodingStuff\\Projects\\Projects\\Language\\tests\\";
//        Lexer lexer = new Lexer(filePrefix + "test_files\\" + args[0]);
//
//        lexer.testStuff();
//        lexer.getToken();

        //Parser parser = new Parser(filePrefix);

        //Program result = parser.parse(args[0]);
        //Typechecker typechecker = new Typechecker(result);
        //typechecker.analyzeProgram();

        byte[] test = createValidBitmap(4);
        for (int i = 0; i < test.length; i++) {
            System.out.println(test[i]);
        }

        System.out.println(1 << 4);


    }


    private static byte[] createValidBitmap(int n) {
        int m = n / 8 + 1;
        byte[] bitmap = new byte[m];
        for (int i = 0; i < m; i++) {
            bitmap[i] = -1;
        }
        bitmap[bitmap.length - 1] = (byte) ((1 << n % 8) -1);
        return bitmap;
    }
}


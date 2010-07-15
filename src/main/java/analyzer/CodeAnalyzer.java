package analyzer;

public class CodeAnalyzer {
       
    public static void main(String[] argv) throws Exception {       
        try {
            new Entrypoint(argv, new UsageWriter(System.err)).run();
        } catch (IllegalArgumentException e) {
            System.exit(1);
        }
    }

}

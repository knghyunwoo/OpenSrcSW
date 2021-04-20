public class midterm {
    public static void main(String[] args) {
        try {
            if (args[0].equals("-f")&&args[2].equals("-q")) {
                genSnippet.genSnippet(args[1], args[3]);
            }
            else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


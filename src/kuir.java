public class kuir {
    public static void main(String[] args) {
//        System.out.println(args[0]);
//        System.out.println(args[1]);
        try {
            //makeCollection 실행하는데 args[1]를 넘겨주면서 실행
            if (args[0].equals("-c")) {
//                System.out.println("sdf");
                makeCollection.makeCollection(args[1]);
            } else if (args[0].equals("-k")) {
            //makeKeyword 실행하는데 args[1]를 넘겨주면서 실행
                makeKeyword.makeKeyword(args[1]);
            }
            else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("asfdasdf");
    }
}

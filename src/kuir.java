public class kuir {
    public static void main(String[] args) {
//        System.out.println(args[0]);
//        System.out.println(args[1]);
        try {
            //makeCollection 실행하는데 args[1]로 html 폴더경로를 넘겨주면서 실행
            if (args[0].equals("-c")) {
                makeCollection.makeCollection(args[1]);
            }
            //makeKeyword 실행하는데 args[1]로 collextion.xml 경로를 넘겨주면서 실행
            else if (args[0].equals("-k")) {
                makeKeyword.makeKeyword(args[1]);
            }
            //indexer 실행하는데 args[1]로 index.xml 경로를 넘겨주면서 실행
            else if (args[0].equals("-i")){
                indexer.indexer(args[1]);
                // index.post 확인하는 코드
                indexer.PostOpen();
            }
            else if(args[0].equals("-s")&&args[2].equals("-q")) {
                searcher.search(args[1], args[3]);
            }
            else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package performance;


import crawer.App;
import crawer.Rank;

public class ThreadNumTest {
    public static void main(String[] args) throws Exception {
        App app = new App();
        Rank rank = app.rank();
        long start = System.currentTimeMillis();
        rank.rankResult("daily");
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time); //7779 7586 7551 7499

    }
}

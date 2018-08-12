package ui;

import java.util.List;

import crawer.Rank;
import javafx.concurrent.Task;

public class RankTask extends Task<List<String>> {

    @Override
    protected List<String> call() throws Exception {
        Rank rank = new Rank();

        List<String> urls = rank.rankResult("daily");
        return urls;
    }

    public static void main(String[] args) throws Exception {
        List<String> list = new RankTask().call();
        for (String string : list) {
            System.out.println(string);
        }
    }

}

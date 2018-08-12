package search;

import java.util.List;

import org.junit.Test;

public class SearchTest {
    @Test
    public void search() {
        new Search().search("水着", "safe", "date_d");

    }

    @Test
    public void search2() {
        new Search().search("水着", "r18", "date");
    }

    @Test
    public void search3() {
        Search search = new Search();
        List<Search> list = search.search("水着", "safe", "date_d");
        for (Search search2 : list) {
            System.out.println(search2);
        }
    }

    @Test
    public void search4() {
        Search search = new Search();
        List<String> images = search.searchImages("水着", "safe", "date_d");
        for (String string : images) {
            System.out.println(string);
        }

    }


}

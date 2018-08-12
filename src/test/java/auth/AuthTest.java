package auth;

import java.util.List;

import org.junit.Test;

public class AuthTest {
    @Test
    public void r18_daily() throws Exception {
        new Auth().r18_rank(5, "daily", "/Users/rongchuan.jin/webmagic");
    }

    @Test
    public void r18_weekly() throws Exception {
        new Auth().r18_rank("weekly");
    }

    @Test
    public void r18_male() throws Exception {
        new Auth().r18_rank("male");
    }

    @Test
    public void r18_female() throws Exception {
        new Auth().r18_rank("female");
    }

    @Test
    public void r18_result() throws Exception {
        List<String> result = new Auth().r18_rank_result("daily");
        for (String string : result) {
            System.out.println(string);
        }
    }
}

package auth;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import base.BaseTestCase;

public class AuthTest extends BaseTestCase {


    @Test
    public void r18_daily() throws Exception {
        new Auth().r18_rank(5, "daily", DIRNAME);
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
        Assert.assertTrue(result.size() == 50);
    }
}

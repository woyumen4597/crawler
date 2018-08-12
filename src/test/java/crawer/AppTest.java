package crawer;

import java.util.List;

import base.BaseTestCase;
import org.junit.Test;

import exception.NotFoundException;
import search.Search;
import user.Detail;

public class AppTest extends BaseTestCase {

    @Test
    public void test1() throws Exception {
        App app = new App();
        app.rank().rank("daily");
    }

    @Test
    public void test2() {
        App app = new App();
        app.user().illust("4755415", 5, DIRNAME);
    }

    @Test
    public void test3() {
        App app = new App();
        app.user("4755415").illust(5, DIRNAME);
    }

}

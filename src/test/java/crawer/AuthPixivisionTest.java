package crawer;


import org.junit.Test;

import exception.NotFoundException;
import base.BaseTestCase;
import utils.FileUtils;

public class AuthPixivisionTest extends BaseTestCase {

    @Test
    public void pixivision() throws NotFoundException {
        new Pixivision().pixivision("2810");
    }

    @Test
    public void pixivision2() throws NotFoundException {
        new Pixivision().pixivision("2840", FileUtils.getDefaultDownloadPath(), 5);
    }
}
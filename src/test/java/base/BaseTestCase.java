package base;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import utils.FileUtils;

import java.io.File;

public class BaseTestCase {
    protected static String DIRNAME = FileUtils.getDefaultDownloadPath();

    @BeforeClass
    public static void mkdir() {
        File file = new File(DIRNAME);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @AfterClass
    public static void clean() {
        FileUtils.delete(DIRNAME);
    }
}

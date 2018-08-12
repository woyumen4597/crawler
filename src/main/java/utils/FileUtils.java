package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 测试类File
 *
 * @author jrc
 */
public class FileUtils {
    private static File file;
    private static boolean flag;
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        line = reader.readLine();
        while (line != null) {
            buffer.append(line);
            buffer.append("\n");
            line = reader.readLine();
        }
        reader.close();
        in.close();
    }

    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        readToBuffer(sb, filePath);
        return sb.toString();
    }

    public static boolean deleteDir(String dirName) {
        if (!dirName.endsWith(File.separator)) {
            dirName += File.separator;
        }
        File dirFile = new File(dirName);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                logger.info(files[i].getAbsolutePath() + " delete success!");
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteDir(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        return dirFile.delete();

    }

    private static boolean deleteFile(String filePath) {
        flag = false;
        file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean delete(String deletePath) {
        flag = false;
        file = new File(deletePath);
        if (!file.exists()) {
            return flag;
        } else {
            if (file.isFile()) {
                return deleteFile(deletePath);
            } else {
                return deleteDir(deletePath);
            }
        }
    }

    public static String getDefaultDownloadPath(){
        String home = System.getProperty("user.home");
        return home + File.separator + "pixiv4j";
    }
}

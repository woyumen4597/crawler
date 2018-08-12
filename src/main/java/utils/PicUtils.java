package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下载工具类
 *
 * @author jrc
 */
public class PicUtils {
    private static CloseableHttpClient client;
    private static Logger logger = LoggerFactory.getLogger(PicUtils.class);

    static {
        client = HttpClients.createDefault();
    }


    public static boolean download(String basePath, String filename, String url) throws IOException {
        HttpGet get = new HttpGet(url);
        enrichRequest(get);
        CloseableHttpResponse response = client.execute(get);
        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            String path = basePath + File.separator + filename;
            File file = new File(path);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            InputStream inputStream = response.getEntity().getContent();
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        } else {
            logger.error(url + " download failed,error code: " + code);
            return false;
        }
    }

    private static void enrichRequest(HttpGet get) {
        get.addHeader("Authorization", "Bearer 8mMXXWT9iuwdJvsVIvQsFYDwuZpRCM");
        get.addHeader("Content-Type", "application/x-www-form-urlencoded");
        get.addHeader("Referer", "https://www.pixiv.net/ranking.php");
    }
}

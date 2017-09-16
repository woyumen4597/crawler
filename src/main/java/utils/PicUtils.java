package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PicUtils {


	public static boolean download(String basePath, String filename, String url) throws IOException {
		Logger logger = LoggerFactory.getLogger(PicUtils.class);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = client.execute(get);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			String path = basePath + "/" + filename;
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
		}else{
			logger.error(url+" download failed,error code: "+code);
			return false;
		}
	}
}

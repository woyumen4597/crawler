package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class PicUtils {
	public static void download(String basePath, String filename, String url) throws Exception {
		String path = basePath + "/" + filename;
		File file = new File(path);

		FileOutputStream outputStream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = client.execute(get);
		InputStream inputStream = response.getEntity().getContent();
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();

	}
}

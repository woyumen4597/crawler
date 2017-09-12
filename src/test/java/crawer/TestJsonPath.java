package crawer;

import java.io.IOException;
import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import utils.FileUtils;

public class TestJsonPath {

	public static void main(String[] args) {
		String filePath = "C:\\Users\\Administrator\\git\\crawer\\src\\test\\java\\json";
		try {
			String json = FileUtils.readFile(filePath);
			DocumentContext context = JsonPath.parse(json);
			List<String> authors = context.read("$.store.book[*].author");
			for (String string : authors) {
				System.out.println(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

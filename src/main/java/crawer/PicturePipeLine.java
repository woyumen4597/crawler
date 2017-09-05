package crawer;
import java.util.List;


import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.PicUtils;

public class PicturePipeLine implements Pipeline{
	private static String BASE_PATH = "D:/webmagic";
	public void process(ResultItems resultItems, Task task) {
		List<String> list = resultItems.get("url");
		for (String url : list) {
			String filename = url.substring(url.lastIndexOf('/'));
			try {
				PicUtils.download(BASE_PATH, filename, url);
			}catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}


	}


}

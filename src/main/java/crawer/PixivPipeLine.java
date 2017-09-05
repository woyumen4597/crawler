package crawer;

import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.PicUtils;

public class PixivPipeLine implements Pipeline {
	private String basePath = "D:\\webmagic";

	public void process(ResultItems resultItems, Task task) {
		List<String> list = resultItems.get("urls");

		for (String url : list) {
			String filename = url.substring(url.lastIndexOf("/"));
				try {
					PicUtils.download(basePath,filename, url);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

		}
	}

}

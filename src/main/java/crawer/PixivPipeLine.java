package crawer;

import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.PicUtils;

public class PixivPipeLine implements Pipeline {
	// 下载的目录
	private String basePath = "D:\\webmagic"; // default
	private int number = 5; // default

	public String getBasePath() {
		return basePath;
	}

	public PixivPipeLine path(String basePath) {
		this.basePath = basePath;
		return this;
	}

	public PixivPipeLine number(int number) {
		this.number = number;
		return this;
	}

	public int getNumber() {
		return number;
	}

	public PixivPipeLine() {
	}

	public PixivPipeLine(String basePath, int number) {
		this.basePath = basePath;
		this.number = number;
	}

	public PixivPipeLine(int number) {
		this.number = number;
	}

	public PixivPipeLine(String basePath) {
		this.basePath = basePath;
	}

	public void process(ResultItems resultItems, Task task) {
		List<String> list = resultItems.get("urls");

		for (int i = 0; i < this.number; i++) {
			String url = list.get(i);
			String filename = url.substring(url.lastIndexOf("/"));
			try {
				PicUtils.download(basePath, filename, url);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}
	}

}

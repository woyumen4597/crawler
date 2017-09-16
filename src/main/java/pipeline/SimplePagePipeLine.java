package pipeline;

import java.io.IOException;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.PicUtils;

public class SimplePagePipeLine implements Pipeline {
	// 下载的目录
	private String basePath = "D:\\webmagic"; // default
	private int number = 5; // default

	public String getBasePath() {
		return basePath;
	}

	public SimplePagePipeLine path(String basePath) {
		this.basePath = basePath;
		return this;
	}

	public SimplePagePipeLine number(int number) {
		this.number = number;
		return this;
	}

	public int getNumber() {
		return number;
	}

	public SimplePagePipeLine() {
	}

	public SimplePagePipeLine(String basePath, int number) {
		this.basePath = basePath;
		this.number = number;
	}

	public SimplePagePipeLine(int number) {
		this.number = number;
	}

	public SimplePagePipeLine(String basePath) {
		this.basePath = basePath;
	}

	public void process(ResultItems resultItems, Task task) {
		List<String> list = resultItems.get("urls");
		int num = this.number<list.size()?this.number:list.size();
		for (int i = 0; i < num; i++) {
			String url = list.get(i);
			String filename = url.substring(url.lastIndexOf("/"));
			try {
				PicUtils.download(basePath, filename, url);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}



}

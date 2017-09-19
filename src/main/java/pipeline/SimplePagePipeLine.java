package pipeline;

import java.io.IOException;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.PicUtils;

public class SimplePagePipeLine implements Pipeline {
	public static String DEFAULT_DOWNLOAD_PATH = "D:\\webmagic"; 
	public static int DEFAULT_DOWNLOAD_NUMBER = 5;
	// 下载的目录
	private String basePath = DEFAULT_DOWNLOAD_PATH; // default
	private int number = DEFAULT_DOWNLOAD_NUMBER; // default

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
		int i = 0;
		while(i<num) {
			String url = list.get(i);
			String filename = url.substring(url.lastIndexOf("/"));
			try {
				if(PicUtils.download(basePath, filename, url)) {
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}



}

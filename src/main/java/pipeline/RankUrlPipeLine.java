package pipeline;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class RankUrlPipeLine implements Pipeline{
	private List<String> urls = new ArrayList<String>();
	public RankUrlPipeLine() {
	}
	public RankUrlPipeLine(List<String> urls) {
		this.urls = urls;
	}
	public void process(ResultItems resultItems, Task task) {
		List<String> list = resultItems.get("urls");
		if(list!=null&&list.size()>0) {
			setUrls(list);
		}
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

}

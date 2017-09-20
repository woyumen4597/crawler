package pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import user.Detail;

/**
 * 处理User信息Detail的PipeLine
 * @author jrc
 * @see user.Detail
 */
public class UserPipeLine implements Pipeline {
	private Detail detail;
	public UserPipeLine() {
	}
	public UserPipeLine(Detail detail){
		this.setDetail(detail);
	}
	public void process(ResultItems resultItems, Task task) {
		Detail detail = resultItems.get("detail");
		setDetail(detail);
	}
	public Detail getDetail() {
		return detail;
	}
	public void setDetail(Detail detail) {
		this.detail = detail;
	}

}

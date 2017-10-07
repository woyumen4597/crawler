package pipeline;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import search.Search;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.DuplicateRemover;

/**
 * 处理搜索结果的PipeLine
 * @author jrc
 *
 */
public class SearchPipeLine implements Pipeline{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private List<Search> searchs;
	private List<String> images;

	@SuppressWarnings("unchecked")
	public void process(ResultItems resultItems, Task task) {
		List<Search> results = resultItems.get("results");
		if(results!=null){
			logger.info("Search Succeed!Saved into List<Search>!");
		}
		setSearchs(results);
		images = resultItems.get("images");
		images = DuplicateRemover.removeDuplicate(images);
		setImages(images);
	}

	public List<Search> getSearchs() {
		return searchs;
	}

	public void setSearchs(List<Search> searchs) {
		this.searchs = searchs;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}

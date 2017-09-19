package crawer;

import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import pipeline.SimplePagePipeLine;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

/**
 *
 * # mode: [day, week, month, male, female, rookie, orignal] day -> daily week
 * -> weekly month -> monthly male -> male female -> female rookie ->rookie
 * original -> original
 *
 * #content illust ->illust ugoira -> ugoira manga -> manga
 *
 * @author jrc
 *
 */
public class Rank implements PageProcessor {
	private String mode;
	private String content;
	private Site site = Site.me().setRetryTimes(3).setTimeOut(10000)
			.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");

	public Rank() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Rank(String mode) {
		this.setMode(mode);
	}

	public Rank(String mode, String content) {
		this.setMode(mode);
		this.setContent(content);
	}

	public void process(Page page) {
		Json json = page.getJson();
		DocumentContext context = JsonPath.parse(json.toString());
		List<String> list = context.read("$.contents[*].url");
		List<String> urls = new ArrayList<String>();
		for (String url : list) {
			urls.add(url);
		}
		page.putField("urls", urls);
	}

	public Site getSite() {
		return site;
	}

	public void rank(String mode) throws Exception {
		String originUrl = "https://www.pixiv.net/ranking.php?mode=" + mode + "&format=json";
		Spider.create(new Rank()).addUrl(originUrl).thread(3).addPipeline(new SimplePagePipeLine()).run();
	}

	public void rank(String mode, String basePath, int number) throws Exception {
		String originUrl = "https://www.pixiv.net/ranking.php?mode=" + mode + "&format=json";
		Spider.create(new Rank()).addUrl(originUrl).thread(3).addPipeline(new SimplePagePipeLine(basePath, number)).run();
	}

	public void rank(String mode, String content) throws Exception {
		String originUrl = "https://www.pixiv.net/ranking.php?mode=" + mode + "&content=" + content + "&format=json";
		Spider.create(new Rank()).addUrl(originUrl).thread(3).addPipeline(new SimplePagePipeLine()).run();
	}

	/**
	 * @param mode
	 *            模式
	 * @param basePath
	 *            下载的目录位置 
	 * @param number
	 *            下载的图片数量
	 * @param content
	 *            内容类型
	 * @throws Exception
	 */
	public void rank(String mode, String basePath, int number, String content) throws Exception {
		String originUrl = "https://www.pixiv.net/ranking.php?mode=" + mode + "&content=" + content + "&format=json";
		Spider.create(new Rank()).addUrl(originUrl).thread(3).addPipeline(new SimplePagePipeLine(basePath, number)).run();
	}

}

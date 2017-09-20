package user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import pipeline.MultiPagePipeLine;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 下载指定画师的作品
 * @author jrc
 *
 */
public class Illust implements PageProcessor {

	private Site site = Site.me().addHeader("Host", "www.pixiv.net")
			.addHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36")
			.addHeader("Upgrade-Insecure-Requests", "1").setTimeOut(10000).setSleepTime(3000).setRetryTimes(3);

	public void process(Page page) {
		Html html = page.getHtml();
		List<String> list = html.css("img[src]").all();
		List<String> imgUrls = new ArrayList<String>();
		for (String url : list) {
			if (StringUtils.contains(url, "600x600")) {
				url = StringUtils.substringBetween(url, "src=\"", "\"");
				url = url.replace("600x600", "240x480");
				imgUrls.add(url);
			}
		}
		page.putField("urls", imgUrls);
		List<String> requests = html.css("a[href]").all();
		List<String> urls = new ArrayList<String>();
		for (String request : requests) {
			if (request.contains("/member_illust.php?mode=medium&amp;illust_id")) {
				request = StringUtils.substringBetween(request, "href=\"", "\"");
				System.out.println("new url: " + request);
				urls.add(request);
			}
		}
		page.addTargetRequests(urls);
	}

	public Site getSite() {
		return site;
	}

	/**
	 * 根据一个illust_id求出同一个画师的其他作品
	 * @param illust_id
	 * @param number 选择下载图片的数量
	 * @param basePath 选择下载图片的位置
	 */
	public void illust(String illust_id,int number,String basePath) {
		String url = "https://www.pixiv.net/member_illust.php?mode=medium&illust_id=" + illust_id;
		Spider.create(new Illust()).addUrl(url).addPipeline(new MultiPagePipeLine(number, basePath)).thread(3).run();
	}

}

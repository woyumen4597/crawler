package crawer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class PicturePageProcessor implements PageProcessor {
	private Site site = Site.me().setRetryTimes(3).setTimeOut(10000)
			.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");

	public void process(Page page) {
		Html html = page.getHtml();
		List<String> list = html.xpath("//img[@class='_thumbnail ui-scroll-view']").all();
		List<String> urls = new ArrayList<String>();
		for (String url : list) {
			if (url.contains("i.pximg.net")) {
				url = StringUtils.substringBetween(url, "data-src=\"", "\"");
				urls.add(url);
				System.out.println(url);
			}
		}
		page.putField("urls", urls);
		page.addTargetRequests(
				page.getHtml().links().regex("https://www\\.pixiv\\.net/ranking\\.php\\?mode=daily.*").all());

	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		try {
			Spider.create(new PicturePageProcessor()).addUrl("https://www.pixiv.net/ranking.php?mode=daily").thread(3)
					.addPipeline(new PixivPipeLine()).run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

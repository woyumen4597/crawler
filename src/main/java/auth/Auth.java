package auth;


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
 * 包括r18等需要认证的内容
 * @author jrc
 *
 */
public class Auth implements PageProcessor{

	private String cookie = "p_ab_id=4; p_ab_id_2=6; login_bc=1; _ga=GA1.2.1597245002.1505723980; _gid=GA1.2.1192202528.1505723988; device_token=72b90be358aec7b350babc77ebae7f6a; a_type=0; is_sensei_service_user=1; login_ever=yes; PHPSESSID=20921007_3810c7be72635083ed95e7964e324af1; module_orders_mypage=%5B%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; __utmt=1; __utma=235335808.1597245002.1505723980.1505792375.1505797607.4; __utmb=235335808.3.9.1505797611225; __utmc=235335808; __utmz=235335808.1505723980.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=male=1^6=user_id=20921007=1^9=p_ab_id=4=1^10=p_ab_id_2=6=1^11=lang=zh=1";
	private Site site = Site.me().addHeader("Cookie", cookie).addHeader("Host", "www.pixiv.net").addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36")
			.setRetryTimes(3).setTimeOut(10000);
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


	/**
	 * @param mode {daily,weekly,male,female} 模式选择
	 * @param number 下载数量
	 * @throws Exception
	 */
	public void r18_rank(String mode,int number) throws Exception{
		r18_rank(number, mode, SimplePagePipeLine.DEFAULT_DOWNLOAD_PATH);
	}

	/**
	 * @param mode  {daily,weekly,male,female} 模式选择
	 * @throws Exception
	 */
	public void r18_rank(String mode) throws Exception{
		r18_rank(SimplePagePipeLine.DEFAULT_DOWNLOAD_NUMBER, mode, SimplePagePipeLine.DEFAULT_DOWNLOAD_PATH);
	}

	/**
	 * @param number 下载数量
	 * @param mode {daily,weekly,male,female} 模式选择
	 * @param basePath 下载目录
	 * @throws Exception
	 */
	public void r18_rank(int number,String mode,String basePath) throws Exception{
		String url = "https://www.pixiv.net/ranking.php?mode="+mode+"_r18&format=json";
		Spider.create(new Auth()).addUrl(url).thread(3).addPipeline(new SimplePagePipeLine(basePath, number)).run();
	}

}

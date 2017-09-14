package auth;

import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import pipeline.PixivPipeLine;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

public class Auth implements PageProcessor{

	private String cookie = "p_ab_id=9; login_ever=yes; ki_t=1482039280489%3B1485442943702%3B1485442943702%3B3%3B9; ki_r=; p_ab_id_2=5; _ga=GA1.2.226561830.1479867556; device_token=b0d7d37ca3a7daecc2d3d1aa756dcdd3; closed_discovery_bubble=1; a_type=0; PHPSESSID=20921007_b68aeeaa4749b75f4919d415cdcd06c3; module_orders_mypage=%5B%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; is_sensei_service_user=1; __utmt=1; __utma=235335808.226561830.1479867556.1505311501.1505393249.43; __utmb=235335808.2.10.1505393249; __utmc=235335808; __utmz=235335808.1504341764.33.10.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^4=p_ab_id_2=5=1^5=gender=male=1^6=user_id=20921007=1^9=p_ab_id=9=1^10=p_ab_id_2=5=1^11=lang=zh=1^13=fanbox_fixed_otodoke_naiyou=no=1^14=hide_upload_form=yes=1^15=machine_translate_test=no=1";
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

	public void r18_rank(String mode,int number) throws Exception{
		String url = "https://www.pixiv.net/ranking.php?mode="+mode+"_r18&format=json";
		Spider.create(new Auth()).addUrl(url).thread(3).addPipeline(new PixivPipeLine(number)).run();
	}

	public void r18_rank(String mode) throws Exception{
		String url = "https://www.pixiv.net/ranking.php?mode="+mode+"_r18&format=json";
		Spider.create(new Auth()).addUrl(url).thread(3).addPipeline(new PixivPipeLine()).run();
	}

	/**
	 * @param number
	 * @param mode {daily,weekly,male,female}
	 * @param basePath
	 * @throws Exception
	 */
	public void r18_rank(int number,String mode,String basePath) throws Exception{
		String url = "https://www.pixiv.net/ranking.php?mode="+mode+"_r18&format=json";
		Spider.create(new Auth()).addUrl(url).thread(3).addPipeline(new PixivPipeLine(basePath, number)).run();
	}

}

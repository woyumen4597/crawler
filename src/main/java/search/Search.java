package search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import pipeline.SearchPipeLine;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class Search implements PageProcessor {
	private String cookie = "p_ab_id=9; login_ever=yes; ki_t=1482039280489%3B1485442943702%3B1485442943702%3B3%3B9; ki_r=; p_ab_id_2=5; closed_discovery_bubble=1; a_type=0; _ga=GA1.2.226561830.1479867556; device_token=b0d7d37ca3a7daecc2d3d1aa756dcdd3; is_sensei_service_user=1; module_orders_mypage=%5B%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; PHPSESSID=20921007_21996a28dc2813e666a4b56f6e176685; __utma=235335808.226561830.1479867556.1505627398.1505631651.59; __utmb=235335808.8.10.1505631651; __utmc=235335808; __utmz=235335808.1504341764.33.10.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^4=p_ab_id_2=5=1^5=gender=male=1^6=user_id=20921007=1^9=p_ab_id=9=1^10=p_ab_id_2=5=1^11=lang=zh=1^13=fanbox_fixed_otodoke_naiyou=no=1^14=hide_upload_form=yes=1^15=machine_translate_test=no=1";
	private Site site = Site.me().setRetryTimes(3).setTimeOut(10000).setCharset("UTF-8")
			.addHeader("Host", "www.pixiv.net").addHeader("Cookie", cookie).addHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
	private String image;
	private String type;
	private String tags;
	private String user_id;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public void process(Page page) {
		Html html = page.getHtml();
		List<String> list = html.css("div._layout-thumbnail").all();
		List<Search> results = new ArrayList<Search>();
		List<String> urls = new ArrayList<String>();
		for (String string : list) {
			if (string.contains("illust")) {
				Search search = new Search();
				String url = StringUtils.substringBetween(string, "data-src=\"", "\"");
				urls.add(url);
				search.setImage(url);
				search.setTags(StringUtils.substringBetween(string, "data-tags=\"", "\""));
				search.setType(StringUtils.substringBetween(string, "data-type=\"", "\""));
				search.setUser_id(StringUtils.substringBetween(string, "data-user-id=\"", "\""));
				results.add(search);
			}
		}
		page.putField("results", results);
		page.putField("urls", urls);
	}

	@Override
	public String toString() {
		return "Search [image=" + image + ", type=" + type + ", tags=" + tags + ", user_id=" + user_id + "]";
	}

	public Site getSite() {
		return site;
	}

	/**
	 * @param word 搜索关键字
	 * @param mode {safe：普通,r18:r18}
	 * @param sort {date_d:按最新排序,date:按最旧排序}
	 *
	 */
	public List<Search> search(String word,String mode, String order) {
		try {
			word = URLEncoder.encode(word, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SearchPipeLine pipeLine = new SearchPipeLine();
		String url ="https://www.pixiv.net/search.php?s_mode=s_tag&word="+word+"&order="+order+"&mode="+mode;
		Spider.create(new Search()).addUrl(url).addPipeline(pipeLine).thread(3).run();
		return pipeLine.getSearchs();
	}
}

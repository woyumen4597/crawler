package user;

import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import pipeline.UserPipeLine;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

/**
 * @author Administrator
 *
 */
public class User implements PageProcessor {
	private String id;
	private Detail detail = new Detail();
	private Illust illust = new Illust();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	public Illust getIllust() {
		return illust;
	}

	public void setIllust(Illust illust) {
		this.illust = illust;
	}

	public User() {
	}

	public User(String id) {
		this.setId(id);
		this.setDetail(new Detail(id));
	}

	public void setSite(Site site) {
		this.site = site;
	}

	private Site site = Site.me().setRetryTimes(3).setTimeOut(10000)
			.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		Json json = page.getJson();
		DocumentContext context = JsonPath.parse(json.toString());
		Detail detail = new Detail();
		detail.setName(context.read("$.user.name").toString());
		detail.setId(context.read("$.user.id").toString());
		detail.setAccount(context.read("$.user.account").toString());
		detail.setGender(context.read("$.profile.gender").toString());
		detail.setTwitter(context.read("$.profile.twitter_url").toString());
		page.putField("detail", detail);
		setDetail(detail);

	}

	
	/**
	 * @return 若用户已经设置id,则返回用户信息
	 * @throws RuntimeException 用户id未设置
	 */
	public Detail detail() throws RuntimeException{
		if (getId() == null || getId().equals("")) {
			throw new RuntimeException("Please input user_id");
		}
		return detail(getId());
	}

	/**
	 * @param id 用户id
	 * @return 用户信息
	 */
	public Detail detail(String id) {
		String url = "https://app-api.pixiv.net/v1/user/detail?user_id=" + id;
		Detail detail = new Detail();
		UserPipeLine pipeLine = new UserPipeLine(detail);
		Spider.create(new User()).addUrl(url).thread(3).addPipeline(pipeLine).run();
		return pipeLine.getDetail();
	}

	/**
	 * 根据用户id查询作品列表并下载指定数量到指定目录
	 *
	 * @param user_id 用户id
	 * @param number 下载数量
	 * @param basePath 下载目录 
	 */
	public void illust(String user_id, int number, String basePath) {
		List<String> illustIds = getDetail().getIllustIds(user_id);
		if(illustIds.size()==0) {
			throw new RuntimeException("Failed to inquery illusts!");
		}else {
			String illust_id = illustIds.get(0);
			getIllust().illust(illust_id, number, basePath);
		}
		
	}

	
	/**
	 * 若user已经设置id，则下载成功，否则抛出RuntimeException:
	 * @param number 下载数量
	 * @param basePath 下载目录 
	 * @throws RuntimeException 
	 */
	public void illust(int number, String basePath) throws RuntimeException{
		if (getId() == null | getId().equals("")) {
			throw new RuntimeException("please input user_id!");
		} else {
			String illust_id = getDetail().getIllustIds(getId()).get(0);
			getIllust().illust(illust_id, number, basePath);
		}
	}

}

package user;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

public class User implements PageProcessor {
	private String name;
	private String id;
	private String account;
	private String gender;
	private String twitter;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	private Site site = Site.me().setRetryTimes(3).setTimeOut(10000)
			.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");

	public void process(Page page) {
		Json json = page.getJson();
		DocumentContext context = JsonPath.parse(json.toString());
		User user = new User();
		user.setName(context.read("$.user.name").toString());
		user.setId(context.read("$.user.id").toString());
		user.setAccount(context.read("$.user.account").toString());
		user.setGender(context.read("$.profile.gender").toString());
		user.setTwitter(context.read("$.profile.twitter_url").toString());
		page.putField("user", user);

	}

	public Site getSite() {
		return site;
	}

}

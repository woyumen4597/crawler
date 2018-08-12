package user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * User的基本信息Detail
 *
 * @author jrc
 * @see user.User
 */
public class Detail implements PageProcessor {
    private String name;
    private String id;
    private String account;
    private String gender;
    private String twitter;
    private List<String> illust_ids;
    private Site site = Site.me().setRetryTimes(3).setTimeOut(10000)
            .setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11")
            .addHeader("Authorization","Bearer 8mMXXWT9iuwdJvsVIvQsFYDwuZpRCM")
            .addHeader("Content-Type","application/x-www-form-urlencoded")
            .addHeader("Referer","https://www.pixiv.net/member.php");

    public Detail() {
    }

    public Detail(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Detail [name=" + name + ", id=" + id + ", account=" + account + ", gender=" + gender + ", twitter="
                + twitter + "]";
    }

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

    public List<String> getIllust_ids() {
        return illust_ids;
    }

    public void setIllust_ids(List<String> illust_ids) {
        this.illust_ids = illust_ids;
    }

    public Site getSite() {
        return site;
    }


    public void process(Page page) {
        Html html = page.getHtml();
        List<String> hrefs = html.css("a[href]").all();
        List<String> ids = new ArrayList<String>();
        for (String href : hrefs) {
            if (href.contains("illust_id")) {
                href = StringUtils.substringBetween(href, "illust_id=", "\"");
                ids.add(href);
            }
        }
        setIllust_ids(ids);
    }

    public List<String> getIllustIds(String user_id) {
        Detail detail = new Detail();
        String url = "https://www.pixiv.net/member.php?id=" + user_id;
        Spider.create(detail).addUrl(url).thread(3).run();
        return detail.getIllust_ids();

    }


}

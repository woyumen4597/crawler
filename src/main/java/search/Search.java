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

/**
 * 搜索
 *
 * @author jrc
 */
public class Search implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setTimeOut(10000).setCharset("UTF-8")
            .addHeader("Host", "www.pixiv.net")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36")
            .addHeader("Authorization", "Bearer 8mMXXWT9iuwdJvsVIvQsFYDwuZpRCM")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Referer", "https://www.pixiv.net/search.php");
    private String image;
    private String type;
    private String tags;
    private String user_id;
    private List<String> images = new ArrayList<String>();
    private List<Search> results = new ArrayList<Search>();

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
        List<String> list = html.css("div.lazyloaded").all();
        for (String string : list) {
            if (string.contains("illust")) {
                Search search = new Search();
                String url = StringUtils.substringBetween(string, "data-src=\"", "\"");
                url = url.replace("150x150", "240x240");
                images.add(url);
                search.setImage(url);
                search.setTags(StringUtils.substringBetween(string, "data-tags=\"", "\""));
                search.setType(StringUtils.substringBetween(string, "data-type=\"", "\""));
                search.setUser_id(StringUtils.substringBetween(string, "data-user-id=\"", "\""));
                results.add(search);
            }
        }
        page.putField("results", results);
        page.putField("images", images);
    }

    @Override
    public String toString() {
        return "Search [image=" + image + ", type=" + type + ", tags=" + tags + ", user_id=" + user_id + "]";
    }

    public Site getSite() {
        return site;
    }

    /**
     * @param word  搜索关键字
     * @param mode  {safe：普通,r18:r18}
     * @param order {date_d:按最新排序,date:按最旧排序}
     */
    public List<Search> search(String word, String mode, String order) {
        try {
            word = URLEncoder.encode(word, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SearchPipeLine pipeLine = new SearchPipeLine();
        String url = "https://www.pixiv.net/search.php?s_mode=s_tag&word=" + word + "&order=" + order + "&mode=" + mode + "&p=";
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(url + i);
        }
        String[] urls = list.toArray(new String[5]);
        Spider.create(new Search()).addUrl(urls).addPipeline(pipeLine).thread(3).run();
        return pipeLine.getSearchs();
    }

    /**
     * @param word
     * @param mode
     * @param order
     * @return 图片url列表
     */
    public List<String> searchImages(String word, String mode, String order) {
        try {
            word = URLEncoder.encode(word, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SearchPipeLine pipeLine = new SearchPipeLine();
        String url = "https://www.pixiv.net/search.php?s_mode=s_tag&word=" + word + "&order=" + order + "&mode=" + mode + "&p=";
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(url + i);
        }
        String[] urls = list.toArray(new String[5]);
        Spider.create(new Search()).addUrl(urls).addPipeline(pipeLine).thread(3).run();
        return pipeLine.getImages();
    }
}

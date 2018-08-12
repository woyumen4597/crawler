package crawer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import exception.NotFoundException;
import pipeline.SimplePagePipeLine;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 查看pixivision
 *
 * @author jrc
 */
public class Pixivision implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setTimeOut(10000)
            .setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11")
            .addHeader("Authorization","Bearer 8mMXXWT9iuwdJvsVIvQsFYDwuZpRCM")
            .addHeader("Content-Type","application/x-www-form-urlencoded")
            .addHeader("Referer","https://www.pixiv.net");

    public void process(Page page) {
        Html html = page.getHtml();
        List<String> list = html.css("img.am__work__illust").all();
        List<String> urls = new ArrayList<String>();
        for (String string : list) {
            String url = StringUtils.substringBetween(string, "src=\"", "\"");
            url = StringUtils.replace(url, "768x1200_80", "240x480");
            urls.add(url);
        }
        page.putField("urls", urls);
    }

    public Site getSite() {
        return site;
    }

    /**
     * @param id pixivsion特辑id
     */
    public void pixivision(String id) throws NotFoundException {
        pixivision(id, SimplePagePipeLine.DEFAULT_DOWNLOAD_PATH, SimplePagePipeLine.DEFAULT_DOWNLOAD_NUMBER);
    }

    /**
     * @param id       pixivsion特辑id
     * @param basePath 图片下载的目录
     * @param number   图片下载数量
     */
    public void pixivision(String id, String basePath, int number) throws NotFoundException {
        String url = "https://www.pixivision.net/zh/a/" + id;
        SimplePagePipeLine pipeLine = new SimplePagePipeLine(basePath, number);
        Spider.create(new Pixivision()).addUrl(url).addPipeline(pipeLine).thread(3)
                .run();
        if (pipeLine.getUrls().isEmpty()) {
            throw new NotFoundException("Can't get pixivision!Maybe Id is wrong!");
        }
    }

    public List<String> pixivisionImage(String id) throws NotFoundException {
        String url = "https://www.pixivision.net/zh/a/" + id;
        SimplePagePipeLine pipeLine = new SimplePagePipeLine();
        Spider.create(new Pixivision()).addUrl(url).addPipeline(pipeLine).thread(3).run();
        if (pipeLine.getUrls().isEmpty()) {
            throw new NotFoundException("Can't get pixivision!Maybe Id is wrong!");
        }
        return pipeLine.getUrls();
    }

}

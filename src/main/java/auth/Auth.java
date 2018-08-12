package auth;


import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import exception.NotFoundException;
import pipeline.RankUrlPipeLine;
import pipeline.SimplePagePipeLine;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

/**
 * 包括r18等需要认证的内容
 *
 * @author jrc
 * 2017年10月5日下午2:22:45
 */
public class Auth implements PageProcessor {

    public static String cookie = "first_visit_datetime_pc=2018-08-12+14%3A57%3A10; p_ab_id=8; p_ab_id_2=4; __utma=235335808.596077283.1534053435.1534053435.1534053435.1; __utmc=235335808; __utmz=235335808.1534053435.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1; _ga=GA1.2.596077283.1534053435; _gid=GA1.2.1576979064.1534053445; _gat=1; login_bc=1; PHPSESSID=20921007_39a51beffdeaf1d6d3804c1ba8bc781e; device_token=74210090d0dd8bed2b649e99dbbde8e9; c_type=21; a_type=0; b_type=1; module_orders_mypage=%5B%7B%22name%22%3A%22sketch_live%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22tag_follow%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22showcase%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22user_events%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; is_sensei_service_user=1; yuid=E3czZxA53; login_ever=yes; __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=male=1^6=user_id=20921007=1^9=p_ab_id=8=1^10=p_ab_id_2=4=1^11=lang=zh=1; __utmb=235335808.2.10.1534053435; privacy_policy_agreement=1; ki_t=1534053463540%3B1534053463540%3B1534053463540%3B1%3B1;";
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
     * @param mode   {daily,weekly,male,female} 模式选择
     * @param number 下载数量
     * @throws NotFoundException
     */
    public void r18_rank(String mode, int number) throws NotFoundException {
        r18_rank(number, mode, SimplePagePipeLine.DEFAULT_DOWNLOAD_PATH);
    }

    /**
     * @param mode {daily,weekly,male,female} 模式选择
     * @throws NotFoundException
     */
    public void r18_rank(String mode) throws NotFoundException {
        r18_rank(SimplePagePipeLine.DEFAULT_DOWNLOAD_NUMBER, mode, SimplePagePipeLine.DEFAULT_DOWNLOAD_PATH);
    }

    /**
     * @param number   下载数量
     * @param mode     {daily,weekly,male,female} 模式选择
     * @param basePath 下载目录
     * @throws NotFoundException
     */
    public void r18_rank(int number, String mode, String basePath) throws NotFoundException {
        String url = "https://www.pixiv.net/ranking.php?mode=" + mode + "_r18&format=json";
        SimplePagePipeLine pipeLine = new SimplePagePipeLine(basePath, number);
        Spider.create(new Auth()).addUrl(url).thread(3).addPipeline(pipeLine).run();
        if (pipeLine.getUrls().isEmpty()) {
            throw new NotFoundException("Can't get r18 rank result!Maybe cookies is expired!");
        }
    }

    /**
     * @param mode
     * @return 图片url列表
     * @throws NotFoundException
     */
    public List<String> r18_rank_result(String mode) throws NotFoundException {
        String url = "https://www.pixiv.net/ranking.php?mode=" + mode + "_r18&format=json";
        List<String> urls = new ArrayList<>();
        RankUrlPipeLine pipeLine = new RankUrlPipeLine(urls);
        Spider.create(new Auth()).addUrl(url).thread(3).addPipeline(pipeLine).run();
        if (pipeLine.getUrls().isEmpty()) {
            throw new NotFoundException("Can't get r18 rank result!Maybe cookies is expired!");
        }
        return pipeLine.getUrls();
    }

}

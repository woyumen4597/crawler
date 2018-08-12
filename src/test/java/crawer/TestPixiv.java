package crawer;


import org.junit.Test;

import exception.NotFoundException;
import utils.PicUtils;

public class TestPixiv {
    private String basePath = "D:\\webmagic";


    @Test
    public void test1() {
        try {
            PicUtils.download(basePath, "1.jpg", "https://i.pximg.net/c/768x1200_80/img-master/img/2017/08/31/00/00/03/64702477_p0_master1200.jpg");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Test
    public void test2() {
        String small = "https://i.pximg.net/c/600x600/img-master/img/2017/07/21/19/54/59/63976302_p0_master1200.jpg";
        //String big = "https://i.pximg.net/img-original/img/2017/07/21/19/54/59/63976302_p0.jpg";
        small = small.replaceAll("/c/\\d+x\\d+", "");
        small = small.replaceAll("img-master", "img-original");
        small = small.replaceAll("_master\\d+", "");
        System.out.println(small);
    }

    @Test
    public void testException() throws NotFoundException {
        new Pixivision().pixivisionImage("ss");
    }


}

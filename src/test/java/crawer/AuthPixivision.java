package crawer;

import java.io.IOException;

import org.junit.Test;

import utils.PicUtils;

public class AuthPixivision{
	@Test
	public void test() throws IOException {
		PicUtils.download("D:\\webmagic", "2.jpg", "https://i.pximg.net/c/768x1200_80/img-master/img/2017/05/01/00/24/51/62665989_p0_master1200.jpg");
	}
	
	@Test
	public void pixivision() {
		new Pixivision().pixivision("2810");
	}
	
	@Test
	public void pixivision2() {
		new Pixivision().pixivision("2840","D:\\webmagic",5);
	}
}
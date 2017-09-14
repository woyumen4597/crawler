package auth;

import org.junit.Test;

public class AuthTest {
	@Test
	public void r18_daily() throws Exception {
		new Auth().r18_rank(5, "daily", "D:\\webmagic");
	}

	@Test
	public void r18_weekly() throws Exception {
		new Auth().r18_rank("weekly");
	}

	@Test
	public void r18_male() throws Exception {
		new Auth().r18_rank("male");
	}

	@Test
	public void r18_female() throws Exception {
		new Auth().r18_rank("female");
	}
}

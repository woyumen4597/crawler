package crawer;

import java.util.List;

import org.junit.Test;

import search.Search;
import user.Detail;

public class AppTest {
	@Test
	public void test1() throws Exception {
		App app = new App();
		Detail detail = app.user("27517").detail();
		System.out.println(detail);
	}

	@Test
	public void test2() throws Exception {
		App app = new App();
		app.rank().rank("daily");
	}

	@Test
	public void test3() throws Exception {
		App app = new App();
		app.user().illust("4755415", 5, "D:\\webmagic");
	}

	@Test
	public void test4() throws Exception {
		App app = new App();
		app.user("4755415").illust(5, "D:\\webmagic");
	}

	@Test
	public void test5() throws Exception {
		App app = new App();
		List<Search> list = app.search().search("水着", "safe", "date_d");
		for (Search search2 : list) {
			System.out.println(search2);
		}
	}

	@Test
	public void test6() {
		App app = new App();
		app.pixivision().pixivision("2810");
	}
}

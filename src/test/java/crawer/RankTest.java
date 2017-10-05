package crawer;

import java.util.List;

import org.junit.Test;

public class RankTest {
	@Test
	public void rank_week() throws Exception{
		new Rank().rank("weekly", "D:\\webmagic", 5);
	}

	@Test
	public void rank_daily() throws Exception{
		new Rank().rank("daily");
	}

	@Test
	public void rank_monthly() throws Exception{
		new Rank().rank("monthly");
	}

	@Test
	public void rank_male() throws Exception{
		new Rank().rank("male");
	}

	@Test
	public void rank_female() throws Exception{
		new Rank().rank("female");
	}

	@Test
	public void rank_rookie() throws Exception{
		new Rank().rank("rookie");
	}

	@Test
	public void rank_original() throws Exception{
		new Rank().rank("original");
	}

	@Test
	public void rank_illust_daily() throws Exception{
		new Rank().rank("daily", "illust");
	}

	@Test
	public void rank_ugoira_daily() throws Exception{
		new Rank().rank("daily", "ugoira");
	}

	@Test
	public void rank_manga_daily() throws Exception{
		new Rank().rank("daily", "manga");
	}

	@Test
	public void rank_r18() throws Exception{
		new Rank().rank("daily_r18");
	}
	
	@Test
	public void rank_urls() throws Exception{	
		List<String> list = new Rank().rankResult("daily");
		for (String string : list) {
			System.out.println(string);
		}
	}
	
}

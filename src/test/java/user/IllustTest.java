package user;

import java.util.List;

import org.junit.Test;


public class IllustTest {
	@Test
	public void illust(){
		new Illust().illust("61723006", 5, "D:\\webmagic");
	}
	
	@Test
	public void illust2() {
		Illust illust = new Illust();
		List<String> list = illust.illust("61723006", 5);
		for (String string : list) {
			System.out.println(string);
		}
	}
}

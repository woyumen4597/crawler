package utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DuplicateReomverTest {
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("aa");
		list.add("ba");
		list.add("ba");
		list.add("ada");
		list.add("afa");
		list = DuplicateRemover.removeDuplicate(list);
		for (String string : list) {
			System.out.println(string);
		}
	}
}

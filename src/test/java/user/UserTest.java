package user;

import java.util.List;

import org.junit.Test;

import exception.UserIdEmptyException;

public class UserTest {
	@Test
	public void user_detail() {
		new User().detail("27517");
	}

	@Test
	public void user_detail2() throws UserIdEmptyException {
		new User("27517").detail();
	}

	@Test
	public void user_exception() throws UserIdEmptyException  {
		new User().detail();
	}

	@Test
	public void user() {
		User user = new User();
		Detail detail = user.detail("27517");
		System.out.println(detail);
	}

	@Test
	public void illust_id() {
		Detail detail = new Detail();
		List<String> ids = detail.getIllustIds("4755415");
		for (String id : ids) {
			System.out.println(id);
		}
	}

	@Test
	public void user_illust() {
		User user = new User();
		user.illust("4755415", 5, "D:\\webmagic");
	}

	@Test
	public void user_illust2() {
		User user = new User("4755415");
		user.illust(5, "D:\\webmagic");
	}

}

package user;

import org.junit.Test;

public class UserTest {
	@Test
	public void user_detail(){
		new User().detail("27517");
	}

	@Test
	public void user_detail2(){
		new User("27517").detail();
	}

	@Test
	public void user_exception(){
		new User().detail();
	}

	@Test
	public void user(){
		User user = new User();
		Detail detail = user.detail("27517");
		System.out.println(detail);
	}

}

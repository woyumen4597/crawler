package crawer;

import auth.Auth;
import search.Search;
import user.User;

public class App {
	public User user() {
		return new User();
	}
	public Rank rank() {
		return new Rank();
	}
	public User user(String user_id) {
		return new User(user_id);
	}
	public Auth auth() {
		return new Auth();
	}
	public Search search() {
		return new Search();
	}
	public Pixivision pixivision() {
		return new Pixivision();
	}
}

package crawer;

import auth.Auth;
import search.Search;
import user.User;

/**
 * 主类
 *
 * @author jrc
 */
public class App {
    /**
     * @return User
     * @see user.User
     */
    public User user() {
        return new User();
    }

    /**
     * @return Rank
     * @see crawer.Rank
     */
    public Rank rank() {
        return new Rank();
    }

    /**
     * @param user_id 用户id
     * @return User(user_id)
     * @see user.User
     */
    public User user(String user_id) {
        return new User(user_id);
    }

    /**
     * @return Auth
     * @see auth.Auth
     */
    public Auth auth() {
        return new Auth();
    }

    /**
     * @return Search
     * @see search.Search
     */
    public Search search() {
        return new Search();
    }

    /**
     * @return Pixivision
     * @see crawer.Pixivision
     */
    public Pixivision pixivision() {
        return new Pixivision();
    }
}

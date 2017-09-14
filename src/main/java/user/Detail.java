package user;

public class Detail {
	private String name;
	@Override
	public String toString() {
		return "Detail [name=" + name + ", id=" + id + ", account=" + account + ", gender=" + gender + ", twitter="
				+ twitter + "]";
	}
	private String id;
	private String account;
	private String gender;
	private String twitter;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
}

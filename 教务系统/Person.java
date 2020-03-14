
public abstract class Person {
	private String strUsername;
	private String strPassword;
	public Person(String inUsername,String inPassword) {
		this.strUsername=inUsername;
		this.strPassword=inPassword;
	}
	public abstract boolean Login(String username,String password);
	public String getStrUsername() {
		return strUsername;
	}
	public void setStrUsername(String strUsername) {
		this.strUsername = strUsername;
	}
	public String getStrPassword() {
		return strPassword;
	}
	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}
}

package ∂‡Ã¨;

public abstract class Person {
	private String strUername;
	private String strPassword;
	public Person(String inUername,String inPassword )
	{
		this.strUername=inUername;
		this.strPassword=inPassword;
	}
	public abstract boolean Login(String uername,String password);
	public String getStrUername() {
		return strUername;
	}
	public void setStrUername(String strUername) {
		this.strUername = strUername;
	}
	public String getStrPassword() {
		return strPassword;
	}
	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}
}

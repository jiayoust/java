package ְ������;

public class Staff {
	private String strname;
	private String strsex;
	private int inAge;
	public Staff(String inName,String inSex,int inAge) {
		this.strname=inName;
		this.strsex=inSex;
		this.inAge=inAge;	
	}
	public void Print(){
		System.out.println("ְ����:"+getStrname()+"   �Ա�: "+getStrsex()+"   ����:"+getInAge());
	}
	public String getStrname() {
		return strname;
	}
	public void setStrname(String strname) {
		this.strname = strname;
	}
	public String getStrsex() {
		return strsex;
	}
	public void setStrsex(String strsex) {
		this.strsex = strsex;
	}
	public int getInAge() {
		return inAge;
	}
	public void setInAge(int inAge) {
		this.inAge = inAge;
	}
	
}
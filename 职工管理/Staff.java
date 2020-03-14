package 职工管理;

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
		System.out.println("职工名:"+getStrname()+"   性别: "+getStrsex()+"   年龄:"+getInAge());
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
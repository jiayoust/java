package ְ������;

public class Employer extends Staff {
	
	private String strDuty;
	private int Salary;
	@SuppressWarnings("unused")
	private String strname;
	@SuppressWarnings("unused")
	private String strsex;
	@SuppressWarnings("unused")
	private String inAge;
	public Employer(String inName, String inSex, int inAge,String strDuty,int Salary) {
		super(inName,inSex,inAge);
		this.Salary=Salary;
		this.strDuty=strDuty;
	}
	public void Print()
	{
		
		System.out.println("ְ����:"+getStrname()+"   �Ա�:"+getStrsex()+"   ����:"+getInAge()+"   ְλ:"+getStrDuty()+"   ��н:"+getSalary());
		
		
	
		// TODO Auto-generated constructor stub
	}
	public String getStrDuty() {
		return strDuty;
	}
	public void setStrDuty(String strDuty) {
		this.strDuty = strDuty;
	}
	public int getSalary() {
		return Salary;
	}
	public void setSalary(int salary) {
		Salary = salary;
	}
	
	

}

package ְ������;

public class Manage extends Staff{
	private  String strPosition;
	private  int strAnnualsalary;
	public Manage(String inName, String inSex, int inAge,String inPosition,int inAnnualsalary) {
		super(inName, inSex, inAge);
		this.strPosition=inPosition;
		this.strAnnualsalary=inAnnualsalary;
		
		
		// TODO Auto-generated constructor stub
	}
	public void Print()
	{
		System.out.println("ְ����:"+getStrname()+"   �Ա�:"+getStrsex()+"   ����:"+getInAge()+"   ְλ:"+getStrPosition()+"   ��н:"+getStrAnnualsalary());
		
	
		// TODO Auto-generated constructor stub
	}
	public String getStrPosition() {
		return strPosition;
	}
	public void setStrPosition(String strPosition) {
		this.strPosition = strPosition;
	}
	public int getStrAnnualsalary() {
		return strAnnualsalary;
	}
	public void setStrAnnualsalary(int strAnnualsalary) {
		this.strAnnualsalary = strAnnualsalary;
	}
	
	
}

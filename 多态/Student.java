package ��̬;

public class Student extends Person {
	private boolean ture;
	public Student(String inName, String inSno) {
		super(inName, inSno);
		// TODO Auto-generated constructor stub
	}
	public boolean Login(String x,String y)
	{
		if(this.getStrUername().equals(x)&&this.getStrPassword().equals(y))
		{
			System.out.println("��ӭ"+this.getStrUername()+"ѧ�����٣�");
			return ture;
		}
		return false;
	}
}
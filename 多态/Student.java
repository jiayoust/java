package 多态;

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
			System.out.println("欢迎"+this.getStrUername()+"学生光临！");
			return ture;
		}
		return false;
	}
}
package 多态;

public class Teacher extends Person {
	public Teacher(String inUername, String inPassword) {
		super(inUername, inPassword);
		// TODO Auto-generated constructor stub
	} 
	public boolean Login(String x,String y)
	{
		if(this.getStrUername().equals(x)&&this.getStrPassword().equals(y))
		{
			System.out.println("欢迎"+this.getStrUername()+"老师光临！");
		}
		return false;
	}
	
}
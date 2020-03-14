package 多态;

public class Administrator extends Person {


	public Administrator(String inUername, String inPassword) {
		super(inUername, inPassword);
	}


		public boolean Login(String x, String y) {
		// TODO Auto-generated method stub
		if(this.getStrUername().equals(x)&&this.getStrPassword().equals(y))
		{
			System.out.println("欢迎"+this.getStrUername()+"管理员光临！");
			return true;
			
		}
		return false;
	}



}

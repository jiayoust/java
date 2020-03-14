
public class Student extends Person{

	private static final Boolean True = null;

	public Student(String inUsername, String inPassword) {
		super(inUsername, inPassword);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Login(String username,String password) {
		// TODO Auto-generated method stub
		if(super.getStrUsername().equals(username)&&super.getStrPassword().equals(password)){
			System.out.println(super.getStrUsername()+"----->> Student can login !");
			return true;
		}
		return false;
	}
	
}

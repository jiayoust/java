
public class Teacher extends Person{

	public Teacher(String inUsername, String inPassword) {
		super(inUsername, inPassword);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Login(String username, String password) {
		// TODO Auto-generated method stub
		if(this.getStrUsername().equals(username)&&this.getStrPassword().equals(password)){
			System.out.println(this.getStrUsername()+"----->> Teacher can login !");
			return true;
	    }
		return false;
	}

}

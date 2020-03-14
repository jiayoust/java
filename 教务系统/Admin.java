
public class Admin extends Person{

	public Admin(String inUsername, String inPassword) {
		super(inUsername, inPassword);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Login(String username, String password) {
		// TODO Auto-generated method stub
		if(this.getStrUsername().equals(username)&&this.getStrPassword().equals(password)){
			System.out.println(this.getStrUsername()+"----->> Admin can login !");
			return true;
	    }
		return false;
	}

}

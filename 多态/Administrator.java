package ��̬;

public class Administrator extends Person {


	public Administrator(String inUername, String inPassword) {
		super(inUername, inPassword);
	}


		public boolean Login(String x, String y) {
		// TODO Auto-generated method stub
		if(this.getStrUername().equals(x)&&this.getStrPassword().equals(y))
		{
			System.out.println("��ӭ"+this.getStrUername()+"����Ա���٣�");
			return true;
			
		}
		return false;
	}



}

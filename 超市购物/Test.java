package ³¬ÊÐ¹ºÎï;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Customer cs=new Customer(1000);
		Cashier cash=new Cashier(100);
		Good gd=new Good("dd",23,3);
		cs.add(gd);
		Good gd2=new Good("kjk",34,5);
		cs.add(gd2);
		cash.cash(cs);
		cash.print(cs);
		System.out.println("Customer's money:"+cs.getfMoney());
	}

}

}

package ³¬ÊÐ¹ºÎï;

public class Cashier {
	private float fMoney;
	public Cashier(float inMoney)
	{
		this.fMoney=inMoney;
	}
	public float getfMoney(){
		return fMoney;
	}
	public void setfMoney(float fMoney){
		this.fMoney=fMoney;
	}
	public void cash(Customer cs)
	{
		float countMoney;
		countMoney=cs.getSc().count();
		this.fMoney+=countMoney;
		cs.setfMoney(cs.getfMoney()-countMoney);
	}
	public void print(Customer cs)
	{
		ShopCar sc=cs.getSc();
		for(int i=0;i<sc.getGoodCar().size();i++)
		{
			Good gd=sc.getGoodCar().get(i);
			System.out.println("Name"+gd);
		}
	}
}


}

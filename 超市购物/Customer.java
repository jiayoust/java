package ³¬ÊÐ¹ºÎï;

public class Customer {
	private float fMoney;
	private ShopCar sc;
	public Customer(float inMoney)
	{
		this.fMoney=inMoney;
		sc=new ShopCar();
	}
	public void add(Good gd)
	{
		this.sc.add(gd);
	}
	public void del(Good gd)
	{
		this.sc.del(gd);
	}
	public float getfMoney() {
		return fMoney;
	}
	public void setfMoney(float fMoney) {
		this.fMoney = fMoney;
	}
	public ShopCar getSc() {
		return sc;
	}
	public void setSc(ShopCar sc) {
		this.sc = sc;
	}
}



}

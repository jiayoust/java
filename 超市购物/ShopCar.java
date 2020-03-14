package ³¬ÊÐ¹ºÎï;
import java.util.ArrayList;
import java.util.List;

public class ShopCar {
	private List<Good> goodCar;
	public ShopCar()
	{
		goodCar=new ArrayList<Good>();
	}
	public void add(Good gd)
	{
		this.goodCar.add(gd);
	}
	public void del(Good gd)
	{
		for(int i=0;i<this.goodCar.size();i++)
		{	
		}	
	}
	public float count()
	{
		float sum=0;
		for(int i=0;i<this.goodCar.size();i++)
		{
			Good gd=this.goodCar.get(i);
			sum+=gd.getfPrice()*gd.getiCount();
		}
		return sum;
		
	}
	public List<Good> getGoodCar() {
		return goodCar;
	}
	public void setGoodCar(List<Good> goodCar) {
		this.goodCar = goodCar;
	}

}

	

}

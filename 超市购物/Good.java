package 超市购物;

public class Good {
	package 超市系统;

	public class Good {
		private String strName;
		private float fPrice;
		private int iCount;
		public Good(String inName,float inPrice,int inCount)
		{
			this.strName=inName;
			this.fPrice=inPrice;
			this.iCount=inCount;
		}
		public String getStrName() {
			return strName;
		}
		public void setStrName(String strName) {
			this.strName = strName;
		}
		public float getfPrice() {
			return fPrice;
		}
		public void setfPrice(float fPrice) {
			this.fPrice = fPrice;
		}
		public int getiCount() {
			return iCount;
		}
		public void setiCount(int iCount) {
			this.iCount = iCount;
		}
	}


}

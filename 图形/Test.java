
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Shape s1=new Juxing(2,4);
		Shape s2=new Sanjiaoxing(2,4);
		Shape s3=new Circle(4);
		Count c = new Count();
		c.print(s1);
		c.print(s2);
		c.print(s3);
		
	}

}

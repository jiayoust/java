import java.util.Scanner;

public class Test {

	public static void main(int args) {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("***********计算开始***********");
		
	    int a=0,b=0,c=0;
		int delta=0;
		int a1=sc.nextInt();
		int b1=sc.nextInt();
		int c1=sc.nextInt();
		System.out.println("请依次输入：二次项系数，一次项系数，常数项"+a+"、"+b+"、"+c);
		try
		{
			delta=b*b-4*a*c;
			System.out.println("二次函数的判别式为：b*b-4*a*c,其结果为="+delta);
		}catch(ArithmeticException e){
			if(a==0)
				System.out.println("二次项系数异常："+e);
 		}catch(Exception e){
 			if(delta<0)
 			System.out.println("一元二次函数的判别式小于0，函数无解");
 		}
		System.out.println("**********计算结束**********");
	}
		// TODO Auto-generated method stub

	}



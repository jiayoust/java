import java.util.Scanner;

public class Test {

	public static void main(int args) {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("***********���㿪ʼ***********");
		
	    int a=0,b=0,c=0;
		int delta=0;
		int a1=sc.nextInt();
		int b1=sc.nextInt();
		int c1=sc.nextInt();
		System.out.println("���������룺������ϵ����һ����ϵ����������"+a+"��"+b+"��"+c);
		try
		{
			delta=b*b-4*a*c;
			System.out.println("���κ������б�ʽΪ��b*b-4*a*c,����Ϊ="+delta);
		}catch(ArithmeticException e){
			if(a==0)
				System.out.println("������ϵ���쳣��"+e);
 		}catch(Exception e){
 			if(delta<0)
 			System.out.println("һԪ���κ������б�ʽС��0�������޽�");
 		}
		System.out.println("**********�������**********");
	}
		// TODO Auto-generated method stub

	}



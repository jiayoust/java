package ��̬;
import java.util.Scanner;
public class Test{
	

	/**
	 * @param args
	 * @param uername 
	 */
	public static void main(String[] args) 
	{

Person  per[] = new Person[6];
per[0] = new Teacher("zhang","zhang");
per[1] = new Teacher("li","zhang");

per[2] = new Student("wang","zhang");
per[3] = new Student("zheng","zhang");

per[4] = new Administrator("admin","zhang");
per[5] = new Student("wu","zhang");

String uername1,password;
@SuppressWarnings("resource")
Scanner sc = new Scanner(System.in);
System.out.println("�ֱ������û���������");
uername1 =  sc.nextLine();
password = sc.nextLine();
Boolean flag=false;
for(int i=0;i<6;i++)
{

	if(per[i].Login(uername1,password))
	{
		Boolean ture = null;
		flag=ture;
		break;
	}
}
	if (flag==false)
	{
		System.out.println("�˺������������");
		
	}
	}
}

	

    
  
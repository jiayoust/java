import java.util.Scanner;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person  per[] = new Person[6];
		per[0] = new Teacher("zhang","zhang");
		per[1] = new Teacher("li","zhang");

		per[2] = new Student("wang","zhang");
		per[3] = new Student("zheng","zhang");

		per[4] = new Admin("admin","zhang");
		per[5] = new Admin ("wu","zhang");

		String user,pass;
		Scanner Sc = new Scanner(System.in);
		user = Sc.nextLine();
		pass = Sc.nextLine();
		Boolean flag=false ;
		for(int i=0;i<6;i++){
		   if(per[i].Login(user,pass)) {			   
			   flag=true;
			   break;
		   }		   
		}
		if(flag==false) {
			System.out.println("ÕËºÅ»òÃÜÂë´íÎó!");			
		}

	}

}

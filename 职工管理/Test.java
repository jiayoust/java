package 职工管理;

//import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Staff s[] =new Staff[3];
		s[0]=new Staff("小明 ","gilr",24);
		s[1]=new Manage("帅田","gilr",19,"BOSS",100000);
		s[2]=new Employer("郑晨聪","boy",18,"职员",35000);
		
		for(int i=0;i<3;i++) {
			s[i].Print();
		}
		
	}


/*    Scanner sc=new Scanner(System.in);
	    System.out.println("请输入你要输入的员工姓名，性别，年龄,部门,年薪");
	    String inName=sc.nextLine();
	    String inSex=sc.nextLine();
	    int inAge=sc.nextInt();
	    String strDuty=sc.nextLine();
        int Salary=sc.nextInt();*/
	    
	    
	    
	    
		// TODO Auto-generated method stub

	}



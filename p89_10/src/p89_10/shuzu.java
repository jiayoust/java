package p89_10;
import java.util.Scanner;
public class shuzu {
	@SuppressWarnings("unused")
	private static Scanner sc;
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		
		@SuppressWarnings({ "resource" })
		Scanner scanner=new Scanner(System.in);
		int intArr[]={12,13,14,15,16,17};
		System.out.println("请输入插入的数：");
		int insertIndex=0;
		for(int i=0;i<intArr.length;i++)
		{
			int intsertNum = 0;
			if(intsertNum<intArr[i])
			{
				
				insertIndex=i;
				break;
			}
		}
		int intArr1[]=new int[intArr.length+1];
		for(int i=0;i<intArr1.length;i++)
		{
			int intsertNum;
			if(i==insertIndex)
				intArr1[i]=insertIndex;
			if(i+1<intArr1.length)
				intArr1[i+1]=intArr[i];
	    	else
		  {
			  intArr1[i]=intArr[i];
		  }
	    }
	for(int i:intArr1)
	{
		System.out.println(i+"");
	}	// TODO Auto-generated method stub
}
}





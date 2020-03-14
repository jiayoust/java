package p89_9;
import java.util.Scanner;
public class again {
	private static Scanner sc;
	public static void main(String[] args)
	{
		int score[]=new int[10];
			int max=0;
			sc = new Scanner(System.in);
			System.out.println("请输入十个数据：");
			  for(int i=0;i<10;i++){
				  score[i]=(int)sc.nextDouble();
			  } 
			  for(int j=0;j<9;j++)
				   for(int i=0;i<9-j;i++)
				    if(score[i+1]>score[i])
				    {
				    	max=score[i];
				     score[i]=score[i+1];
				     score[i+1]=max;
				     }
				  for(int i=0;i<10;i++)
				  {	  
					System.out.println(score[i]);
				  }
		}

	}

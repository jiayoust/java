package p87_7;

public class ni {

	public static void main(String[] args) {
		int score[]={91,92,93,94,95,96,97,98,99,95};
		int max,i;
		max=score[0];
		for(i=0;i<=9;i++)
		{
			if(score[i]>max)
			{
				max=score[i];
			}
				
		}
		System.out.println("最高分是="+max);
		// TODO Auto-generated method stub

	}

}

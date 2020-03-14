
public class mi {

	public static void main(String[] args)
	{
		int a[]=new int[30],b[]=new int[10];
		int i;
		for(i=0;i<a.length;i++)
		{
			a[i]=(int)(Math.random()*10);
			System.out.println(a[i]+"、");
		}
		for(i=0;i<a.length;i++)
		{
			b[a[i]]++;
		}
		System.out.println();
		for(i=0;i<b.length;i++)
		{
			System.out.println(i+"出现了"+b[i]+"次");
		}
		
			
			
		    
		}
		// TODO Auto-generated method stub

	}



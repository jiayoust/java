package ���߳�;

import java.util.Scanner;
public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		int Money;
		Seller st = new Seller();
		Scanner sc = new Scanner(System.in);
		for(i =1;i<10;i++)
		{
			System.out.println("Num." + i + "please input the money:");  //��ͬѧ��˼��ΪʲôҪ������ط�����˿ʹ���Ǯ����û�и��õķ��������Ƕ���������
			Money = sc.nextInt();
			String strInfo = "Num." + i + ":" + Money;
			new Thread(st, strInfo).start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

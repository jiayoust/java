package ͼ��ݽ���;

public class Text {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student st=new Student("С��","12346789");
		Administrator Adm=new Administrator("С��");
		Book bk1=new Book("һǧ��һҹ","�",23156,4);
		Book bk2=new Book("��ͽ��ͯ��","С��",75458,5);
		Book bk3=new Book("��","С��",17895,1);
		st.add(bk1);
		st.add(bk2);
		st.add(bk3);
		Adm.print(st);
		System.out.println("Student's name:"+st.getStuName()+"  Student's Sno:"+st.getSno());
		System.out.println("Administrator's name:"+Adm.getAdmName());
		
	

	}
	


}

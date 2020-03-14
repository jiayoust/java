package 图书馆借书;

public class Text {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student st=new Student("小明","12346789");
		Administrator Adm=new Administrator("小红");
		Book bk1=new Book("一千零一夜","李华",23156,4);
		Book bk2=new Book("安徒生童话","小兰",75458,5);
		Book bk3=new Book("简爱","小刘",17895,1);
		st.add(bk1);
		st.add(bk2);
		st.add(bk3);
		Adm.print(st);
		System.out.println("Student's name:"+st.getStuName()+"  Student's Sno:"+st.getSno());
		System.out.println("Administrator's name:"+Adm.getAdmName());
		
	

	}
	


}

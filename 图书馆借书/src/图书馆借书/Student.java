package Õº Èπ›ΩË È;

public class Student {
	private String stuName;
	private String Sno;
	private CustomerCar bc;
	public Student(String instuName,String inSno)
	{
		this.stuName=instuName;
		this.Sno=inSno;
		bc=new CustomerCar();
	}
	public void add(Book bk)
	{
		this.bc.add(bk);
	}
	public void del(Book bk)
	{
		this.bc.del(bk);
	}
	
	
	
	
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getSno() {
		return Sno;
	}
	public void setSno(String sno) {
		Sno = sno;
	}
	public CustomerCar getBc() {
		return bc;
	}
	public void setBc(CustomerCar bc) {
		this.bc = bc;
	}

}



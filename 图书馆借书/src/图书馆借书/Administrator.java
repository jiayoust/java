package Õº Èπ›ΩË È;

public class Administrator {
	private String AdmName;
public Administrator(String inAdmName)
{
	this.AdmName=inAdmName;
}
public String getAdmName() {
	return AdmName;
}
public void setAdmName(String admName) {
	AdmName = admName;
}
public void print(Student st)
{
	CustomerCar bc=st.getBc();
	for(int i=0;i<bc.getBookCar().size();i++)
	{
		Book bk=bc.getBookCar().get(i);
		System.out.println("Name:  "+bk.getStrName()+"  Author:  "   +bk.getStrAuthor()
		+"  bookNumber:  "   +bk.getBookNumber()+"  Count  "   +bk.getCount());
		
	}
	
}
}

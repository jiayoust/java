package Õº Èπ›ΩË È;
import java.util.ArrayList;
import java.util.List;
public class CustomerCar {
	private List<Book> bookCar;
	public CustomerCar()
	{
		bookCar=new ArrayList<Book>();
	}
	public void add(Book bk)
	{
		this.bookCar.add(bk);
	}
	public void del(Book bk)
	{
		Book CurBk;
		for(int i=0;i<bookCar.size();i++)
		{
		CurBk=this.bookCar.get(i);
		if(CurBk.getStrName().equals(bk.getStrName()))
		{
			this.bookCar.remove(i);
		}	
		}		
	}
	public List<Book> getBookCar() {
		return bookCar;
	}
	public void setBookCar(List<Book> bookCar) {
		this.bookCar = bookCar;
	}

}




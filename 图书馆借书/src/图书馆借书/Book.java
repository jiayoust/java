package Õº Èπ›ΩË È;

public class Book {private String strName;
private String strAuthor;
private int Count;
private int BookNumber;
public Book(String inName,String inAuthor,int inNumber,int inCount)
{
	this.BookNumber=inNumber;
	this.strAuthor=inAuthor;
	this.strName=inName;
	this.Count=inCount;
	
}
public String getStrName() {
	return strName;
}
public void setStrName(String strName) {
	this.strName = strName;
}
public String getStrAuthor() {
	return strAuthor;
}
public int getCount() {
	return Count;
}
public void setCount(int count) {
	Count = count;
}
public void setStrAuthor(String strAuthor) {
	this.strAuthor = strAuthor;
}
public int getBookNumber() {
	return BookNumber;
}
public void setBookNumber(int bookNumber) {
	BookNumber = bookNumber;
}



}



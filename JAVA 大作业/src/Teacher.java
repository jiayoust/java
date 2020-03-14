import java.util.ArrayList;

import java.util.List;
public class Teacher {
	
	private int Id;
	private String UseName;
	private String PassWord;
	private String Name;
	private String Age;
	private String Sex;
	private String Subject;
	private String Zhicheng;
	private List<Course>TCourse;
	public Teacher()
	{
		this.TCourse=new ArrayList<Course>();
	}

	public Teacher( int inId,String inName, String inAge, String inSex, 
			String inSubject,String inZhicheng) {
		this();
		this.Id=inId;
		this.Name=inName;
		this.Age=inAge;
		this.Sex=inSex;
		this.Subject=inSubject;
		this.Zhicheng=inZhicheng;
		
		// TODO Auto-generated constructor stub
	}
	

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUseName() {
		return UseName;
	}

	public void setUseName(String useName) {
		UseName = useName;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAge() {
		return Age;
	}

	public void setAge(String age) {
		Age = age;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getZhicheng() {
		return Zhicheng;
	}

	public void setZhicheng(String zhicheng) {
		this.Zhicheng = zhicheng;
	}

	public List<Course> getTCourse() {
		return TCourse;
	}

	public void setTCourse(List<Course> tCourse) {
		TCourse = tCourse;
	}
	public String toString()
	{
		return "/n教师序号: "+this.Id+", 教师名: "+this.Name+", 年龄: "+this.Age+", 性别: "+
				this.Sex+", \n专业 : "+this.Subject+", 职称: "+this.Zhicheng;

	}
	
	public boolean Login(String x,String y)
	{
		if(this.UseName.equals(x)&&this.PassWord.equals(y))
		{
			System.out.println("欢迎"+this.UseName+"教师光临！");
			return true;
		}
		return false;
	}

}









import java.util.ArrayList;

import java.util.List;
public class Student {
	private String UseName;
		private String PassWord;
		private int Id;
		private String Name;
		private String Age;
		private String Sex;
		private String Subject;
		private String Classes;
		private String Sno;
		private String Email;
		private String PhoneNumber;
		private String QQ;
		private List<Course> PCourses;
		public Student()
		{
			this.PCourses=new ArrayList<Course>();
		}

		public Student(int inId, String inName, String inAge, String inSex,
				String inSubject,String inClasses,String inSno,String inEmail,
				String inPhoneNumber,String inQQ){
			this();
			this.Id=inId;
			this.Name=inName;
			this.Age=inAge;
			this.Sex=inSex;
			this.Subject=inSubject;
			this.Classes=inClasses;
			this.Sno=inSno;
			this.Email=inEmail;
			this.PhoneNumber=inPhoneNumber;
			this.QQ=inQQ;

			
		}
		
		public boolean Login(String x,String y)
		{
			if(this.UseName.equals(x)&&this.PassWord.equals(y))
			{
				System.out.println("欢迎"+this.UseName+"学生光临！");
				return true;
			}
			return false;
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



		public int getId() {
			return Id;
		}

		public void setId(int id) {
			Id = id;
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

		public void setPCourses(List<Course> pCourses) {
			PCourses = pCourses;
		}

		public String getClasses() {
			return Classes;
		}

		public void setClasses(String classes1) {
			Classes = classes1;
		}

		public String getSno() {
			return Sno;
		}

		public void setSno(String sno) {
			Sno = sno;
		}

		public List<Course> getPCourses() {
			return PCourses;
		}

		public void setAllCourses(List<Course> PCourses) {
			this.PCourses = PCourses;
		}

		
		
		public String getEmail() {
			return Email;
		}

		public void setEmail(String email) {
			Email = email;
		}

		public String getPhoneNumber() {
			return PhoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			PhoneNumber = phoneNumber;
		}

		public String getQQ() {
			return QQ;
		}

		public void setQQ(String qQ) {
			QQ = qQ;
		}

		public  String toString()
		{
			return "\n学生序号: "+this.Id+"; \n学生姓名: "+this.Name+"; 年龄: "+this.Age
			+"; 性别: "+this.Sex+"; 专业: "+this.Subject+
			"; 班级: "+this.Classes+"\n 学号: "+this.Sno+"; 邮箱: "+this.Email+
			"; 电话: "+this.PhoneNumber+"; qq: "+this.QQ;
		}
		

	}




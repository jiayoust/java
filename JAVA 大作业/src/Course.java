import java.util.ArrayList;
import java.util.List;
public class Course {
	private String Teacher;
	private String Name;
	private int Id;
	private String Time;//课程学时
	private String Demand;//课程要求
	private String Details;//课程要讲的内容
	private String Exam;//考核方式
	private List<Student> allStudents;
	public Course(){
		this.allStudents=new ArrayList<Student>();
	}
	public Course(String inName,String inTeacher,int inId,String inTime
			,String inDemand,String inDetails,String inExam){
		this();
		this.Name=inName;
		this.Teacher=inTeacher;
		this.Id=inId;
		this.Time=inTime;
		this.Demand=inDemand;
		this.Details=inDetails;
		this.Exam=inExam;
		
	}
	
	public String getTeacher() {
		return Teacher;
	}
	public void setTeacher(String teacher) {
		Teacher = teacher;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		this.Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<Student> getAllStudents() {
		return allStudents;
	}
	public void setAllStudents(List<Student> allStudents) {
		this.allStudents = allStudents;
	}
	

	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getDemand() {
		return Demand;
	}
	public void setDemand(String demand) {
		Demand = demand;
	}
	public String getDetails() {
		return Details;
	}
	public void setDetails(String details) {
		Details = details;
	}
	public String getExam() {
		return Exam;
	}
	public void setExam(String exam) {
		Exam = exam;
	}
	public String toString()
	{
	
		return "\n*课程号为: "+this.Id+"\n  *课程名称 : "+this.Name+",  *老师为: "+this.Teacher
				+",  *课程学时: "+this.Time+",  *课程要求: "+this.Demand+",  *课程要讲的内容: "
				+this.Details+"   *考核方式: "+this.Exam;
	}
	}
	





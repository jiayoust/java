import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import java.io.File;
public class test {
	private static Scanner console = new Scanner(System.in);  //键盘输入
    private static String managerPass = "12345";  //管理员登陆密码为12345
    private static Connection conn = DBUtil.getConnection();//连接数据库
    private static List<Course> stuCourseList = new ArrayList<Course>(); 
    private static List<Course> teaCourseList = new ArrayList<Course>();//定义集合保存课程 
    private static Map<Integer, Course> courseList = new HashMap<Integer, Course>(); //map接口 无序存放，key（即现在的integer）不允许重复 
    
    public static void main(String[] args) {
       
        managerTest();
    }
    
    
    
    
    /*学生*/ 
    public static void showMenuStu() {  
        System.out.println("*************************学生菜单*************************");  
        System.out.println("1.注册学生");
        System.out.println("2.学生登陆");
        System.out.println("3.查看课程列表");
        System.out.println("4.选课");  
        System.out.println("5.修改学生信息(密码)"); 
        System.out.println("6.查看老师开课情况");
        System.out.println("7.EXIT");  
    } 
    
    
    
    
    /*管理员*/ 
    public static void showMenuMa(){
    	System.out.println("*************************管理员菜单*************************");  
        System.out.println("1.审核课程");
        System.out.println("2.查看学生信息列表");
        System.out.println("3.查看课程信息列表");
        System.out.println("4.EXIT");  
  
    	
    }
    
    
    
    
    /*教师*/ 
    
    public static void showMenuTea(){
    	System.out.println("*************************教师菜单*************************"); 
        System.out.println("1.开设课程"); 
        System.out.println("2.查看学生信息列表"); 
        System.out.println("3.查看课程信息列表");
        System.out.println("4.教师登陆");
        System.out.println("5.修改登录密码");
        System.out.println("6.注册老师");
        System.out.println("7.将选择自己课程的学生以excel形式导出");
        System.out.println("8.EXIT");  
  
    	
    }
    
    /*选择身份*/ 
    
    private static void managerTest() {
    	
        System.out.println("0.学生登录\n");  
        System.out.println("1.管理员登陆\n");
        System.out.println("2.教师登陆\n");
        System.out.println("请输入你要选择的系统：\n");
        int info = console.nextInt();  
        if (info == 1) {  
            System.out.println("请输入管理员密码:");  
            String password = console.next();  
            if (password.equals(managerPass)) {  
                System.out.println("*******************欢迎进入管理员系统*******************");  
                getMenuForManager();  
            } 
            else {  
                System.out.println("您的密码错误！");  
            }  
        } 
        else if (info == 0) {  
            System.out.println("*******************欢迎进入学生系统*******************");  
            getMenuForStu();  
        }
        else if(info==2){
        	System.out.println("*******************欢迎进入教师系统*******************");
        	getMenuForTea();
        }
        else {  
            System.out.println("请按要求输入！");
            managerTest(); 
        }  
    }
        
    /*学生菜单 */ 
        private static void getMenuForStu(){
        	  showMenuStu();
        	  System.out.println("请输入您要执行的选项：");  
              int info= console.nextInt();  
              while(info!=7)
              {
            	  switch(info)
            	  {
            	  case 1:createStudent();break;//注册学生
            	  case 2:studentLogin();break;//学生登录
            	  case 3:getCourseList();break;//获取课程
            	  case 4:studentChooseCourse();break;//选择课程
            	  case 5:changestuPassword();break;//修改密码
            	  case 6:getTeaCourses(); break;//选课
            	  default: System.out.println("请输入正确的选择! "); 
            		  
            	  }
            	  showMenuStu();
            	  System.out.println("选择其他功能：  ");  
                  info = console.nextInt();  
            		  
              }
        }
        /*管理员菜单*/ 
        private static void getMenuForManager(){
        	  showMenuMa();
        	  System.out.println("请输入您要执行的选项：");  
              int info= console.nextInt();   
              while(info!=4)
              {
            	  switch(info)
            	  {
            	  case 1:break;
            	  case 2:getStudentList();break;
            	  case 3:getCourseList();break;
            	  default: System.out.println("请输入正确的选择! "); 
            		  
            	  }
            	  showMenuMa();
            	  System.out.println("选择其他功能：  ");  
                  info = console.nextInt();  
            		  
              }
        	
        }
        
        
        /* 教师菜单*/ 
        private static void getMenuForTea(){
        	  showMenuTea();
        	  System.out.println("请输入你要选择的选项:");  
              int info= console.nextInt();    
              while(info!=8)
              {
            	  switch(info)
            	  {
            	  case 1:createCourse();break;//创建课程
            	  case 2:getStudentList();break;//获取学生列表
            	  case 3:getCourseList();break;//获取课程信息
            	  case 4:teacherLogin();break;//教师登录
            	  case 5:changeteaPassword();break;//修改登录密码
            	  case 6:createTeacher();break;
            	  case 7:getStuCourse();break;//获取学生选课信息
            	  default: System.out.println("请输入正确的选项："); 
            		  
            	  }
            	  showMenuTea();
            	  System.out.println("选择其他功能：  "); 
            	  
                  info = console.nextInt();  	  
              }
        	
        }  
        
        
        
        /*学生登录*/ 
        public static String studentLogin() {  
            
            boolean flag;  
      
            System.out.println("***************学生登录***************");  
      
            System.out.println("请输入的学生序号:");  
            int stuID = console.nextInt();  
            System.out.println("请输入你的用户名:\n");  
            String stuName = console.next();  
            System.out.println("请输入你的密码:");  
            String stuPassword = console.next();  
            String name = null, password = null;  
            Statement stmt;  //数据库操作
            ResultSet rs;  //保存从数据库中查询的结果
            try {  
                stmt = conn.createStatement();  //实例化Statement对象
                rs = stmt.executeQuery("SELECT * FROM stulogin WHERE id=" + stuID);  
                while (rs.next()) {  
                    name = rs.getString("stuName");  //用getStringd方法取得信息
                    password = rs.getString("stuPassword");  
                }  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
      
            if (stuName.equals(name) && stuPassword.equals(password)) {  
                System.out.println("登陆成功，欢迎进入！");  
                flag=true;  
      
            } else {  
                System.out.println("您的用户名或密码有误!");  
                flag=false;  
            }  
            return stuID+","+flag;  
        }
        
        
        
        
/*教师登录 */ 
	public static String teacherLogin (){      
	    boolean flag;  	
	    System.out.println("***************教师登陆***************");  	
	    System.out.println("请输入你的序号:");  
	    int teaID = console.nextInt();  
	    System.out.println("请输入你的用户名:\n");  
	    String teaName = console.next();  
	    System.out.println("请输入你的密码:");  
	    String teaPassword = console.next();  
	    String name = null, password = null;  
	    Statement stmt;  
	    ResultSet rs;  
	
	    try {  
	        stmt = conn.createStatement();  
	        rs = stmt.executeQuery("SELECT * FROM tealogin WHERE id=" + teaID);  
	        while (rs.next()) {  
	            name = rs.getString("teaName");  
	            password = rs.getString("teaPassword");  
	        }  
	    } catch (SQLException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	
	    if (teaName.equals(name) && teaPassword.equals(password)) {  
	        System.out.println("登陆成功，欢迎进入！");  
	        flag=true;  
	
	    } else {  
	        System.out.println("您的用户名或密码有误!");  
	        flag=false;  
	    }  
	    return teaID+","+flag;  
	}  
	
	
	
	/*判断是否已经登陆 以防还未登陆就进行选课 */  
    public static void studentChooseCourse() 
    {  
        String info=studentLogin();  
        while(true)
        {  
            String[] infos=info.split(",");  
            String flag =infos[1];  
            int stuID =Integer.parseInt(infos[0]);  
            if (flag.equals("false"))
            {  
                System.out.println("请先登录！");  
                info=studentLogin();  
            } 
            else 
            {  
                studentChooseCourse(stuID);  }
              
        } 
    }
  
        
        /*y学生选课 */  
        private static void studentChooseCourse(int stuID) {  
            System.out.println("***************学生选课***************");  
            getStuCourses(stuID);  
            getCourseList();  
      
            int flag = 1;  
            do {  
                System.out.println("请输入课程号 :");  
                int couID = console.nextInt();  
                Set<Integer> keySet = courseList.keySet();  
                Iterator<Integer> keyIt = keySet.iterator();  
                while (keyIt.hasNext()) {  
      
                    if (couID == keyIt.next()) {  
      
                        Course c = courseList.get(couID);  
                        stuCourseList.add(c);  
                    }  
                }  
      
                System.out.println("您还要继续选课吗？  0/1");  
                flag = console.nextInt();  
            } while (flag == 1);  
      
            saveDataToMysql(stuID);  
        }  
        
        
        
        /*将学生的选课保存到数据库中 */  
        private static void saveDataToMysql(int stuId) {  
            String sql = "INSERT stucourses(stuID,couID) VALUES(" + stuId + ",?)";  
            PreparedStatement ptmt = null;  
      
            try {  
                ptmt = conn.prepareStatement(sql);  
      
                for (Course c : stuCourseList) {  
                    System.out.println("b");  
                    ptmt.setInt(1,  c.getId());  
                    ptmt.execute();  
                }  
      
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
/*y从数据库中得到学生已经选择的课 */  
        private static void getStuCourses(int stuID) {  
            System.out.println("****************查询课程*******************");  
            System.out.println("您已经选择的课程有：");  
      
            Statement stmt = null;  
            ResultSet rs = null;  
      
            try {  
                stmt = conn.createStatement();  
                rs = stmt.executeQuery("SELECT * FROM courses AS c INNER JOIN stucourses AS s" +  
                        " ON s.stuID="+ stuID + " WHERE s.couID=c.couID;");  
                while (rs.next()) {  
                    Course course = new Course();  
                    course.setId(rs.getInt("couId"));  
                    course.setName(rs.getString("couName"));  
                    course.setTeacher(rs.getString("couTeacher"));  
                    course.setTime(rs.getString("couTime"));
                    course.setExam(rs.getString("couExam"));
                    course.setDetails(rs.getString("couDetails"));
                    course.setDemand(rs.getString("couDemand"));  
      
                    System.out.println(course.toString());  
                }  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        /*从数据库中获取学生信息*/  
        public static void getStudentList() {  
            System.out.println("***************学生列表*****************");  
            System.out.println("这是所有的学生信息:");  
            Statement stmt = null;  
            ResultSet rs = null;       
            try {  
                stmt = conn.createStatement();  
                rs = stmt.executeQuery("select * from students");      
                while (rs.next()) {  
      
                    Student student = new Student();  
                    student.setId(rs.getInt("stuID"));  
                    student.setName(rs.getString("stuName"));  
                    student.setAge(rs.getString("stuAge"));  
                    student.setSno(rs.getString("stuSno"));  
                    student.setSex(rs.getString("stuSex"));
                    student.setSubject(rs.getString("stuSubject"));
                    student.setQQ(rs.getString("stuQQ"));
                    student.setPhoneNumber(rs.getString("stuPhoneNumber"));
                    student.setEmail(rs.getString("stuEmail"));
                    student.setClasses(rs.getString("stuClasses"));
      
                    System.out.println(student.toString());  
                }  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
        /*y从数据库中获取课程列表集合*/  
        public static void getCourseList() {  
      
            System.out.println("*****************课程列表********************");  
            System.out.println("请从以下选出您的课程:");  
      
            Statement stmt = null;  
            ResultSet rs = null;  
            try {  
                stmt = conn.createStatement();  
                rs = stmt.executeQuery("select * from courses");  
      
                while (rs.next()) {  
                    Course course = new Course();  
                    course.setId(rs.getInt("couId"));  
                    course.setName(rs.getString("couName"));  
                    course.setTeacher(rs.getString("couTeacher"));  
                    course.setTime(rs.getString("couTime"));
                    course.setExam(rs.getString("couExam"));
                    course.setDetails(rs.getString("couDetails"));
                    course.setDemand(rs.getString("couDemand"));
      
                    courseList.put(course.getId(), course);  
      
                    System.out.println(course.toString());  
                }  
      
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
        
        /*课程集合 将课程信息保存在数据库中  */  
        private static void setCourseList(Course course) {  
      
            courseList.put(course.getId(), course);        
            String sql = "" + "INSERT courses (couId,couName,couTeacher,couTime,couDemand,couDetails,couExam) "  
                    + "VALUES(?,?,?,?,?,?,?)"; 
      
            try {  
                PreparedStatement ptmt = conn.prepareStatement(sql);  
                ptmt.setInt(1, course.getId());  
                ptmt.setString(2, course.getName());  
                ptmt.setString(3, course.getTeacher());  
                ptmt.setString(4, course.getTime());
                ptmt.setString(5, course.getDemand());
                ptmt.setString(6, course.getDetails());
                ptmt.setString(7, course.getExam());
                ptmt.execute();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
      
        }  
      
        /*将学生信息保存在数据库中  */  
        private static void setStudentList(Student student) {  
      
            String sql = ""+ "INSERT students (stuId,stuName,stuSex,stuAge,stuSubject,stuClasses,stuSno,stuEmail,stuPhoneNumber,stuQQ) "  
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";  
      
            String sql2 = "INSERT stulogin (stuName,stuPassWord) VALUES(?,12345)";  
      
            PreparedStatement ptmt;  
            try {  
                ptmt = conn.prepareStatement(sql);  
                ptmt.setInt(1, student.getId());  
                ptmt.setString(2, student.getName());  
                ptmt.setString(3, student.getSex());  
                ptmt.setString(4, student.getAge());  
                ptmt.setString(5, student.getSubject());  
                ptmt.setString(6, student.getClasses());
                ptmt.setString(7, student.getSno());
                ptmt.setString(8, student.getEmail());
                ptmt.setString(9, student.getPhoneNumber());
                ptmt.setString(10, student.getQQ());
      
                ptmt.execute();  
      
                ptmt = conn.prepareStatement(sql2);  
                ptmt.setString(1, student.getName());  
                ptmt.execute();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
        
        
        /*y创建课程对象 添加到课程集合 添加到数据库中 
         * @throws SQLException   */  
        public static void createCourse(int teaID)  {  
            System.out.println("********************创建课程********************");  
            System.out.println("请输入课程序号:");  
            int couId = console.nextInt();  
            System.out.println("请输入课程名:");  
            String couName = console.next();  
            System.out.println("请输入授课老师:");  
            String couTeacher = console.next(); 
            System.out.println("请输入课程学时:");
            String couTime=console.next();
            System.out.println("请输入课程要求:");
            String couDemand=console.next();
            System.out.println("请输入课程主要内容:");
            String couDetails=console.next();
            System.out.println("请输入考核方式:");
            String couExam=console.next(); 
        

            Course course = new Course(couName, couTeacher, couId,  couTime, couDemand, couDetails, couExam);  
            System.out.println("课程创建成功!");  
            setCourseList(course);  
        }  
        
        
       
        
        
        

        
        
        
    	/*判断是否已经登陆 以防还未登陆就进行建立课程 */  
        public static void createCourse() {  
            String info=teacherLogin();  
            while(true){  
                String[] infos=info.split(",");  
                String flag =infos[1];  
                int teaID =Integer.parseInt(infos[0]);  
                if (flag.equals("false")) {  
                    System.out.println("请重新登录");  
                    info=teacherLogin();  
                } else {  
                	createCourse(teaID);  
                }  
            }  
      
        }  
          
      
        /*y创建一个学生对象 添加到学生集合 添加到数据库中 */  
        public static void createStudent() {  
            System.out.println("********************注册学生信息********************");  
            System.out.println("请输入学生序号:");  
            int stuId = console.nextInt();  
            System.out.println("请输入学生名字:");  
            String stuName = console.next();  
            System.out.println("请输入学生年龄:");  
            String stuAge = console.next();  
            System.out.println("请输入学号:");  
            String stuSno = console.next();  
            System.out.println("请输入学生性别:");  
            String stuSex = console.next();  
            System.out.println("请输入学生专业:\n");
            String stuSubject=console.next();
            System.out.println("请输入学生班级:");
            String stuClasses=console.next();
            System.out.println("请输入邮箱:");
            String stuEmail=console.next();
            System.out.println("请输入电话号码:");
            String stuPhoneNumber=console.next();
            System.out.println("请输入qq号:");
            String stuQQ=console.next();
            System.out.println("创建学生信息成功，欢迎使用!");       
            Student student = new Student(stuId, stuName, stuAge, stuSex, stuSubject,  stuClasses, stuSno, stuEmail, stuPhoneNumber, stuQQ);  
      
            setStudentList(student);  
        }
        
        
        /*y修改学生登录密码 */  
        public static void changestuPassword() {  
            System.out.println("请输入您的名字:");  
            String name = console.next();  
            System.out.println("请输入您已经确定的密码:");  
            String newPassword = console.next();   
            String sql = " " + "UPDATE stulogin SET stuPassword=" + "'" + newPassword  
                    + "' WHERE stuName=" + "'" + name + "'";  
            PreparedStatement ptmt;  
            try {  
                ptmt = conn.prepareStatement(sql);  
                ptmt.execute();  
                System.out.println("修改密码成功,请重新登录!");   
                studentLogin();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
        /*y修改教师登录密码 */  
        public static void changeteaPassword() {  
            System.out.println("请输入您的名字:");  
            String name = console.next();  
            System.out.println("请输入您已经确定的密码:");  
            String newPassword = console.next();  
       
            String sql = " " + "UPDATE tealogin SET teaPassword=" + "'" + newPassword  
                    + "' WHERE teaName=" + "'" + name + "'";  
            PreparedStatement ptmt;  
            try {  
                ptmt = conn.prepareStatement(sql);  
                ptmt.execute();  
                System.out.println("修改密码成功,请重新登录!");   
                studentLogin();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
    //y
        private static void getTeaCourses()
        {
        	 System.out.println("******************查看老师开课*****************");  
        	 System.out.println("输入你要查看老师的序号");
        	 String teaId = console.next(); 
       
             Statement stmt = null;  
             ResultSet rs = null;
       
             try {  
                 stmt = conn.createStatement();  
                 rs = stmt.executeQuery("SELECT * FROM courses AS c INNER JOIN teacourses AS s" +  
                         " ON s.teaID="+ teaId + " WHERE s.couID=c.couID;");  
                 while (rs.next()) {  
                     Course course = new Course();  
                     course.setId(rs.getInt("couId"));  
                     course.setName(rs.getString("couName"));  
                     course.setTeacher(rs.getString("couTeacher"));  
                     course.setTime(rs.getString("couTime"));
                     course.setExam(rs.getString("couExam"));
                     course.setDetails(rs.getString("couDetails"));
                     course.setDemand(rs.getString("couDemand"));  
       
                     System.out.println(course.toString());  
                 }  
             } catch (SQLException e) {  
                 // TODO Auto-generated catch block  
                 e.printStackTrace();  
             }  
        	
        }
        
        
        
        
       //y 
        private static void getStuCourse()
        {
        	System.out.println("******************查看学生选课信息***************");  
        	System.out.println("输入你要查看学生的序号");
        	String teaId = console.next(); 
            Statement stmt = null;  
            ResultSet rs = null;
            int t= 0;
      
            try {  
            	WritableWorkbook book = Workbook.createWorkbook(new File("test1.xls"));
                stmt = conn.createStatement(); 
                WritableFont font1 = new WritableFont(WritableFont.TIMES,15,WritableFont.BOLD);
                WritableCellFormat format1=new WritableCellFormat(font1);
                // 生成名为“第一页”的工作表，参数0表示这是第一页
                WritableSheet sheet = book.createSheet("第一页", 0);
                
            	jxl.write.Label label1;
        		jxl.write.Label label2;
        		jxl.write.Label label3;
        		jxl.write.Label label4;
        		jxl.write.Label label5;
        		jxl.write.Label label6;
        		jxl.write.Label label7;
        		jxl.write.Label label8;
        		jxl.write.Label label9;
        		jxl.write.Label labe20;
                //设置对齐
                format1.setAlignment(jxl.format.Alignment.CENTRE);
                format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                rs = stmt.executeQuery("SELECT * FROM students AS c INNER JOIN stutea AS s" +  
                        " ON s.teaID="+ teaId + " WHERE s.stuID=c.stuId;");  
                while (rs.next()) {  
                    Student student = new Student();  
                    student.setId(rs.getInt("stuId"));  
                    student.setName(rs.getString("stuName"));  
                    student.setAge(rs.getString("stuAge"));  
                    student.setSex(rs.getString("stuSex"));
                    student.setSubject(rs.getString("stuSubject"));
                    student.setClasses(rs.getString("stuClasses"));
                    student.setSno(rs.getString("stuSno"));  
                    student.setEmail(rs.getString("stuEmail")); 
                    student.setPhoneNumber(rs.getString("stuPhoneNumber")); 
                    student.setQQ(rs.getString("stuQQ")); 
      
                    System.out.println(student.toString()); 
                	label1=new jxl.write.Label(0,t,rs.getString("stuId"));
                	label2=new jxl.write.Label(1,t,rs.getString("stuName"));
                	label3=new jxl.write.Label(2,t,rs.getString("stuAge"));
                	label4=new jxl.write.Label(3,t,rs.getString("stuSex"));
                	label5=new jxl.write.Label(4,t,rs.getString("stuSubject"));
                	label6=new jxl.write.Label(5,t,rs.getString("stuClasses"));
                	label7=new jxl.write.Label(6,t,rs.getString("stuSno"));
                	label8=new jxl.write.Label(7,t,rs.getString("stuEmail"));
                	label9=new jxl.write.Label(8,t,rs.getString("stuPhoneNumber"));
                	labe20=new jxl.write.Label(9,t,rs.getString("stuQQ"));
                	
                	sheet.addCell(label1);
        			sheet.addCell(label2);
        			sheet.addCell(label3);
        			sheet.addCell(label4);
        			sheet.addCell(label5);
        			sheet.addCell(label6);
        			sheet.addCell(label7);
        			sheet.addCell(label8);
        			sheet.addCell(label9);
        			sheet.addCell(labe20);
        			t++;               
                }  
                // 写入数据并关闭文件
                book.write();
                book.close();
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
 	
        }
        
       
        
        
        
        
        
        /*Y创建一个教师对象 添加到教师集合 添加到数据库中 */  
        public static void createTeacher() {  
            System.out.println("******************注册老师******************");  
            System.out.println("请输入教师序号:");  
            int teaId = console.nextInt();  
            System.out.println("请输入教师名字:");  
            String teaName = console.next();  
            System.out.println("请输入教师年龄:");  
            String teaAge = console.next();   
            System.out.println("请输入教师性别:");  
            String teaSex = console.next();  
            System.out.println("请输入教师专业:");
            String teaSubject=console.next();
            System.out.println("请输入教师职称:");
            String teaZhicheng=console.next();
            
      
            System.out.println("创建成功，欢迎使用!");  
      
            Teacher teacher = new Teacher(teaId, teaName, teaAge, teaSex, teaSubject,  teaZhicheng);  
      
            setTeacherList(teacher);  
        }
        
        
        
        
        
        /*教师集合 将教师集合保存在数据库中*/  
        private static void setTeacherList(Teacher teacher) {  
      
            String sql = ""+ "INSERT teachers (teaId,teaName,teaAge,teaSex,teaSubject,teaZhicheng) "  
                    + "VALUES(?,?,?,?,?,?)";  
      
            String sql2 = "INSERT tealogin (teaName,teaPassword) VALUES(?,12345)";  
      
            PreparedStatement ptmt;  
            try {  
                ptmt = conn.prepareStatement(sql);  
                ptmt.setInt(1, teacher.getId());  
                ptmt.setString(2, teacher.getName());  
                ptmt.setString(3, teacher.getAge());  
                ptmt.setString(4, teacher.getSex());  
                ptmt.setString(5, teacher.getSubject());  
                ptmt.setString(6, teacher.getZhicheng());
      
                ptmt.execute();  
      
                ptmt = conn.prepareStatement(sql2);  
                ptmt.setString(1, teacher.getName());  
                ptmt.execute();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
      
        
    	
    }








    
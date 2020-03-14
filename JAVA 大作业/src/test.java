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
	private static Scanner console = new Scanner(System.in);  //��������
    private static String managerPass = "12345";  //����Ա��½����Ϊ12345
    private static Connection conn = DBUtil.getConnection();//�������ݿ�
    private static List<Course> stuCourseList = new ArrayList<Course>(); 
    private static List<Course> teaCourseList = new ArrayList<Course>();//���弯�ϱ���γ� 
    private static Map<Integer, Course> courseList = new HashMap<Integer, Course>(); //map�ӿ� �����ţ�key�������ڵ�integer���������ظ� 
    
    public static void main(String[] args) {
       
        managerTest();
    }
    
    
    
    
    /*ѧ��*/ 
    public static void showMenuStu() {  
        System.out.println("*************************ѧ���˵�*************************");  
        System.out.println("1.ע��ѧ��");
        System.out.println("2.ѧ����½");
        System.out.println("3.�鿴�γ��б�");
        System.out.println("4.ѡ��");  
        System.out.println("5.�޸�ѧ����Ϣ(����)"); 
        System.out.println("6.�鿴��ʦ�������");
        System.out.println("7.EXIT");  
    } 
    
    
    
    
    /*����Ա*/ 
    public static void showMenuMa(){
    	System.out.println("*************************����Ա�˵�*************************");  
        System.out.println("1.��˿γ�");
        System.out.println("2.�鿴ѧ����Ϣ�б�");
        System.out.println("3.�鿴�γ���Ϣ�б�");
        System.out.println("4.EXIT");  
  
    	
    }
    
    
    
    
    /*��ʦ*/ 
    
    public static void showMenuTea(){
    	System.out.println("*************************��ʦ�˵�*************************"); 
        System.out.println("1.����γ�"); 
        System.out.println("2.�鿴ѧ����Ϣ�б�"); 
        System.out.println("3.�鿴�γ���Ϣ�б�");
        System.out.println("4.��ʦ��½");
        System.out.println("5.�޸ĵ�¼����");
        System.out.println("6.ע����ʦ");
        System.out.println("7.��ѡ���Լ��γ̵�ѧ����excel��ʽ����");
        System.out.println("8.EXIT");  
  
    	
    }
    
    /*ѡ�����*/ 
    
    private static void managerTest() {
    	
        System.out.println("0.ѧ����¼\n");  
        System.out.println("1.����Ա��½\n");
        System.out.println("2.��ʦ��½\n");
        System.out.println("��������Ҫѡ���ϵͳ��\n");
        int info = console.nextInt();  
        if (info == 1) {  
            System.out.println("���������Ա����:");  
            String password = console.next();  
            if (password.equals(managerPass)) {  
                System.out.println("*******************��ӭ�������Աϵͳ*******************");  
                getMenuForManager();  
            } 
            else {  
                System.out.println("�����������");  
            }  
        } 
        else if (info == 0) {  
            System.out.println("*******************��ӭ����ѧ��ϵͳ*******************");  
            getMenuForStu();  
        }
        else if(info==2){
        	System.out.println("*******************��ӭ�����ʦϵͳ*******************");
        	getMenuForTea();
        }
        else {  
            System.out.println("�밴Ҫ�����룡");
            managerTest(); 
        }  
    }
        
    /*ѧ���˵� */ 
        private static void getMenuForStu(){
        	  showMenuStu();
        	  System.out.println("��������Ҫִ�е�ѡ�");  
              int info= console.nextInt();  
              while(info!=7)
              {
            	  switch(info)
            	  {
            	  case 1:createStudent();break;//ע��ѧ��
            	  case 2:studentLogin();break;//ѧ����¼
            	  case 3:getCourseList();break;//��ȡ�γ�
            	  case 4:studentChooseCourse();break;//ѡ��γ�
            	  case 5:changestuPassword();break;//�޸�����
            	  case 6:getTeaCourses(); break;//ѡ��
            	  default: System.out.println("��������ȷ��ѡ��! "); 
            		  
            	  }
            	  showMenuStu();
            	  System.out.println("ѡ���������ܣ�  ");  
                  info = console.nextInt();  
            		  
              }
        }
        /*����Ա�˵�*/ 
        private static void getMenuForManager(){
        	  showMenuMa();
        	  System.out.println("��������Ҫִ�е�ѡ�");  
              int info= console.nextInt();   
              while(info!=4)
              {
            	  switch(info)
            	  {
            	  case 1:break;
            	  case 2:getStudentList();break;
            	  case 3:getCourseList();break;
            	  default: System.out.println("��������ȷ��ѡ��! "); 
            		  
            	  }
            	  showMenuMa();
            	  System.out.println("ѡ���������ܣ�  ");  
                  info = console.nextInt();  
            		  
              }
        	
        }
        
        
        /* ��ʦ�˵�*/ 
        private static void getMenuForTea(){
        	  showMenuTea();
        	  System.out.println("��������Ҫѡ���ѡ��:");  
              int info= console.nextInt();    
              while(info!=8)
              {
            	  switch(info)
            	  {
            	  case 1:createCourse();break;//�����γ�
            	  case 2:getStudentList();break;//��ȡѧ���б�
            	  case 3:getCourseList();break;//��ȡ�γ���Ϣ
            	  case 4:teacherLogin();break;//��ʦ��¼
            	  case 5:changeteaPassword();break;//�޸ĵ�¼����
            	  case 6:createTeacher();break;
            	  case 7:getStuCourse();break;//��ȡѧ��ѡ����Ϣ
            	  default: System.out.println("��������ȷ��ѡ�"); 
            		  
            	  }
            	  showMenuTea();
            	  System.out.println("ѡ���������ܣ�  "); 
            	  
                  info = console.nextInt();  	  
              }
        	
        }  
        
        
        
        /*ѧ����¼*/ 
        public static String studentLogin() {  
            
            boolean flag;  
      
            System.out.println("***************ѧ����¼***************");  
      
            System.out.println("�������ѧ�����:");  
            int stuID = console.nextInt();  
            System.out.println("����������û���:\n");  
            String stuName = console.next();  
            System.out.println("�������������:");  
            String stuPassword = console.next();  
            String name = null, password = null;  
            Statement stmt;  //���ݿ����
            ResultSet rs;  //��������ݿ��в�ѯ�Ľ��
            try {  
                stmt = conn.createStatement();  //ʵ����Statement����
                rs = stmt.executeQuery("SELECT * FROM stulogin WHERE id=" + stuID);  
                while (rs.next()) {  
                    name = rs.getString("stuName");  //��getStringd����ȡ����Ϣ
                    password = rs.getString("stuPassword");  
                }  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
      
            if (stuName.equals(name) && stuPassword.equals(password)) {  
                System.out.println("��½�ɹ�����ӭ���룡");  
                flag=true;  
      
            } else {  
                System.out.println("�����û�������������!");  
                flag=false;  
            }  
            return stuID+","+flag;  
        }
        
        
        
        
/*��ʦ��¼ */ 
	public static String teacherLogin (){      
	    boolean flag;  	
	    System.out.println("***************��ʦ��½***************");  	
	    System.out.println("������������:");  
	    int teaID = console.nextInt();  
	    System.out.println("����������û���:\n");  
	    String teaName = console.next();  
	    System.out.println("�������������:");  
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
	        System.out.println("��½�ɹ�����ӭ���룡");  
	        flag=true;  
	
	    } else {  
	        System.out.println("�����û�������������!");  
	        flag=false;  
	    }  
	    return teaID+","+flag;  
	}  
	
	
	
	/*�ж��Ƿ��Ѿ���½ �Է���δ��½�ͽ���ѡ�� */  
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
                System.out.println("���ȵ�¼��");  
                info=studentLogin();  
            } 
            else 
            {  
                studentChooseCourse(stuID);  }
              
        } 
    }
  
        
        /*yѧ��ѡ�� */  
        private static void studentChooseCourse(int stuID) {  
            System.out.println("***************ѧ��ѡ��***************");  
            getStuCourses(stuID);  
            getCourseList();  
      
            int flag = 1;  
            do {  
                System.out.println("������γ̺� :");  
                int couID = console.nextInt();  
                Set<Integer> keySet = courseList.keySet();  
                Iterator<Integer> keyIt = keySet.iterator();  
                while (keyIt.hasNext()) {  
      
                    if (couID == keyIt.next()) {  
      
                        Course c = courseList.get(couID);  
                        stuCourseList.add(c);  
                    }  
                }  
      
                System.out.println("����Ҫ����ѡ����  0/1");  
                flag = console.nextInt();  
            } while (flag == 1);  
      
            saveDataToMysql(stuID);  
        }  
        
        
        
        /*��ѧ����ѡ�α��浽���ݿ��� */  
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
        
        
        
/*y�����ݿ��еõ�ѧ���Ѿ�ѡ��Ŀ� */  
        private static void getStuCourses(int stuID) {  
            System.out.println("****************��ѯ�γ�*******************");  
            System.out.println("���Ѿ�ѡ��Ŀγ��У�");  
      
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
        
        
        /*�����ݿ��л�ȡѧ����Ϣ*/  
        public static void getStudentList() {  
            System.out.println("***************ѧ���б�*****************");  
            System.out.println("�������е�ѧ����Ϣ:");  
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
        
        
        
        /*y�����ݿ��л�ȡ�γ��б���*/  
        public static void getCourseList() {  
      
            System.out.println("*****************�γ��б�********************");  
            System.out.println("�������ѡ�����Ŀγ�:");  
      
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
        
        
        
        
        /*�γ̼��� ���γ���Ϣ���������ݿ���  */  
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
      
        /*��ѧ����Ϣ���������ݿ���  */  
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
        
        
        
        
        
        /*y�����γ̶��� ��ӵ��γ̼��� ��ӵ����ݿ��� 
         * @throws SQLException   */  
        public static void createCourse(int teaID)  {  
            System.out.println("********************�����γ�********************");  
            System.out.println("������γ����:");  
            int couId = console.nextInt();  
            System.out.println("������γ���:");  
            String couName = console.next();  
            System.out.println("�������ڿ���ʦ:");  
            String couTeacher = console.next(); 
            System.out.println("������γ�ѧʱ:");
            String couTime=console.next();
            System.out.println("������γ�Ҫ��:");
            String couDemand=console.next();
            System.out.println("������γ���Ҫ����:");
            String couDetails=console.next();
            System.out.println("�����뿼�˷�ʽ:");
            String couExam=console.next(); 
        

            Course course = new Course(couName, couTeacher, couId,  couTime, couDemand, couDetails, couExam);  
            System.out.println("�γ̴����ɹ�!");  
            setCourseList(course);  
        }  
        
        
       
        
        
        

        
        
        
    	/*�ж��Ƿ��Ѿ���½ �Է���δ��½�ͽ��н����γ� */  
        public static void createCourse() {  
            String info=teacherLogin();  
            while(true){  
                String[] infos=info.split(",");  
                String flag =infos[1];  
                int teaID =Integer.parseInt(infos[0]);  
                if (flag.equals("false")) {  
                    System.out.println("�����µ�¼");  
                    info=teacherLogin();  
                } else {  
                	createCourse(teaID);  
                }  
            }  
      
        }  
          
      
        /*y����һ��ѧ������ ��ӵ�ѧ������ ��ӵ����ݿ��� */  
        public static void createStudent() {  
            System.out.println("********************ע��ѧ����Ϣ********************");  
            System.out.println("������ѧ�����:");  
            int stuId = console.nextInt();  
            System.out.println("������ѧ������:");  
            String stuName = console.next();  
            System.out.println("������ѧ������:");  
            String stuAge = console.next();  
            System.out.println("������ѧ��:");  
            String stuSno = console.next();  
            System.out.println("������ѧ���Ա�:");  
            String stuSex = console.next();  
            System.out.println("������ѧ��רҵ:\n");
            String stuSubject=console.next();
            System.out.println("������ѧ���༶:");
            String stuClasses=console.next();
            System.out.println("����������:");
            String stuEmail=console.next();
            System.out.println("������绰����:");
            String stuPhoneNumber=console.next();
            System.out.println("������qq��:");
            String stuQQ=console.next();
            System.out.println("����ѧ����Ϣ�ɹ�����ӭʹ��!");       
            Student student = new Student(stuId, stuName, stuAge, stuSex, stuSubject,  stuClasses, stuSno, stuEmail, stuPhoneNumber, stuQQ);  
      
            setStudentList(student);  
        }
        
        
        /*y�޸�ѧ����¼���� */  
        public static void changestuPassword() {  
            System.out.println("��������������:");  
            String name = console.next();  
            System.out.println("���������Ѿ�ȷ��������:");  
            String newPassword = console.next();   
            String sql = " " + "UPDATE stulogin SET stuPassword=" + "'" + newPassword  
                    + "' WHERE stuName=" + "'" + name + "'";  
            PreparedStatement ptmt;  
            try {  
                ptmt = conn.prepareStatement(sql);  
                ptmt.execute();  
                System.out.println("�޸�����ɹ�,�����µ�¼!");   
                studentLogin();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
        /*y�޸Ľ�ʦ��¼���� */  
        public static void changeteaPassword() {  
            System.out.println("��������������:");  
            String name = console.next();  
            System.out.println("���������Ѿ�ȷ��������:");  
            String newPassword = console.next();  
       
            String sql = " " + "UPDATE tealogin SET teaPassword=" + "'" + newPassword  
                    + "' WHERE teaName=" + "'" + name + "'";  
            PreparedStatement ptmt;  
            try {  
                ptmt = conn.prepareStatement(sql);  
                ptmt.execute();  
                System.out.println("�޸�����ɹ�,�����µ�¼!");   
                studentLogin();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        
        
    //y
        private static void getTeaCourses()
        {
        	 System.out.println("******************�鿴��ʦ����*****************");  
        	 System.out.println("������Ҫ�鿴��ʦ�����");
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
        	System.out.println("******************�鿴ѧ��ѡ����Ϣ***************");  
        	System.out.println("������Ҫ�鿴ѧ�������");
        	String teaId = console.next(); 
            Statement stmt = null;  
            ResultSet rs = null;
            int t= 0;
      
            try {  
            	WritableWorkbook book = Workbook.createWorkbook(new File("test1.xls"));
                stmt = conn.createStatement(); 
                WritableFont font1 = new WritableFont(WritableFont.TIMES,15,WritableFont.BOLD);
                WritableCellFormat format1=new WritableCellFormat(font1);
                // ������Ϊ����һҳ���Ĺ���������0��ʾ���ǵ�һҳ
                WritableSheet sheet = book.createSheet("��һҳ", 0);
                
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
                //���ö���
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
                // д�����ݲ��ر��ļ�
                book.write();
                book.close();
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
 	
        }
        
       
        
        
        
        
        
        /*Y����һ����ʦ���� ��ӵ���ʦ���� ��ӵ����ݿ��� */  
        public static void createTeacher() {  
            System.out.println("******************ע����ʦ******************");  
            System.out.println("�������ʦ���:");  
            int teaId = console.nextInt();  
            System.out.println("�������ʦ����:");  
            String teaName = console.next();  
            System.out.println("�������ʦ����:");  
            String teaAge = console.next();   
            System.out.println("�������ʦ�Ա�:");  
            String teaSex = console.next();  
            System.out.println("�������ʦרҵ:");
            String teaSubject=console.next();
            System.out.println("�������ʦְ��:");
            String teaZhicheng=console.next();
            
      
            System.out.println("�����ɹ�����ӭʹ��!");  
      
            Teacher teacher = new Teacher(teaId, teaName, teaAge, teaSex, teaSubject,  teaZhicheng);  
      
            setTeacherList(teacher);  
        }
        
        
        
        
        
        /*��ʦ���� ����ʦ���ϱ��������ݿ���*/  
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








    
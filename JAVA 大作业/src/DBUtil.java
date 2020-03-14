import java.sql.DriverManager;

import com.mysql.jdbc.Connection;
import java.sql.SQLException;
public class DBUtil {
	private static final String URL="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8";  
    private static final String USER="root";  
    private static final String PASSWORD="root";  
  
    private static Connection conn = null;  
  
    static {  
        try {  
            Class.forName("com.mysql.jdbc.Driver");  
            conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);  
  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    }  
      
    public static Connection getConnection(){  
        return conn;  
    }  

}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import bankatm.DataBase;

public class Test {
	public static String className = "com.mysql.jdbc.Driver";
	public static String host = "jdbc:mysql://sql2.njit.edu/";
	public static String db = "ad546";
	public static String user = "ad546";
	public static String pwd = "KfSadnuB";
	public static void main(String args[]) {
		
		try{
		Class.forName(className);
		Connection con = DriverManager.getConnection(host + db,	user, pwd);
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery("select * from test;");
		if (res.next()) {
			System.out.println(res.getString(1));
		}
		}catch(Exception e){
			System.out.println("Error - "+e);
		}
		
		
	}
}

import java.io.Console;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UserActivities {
	java.sql.Connection con;
	Statement stmt;
	public UserActivities(){
	
	}
	
	public void changePwd(TransactionObject t){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "oracle");
			stmt = con.createStatement();
			boolean flag = true;
			while(flag){
		String id = t.getId(),opwd = "",npwd="";
		Scanner sc = new Scanner(System.in);
	
		System.out.println("Please Enter Old Password");
		opwd = sc.next();
		System.out.println("Please Enter New Password");
		npwd = sc.next();
		
		ResultSet res = stmt.executeQuery("select password from banklogin where userid = '" + id + "' and password = '" + opwd + "';");

		if (res.next()) {
			String query1 = " update banklogin set password = ? where userid = '"+id+"';";
			PreparedStatement ps1 = con.prepareStatement(query1);
			ps1.setString(1, npwd);
			ps1.execute();
			System.out.println("Password changed successfully");
			flag = false;
		}else{
			System.out.println("Sorry wrong password. Retry");
		}
			
		
			}
			con.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void viewAcct(TransactionObject t){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "oracle");
			stmt = con.createStatement();
		ResultSet res = stmt.executeQuery("select * from bankaccount where userid = '"+t.getId()+"';");
		System.out.println();
		System.out.printf("%-22s%-22s%-22s%-22s%-22s","Name","Account Number","Account Type","Balance","Date");
		
		while(res.next()){	
			System.out.println();
			System.out.printf("%-22s%-22d%-22s%-22f%-22s",res.getString("username"),res.getInt("acctnum"),res.getString("type"),res.getDouble("amount"),res.getDate("date"));
		}
		System.out.println();
		System.out.println();
		con.close();
		}catch(Exception e){
		System.out.println(e);
		}

	}
}

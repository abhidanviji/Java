import java.io.Console;
import java.sql.*;
import java.util.Scanner;

public class AdminActivities {
	java.sql.Connection con;
	Statement stmt;

	public AdminActivities() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "oracle");
			stmt = con.createStatement();
		} catch (Exception e) {

		}
	}

	public void createUser() {
		try {
			String user = "", type = "", id = "", temppwd = "";
			Double amount = 0.0;
			Scanner sc = new Scanner(System.in);
			System.out.println("Please Enter UserID");
			id = sc.next();
			System.out.println("Please Enter Temp Password");
			temppwd = sc.next();
			System.out.println("Please Enter Username");
			user = sc.next();
			System.out.println("Please Enter Account Type");
			type = sc.next();
			System.out.println("Please Enter Amount");
			amount = Double.parseDouble(sc.next());

			String query = " insert into banklogin (userid, password) values (?, ?);";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, id);
			preparedStmt.setString(2, temppwd);
			preparedStmt.execute();

			System.out.println("Login Successfully Created");
			String query1 = " insert into bankaccount (userid, username,type,amount) values (?,?,?,?);";
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			preparedStmt1.setString(1, id);
			preparedStmt1.setString(2, user);
			preparedStmt1.setString(3, type);
			preparedStmt1.setDouble(4, amount);
			preparedStmt1.execute();

			System.out.println("Account Successfully Created");
			ResultSet res = stmt.executeQuery(
					"select acctnum from bankaccount where userid = '" + id + "' and username = '" + user + "' order by date desc;");

			if (res.next()) {
				System.out.println("Account Num - " + res.getString(1));
			}

		} catch (Exception e) {
		}

	}
	
	public void createAccount(TransactionObject t){
		try{
		String type = "",user ="",id="";
		Double amount = 0.0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please Enter Username for whom the new account should be added");
		user = sc.next();
		
		ResultSet rs = stmt.executeQuery("select userid, from bankaccount where  username = '" + user + "';");
		if(rs.next()){
			id = rs.getString(1);
		}
		
		System.out.println("Please Enter Account Type");
		type = sc.next();
		System.out.println("Please Enter Amount");
		amount = Double.parseDouble(sc.next());
		String query1 = " insert into bankaccount (userid, username,type,amount) values (?,?,?,?);";
		PreparedStatement preparedStmt1 = con.prepareStatement(query1);
		preparedStmt1.setString(1, id);
		preparedStmt1.setString(2, user);
		preparedStmt1.setString(3, type);
		preparedStmt1.setDouble(4, amount);
		preparedStmt1.execute();

		System.out.println("Account Successfully Created");
		ResultSet res = stmt.executeQuery(
				"select acctnum from bankaccount where userid = '" +id+ "' and username = '" +user+ "' order by date desc;");

		if (res.next()) {
			System.out.println("Account Num - " + res.getString(1));
		}
	} catch (Exception e) {
	}
		
	}
}

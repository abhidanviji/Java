import java.io.*;
import java.sql.*;
import java.util.*;

public class Bank {

	public static void main(String[] args) {
		String id = "na";
		boolean flag = true, aflag = true, uflag = true;
		;
		TransactionObject t = new TransactionObject();
		AdminActivities aa = new AdminActivities();
		UserActivities ua = new UserActivities();
		String userid = "", pass = "";
		Scanner sc = new Scanner(System.in);
		try {
			while (flag) {

				System.out.println("Please Enter Username");
				userid = sc.next();
				System.out.println("Please Enter Password");
				pass = sc.next();

				Class.forName("com.mysql.jdbc.Driver");
				java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root",
						"oracle");
				Statement stmt = con.createStatement();
				ResultSet res = stmt.executeQuery("select * from banklogin");
				while (res.next()) {
					if (res.getString(1).equals(userid) && res.getString(2).equals(pass)) {
						System.out.println("Login Successfull");
						t.setId(userid);
						id = t.getId();
						aflag = true;
						uflag = true;
						System.out.println("Welcome " + id);
					}
				}
				if (id.equals("admin")) {

					while (aflag) {
						System.out.println("Select from following options and enter option number:");
						System.out.println("1. Create User \n2. Create another account \n3. Logout \n4. Exit Application");
						int option = Integer.parseInt(sc.next());
						switch (option) {
						case 1:
							aa.createUser();
							break;
							
						case 2:
							aa.createAccount(t);
							break;

						case 3:
							aflag = false;
							break;

						case 4:
							flag = false;
							break;
						}
					}
				} else if (id.equals("na")) {
					System.out.println("Try again");
				} else {
					while (uflag) {
						System.out.println("Select from following options and enter option number:");
						System.out.println("1. Change password \n2. View Account Details \n3. Logout \n4. Exit Application");
						int option = Integer.parseInt(sc.next());
						switch (option) {
						case 1:
							ua.changePwd(t);
							break;
							
						case 2:
							ua.viewAcct(t);
							break;
							
						case 3:
							uflag = false;
							break;
							
						case 4:
							flag = false;
							break;
						}
					}

				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

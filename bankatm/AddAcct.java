package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AddAcct {
	
	String msg = "";
	int count=0;
	String username[];
	String accttype[] = {"Savings","Checkings"};
	
	public AddAcct(TransactionObject t){
		try{
			Class.forName(new DataBase().className);
			Connection con = DriverManager.getConnection(new DataBase().host + new DataBase().db, new DataBase().user, new DataBase().pwd);
		Statement stmt = con.createStatement();
		
		ResultSet res = stmt.executeQuery("select distinct username from bankaccount;");

		while (res.next()) {
			count++;

		}
		username = new String[count];
		ResultSet res1 = stmt.executeQuery("select distinct username from bankaccount;");
		count = 0;
		while (res1.next()) {
			username[count] = res1.getString(1);
			count++;
		}
		
		
		JFrame frame;
		frame = new JFrame(t.getId()+" ATM");
		JPanel panel = new JPanel();
		JLabel lname = new JLabel("User Name");
		JComboBox name = new JComboBox(username);
		JLabel ltype = new JLabel("Account Type");
		JComboBox atype = new JComboBox(accttype);
		JLabel lamt = new JLabel("Amount");
		JTextField amt = new JTextField(10);
		JButton create = new JButton("Create");
		JButton back = new JButton("Back");
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		
		panel.add(lname, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(name, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		gbc.gridx = 0;
		panel.add(ltype, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(atype, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		gbc.gridx = 0;
		panel.add(lamt, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(amt, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		
		panel.add(create,gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		
		panel.add(back,gbc);
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Admin(t);
			}
		});
		
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					
										
					t.setName((String)name.getSelectedItem());
					t.setType((String)atype.getSelectedItem());
					t.setAmount(Float.parseFloat(amt.getText()));
					
					ResultSet rs = stmt.executeQuery("select userid from bankaccount where  username = '" + t.getName() + "';");
					if(rs.next()){
						t.setId(rs.getString(1));
					}
					
					
					
				String query1 = " insert into bankaccount (userid, username,type,amount) values (?,?,?,?);";
				PreparedStatement preparedStmt1 = con.prepareStatement(query1);
				preparedStmt1.setString(1, t.getId());
				preparedStmt1.setString(2, t.getName());
				preparedStmt1.setString(3, t.getType());
				preparedStmt1.setDouble(4, t.getAmount());
				preparedStmt1.execute();

				msg = msg+"Account Successfully Added. ";
				ResultSet res = stmt.executeQuery(
						"select acctnum from bankaccount where userid = '" + t.getId() + "' and username = '" + t.getName()+ "' order by date desc;");

				if (res.next()) {
				
					msg = msg+"Account Num - " + res.getString(1);
				}
				con.close();
			} catch (Exception ex) {
				msg = msg+"Something went wrong!"+ex;
			}
				t.setMessage(msg);
				t.setId("admin");
				frame.setVisible(false);
				new Admin(t);
			}
		});
		
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		Dimension frameDim = frame.getSize();
		frame.setSize(550, 400);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - frameDim.width / 2, screenSize.height / 4 - frameDim.height / 2);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		frame.setVisible(true);
		
		}catch(Exception e){
			
		}
	}
}

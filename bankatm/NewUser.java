package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class NewUser {
	
	String msg = "";

	public NewUser(TransactionObject t){
				
		JFrame frame;
		frame = new JFrame(t.getId()+" ATM");
		JPanel panel = new JPanel();
		
		JLabel luid = new JLabel("User ID");
		JTextField uid = new JTextField(10);
		JLabel ltpwd = new JLabel("Temporary Password");
		JPasswordField tpwd = new JPasswordField(10);
		
		JLabel lname = new JLabel("User Name");
		JTextField name = new JTextField(10);
		JLabel ltype = new JLabel("Account Type");
		JTextField atype = new JTextField(10);
		JLabel lamt = new JLabel("Amount");
		JTextField amt = new JTextField(10);
		JButton create = new JButton("Create");
		JButton back = new JButton("Back");
		
		
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(luid, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(uid, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		gbc.gridx = 0;
		panel.add(ltpwd, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(tpwd, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		gbc.gridx = 0;
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
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "oracle");
					Statement stmt = con.createStatement();
										
					t.setId(uid.getText());
					t.setNum(String.valueOf(tpwd.getPassword()));
					t.setName(name.getText());
					t.setType(atype.getText());
					t.setAmount(Float.parseFloat(amt.getText()));
					
					String query = " insert into banklogin (userid, password) values (?, ?);";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, t.getId());
				preparedStmt.setString(2, t.getNum());
				preparedStmt.execute();

				msg = msg+"Login Successfully Created. ";
				String query1 = " insert into bankaccount (userid, username,type,amount) values (?,?,?,?);";
				PreparedStatement preparedStmt1 = con.prepareStatement(query1);
				preparedStmt1.setString(1, t.getId());
				preparedStmt1.setString(2, t.getName());
				preparedStmt1.setString(3, t.getType());
				preparedStmt1.setDouble(4, t.getAmount());
				preparedStmt1.execute();

				msg = msg+"Account Successfully Created. ";
				ResultSet res = stmt.executeQuery(
						"select acctnum from bankaccount where userid = '" + t.getId() + "' and username = '" + t.getName() + "' order by date desc;");

				if (res.next()) {
				
					msg = msg+"Account Num - " + res.getString(1);
				}

			} catch (Exception ex) {
				msg = msg+"Something went wrong!";
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
	}
}

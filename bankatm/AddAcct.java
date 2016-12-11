package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AddAcct {
	
	String msg = "";
	public AddAcct(TransactionObject t){
		JFrame frame;
		frame = new JFrame(t.getId()+" ATM");
		JPanel panel = new JPanel();
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
										
					t.setName(name.getText());
					t.setType(atype.getText());
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
	}
}

package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class RemoveAcct {
	
	String user = "",msg="";
	int acctnum=0;

	public RemoveAcct(TransactionObject t){
		
		JFrame frame;
		frame = new JFrame(t.getId()+" ATM");
		JPanel panel = new JPanel();
		
		JLabel lname = new JLabel("User Name");
		JTextField name = new JTextField(10);
		JLabel lacct = new JLabel("Account to be deleted");
		JTextField acct = new JTextField(10);
		JButton remove = new JButton("Delete");
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
		panel.add(lacct, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(acct, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		
		panel.add(remove,gbc);
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
		
		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "oracle");
					Statement stmt = con.createStatement();
					
					user = name.getText();
					acctnum = Integer.parseInt(acct.getText());
					
					
					
					
				String query1 = "delete from bankaccount where username = ? and acctnum = ?";
				PreparedStatement preparedStmt1 = con.prepareStatement(query1);
				preparedStmt1.setString(1, user);
				preparedStmt1.setInt(2, acctnum);
				preparedStmt1.execute();

				msg = msg+"Account Successfully Removed. ";

			} catch (Exception ex) {
				msg = msg+"Something went wrong!"+ex;
			}
				t.setMessage(msg);
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

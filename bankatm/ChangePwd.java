package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ChangePwd {
	
	String id = "", opwd = "",pwd="",msg="";

	public ChangePwd(TransactionObject t){
		
		JFrame frame;
		frame = new JFrame(t.getId()+" ATM");
		JPanel panel = new JPanel();
		
		
		JLabel ltpwd = new JLabel("New Password");
		JPasswordField tpwd = new JPasswordField(10);
		JLabel lnpwd = new JLabel("Re-enter Password");
		JPasswordField npwd = new JPasswordField(10);
		
		
		JButton change = new JButton("Change");
		JButton back = new JButton("Back");
		
		
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		panel.add(ltpwd, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(tpwd, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		gbc.gridx = 0;
		panel.add(lnpwd, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(npwd, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		
		panel.add(change,gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		
		panel.add(back,gbc);
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new User(t);
			}
		});
		
		change.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "oracle");
					Statement stmt = con.createStatement();
					opwd= String.valueOf(tpwd.getPassword());
					pwd= String.valueOf(npwd.getPassword());
					if(opwd.equals(pwd)){
					String query1 = " update banklogin set password = ? where userid = '" + t.getId() + "';";
					PreparedStatement ps1 = con.prepareStatement(query1);
					ps1.setString(1, pwd);
					ps1.execute();

				msg = msg+"Password Reset Successful. ";
					}else{
						msg = msg+"Password Mismatch. ";
					}

			} catch (Exception ex) {
				msg = msg+"Something went wrong!"+ex;
			}
				t.setMessage(msg);
				frame.setVisible(false);
				new User(t);
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

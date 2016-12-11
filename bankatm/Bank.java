package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Bank {
	String id = "na";
	TransactionObject t = new TransactionObject();

	public Bank() {
		JFrame frame;
		frame = new JFrame("ATM");
		JPanel panel = new JPanel();
		
		JLabel ulabel = new JLabel("User ID");
		JTextField userid = new JTextField(10);
		JLabel plabel = new JLabel("Password");
		JPasswordField pwd = new JPasswordField(10);
		JButton login = new JButton("Login");
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(ulabel, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(userid, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		gbc.gridx = 0;
		panel.add(plabel, gbc);
		gbc.gridx = gbc.gridx + 10;
		panel.add(pwd, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(login, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Login Validation
				try {
					Class.forName("com.mysql.jdbc.Driver");
					java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root",
							"oracle");
					Statement stmt = con.createStatement();
					ResultSet res = stmt
							.executeQuery("select * from banklogin where userid = '" + userid.getText() + "';");
					if (res.next()) {
						if (res.getString(1).equals(userid.getText())
								&& res.getString(2).equals(String.valueOf(pwd.getPassword()))) {

							System.out.println("Login Successfull");
							t.setId(userid.getText());
							id = t.getId();
							System.out.println("Welcome " + id);
							if (id.equals("admin")) {
								t.setMessage("Welcome "+id);
								frame.setVisible(false);
								new Admin(t);
							} else {
								t.setMessage("Welcome "+id);
								frame.setVisible(false);
								new User(t);
							}

						}
					}

				} catch (Exception exc) {
					exc.printStackTrace();
				}
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

	public static void main(String[] args) {
		new Bank();

	}

}

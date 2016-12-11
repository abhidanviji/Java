package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Deposit {
	String msg = "";
	String acct[];
	int count = 0;
	Double amto = 0.0, amfrom = 0.0;
	
	public Deposit(TransactionObject t){
		try {

			Class.forName(t.className);
			Connection con = DriverManager.getConnection(t.host+t.db, t.user, t.pwd);
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("select acctnum from bankaccount where userid = '" + t.getId() + "';");

			while (res.next()) {
				count++;

			}
			acct = new String[count];
			ResultSet res1 = stmt.executeQuery("select acctnum from bankaccount where userid = '" + t.getId() + "';");
			count = 0;
			while (res1.next()) {
				acct[count] = res1.getString(1);
				count++;
			}

			JFrame frame;
			frame = new JFrame(t.getId() + " ATM");
			JPanel panel = new JPanel();

			JLabel lto = new JLabel("Account to Deposit to");
			// JTextField from = new JTextField(10);
			JComboBox to = new JComboBox(acct);
			
			JLabel lamt = new JLabel("Amount to Deposit");
			JTextField amt = new JTextField(10);

			JButton deposit = new JButton("Deposit");
			JButton back = new JButton("Back");

			

			panel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = 3;
			gbc.gridx = 0;
			gbc.gridy = 0;

			panel.add(lto, gbc);
			gbc.gridx = gbc.gridx + 10;
			panel.add(to, gbc);
			gbc.gridwidth = 1;
			gbc.gridy = gbc.gridy + 5;
			gbc.gridx = 0;
			
			panel.add(lamt, gbc);
			gbc.gridx = gbc.gridx + 10;
			panel.add(amt, gbc);
			gbc.gridwidth = 1;
			gbc.gridy = gbc.gridy + 5;

			panel.add(deposit, gbc);
			gbc.gridwidth = 1;
			gbc.gridy = gbc.gridy + 5;

			panel.add(back, gbc);

			back.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					new User(t);
				}
			});

			deposit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					t.setNum((String) to.getSelectedItem());
					try {
						

						ResultSet rs = stmt.executeQuery(
								"select amount from bankaccount where acctnum = '" + t.getNum() + "';");

						if (rs.next()) {
							amto = rs.getDouble(1);
							
								t.setAmount(amto.floatValue() + Float.parseFloat(amt.getText()) );
								
								String query = " update bankaccount set amount = ? where acctnum = '"
										+ t.getNum() + "';";
								PreparedStatement ps = con.prepareStatement(query);
								ps.setDouble(1, t.getAmount());
								ps.execute();

								msg = msg + "Deposit Successfull. ";
							}

						
						con.close();
					} catch (Exception ex) {
						msg = msg + "Something went wrong!" + ex;
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

		} catch (Exception exc) {

		}
	}
}

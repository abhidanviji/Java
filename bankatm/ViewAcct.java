package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ViewAcct {
	String msg = "", det = "";

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}

	public ViewAcct(TransactionObject t) {
		JFrame frame;
		frame = new JFrame(t.getId() + " ATM");
		JPanel panel = new JPanel();

		// JTextArea details = new JTextArea(5,8);
		JTextPane details = new JTextPane();
		JButton back = new JButton("Back");

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;

		panel.add(details, gbc);

		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 20;

		panel.add(back, gbc);

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (t.getId().equals("admin")) {
					frame.setVisible(false);
					new Admin(t);
				} else {
					frame.setVisible(false);
					new User(t);
				}
			}
		});

		try {
			Class.forName(t.className);
			Connection con = DriverManager.getConnection(t.host+t.db, t.user, t.pwd);
			Statement stmt = con.createStatement();

			if (t.getId().equals("admin")) {
				ResultSet res = stmt.executeQuery("select * from bankaccount;");
				det = det + padRight("Name", 20) + padRight("Acct No", 20) + padRight("Acct Type", 20)
						+ padRight("Amount", 20) + padRight("Date", 20) + "\n";
				det = det + "**********************************************************************************\n";
				while (res.next()) {
					det = det + padRight(res.getString("username"), 20)
							+ padRight(String.valueOf(res.getInt("acctnum")), 20) + padRight(res.getString("type"), 20)
							+ padRight(String.valueOf(res.getDouble("amount")), 20)
							+ padRight(String.valueOf(res.getDate("date")), 20) + "\n";
				}
			} else {

				ResultSet res = stmt.executeQuery("select * from bankaccount where userid = '" + t.getId() + "';");
				det = det + padRight("Name", 20) + padRight("Acct No", 20) + padRight("Acct Type", 20)
						+ padRight("Amount", 20) + padRight("Date", 20) + "\n";
				det = det + "**********************************************************************************\n";
				while (res.next()) {
					det = det + padRight(res.getString("username"), 20)
							+ padRight(String.valueOf(res.getInt("acctnum")), 20) + padRight(res.getString("type"), 20)
							+ padRight(String.valueOf(res.getDouble("amount")), 20)
							+ padRight(String.valueOf(res.getDate("date")), 20) + "\n";
				}
			}
			details.setText(det);
			details.setFocusable(false);

			con.close();
		} catch (Exception ex) {
			msg = msg + "Something went wrong!" + ex;
		}

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

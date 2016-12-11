package bankatm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Admin {
	public Admin(TransactionObject t) {
		JFrame frame;
		frame = new JFrame(t.getId() + " ATM");
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();

		JTextPane msg = new JTextPane();
		msg.setText(t.getMessage());
		msg.setFocusable(false);

		JButton newuser = new JButton("New User");
		JButton addacct = new JButton("Add Account");
		JButton removeacct = new JButton("Remove Account");
		JButton deleteuser = new JButton("Delete User");
		JButton logout = new JButton("Logout");

		newuser.setPreferredSize(new Dimension(200, 20));
		addacct.setPreferredSize(new Dimension(200, 20));
		removeacct.setPreferredSize(new Dimension(200, 20));
		deleteuser.setPreferredSize(new Dimension(200, 20));
		logout.setPreferredSize(new Dimension(200, 20));

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel1.add(msg);

		panel.add(newuser, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(addacct, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(removeacct, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(deleteuser, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(logout, gbc);

		newuser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new NewUser(t);
			}
		});

		addacct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new AddAcct(t);
			}
		});

		removeacct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new RemoveAcct(t);
			}
		});
		
		deleteuser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new DeleteUser(t);
			}
		});

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Bank();
			}
		});

		frame.add(panel, BorderLayout.CENTER);
		frame.add(panel1, BorderLayout.NORTH);
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

package bankatm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class User {

	public User(TransactionObject t){
		JFrame frame;
		frame = new JFrame(t.getId()+" ATM");
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		
		JTextPane msg = new JTextPane();
		msg.setText(t.getMessage());
		
		JButton changepwd = new JButton("Change Password");
		JButton viewacct = new JButton("View Account Details");
		JButton intratrans = new JButton("Intra Account Transfer");
		JButton intertrans = new JButton("Inter Account Transfer");
		JButton logout = new JButton("Logout");
		
		changepwd.setPreferredSize(new Dimension(200, 20));
		viewacct.setPreferredSize(new Dimension(200, 20));
		intratrans.setPreferredSize(new Dimension(200, 20));
		intertrans.setPreferredSize(new Dimension(200, 20));
		logout.setPreferredSize(new Dimension(200, 20));
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel1.add(msg);
		panel.add(changepwd,gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(viewacct,gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(intratrans,gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(intertrans,gbc);
		gbc.gridwidth = 1;
		gbc.gridy = gbc.gridy + 5;
		panel.add(logout,gbc);
		
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

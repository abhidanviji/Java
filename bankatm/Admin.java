package bankatm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Admin {
	public Admin(TransactionObject t){
		JFrame frame;
		frame = new JFrame(t.getId()+" ATM");
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JTextField t1 = new JTextField();
		t1.setText("Hi");
		
		
		JButton newuser = new JButton("New User");
		JButton addacct = new JButton("Add Account");
		JButton removeacct = new JButton("Remove Account");
		JButton deleteuser = new JButton("Delete User");
		JButton logout = new JButton("Logout");
		
		newuser.setPreferredSize(new Dimension(150, 20));
		addacct.setPreferredSize(new Dimension(150, 20));
		removeacct.setPreferredSize(new Dimension(150, 20));
		deleteuser.setPreferredSize(new Dimension(150, 20));
		logout.setPreferredSize(new Dimension(150, 20));
		
		
		
		panel.add(newuser);
		panel.add(addacct);
		panel.add(removeacct);
		panel.add(deleteuser);
		panel.add(logout);
		
		newuser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				panel1.add(t1);
				
			
			}
		});
		
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Bank();
			}
		});
		
		frame.add(panel, BorderLayout.NORTH);
		frame.add(panel1, BorderLayout.CENTER);
		frame.pack();
		Dimension frameDim = frame.getSize();
		frame.setSize(850, 400);
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

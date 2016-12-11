package bankatm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class RemoveAcct {
	
	String msg="";
	int count=0;
	String acctnum[];
	

	public RemoveAcct(TransactionObject t){
		try{
			Class.forName(t.className);
			Connection con = DriverManager.getConnection(t.host+t.db, t.user, t.pwd);
			Statement stmt = con.createStatement();
			
			ResultSet res = stmt.executeQuery("select acctnum from bankaccount;");

			while (res.next()) {
				count++;

			}
			acctnum = new String[count];
			ResultSet res1 = stmt.executeQuery("select acctnum from bankaccount;");
			count = 0;
			while (res1.next()) {
				acctnum[count] = res1.getString(1);
				count++;
			}
			
		JFrame frame;
		frame = new JFrame(t.getId()+" ATM");
		JPanel panel = new JPanel();
		
		JLabel lacct = new JLabel("Account to be deleted");
		JComboBox acct = new JComboBox(acctnum);
		JButton remove = new JButton("Delete");
		JButton back = new JButton("Back");
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
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
					
					
					t.setNum((String)acct.getSelectedItem());
					
					
					
				String query1 = "delete from bankaccount where acctnum = ?";
				PreparedStatement preparedStmt1 = con.prepareStatement(query1);
				preparedStmt1.setInt(1, Integer.parseInt(t.getNum()));
				preparedStmt1.execute();

				msg = msg+"Account Successfully Removed. ";
				con.close();
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
		
		}catch(Exception e){
			
		}
	}
}

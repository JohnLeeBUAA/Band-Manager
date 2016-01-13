package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

//发布团内通知窗口类
public class makeannouncementframe 
{
	private JFrame f;
	private JTextField input;
	private JButton save;
	private JButton back;
	private String username;
	
	public makeannouncementframe(String name)
	{
		username=new String(name);
		f=new JFrame("发布通知");
		input=new JTextField("",40);
		save=new JButton("发布");
		back=new JButton("退出编辑");
		
		save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getinput=input.getText();
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
			    	Connection c
			    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			    	Statement s=c.createStatement();
			    	s.executeUpdate(
			    			"update announcement set content=\'"+getinput+"\'where id=1");
			    	
			    	//将每个人的查看状态参数设为1--未看
			    	s.executeUpdate(
			    			"update personlist set checkstatus=1");
			    	
			    	s.close();
			    	c.close();
			    	JOptionPane.showMessageDialog(null, "发布成功");
			    	managerframe a=new managerframe(username);
					a.buildmanagerframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		back.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {
				try
				{
					managerframe a=new managerframe(username);
					a.buildmanagerframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
	}
	
	public void buildmakeannouncementframe()
	{
		f.setSize(400,100);
		f.setLayout(new GridLayout(2,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,2));
		p.add(save);
		p.add(back);
		f.add(input);
		f.add(p);
		f.setVisible(true);
	}

}

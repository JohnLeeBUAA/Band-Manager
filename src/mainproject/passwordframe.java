package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//更改密码窗口类
public class passwordframe 
{
	private String username;
	private int identity;
	private JFrame f;
	private JLabel password;
	private JLabel password2;
	private JPasswordField passwordinput;
	private JPasswordField passwordinput2;
	private JButton save;
	private JButton back;
	
	public passwordframe(String name,int id)
	{
		username=new String(name);
		identity=id;
		f=new JFrame("更改密码");
		password=new JLabel("输入新密码",JLabel.CENTER);
		password2=new JLabel("确认密码",JLabel.CENTER);
		passwordinput=new JPasswordField("",40);
		passwordinput2=new JPasswordField("",40);
		save=new JButton("保存");
    	back=new JButton("退出编辑");
    	
    	save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getpassword=new String(passwordinput.getText());
					String getpassword2=new String(passwordinput2.getText());
					if(getpassword.equals("")||getpassword2.equals(""))
					{   //判断是否有空输入
						JOptionPane.showMessageDialog(null, "输入为空");
				    }
					else if(!(getpassword.equals(getpassword2)))
					{
						JOptionPane.showMessageDialog(null, "您两次输入的密码不相同");
					}
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
				    	Connection c
				    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
				    	Statement s=c.createStatement();
				    	s.executeUpdate(
				    			"update personlist set password=\'"+getpassword+"\'where username=\'"+username+"\'");
				    	s.close();
				    	c.close();
				    	
				    	JOptionPane.showMessageDialog(null, "保存成功");
				    	
				    	if(identity==1)
						{
							memberframe a=new memberframe(username);
							a.buildmemberframe();
						}
						else if(identity==2)
						{
							managerframe a=new managerframe(username);
							a.buildmanagerframe();
						}
						else
						{
							guestframe a=new guestframe(username);
							a.buildguestframe();
						}
						f.dispose();
					}
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
					if(identity==1)
					{
						memberframe a=new memberframe(username);
						a.buildmemberframe();
					}
					else if(identity==2)
					{
						managerframe a=new managerframe(username);
						a.buildmanagerframe();
					}
					else
					{
						guestframe a=new guestframe(username);
						a.buildguestframe();
					}
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
	
	public void buildpasswordframe()
	{
		f.setSize(400,200);
		f.setLayout(new GridLayout(3,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(password);
		f.add(passwordinput);
		f.add(password2);
		f.add(passwordinput2);
		f.add(save);
		f.add(back);
		f.setVisible(true);
	}
	

}

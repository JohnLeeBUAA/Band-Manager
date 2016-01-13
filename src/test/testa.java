package test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

/*
 * 初始界面
 */
public class testa
{
	
	private JFrame f;
	private JLabel username;
	private JLabel password;
	private JLabel identity;
	private JTextField usernameinput;
	private JPasswordField passwordinput;
	private JComboBox identityselect;
	private JButton signin;
	private JButton register;
	
	public testa()
	{             //构造函数
		
		f=new JFrame("登陆界面");
		username=new JLabel("用户名");
		password=new JLabel("密码");
		identity=new JLabel("身份");
		usernameinput=new JTextField("",40);
		passwordinput=new JPasswordField("",40);
		String[] s={"团员","管理员","游客"};
		identityselect=new JComboBox(s);
		signin=new JButton("登录");
		register=new JButton("注册一个账号");
		
		
	}
	
	public void buildstartframe()
	{
		
		f.setSize(400,200);
		f.setLayout(new GridLayout(4,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(username);
		f.add(usernameinput);
		f.add(password);
		f.add(passwordinput);
		f.add(identity);
		f.add(identityselect);
		f.add(signin);
		f.add(register);
		f.setVisible(true);
	}
	
	
	
	public static void main(String[] args)
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
	    	Connection c
	    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
	    	Statement s=c.createStatement();
	    	s.executeUpdate(
	    	"update assess set concert1=90, concert2=90 where username=\'bx\'");
	    	System.out.println("1");
	    	
	    	s.close();
	    	c.close();
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
	}
	

}


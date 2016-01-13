package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

//团员窗口类
public class memberframe 
{
	
	private String username;
	private JFrame f;
	private JButton check;			//查看团内消息
	private JButton evaluate;		//查看管理员评价
	private JButton pu;				//乐谱
	private JButton checkgrade;		//查看成绩
	private JButton bandblog;
	private JButton personal;
	private JButton share;
	private JButton logoff;
	private JButton changepassword;
	private String checkcontent;   //记录团内消息内容
	
	public memberframe(String name)
	{
		
		username=new String(name);
		f=new JFrame("您好："+username+"，您的身份是团员");
		check=new JButton("查看团内消息");
		evaluate=new JButton("查看管理员评价");
		bandblog=new JButton("团日志");
		personal=new JButton("个人资料");
		share=new JButton("交流区");
		logoff=new JButton("注销");
		changepassword=new JButton("修改密码");
		pu=new JButton("查看乐谱");
		checkgrade=new JButton("查看考核成绩");
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
    		Connection c
    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
    		Statement s=c.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
    		ResultSet r=s.executeQuery("select * From announcement");
    		r.beforeFirst();
    		while(r.next())
    		{
    			if(r.getInt("id")==1)
    			{
    				checkcontent=new String(r.getString("content"));
    			}
			}
    		s.close();
    		c.close();
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
		
		evaluate.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
		    		Connection c
		    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
		    		Statement s=c.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
		    		ResultSet r=s.executeQuery("select * From personlist");
		    		r.beforeFirst();
		    		while(r.next())
		    		{
		    			if(r.getString("username").equals(username))
		    			{
		    				String temp=new String(r.getString("managerevaluate"));
		    				if(temp.equals(""))
		    					JOptionPane.showMessageDialog(null, "当前无信息");
		    				else
		    					JOptionPane.showMessageDialog(null, temp);
		    				break;
		    			}
					}
		    		s.close();
		    		c.close();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		bandblog.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					bandblogframe a=new bandblogframe(username,1);
					a.buildbandblogframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		pu.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					yuepuframe a=new yuepuframe(username);
					a.buildyuepuframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		check.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					JOptionPane.showMessageDialog(null, checkcontent);
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		personal.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					personalframe a=new personalframe(username,1);
					a.buildpersonalframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		logoff.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				try
				{
					startframe a=new startframe();
					a.buildstartframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		changepassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					passwordframe a=new passwordframe(username,1);
					a.buildpasswordframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		share.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					shareframe a=new shareframe(username,1);
					a.buildshareframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		checkgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					checkgradeframe a=new checkgradeframe(username);
					a.buildcheckgradeframe();
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
	
	public void buildmemberframe()
	{
		
		f.setSize(400,400);
		f.setLayout(new GridLayout(9,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(check);
		f.add(evaluate);
		f.add(checkgrade);
		f.add(pu);
		f.add(bandblog);
		f.add(share);
		f.add(personal);
		f.add(changepassword);
		f.add(logoff);
		f.setVisible(true);
		
		//如果查看状态是1--未看，那么打开团员窗口自动弹出最新团内消息
		//并把查看状态设为2--已看
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
    		Connection c
    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
    		Statement s=c.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
    		ResultSet r=s.executeQuery("select * From personlist");
    		r.beforeFirst();
    		while(r.next())
    		{
    			if(r.getString("username").equals(username))
    			{
    				if(r.getInt("checkstatus")==1)
    				{
    					JOptionPane.showMessageDialog(null, checkcontent);
    					s.executeUpdate(
    							"update personlist set checkstatus=2 where username=\'"+username+"\'");
    				}
    				break;
    			}
			}
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

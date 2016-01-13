package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//乐团日志窗口类
public class bandblogframe 
{
	private JFrame f;    			//窗口
	private JButton[] namelist;    	//日志名列表，按键形式，点击查看
	private JButton write;			//写日志键
	private JButton back;			//返回键
	private String username;		//记录当前用户名
	private int identity;			//记录当前用户身份
	private int counter;			//记录当前用户日志数，初始化按键用
	
	
	//构造函数
	public bandblogframe(String name,int id)	
	{
		username=new String(name);
		identity=id;
		f=new JFrame("点击标题查看个人日志");
		write=new JButton("写日志");
		back=new JButton("返回");
		counter=0;
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
			Connection c
			=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			Statement s=c.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet r=s.executeQuery("select * From bandblog");
			r.beforeFirst();
			while(r.next())
			{
				if(username.equals(r.getString("username")))		//统计当前用户日志数
					counter++;
			}
			namelist=new JButton[counter];							//初始化按键数组
			counter=0;												//counter置零，用来构造按键数组
			r.beforeFirst();
			while(r.next())
			{
				if(username.equals(r.getString("username")))
				{
					namelist[counter]=new JButton(r.getString("title"));		//构造按键数组
					final int zz=r.getInt("id");					//final值传参用，记录日志id
					
					//设置按键监听器
					namelist[counter].addActionListener(new ActionListener() {   
						public void actionPerformed(ActionEvent e) {
							try
							{
								//点击后构造查看日志的新窗口
								checkbandblogframe a=new checkbandblogframe(username,identity,zz);		
								a.buildcheckbandblogframe();
								f.dispose();
							}
							catch(Exception ex)
							{
								System.err.println("Exception :" + ex);
								ex.printStackTrace();
							}
						}
					});
					
					counter++;
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
		
		//写日志按键监听器
		write.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					//点击构建写日志的新窗口
					writebandblogframe a=new writebandblogframe(username,identity);
					a.buildwritebandblogframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		
		//返回键按键监听器
		back.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					//按当前用户身份和用户名构建对应窗口
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
	
	//窗口搭建函数
	public void buildbandblogframe()
	{
		if(counter!=0)		//如果当前用户日志数不为零
		{
			f.setSize(300,(counter+1)*50);
			f.setLayout(new GridLayout(counter+1,1));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel p=new JPanel();
			p.setLayout(new GridLayout(1,2));
			p.add(write);
			p.add(back);
			for(int i=0;i<counter;i++)
				f.add(namelist[i]);
			f.add(p);
			f.setVisible(true);
		}
		else			//如果当前用户日志数为零
		{
			f.setSize(300,100);
			f.setLayout(new GridLayout(2,1));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel p=new JPanel();
			p.setLayout(new GridLayout(1,2));
			p.add(write);
			p.add(back);
			JLabel l=new JLabel("还没有写日志",JLabel.CENTER);
			f.add(l);
			f.add(p);
			f.setVisible(true);
		}
	}

}

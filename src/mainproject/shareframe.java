package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//交流区窗口类
public class shareframe 
{
	private JFrame f;
	private JTextArea t;		//交流区文本域
	private JScrollPane s;		//滑动条
	private JLabel l;
	private JTextField tf;		//输入个人想法的文本框
	private JButton publish;
	private JButton back;
	private String username;
	private int identity;
	
	public shareframe(String name,int id)
	{
		username=new String(name);
		identity=id;
		f=new JFrame("交流区");
		t=new JTextArea("",10,10);
		s=new JScrollPane(t);
		l=new JLabel("分享你的想法：");
		tf=new JTextField();
		publish=new JButton("发表");
		back=new JButton("返回");
		
		
		//设置交流区文本域内容
		//为所有大家交流的记录
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
			Connection c
			=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			Statement s=c.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet r=s.executeQuery("select * From share");
			r.beforeFirst();
			while(r.next())
			{
				t.append(r.getString("username")+" 说："+r.getString("content")+"\n");
			}
			s.close();
			c.close();
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
		
		//发表按键监听器
		publish.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getinput=new String(tf.getText());
					if(getinput.equals(""))
						JOptionPane.showMessageDialog(null, "输入为空");
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						s.executeUpdate(
								"insert into share(username,content) values(\'"+username+"\',\'"+getinput+"\')");
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "发布成功");
						f.dispose();
						shareframe a=new shareframe(username,identity);
						a.buildshareframe();
					}
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		
		//返回按键监听器
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
	
	public void buildshareframe()
	{
		f.setSize(500,400);
		f.setLayout(new GridLayout(2,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setEditable(false);		//设置交流区文本域为不可编辑
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(3,1));
		JPanel pp=new JPanel();
		pp.setLayout(new GridLayout(1,2));
		pp.add(publish);
		pp.add(back);
		p.add(l);
		p.add(tf);
		p.add(pp);
		f.add(s);
		f.add(p);
		f.setVisible(true);
	}

}

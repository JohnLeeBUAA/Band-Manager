package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//查看日志窗口类
public class checkbandblogframe 
{
	private JFrame f;				//窗口
	private JTextArea title;		//标题文本域
	private JTextArea content;		//正文文本域
	private JButton delete;			//删除键
	private JButton back;			//返回键
	private JButton edit;			//编辑键
	private String username;		//当前用户用户名
	private int identity;			//当前用户身份
	private int blogid;				//日志id
	private String gettitle;		//记录标题的字符串
	private String getcontent;		//记录内容的字符串
	
	
	//构造函数
	public checkbandblogframe(String name,int id,int idblog)
	{
		username=new String(name);
		identity=id;
		blogid=idblog;
		f=new JFrame("查看日志");
		title=new JTextArea("",1,10);
		content=new JTextArea("",10,10);
		delete=new JButton("删除");
		edit=new JButton("编辑");
		back=new JButton("返回");
		gettitle=new String("");
		getcontent=new String("");
		
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
				if(blogid==r.getInt("id"))		//找到对应日志id的日志
				{
					//更新数据
					gettitle=r.getString("title");
					getcontent=r.getString("content");
					title.setText(gettitle);
					content.setText(getcontent);
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
		
		//删除键按键监听器
		delete.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					int option = JOptionPane.showConfirmDialog(null, "确认删除日志 ："
							+gettitle+"  吗？","Attention", JOptionPane.YES_NO_OPTION);
					if(option==0)
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						s.executeUpdate("delete from bandblog where id="+blogid);
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "删除成功");
						bandblogframe a=new bandblogframe(username,identity);
						a.buildbandblogframe();
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
		
		//返回键按键监听器
		back.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					bandblogframe a=new bandblogframe(username,identity);
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
		
		//编辑键按键监听器
		edit.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					//构建编辑日志窗口类
					editbandblogframe a=new editbandblogframe(username,identity,blogid);
					a.buildeditbandblogframe();
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
	public void buildcheckbandblogframe()
	{
		f.setSize(500,400);
		f.setLayout(new BorderLayout(0,10));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,3));
		p.add(edit);
		p.add(delete);
		p.add(back);
		title.setEditable(false);
		content.setEditable(false);
		f.add(title,BorderLayout.NORTH);
		f.add(content,BorderLayout.CENTER);
		f.add(p,BorderLayout.SOUTH);
		f.setVisible(true);

	}


}

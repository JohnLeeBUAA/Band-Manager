package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//编辑日志窗口类
public class editbandblogframe 
{
	private JFrame f;
	private JTextArea title;
	private JTextArea content;
	private JButton save;
	private JButton back;
	private String username;
	private int identity;
	private int blogid;
	
	public editbandblogframe(String name,int id,int idblog)
	{
		username=new String(name);
		identity=id;
		blogid=idblog;
		f=new JFrame("编辑日志");
		title=new JTextArea("",1,10);
		content=new JTextArea("",10,10);
		title.setEditable(true);
		content.setEditable(true);
		save=new JButton("保存");
		back=new JButton("返回");
		
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
				if(blogid==r.getInt("id"))
				{
					title.setText(r.getString("title"));
					content.setText(r.getString("content"));
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
		
		save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String gettitle=new String(title.getText());
					String getcontent=new String(content.getText());
					if(gettitle.equals("")||getcontent.equals("")
							||gettitle.equals("题目：")||getcontent.equals("正文："))
						JOptionPane.showMessageDialog(null, "输入为空");
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						s.executeUpdate(
								"update bandblog set title=\'"+gettitle+"\' where id="+blogid);
						s.executeUpdate(
								"update bandblog set content=\'"+getcontent+"\' where id="+blogid);
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "保存成功");
						checkbandblogframe a=new checkbandblogframe(username,identity,blogid);
						a.buildcheckbandblogframe();
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
					checkbandblogframe a=new checkbandblogframe(username,identity,blogid);
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
	}
	
	public void buildeditbandblogframe()
	{
		f.setSize(500,400);
		f.setLayout(new BorderLayout(0,10));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,2));
		p.add(save);
		p.add(back);
		f.add(title,BorderLayout.NORTH);
		f.add(content,BorderLayout.CENTER);
		f.add(p,BorderLayout.SOUTH);
		f.setVisible(true);

	}

}

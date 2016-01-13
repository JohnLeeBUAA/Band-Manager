package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//写日志窗口类
public class writebandblogframe 
{
	private JFrame f;
	private JTextArea title;      	//标题文本域
	private JTextArea content;		//正文文本域
	private JButton save;
	private JButton back;
	private String username;
	private int identity;
	
	public writebandblogframe(String name,int id)
	{
		username=new String(name);
		identity=id;
		f=new JFrame("写日志");
		title=new JTextArea("题目：",1,10);
		content=new JTextArea("正文：",10,10);
		save=new JButton("发表");
		back=new JButton("返回");
		
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
								"insert into bandblog(username,content,title) values(\'"+username+"\',\'"+getcontent+"\',\'"+gettitle+"\')");
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "保存成功");
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
	}
	
	public void buildwritebandblogframe()
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

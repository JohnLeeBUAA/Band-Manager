package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//游客窗口类，游客身份为3
public class guestframe 
{
	
	private String username;		//记录当前用户用户名
	private JFrame f;
	private JButton culture;		//乐团文化按键（无功能）
	private JButton link;			//链接区按键（无功能）
	private JButton personal;		//个人资料按键
	private JButton share;			//交流区按键
	private JButton logoff;			//注销按键
	private JButton changepassword;	//修改密码按键
	
	public guestframe(String name)
	{
		
		username=new String(name);
		f=new JFrame("您好："+username+"，您的身份是游客");
		culture=new JButton("乐团文化");
		link=new JButton("链接区");
		personal=new JButton("个人资料");
		share=new JButton("交流区");
		logoff=new JButton("注销");
		changepassword=new JButton("修改密码");
		
		//个人资料按键监听器
		personal.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					//构建个人资料窗口类
					personalframe a=new personalframe(username,3);
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
					passwordframe a=new passwordframe(username,3);
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
					shareframe a=new shareframe(username,3);
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
		
	}
	
	public void buildguestframe()
	{
		
		f.setSize(400,400);
		f.setLayout(new GridLayout(6,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(culture);
		f.add(link);
		f.add(share);
		f.add(personal);
		f.add(changepassword);
		f.add(logoff);
		f.setVisible(true);
	}
	
}

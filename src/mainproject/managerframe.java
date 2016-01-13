package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//管理员窗口类
public class managerframe 
{
	
	private String username;		//记录用户名
	private JFrame f;
	private JButton publish;		//发布团内消息
	private JButton personal;		//个人资料
	private JButton share;			//交流区
	private JButton bandblog;		//团日志
	private JButton manage;			//管理团员
	private JButton manageinstrument;	//管理乐器
	private JButton manageconcert;		//管理演出
	private JButton logoff;			//注销
	private JButton changepassword;	//修改密码
	private JButton assessmember;	//团员考核
	
	public managerframe(String name)
	{
		
		username=new String(name);
		f=new JFrame("您好："+username+"，您的身份是管理员");
		bandblog=new JButton("团日志");
		publish=new JButton("发布团内消息");
		personal=new JButton("个人资料");
		share=new JButton("交流区");
		manage=new JButton("管理团员");
		logoff=new JButton("注销");
		changepassword=new JButton("修改密码");
		manageinstrument=new JButton("管理乐器");
		manageconcert=new JButton("管理演出");
		assessmember=new JButton("签到及考核");
		
		publish.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					makeannouncementframe a=new makeannouncementframe(username);
					a.buildmakeannouncementframe();
					f.dispose();
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
					bandblogframe a=new bandblogframe(username,2);
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
		
		personal.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					personalframe a=new personalframe(username,2);
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
		
		manageconcert.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					manageconcertframe a=new manageconcertframe(username);
					a.buildmanageconcertframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		assessmember.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					assessmemberframe a=new assessmemberframe(username);
					a.buildassessmemberframe();
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
		
		manageinstrument.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					manageinstrumentframe a=new manageinstrumentframe(username);
					a.buildmanageinstrumentframe();
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
					passwordframe a=new passwordframe(username,2);
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
					shareframe a=new shareframe(username,2);
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
		
		manage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					managememberframe a=new managememberframe(username,2);
					a.buildmanagememberframe();
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
	
	public void buildmanagerframe()
	{
		
		f.setSize(400,500);
		f.setLayout(new GridLayout(10,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(publish);
		f.add(manage);
		f.add(manageinstrument);
		f.add(manageconcert);
		f.add(assessmember);
		f.add(bandblog);
		f.add(share);
		f.add(personal);
		f.add(changepassword);
		f.add(logoff);
		f.setVisible(true);
	}
	
}

package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//个人资料窗口类
//注册的第二步用此窗口
//对应身份主页面个人资料用此窗口
//管理团员查看团员信息用此窗口
public class personalframe 
{
	
	private JFrame f;
	private JTextField realnameinput;			//真实姓名
	private JLabel realname;
	private JTextField sexinput;				//性别
	private JLabel sex;
	private JTextField schoolinput;				//学校
	private JLabel school;
	private JTextField instrumentinput;			//乐器
	private JLabel instrument;
	private JTextField contactinput;			//联系方式
	private JLabel contact;
	private JTextField majorinput;				//专业
	private JLabel major;
	private JTextField instrumentbelonginginput;			//乐器归属
	private JLabel instrumentbelonging;
	private JButton save;
	private JButton back;
    private String username;
    private int identity;
    
    public personalframe(String name,int id)    //两个参数的构造函数用于正常退出
    {
    	username=new String(name);
    	identity=id;
    	f=new JFrame("个人资料 ");
    	realnameinput=new JTextField("",40);
    	realname=new JLabel("真实姓名",JLabel.CENTER);
    	sexinput=new JTextField("",40);
    	sex=new JLabel("性别（m/f）",JLabel.CENTER);
    	schoolinput=new JTextField("",40);
    	school=new JLabel("学校",JLabel.CENTER);
    	instrumentinput=new JTextField("",40);
    	instrument=new JLabel("乐器",JLabel.CENTER);
    	contact=new JLabel("联系方式",JLabel.CENTER);
    	contactinput=new JTextField("",40);
    	major=new JLabel("专业",JLabel.CENTER);
    	majorinput=new JTextField("",40);
    	instrumentbelonging=new JLabel("乐器归属（学校/个人）",JLabel.CENTER);
    	instrumentbelonginginput=new JTextField("",40);
    	save=new JButton("保存");
    	back=new JButton("退出编辑");
    	
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
    			//初始化文本内容，使得打开时可看到已有信息
    			if(username.equals(r.getString("username")))
    			{
    				realnameinput.setText(r.getString("realname"));
    				sexinput.setText(r.getString("sex"));
    				schoolinput.setText(r.getString("school"));
    				instrumentinput.setText(r.getString("instrument"));
    				contactinput.setText(r.getString("contact"));
    				majorinput.setText(r.getString("major"));
    				instrumentbelonginginput.setText(r.getString("instrumentbelonging"));
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
					String getrealname=new String(realnameinput.getText());
					String getsex=new String(sexinput.getText());
					String getschool=new String(schoolinput.getText());
					String getinstrument=new String(instrumentinput.getText());
					String getcontact=new String(contactinput.getText());
					String getmajor=new String(majorinput.getText());
					String getinstrumentbelonging=new String(instrumentbelonginginput.getText());
					if(getrealname.equals("")||getsex.equals("")||getschool.equals("")
							||getmajor.equals("")||getcontact.equals(""))
					{   //判断是否有空输入
						JOptionPane.showMessageDialog(null, "请填写完全");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
				    	Connection c
				    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
				    	Statement s=c.createStatement();
				    	s.executeUpdate(
				    			"update personlist set realname=\'"+getrealname+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set sex=\'"+getsex+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set school=\'"+getschool+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set instrument=\'"+getinstrument+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set instrumentbelonging=\'"+getinstrumentbelonging+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set contact=\'"+getcontact+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set major=\'"+getmajor+"\'where username=\'"+username+"\'");
				    	
				    	s.close();
				    	c.close();
				    	JOptionPane.showMessageDialog(null, "保存成功");
				    	
				    	//根据用户名和身份返回
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
    	
    	//根据用户名和身份返回
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
    
    public personalframe(String name,int id,String managername)   //三个参数的构造函数用于返回管理团员界面
    {
    	username=new String(name);
    	identity=id;
    	f=new JFrame("个人资料 ");
    	realnameinput=new JTextField("",40);
    	realname=new JLabel("真实姓名",JLabel.CENTER);
    	sexinput=new JTextField("",40);
    	sex=new JLabel("性别",JLabel.CENTER);
    	schoolinput=new JTextField("",40);
    	school=new JLabel("学校",JLabel.CENTER);
    	instrumentinput=new JTextField("",40);
    	instrument=new JLabel("乐器",JLabel.CENTER);
    	contact=new JLabel("联系方式",JLabel.CENTER);
    	contactinput=new JTextField("",40);
    	major=new JLabel("专业",JLabel.CENTER);
    	majorinput=new JTextField("",40);
    	instrumentbelonging=new JLabel("乐器归属（学校/个人）",JLabel.CENTER);
    	instrumentbelonginginput=new JTextField("",40);
    	save=new JButton("保存");
    	back=new JButton("退出编辑");
    	
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
    			if(username.equals(r.getString("username")))
    			{
    				realnameinput.setText(r.getString("realname"));
    				sexinput.setText(r.getString("sex"));
    				schoolinput.setText(r.getString("school"));
    				instrumentinput.setText(r.getString("instrument"));
    				contactinput.setText(r.getString("contact"));
    				majorinput.setText(r.getString("major"));
    				instrumentbelonginginput.setText(r.getString("instrumentbelonging"));
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
    	
    	
    	final String temp=new String(managername);
    	save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getrealname=new String(realnameinput.getText());
					String getsex=new String(sexinput.getText());
					String getschool=new String(schoolinput.getText());
					String getinstrument=new String(instrumentinput.getText());
					String getcontact=new String(contactinput.getText());
					String getmajor=new String(majorinput.getText());
					String getinstrumentbelonging=new String(instrumentbelonginginput.getText());
					if(getrealname.equals("")||getsex.equals("")||getschool.equals("")
							||getmajor.equals("")||getcontact.equals(""))
					{   //判断是否有空输入
						JOptionPane.showMessageDialog(null, "请填写完全");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
				    	Connection c
				    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
				    	Statement s=c.createStatement();
				    	s.executeUpdate(
				    			"update personlist set realname=\'"+getrealname+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set sex=\'"+getsex+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set school=\'"+getschool+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set instrument=\'"+getinstrument+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set instrumentbelonging=\'"+getinstrumentbelonging+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set contact=\'"+getcontact+"\'where username=\'"+username+"\'");
				    	s.executeUpdate(
				    			"update personlist set major=\'"+getmajor+"\'where username=\'"+username+"\'");
				    	
				    	
				    	
				    	s.close();
				    	c.close();
				    	JOptionPane.showMessageDialog(null, "保存成功");
				    	
				    	//返回到管理团员界面
				    	managememberframe a=new managememberframe(temp,2);
						a.buildmanagememberframe();
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
    	
    	//返回到管理团员界面
    	back.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					managememberframe a=new managememberframe(temp,2);
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
    
    public void buildpersonalframe()
    {
    	//根据身份不同搭建不同窗口
    	//游客与管理员窗口相同
    	//团员多出乐器与乐器归属两个项目
    	if(identity==1)		//如果是团员
    	{
    		f.setSize(500,400);
    		f.setLayout(new GridLayout(8,2));
    		f.setLocationRelativeTo(null);
    		f.setResizable(false);
    		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		f.add(realname);
    		f.add(realnameinput);
    		f.add(sex);
    		f.add(sexinput);
    		f.add(contact);
    		f.add(contactinput);
    		f.add(school);
    		f.add(schoolinput);
    		f.add(major);
    		f.add(majorinput);
    		f.add(instrument);
    		f.add(instrumentinput);
    		f.add(instrumentbelonging);
    		f.add(instrumentbelonginginput);
    		f.add(save);
    		f.add(back);
    		f.setVisible(true);
    	}
    	else
    	{
    		f.setSize(500,300);
    		f.setLayout(new GridLayout(6,2));
    		f.setLocationRelativeTo(null);
    		f.setResizable(false);
    		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		f.add(realname);
    		f.add(realnameinput);
    		f.add(sex);
    		f.add(sexinput);
    		f.add(contact);
    		f.add(contactinput);
    		f.add(school);
    		f.add(schoolinput);
    		f.add(major);
    		f.add(majorinput);
    		f.add(save);
    		f.add(back);
    		f.setVisible(true);
    	}
    }
	

}

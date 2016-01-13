package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//登陆界面窗口类
public class startframe 
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
	
	public startframe()
	{             //构造函数
		
		f=new JFrame("登陆界面");
		username=new JLabel("用户名",JLabel.CENTER);
		password=new JLabel("密码",JLabel.CENTER);
		identity=new JLabel("身份",JLabel.CENTER);
		usernameinput=new JTextField("",40);
		passwordinput=new JPasswordField("",40);
		String[] s={"团员","管理员","游客"};
		identityselect=new JComboBox(s);
		signin=new JButton("登录");
		register=new JButton("注册一个账号");
		
		signin.addActionListener(new ActionListener() {   //登录键
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getusername=new String(usernameinput.getText());
					String getpassword=new String(passwordinput.getText());
					int getidentity=identityselect.getSelectedIndex()+1;   //1-团员 2-管理员 3-游客
					if(getusername.equals("")||getpassword.equals(""))
					{   //判断是否有空输入
						JOptionPane.showMessageDialog(null, "输入为空");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						ResultSet r=s.executeQuery("select * From personlist");
						String temp=new String(getusernamepassword(r,getusername,getidentity));
						if(temp.equals(""))
						{
							JOptionPane.showMessageDialog(null, "不存在该用户");
						}
						else 
						{
							if(temp.equals(getpassword))
							{
								if(getidentity==1)
								{
									memberframe a=new memberframe(getusername);
									a.buildmemberframe();
								}
								else if(getidentity==2)
								{
									managerframe a=new managerframe(getusername);
									a.buildmanagerframe();
								}
								else
								{
									guestframe a=new guestframe(getusername);
									a.buildguestframe();
								}
								f.dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "您输入的密码不正确");
							}
						}
						s.close();
						c.close();
					}
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		register.addActionListener(new ActionListener() {   //申请账号键
			public void actionPerformed(ActionEvent e) {
				try
				{
					registerframe a=new registerframe();
					a.buildregisterframe();
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
	
	//该函数的作用是判断用户名是否存在
	//若存在则返回对应密码用于比对
	//若不存则返回空字符串
	public String getusernamepassword(ResultSet r,String name,int id)
	{
		
		try
		{
			r.beforeFirst();
			while(r.next())
			{
				if(name.equals(r.getString("username"))&&id==r.getInt("identity"))
					return r.getString("password");
			}
			return "";
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		
		startframe a=new startframe();
		a.buildstartframe();
	}
	

}

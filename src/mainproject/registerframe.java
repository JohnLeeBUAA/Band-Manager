package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//注册窗口
public class registerframe 
{
	
	private JFrame f;
	private JLabel username;
	private JLabel password;
	private JLabel password2;
	private JLabel identity;
	private JTextField usernameinput;
	private JPasswordField passwordinput;
	private JPasswordField passwordinput2;
	private JComboBox identityselect;
	private JButton next;
	
	public registerframe()
	{
		
		f=new JFrame("注册一个账号");
		username=new JLabel("用户名",JLabel.CENTER);
		password=new JLabel("密码",JLabel.CENTER);
		password2=new JLabel("确认密码",JLabel.CENTER);
		identity=new JLabel("选择身份",JLabel.CENTER);
		usernameinput=new JTextField("",40);
		passwordinput=new JPasswordField("",40);
		passwordinput2=new JPasswordField("",40);
		String[] s={"团员","管理员","游客"};
		identityselect=new JComboBox(s);
		next=new JButton("下一步");
		
		next.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getusername=new String(usernameinput.getText());
					String getpassword=new String(passwordinput.getText());
					String getpassword2=new String(passwordinput2.getText());
					int getidentity=identityselect.getSelectedIndex()+1;   //1-团员 2-管理员 3-游客
					if(getusername.equals("")||getpassword.equals("")||getpassword2.equals(""))
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
						ResultSet r=s.executeQuery("select username From personlist");
						r.beforeFirst();
						boolean flag=true;//记录将要申请的用户名是否合法
						while(r.next())
						{
							if(getusername.equals(r.getString("username")))
							{
								flag=false;
								break;
							}
						}
						if(!flag)
						{
							JOptionPane.showMessageDialog(null, "该用户名已存在");
						}
						else if(!(getpassword.equals(getpassword2)))
						{
							JOptionPane.showMessageDialog(null, "您两次输入的密码不相同");
						}
						else
						{
							String empty=new String("");
							s.executeUpdate(
								"insert into personlist values(\'"+getusername+"\',\'"+getpassword+
								"\',\'"+empty+"\',\'"+empty+"\',\'"+empty+"\',\'"+empty+"\',\'"
								+empty+"\',"+getidentity+",\'"+empty+"\',\'"+empty+"\',\'"+empty+"\',"+1+")");
							
							//根据assess表中的列数构建sql增加记录语句temp和temp1
							r=s.executeQuery("select * from assess");
							ResultSetMetaData m=r.getMetaData();
							int n=m.getColumnCount(); //列数
							
							StringBuffer temp=new StringBuffer("insert into assess values(\'"+getusername+"\'");
							
							for(int i=0;i<n-1;i++)
								temp.append(",0"); //对应列记录添加零值
							
							temp.append(")");
							String temp1=new String(temp);//转换成String类型
							
							s.executeUpdate(temp1);		//在assess表中添加团员记录
							
							//注册成功，构建个人资料窗口类
							JOptionPane.showMessageDialog(null, "注册成功，请填写个人资料");
							personalframe a=new personalframe(getusername,getidentity);
							a.buildpersonalframe();
							f.dispose();
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
	}
	
    public void buildregisterframe()
    {
		
		f.setSize(400,200);
		f.setLayout(new GridLayout(5,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(username);
		f.add(usernameinput);
		f.add(password);
		f.add(passwordinput);
		f.add(password2);
		f.add(passwordinput2);
		f.add(identity);
		f.add(identityselect);
		f.add(next);
		f.setVisible(true);
		
	}
    
}

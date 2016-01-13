package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//管理团员窗口类
public class managememberframe 
{
	private JFrame f;
	private JButton[] memberlist;		//团员名单按键数组
	private JButton[] evaluatelist;		//评价键按键数组
	private JButton[] deletelist;		//删除键按键数组
	private JButton back;
	private int counter;				//记录团员个数，初始化按键数组用
	private String username;			//记录当前管理员用户名
	private int identity;				//记录身份（其实管理员身份就是2，这个值没用）
	
	public managememberframe(String name,int id)
	{
		f=new JFrame("点击用户名查看团员个人资料");
		back=new JButton("返回");
		counter=0;
		username=new String(name);
		identity=id;
		
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
				if(r.getInt("identity")==1)			//如果是团员，计数器增一
					counter++;
			}
			
			//初始化按键数组
			memberlist=new JButton[counter];
			evaluatelist=new JButton[counter];
			deletelist=new JButton[counter];
			counter=0;
			r.beforeFirst();
			while(r.next())
			{
				if(r.getInt("identity")==1)
				{
					//用来记录正在操作的团员的用户名
					final String temp=new String(r.getString("username"));
					
					//构造按键数组并设置按键监听器
					memberlist[counter]=new JButton(temp);
					memberlist[counter].addActionListener(new ActionListener() {   
						public void actionPerformed(ActionEvent e) {
							try
							{
								//利用重载，执行个人资料窗口类含三个参数的构造函数
								//这样返回时就能返回到这个类
								personalframe a=new personalframe(temp,1,username);
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
					
					evaluatelist[counter]=new JButton("评价");
					evaluatelist[counter].addActionListener(new ActionListener() {   
						public void actionPerformed(ActionEvent e) {
							try
							{
								evaluateframe a=new evaluateframe(username,temp);
								a.buildevaluateframe();
								f.dispose();
							}
							catch(Exception ex)
							{
								System.err.println("Exception :" + ex);
								ex.printStackTrace();
							}
						}
					});
					
					deletelist[counter]=new JButton("删除");
					deletelist[counter].addActionListener(new ActionListener() {   
						public void actionPerformed(ActionEvent e) {
							try
							{
								int option = JOptionPane.showConfirmDialog(null, "确认删除团员："
										+temp+"  吗？","Attention", JOptionPane.YES_NO_OPTION);
								if(option==0)
								{
									Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
									Connection c
									=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
									Statement s=c.createStatement();
									s.executeUpdate("delete from personlist where username=\'"+temp+"\'");
									s.executeUpdate("delete from assess where username=\'"+temp+"\'");
									s.close();
									c.close();
									
									JOptionPane.showMessageDialog(null, "删除成功");
									managememberframe a=new managememberframe(username,identity);
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
		
		back.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					managerframe a=new managerframe(username);
					a.buildmanagerframe();
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
	
	public void buildmanagememberframe()
	{
		
		//按有团员和没有团员搭建不同的窗口
		if(counter!=0)
		{
			JPanel cen=new JPanel();
			cen.setLayout(new GridLayout(counter,3));
			for(int i=0;i<counter;i++)
			{
				cen.add(memberlist[i]);
				cen.add(evaluatelist[i]);
				cen.add(deletelist[i]);
			}
			JPanel sou=new JPanel();
			sou.setLayout(new FlowLayout());
			sou.add(back);
			f.setSize(300,(counter+1)*50);
			f.setLayout(new BorderLayout());
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.add(cen,BorderLayout.CENTER);
			f.add(sou,BorderLayout.SOUTH);
			f.setVisible(true);
		}
		else
		{
			f.setSize(300,100);
			f.setLayout(new GridLayout(2,1));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel sou=new JPanel();
			sou.setLayout(new FlowLayout());
			sou.add(back);
			JLabel l=new JLabel("还没有团员",JLabel.CENTER);
			f.add(l);
			f.add(sou);
			f.setVisible(true);
		}
	}

}

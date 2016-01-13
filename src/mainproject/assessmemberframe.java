package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//团员评价、签到窗口类
public class assessmemberframe 
{
	private JFrame f;
	private JButton[] namelist;
	private JButton[] sign;
	private JButton assess;
	private JButton back;
	private String username;
	private int counter;
	
	public assessmemberframe(String name)
	{
		username=new String(name);
		assess=new JButton("考核");
		back=new JButton("返回");
		f=new JFrame("团员签到及考核");
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
			Connection c
			=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			Statement s=c.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet r=s.executeQuery("select * From assess");
			r.last();
			counter=r.getRow();
			
			namelist=new JButton[counter];		//初始化
			sign=new JButton[counter];			//初始化
			
			counter=0;
			r.beforeFirst();
			while(r.next())
			{
				final String temp=new String(r.getString("username"));
				namelist[counter]=new JButton(temp);
				//设置按键监听器
				namelist[counter].addActionListener(new ActionListener() {   
					public void actionPerformed(ActionEvent e) {
						try
						{
							//建立查看成绩窗口类
							checkgradeframe a=new checkgradeframe(username,temp);
							a.buildcheckgradeframe();
							f.dispose();
						}
						catch(Exception ex)
						{
							System.err.println("Exception :" + ex);
							ex.printStackTrace();
						}
					}
				});
				//设置按键监听器
				sign[counter]=new JButton("签到");
				sign[counter].addActionListener(new ActionListener() {   
					public void actionPerformed(ActionEvent e) {
						try
						{
							int signnum=0;
							Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
							Connection c
							=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
							Statement s=c.createStatement(
									ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							ResultSet r=s.executeQuery("select * From assess");
							r.beforeFirst();
							while(r.next())
							{
								if(r.getString("username").equals(temp))		//找到对应团员
								{
									signnum=r.getInt("sign");		//取到原来签到值
									signnum++;						//增一
									break;
								}
							}
							//更新信息
							s.executeUpdate("update assess set sign="+signnum+" where username=\'"+temp+"\'");
							JOptionPane.showMessageDialog(null, "团员"+temp+"签到成功");
							s.close();
							c.close();
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
			s.close();
			c.close();
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
		
		assess.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String[] concertname;
					String[] membername;
					int[] concertweight;
					int skill=0,sign=0;
					int[] grade;
					int counter=0;		//记录演出数
					int countermember;	//记录团员数
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
					Connection c
					=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
					Statement s=c.createStatement(
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					ResultSet r=s.executeQuery("select * From concert");
					r.last();
					counter=r.getRow();
					concertname=new String[counter];
					concertweight=new int[counter];
					counter=0;
					r.beforeFirst();
					while(r.next())
					{
						//记录演出的名称和权重
						concertname[counter]=new String(r.getString("concertname"));
						concertweight[counter]=r.getInt("weight");
						counter++;
					}
					r=s.executeQuery("select * from assess");
					r.last();
					countermember=r.getRow();
					membername=new String[countermember];
					grade=new int[countermember];
					countermember=0;
					r.beforeFirst();
					while(r.next())
					{
						//计算每一个团员的成绩
						membername[countermember]=r.getString("username");
						skill=r.getInt("skill");
						sign=r.getInt("sign");
						grade[countermember]=sign*10+skill;
						for(int i=0;i<counter;i++)
							grade[countermember]+=r.getInt(concertname[i])*concertweight[i];
						countermember++;
					}
					//更新数据库
					for(int i=0;i<countermember;i++)
						s.executeUpdate("update assess set grade="+grade[i]+" where username=\'"+membername[i]+"\'");
					JOptionPane.showMessageDialog(null, "团员考核完成");
					
					//建立一个显示信息的字符串
					StringBuffer showrank=new StringBuffer("排名      姓名      成绩\n");
					r=s.executeQuery("select * from assess order by grade DESC");
					int rank=1;
					r.beforeFirst();
					while(r.next())
					{
						showrank.append(rank+"             "+r.getString("username")+"          "+r.getInt("grade")+"\n");
						rank++;
					}
					s.close();
					c.close();
					JOptionPane.showMessageDialog(null, showrank);		//显示信息
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
	
	public void buildassessmemberframe()
	{
		
		//按有团员和没有团员搭建不同的窗口
		if(counter!=0)
		{
			f.setLayout(new GridLayout(counter+1,2));
			f.setSize(300,(counter+1)*50);
			for(int i=0;i<counter;i++)
			{
				f.add(namelist[i]);
				f.add(sign[i]);
			}
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.add(assess);
			f.add(back);
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
			sou.setLayout(new GridLayout(1,2));
			sou.add(assess);
			sou.add(back);
			JLabel l=new JLabel("还没有团员",JLabel.CENTER);
			f.add(l);
			f.add(sou);
			f.setVisible(true);
		}
	}

}

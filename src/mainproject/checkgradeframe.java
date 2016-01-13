package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//查看成绩窗口类
//构造函数两个参数用在管理员管理窗口
//构造函数一个参数用在团员查看窗口
public class checkgradeframe 
{
	private JFrame f;
	private JLabel membername;
	private JLabel sign;
	private JLabel skill;
	private JLabel[] concertlist;
	private JLabel grade;
	private JTextField nameinput;
	private JTextField signinput;
	private JTextField skillinput;
	private JTextField[] concertgradeinput;
	private JTextField gradeinput;
	private JButton confirm;
	private JButton back;
	private String username;
	private String tempname;
	private int counter;
	private String[] concert;
	
	public checkgradeframe(String name,String name2)  //返回assessmemberframe
	{
		username=new String(name);
		tempname=new String(name2);
		counter=0;
		f=new JFrame("编辑团员成绩");
		membername=new JLabel("用户名",JLabel.CENTER);
		sign=new JLabel("签到次数",JLabel.CENTER);
		skill=new JLabel("技术分",JLabel.CENTER);
		grade=new JLabel("最终成绩",JLabel.CENTER);
		nameinput=new JTextField(tempname,40);
		nameinput.setEditable(false);			//设置姓名不可编辑
		signinput=new JTextField("",40);
		skillinput=new JTextField("",40);
		gradeinput=new JTextField("",40);
		confirm=new JButton("确定");
		back=new JButton("返回");
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
    		Connection c
    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
    		Statement s=c.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
    		ResultSet r=s.executeQuery("select * From concert");
    		r.last();
    		counter=r.getRow();
    		concertlist=new JLabel[counter];
    		concertgradeinput=new JTextField[counter];
    		concert=new String[counter];
    		counter=0;
    		r.beforeFirst();
    		while(r.next())
    		{
    			//构造
    			concert[counter]=new String(r.getString("concertname"));
    			concertlist[counter]=new JLabel(concert[counter],JLabel.CENTER);
    			concertgradeinput[counter]=new JTextField("",40);
    			counter++;
    		}
    		r=s.executeQuery("select * From assess");
    		r.beforeFirst();
    		while(r.next())
    		{
    			//设置文本信息
    			if(tempname.equals(r.getString("username")))
    			{
    				signinput.setText(r.getString("sign"));
    				skillinput.setText(r.getString("skill"));
    				if(r.getString("grade").equals("0"))
    					gradeinput.setText("未进行考核");
    				else
    					gradeinput.setText(r.getString("grade"));
    				
    				//最终成绩设为不可编辑
    				gradeinput.setEditable(false);
    				
    				for(int i=0;i<counter;i++)
    					concertgradeinput[i].setText(r.getString(concert[i]));
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
		
		confirm.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getsigninput=new String(signinput.getText());
					String getskillinput=new String(skillinput.getText());
					String getgradeinput=new String(gradeinput.getText());
					if(getgradeinput.equals("未进行考核"))
						getgradeinput="0";
					String[] getconcertinput=new String[counter];
					for(int i=0;i<counter;i++)
						getconcertinput[i]=new String(concertgradeinput[i].getText());
					boolean flag=true;		//判断是否有空输入
					if(getsigninput.equals("")||getskillinput.equals("")||getgradeinput.equals(""))
						flag=false;
					for(int i=0;i<counter;i++)
					{
						if(getconcertinput.equals(""))
						{
							flag=false;
							break;
						}
					}
					if(!flag)
					{
						JOptionPane.showMessageDialog(null, "输入为空");
					}
					else
					{
						StringBuffer sqlorder=new StringBuffer("update assess set ");
						sqlorder.append("grade="+getgradeinput+", skill="+getskillinput+", sign="+getsigninput);
						for(int i=0;i<counter;i++)
							sqlorder.append(", "+concert[i]+"="+getconcertinput[i]);
						sqlorder.append(" where username=\'"+tempname+"\'");
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
			    		Connection c
			    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			    		Statement s=c.createStatement();
			    		String sqlorder2=new String(sqlorder);
			    		
			    		s.executeUpdate(sqlorder2);
			    		s.close();
			    		c.close();
			    		JOptionPane.showMessageDialog(null, "编辑 "+tempname+" 成绩成功");
			    		assessmemberframe a=new assessmemberframe(username);
						a.buildassessmemberframe();
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
	}
	
	public checkgradeframe(String name)  //返回memberframe
	{
		tempname=new String(name);
		counter=0;
		f=new JFrame("查看考核成绩");
		membername=new JLabel("用户名",JLabel.CENTER);
		sign=new JLabel("签到次数",JLabel.CENTER);
		skill=new JLabel("技术分",JLabel.CENTER);
		grade=new JLabel("最终成绩",JLabel.CENTER);
		nameinput=new JTextField(tempname,40);
		nameinput.setEditable(false);
		signinput=new JTextField("",40);
		skillinput=new JTextField("",40);
		gradeinput=new JTextField("",40);
		confirm=new JButton("确定");
		back=new JButton("返回");
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
    		Connection c
    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
    		Statement s=c.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
    		ResultSet r=s.executeQuery("select * From concert");
    		r.last();
    		counter=r.getRow();
    		concertlist=new JLabel[counter];
    		concertgradeinput=new JTextField[counter];
    		concert=new String[counter];
    		counter=0;
    		r.beforeFirst();
    		while(r.next())
    		{
    			concert[counter]=new String(r.getString("concertname"));
    			concertlist[counter]=new JLabel(concert[counter],JLabel.CENTER);
    			concertgradeinput[counter]=new JTextField("",40);
    			counter++;
    		}
    		r=s.executeQuery("select * From assess");
    		r.beforeFirst();
    		while(r.next())
    		{
    			if(tempname.equals(r.getString("username")))
    			{
    				signinput.setText(r.getString("sign"));
    				skillinput.setText(r.getString("skill"));
    				if(r.getString("grade").equals("0"))
    					gradeinput.setText("未评定");
    				else
    					gradeinput.setText(r.getString("grade"));
    				
    				//团员查看不可修改，均设为不可编辑
    				signinput.setEditable(false);
    				skillinput.setEditable(false);
    				gradeinput.setEditable(false);
    				for(int i=0;i<counter;i++)
    				{
    					concertgradeinput[i].setText(r.getString(concert[i]));
    					concertgradeinput[i].setEditable(false);
    				}
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
		
		
		
		confirm.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					memberframe a=new memberframe(tempname);
					a.buildmemberframe();
					f.dispose();
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
					memberframe a=new memberframe(tempname);
					a.buildmemberframe();
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
	
	public void buildcheckgradeframe()
	{
		f.setSize(300,(counter+5)*50);
		f.setLayout(new GridLayout(counter+5,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(membername);
		f.add(nameinput);
		f.add(sign);
		f.add(signinput);
		f.add(skill);
		f.add(skillinput);
		for(int i=0;i<counter;i++)
		{
			f.add(concertlist[i]);
			f.add(concertgradeinput[i]);
		}
		f.add(grade);
		f.add(gradeinput);
		f.add(confirm);
		f.add(back);
		f.setVisible(true);
	}

}

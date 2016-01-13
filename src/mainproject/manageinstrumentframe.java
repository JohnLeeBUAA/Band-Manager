package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//管理乐器窗口类
//与管理团员界面基本相同，注释略
public class manageinstrumentframe 
{
	private JFrame f;
	private JLabel[] l;
	private JLabel wuyong1;
	private JLabel wuyong2;
	private JLabel wuyong3;
	private JLabel wuyong4;
	private JLabel wuyong5;
	private JLabel wuyong6;
	private JButton[] edit;		
	private JButton[] delete;			
	private JButton back;
	private JButton addinstrument;
	private int counter;				
	private String username;			
	
	
	public manageinstrumentframe(String name)
	{
		f=new JFrame("管理乐器");
		back=new JButton("返回");
		addinstrument=new JButton("添加");
		wuyong1=new JLabel("  ");
		wuyong2=new JLabel("  ");
		wuyong3=new JLabel("  ");
		wuyong4=new JLabel("  ");
		wuyong5=new JLabel("  ");
		wuyong6=new JLabel("  ");
		counter=0;
		username=new String(name);
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
			Connection c
			=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			Statement s=c.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet r=s.executeQuery("select * From instrument");
			
			r.last();
			counter=r.getRow();
			l=new JLabel[(counter+1)*4];
			edit=new JButton[counter];
			delete=new JButton[counter];
			l[0]=new JLabel("名称",JLabel.CENTER);
			l[1]=new JLabel("数量",JLabel.CENTER);
			l[2]=new JLabel("是否借出",JLabel.CENTER);
			l[3]=new JLabel("借入者",JLabel.CENTER);
			
			counter=0;
			r.beforeFirst();
			while(r.next())
			{
				final String temp=new String(r.getString("instrumentname"));
				edit[counter]=new JButton("编辑");
				edit[counter].addActionListener(new ActionListener() {   
					public void actionPerformed(ActionEvent e) {
						try
						{
							
							editinstrumentframe a=new editinstrumentframe(username,temp);
							a.buildeditinstrumentframe();
							f.dispose();
						}
						catch(Exception ex)
						{
							System.err.println("Exception :" + ex);
							ex.printStackTrace();
						}
					}
				});
					
				delete[counter]=new JButton("删除");
				delete[counter].addActionListener(new ActionListener() {   
					public void actionPerformed(ActionEvent e) {
						try
						{
							int option = JOptionPane.showConfirmDialog(null, "确认删除乐器："
										+temp+"  吗？","Attention", JOptionPane.YES_NO_OPTION);
							if(option==0)
							{
								Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
								Connection c
								=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
								Statement s=c.createStatement();
								s.executeUpdate("delete from instrument where instrumentname=\'"+temp+"\'");
								s.close();
								c.close();
									
								JOptionPane.showMessageDialog(null, "删除成功");
								manageinstrumentframe a=new manageinstrumentframe(username);
								a.buildmanageinstrumentframe();
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
				l[counter*4]=new JLabel(r.getString("instrumentname"),JLabel.CENTER);
				l[counter*4+1]=new JLabel(r.getString("quantity"),JLabel.CENTER);
				l[counter*4+2]=new JLabel(r.getString("lend"),JLabel.CENTER);
				l[counter*4+3]=new JLabel(r.getString("borrower"),JLabel.CENTER);
			
			}
			s.close();
			c.close();
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
		
		addinstrument.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					editinstrumentframe a=new editinstrumentframe(username);
					a.buildeditinstrumentframe();
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
	
	public void buildmanageinstrumentframe()
	{
		
		//按有乐器和没有乐器搭建不同的窗口
		if(counter!=0)
		{
			f.setSize(500,(counter+2)*50);
			f.setLayout(new GridLayout(counter+2,6));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			for(int i=0;i<=3;i++)
				f.add(l[i]);
			f.add(wuyong1);
			f.add(wuyong2);
			for(int i=0;i<counter;i++)
			{
				f.add(l[(i+1)*4]);
				f.add(l[(i+1)*4+1]);
				f.add(l[(i+1)*4+2]);
				f.add(l[(i+1)*4+3]);
				f.add(edit[i]);
				f.add(delete[i]);
			}
			f.add(wuyong3);
			f.add(wuyong4);
			f.add(addinstrument);
			f.add(back);
			f.add(wuyong5);
			f.add(wuyong6);
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
			sou.add(addinstrument);
			sou.add(back);
			JLabel l=new JLabel("还没有乐器");
			f.add(l);
			f.add(sou);
			f.setVisible(true);
		}
	}

}


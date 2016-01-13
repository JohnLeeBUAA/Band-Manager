package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//查看演出窗口类
public class checkconcertframe 
{
	private JFrame f;
	private JLabel concertname;
	private JLabel weight;
	private JLabel info;
	private JTextField nameinput;
	private JTextField weightinput;
	private JTextField infoinput;
	private JButton edit;
	private JButton delete;
	private JButton back;
	private String username;
	private String tempname;
	
	checkconcertframe(String name1,String name2)
	{
		username=new String(name1);
		tempname=new String(name2);
		f=new JFrame("查看演出");
		concertname=new JLabel("演出名称",JLabel.CENTER);
		weight=new JLabel("考核所占权重（1-100）",JLabel.CENTER);
		info=new JLabel("演出信息",JLabel.CENTER);
		nameinput=new JTextField(tempname,40);
		weightinput=new JTextField("",40);
		infoinput=new JTextField("",40);
		edit=new JButton("编辑");
		delete=new JButton("删除");
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
			r.beforeFirst();
			while(r.next())
			{
				if(tempname.equals(r.getString("concertname")))		//找到对应tempname的演出
				{
					//更新数据
					weightinput.setText(r.getString("weight"));
					infoinput.setText(r.getString("info"));
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
		
		nameinput.setEditable(false);
		weightinput.setEditable(false);
		infoinput.setEditable(false);
		
		//删除键按键监听器
		delete.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					int option = JOptionPane.showConfirmDialog(null, "确认删除演出 ："
							+tempname+"  吗？","Attention", JOptionPane.YES_NO_OPTION);
					if(option==0)
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						s.executeUpdate("delete from concert where concertname=\'"+tempname+"\'");
						s.executeUpdate("alter table assess drop "+tempname);
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "删除成功");
						manageconcertframe a=new manageconcertframe(username);
						a.buildmanageconcertframe();
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
		
		//返回键按键监听器
		back.addActionListener(new ActionListener() {   
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
		
		//编辑键按键监听器
		edit.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					editconcertframe a=new editconcertframe(username,tempname);
					a.buildeditconcertframe();
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
	
	public void buildcheckconcertframe()
	{
		f.setSize(400,300);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(3,2));
		f.setLayout(new BorderLayout());
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.add(concertname);
		p.add(nameinput);
		p.add(weight);
		p.add(weightinput);
		p.add(info);
		p.add(infoinput);
		JPanel ps=new JPanel();
		ps.setLayout(new FlowLayout());
		ps.add(edit);
		ps.add(delete);
		ps.add(back);
		f.add(p,BorderLayout.CENTER);
		f.add(ps,BorderLayout.SOUTH);
		f.setVisible(true);

	}

}

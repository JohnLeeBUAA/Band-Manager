package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//添加演出信息窗口类
public class addconcertframe 
{
	private JFrame f;
	private JLabel concertname;
	private JLabel weight;
	private JLabel info;
	private JTextField nameinput;
	private JTextField weightinput;
	private JTextField infoinput;
	private JButton save;
	private JButton back;
	private String username;
	
	public addconcertframe(String name)
	{
		username=new String(name);
		f=new JFrame("添加演出");
		concertname=new JLabel("演出名称",JLabel.CENTER);
		weight=new JLabel("考核所占权重（1-100）",JLabel.CENTER);
		info=new JLabel("演出信息",JLabel.CENTER);
		nameinput=new JTextField("",40);
		weightinput=new JTextField("",40);
		infoinput=new JTextField("",40);
		save=new JButton("保存");
		back=new JButton("返回");
		
		save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getname=new String(nameinput.getText());
					String getweight=new String(weightinput.getText());
					String getinfo=new String(infoinput.getText());
					if(getname.equals("")||getweight.equals("")
							||getinfo.equals(""))
						JOptionPane.showMessageDialog(null, "输入为空");
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						ResultSet r=s.executeQuery("select * from concert");
						r.beforeFirst();
						boolean flag=true;		//判断演出是否已经存在，防止重名
						while(r.next())
						{
							if(getname.equals(r.getString("concertname")))
							{
								flag=false;
								break;
							}
						}
						if(!flag)
						{
							JOptionPane.showMessageDialog(null, "该演出已存在");
						}
						else
						{
							int we=Integer.valueOf(getweight);
							//concert、assess两个表都需要更新
							s.executeUpdate(
									"insert into concert values(\'"+getname+"\',"+we+",\'"+getinfo+"\')");
							s.executeUpdate(
									"alter table assess add "+getname+" int");
							s.executeUpdate(
					    			"update assess set "+getname+"=0");
						}
						
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "保存成功");
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
	}
	
	public void buildaddconcertframe()
	{
		f.setSize(400,300);
		f.setLayout(new GridLayout(4,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(concertname);
		f.add(nameinput);
		f.add(weight);
		f.add(weightinput);
		f.add(info);
		f.add(infoinput);
		f.add(save);
		f.add(back);
		f.setVisible(true);

	}

}

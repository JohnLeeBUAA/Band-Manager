package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//编辑乐器信息窗口类
//添加乐器信息窗口类
//构造函数含两个参数的为编辑乐器信息
//构造函数含一个参数的为添加乐器信息
public class editinstrumentframe 
{
	private JFrame f;
	private JTextField instrumentnameinput;			//名称
	private JLabel instrumentname;
	private JTextField quantityinput;				//数量
	private JLabel quantity;
	private JTextField lendinput;				//是否借出
	private JLabel lend;
	private JTextField borrowerinput;			//借入者
	private JLabel borrower;
	private JButton save;
	private JButton back;
    private String username;
    private String insname;
    
    //含两个参数的构造函数
    public editinstrumentframe(String name,String name2)
    {
    	username=new String(name);
    	insname=new String(name2);
    	f=new JFrame("编辑乐器信息");
    	instrumentnameinput=new JTextField(insname,40);
    	instrumentnameinput.setEditable(false);				//设置乐器名称不可编辑
    	instrumentname=new JLabel("名称",JLabel.CENTER);
    	quantityinput=new JTextField("",40);
    	quantity=new JLabel("数量",JLabel.CENTER);
    	lendinput=new JTextField("",40);
    	lend=new JLabel("是否借出",JLabel.CENTER);
    	borrowerinput=new JTextField("",40);
    	borrower=new JLabel("借入者",JLabel.CENTER);
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
    		ResultSet r=s.executeQuery("select * From instrument");
    		r.beforeFirst();
    		while(r.next())
    		{
    			//初始化文本内容，使得打开时可看到已有信息
    			if(insname.equals(r.getString("instrumentname")))
    			{
    				quantityinput.setText(r.getString("quantity"));
    				lendinput.setText(r.getString("lend"));
    				borrowerinput.setText(r.getString("borrower"));
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
					String getquantity=new String(quantityinput.getText());
					String getlend=new String(lendinput.getText());
					String getborrower=new String(borrowerinput.getText());
					if(getlend.equals("")||getquantity.equals("")||getborrower.equals(""))
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
				    			"update instrument set quantity=\'"+getquantity+"\'where instrumentname=\'"+insname+"\'");
				    	s.executeUpdate(
				    			"update instrument set lend=\'"+getlend+"\'where instrumentname=\'"+insname+"\'");
				    	s.executeUpdate(
				    			"update instrument set borrower=\'"+getborrower+"\'where instrumentname=\'"+insname+"\'");
				    	
				    	s.close();
				    	c.close();
				    	JOptionPane.showMessageDialog(null, "保存成功");
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
    	
    	
    	back.addActionListener(new ActionListener() {   
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
    }
    
    
    //含一个参数的构造函数
    public editinstrumentframe(String name)
    {
    	username=new String(name);
    	f=new JFrame("添加乐器信息");
    	instrumentnameinput=new JTextField("",40);
    	instrumentname=new JLabel("名称",JLabel.CENTER);
    	quantityinput=new JTextField("",40);
    	quantity=new JLabel("数量",JLabel.CENTER);
    	lendinput=new JTextField("",40);
    	lend=new JLabel("是否借出",JLabel.CENTER);
    	borrowerinput=new JTextField("",40);
    	borrower=new JLabel("借入者",JLabel.CENTER);
    	save=new JButton("保存");
    	back=new JButton("退出编辑");
    	
    	save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getinstrumentname=new String(instrumentnameinput.getText());
					String getquantity=new String(quantityinput.getText());
					String getlend=new String(lendinput.getText());
					String getborrower=new String(borrowerinput.getText());
					if(getinstrumentname.equals("")||getlend.equals("")||getquantity.equals(""))
					{   //判断是否有空输入
						JOptionPane.showMessageDialog(null, "请填写完全");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //连接数据库
				    	Connection c
				    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
				    	Statement s=c.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						ResultSet r=s.executeQuery("select * From instrument");
						if(exist(r,getinstrumentname))
						{	//判断是否有重名
							JOptionPane.showMessageDialog(null, "该乐器已记录");
						}
						else
						{
							s.executeUpdate(
									"insert into instrument values(\'"+getinstrumentname+"\',\'"+getquantity+
									"\',\'"+getlend+"\',\'"+getborrower+"\')");
						}
				    	s.close();
				    	c.close();
				    	JOptionPane.showMessageDialog(null, "添加乐器成功");
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
    	
    
    	back.addActionListener(new ActionListener() {   
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
    }
    
    //判断乐器是否存在，存在返回true
    public boolean exist(ResultSet r,String name)
    {
    	try
    	{
    		r.beforeFirst();
    		while(r.next())
    		{
    			if(name.equals(r.getString("instrumentname")))
    				return true;
			}
    		return false;
    	}
    	catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
    	return false;
    }
    
    
    //搭建窗口
    public void buildeditinstrumentframe()
    {
    	f.setSize(400,300);
		f.setLayout(new GridLayout(5,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(instrumentname);
		f.add(instrumentnameinput);
		f.add(quantity);
		f.add(quantityinput);
		f.add(lend);
		f.add(lendinput);
		f.add(borrower);
		f.add(borrowerinput);
		f.add(save);
		f.add(back);
		f.setVisible(true);
    }

}

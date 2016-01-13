package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//�������ϴ�����
//ע��ĵڶ����ô˴���
//��Ӧ�����ҳ����������ô˴���
//������Ա�鿴��Ա��Ϣ�ô˴���
public class personalframe 
{
	
	private JFrame f;
	private JTextField realnameinput;			//��ʵ����
	private JLabel realname;
	private JTextField sexinput;				//�Ա�
	private JLabel sex;
	private JTextField schoolinput;				//ѧУ
	private JLabel school;
	private JTextField instrumentinput;			//����
	private JLabel instrument;
	private JTextField contactinput;			//��ϵ��ʽ
	private JLabel contact;
	private JTextField majorinput;				//רҵ
	private JLabel major;
	private JTextField instrumentbelonginginput;			//��������
	private JLabel instrumentbelonging;
	private JButton save;
	private JButton back;
    private String username;
    private int identity;
    
    public personalframe(String name,int id)    //���������Ĺ��캯�����������˳�
    {
    	username=new String(name);
    	identity=id;
    	f=new JFrame("�������� ");
    	realnameinput=new JTextField("",40);
    	realname=new JLabel("��ʵ����",JLabel.CENTER);
    	sexinput=new JTextField("",40);
    	sex=new JLabel("�Ա�m/f��",JLabel.CENTER);
    	schoolinput=new JTextField("",40);
    	school=new JLabel("ѧУ",JLabel.CENTER);
    	instrumentinput=new JTextField("",40);
    	instrument=new JLabel("����",JLabel.CENTER);
    	contact=new JLabel("��ϵ��ʽ",JLabel.CENTER);
    	contactinput=new JTextField("",40);
    	major=new JLabel("רҵ",JLabel.CENTER);
    	majorinput=new JTextField("",40);
    	instrumentbelonging=new JLabel("����������ѧУ/���ˣ�",JLabel.CENTER);
    	instrumentbelonginginput=new JTextField("",40);
    	save=new JButton("����");
    	back=new JButton("�˳��༭");
    	
    	try
    	{
    		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
    		Connection c
    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
    		Statement s=c.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
    		ResultSet r=s.executeQuery("select * From personlist");
    		r.beforeFirst();
    		while(r.next())
    		{
    			//��ʼ���ı����ݣ�ʹ�ô�ʱ�ɿ���������Ϣ
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
					{   //�ж��Ƿ��п�����
						JOptionPane.showMessageDialog(null, "����д��ȫ");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
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
				    	JOptionPane.showMessageDialog(null, "����ɹ�");
				    	
				    	//�����û�������ݷ���
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
    	
    	//�����û�������ݷ���
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
    
    public personalframe(String name,int id,String managername)   //���������Ĺ��캯�����ڷ��ع�����Ա����
    {
    	username=new String(name);
    	identity=id;
    	f=new JFrame("�������� ");
    	realnameinput=new JTextField("",40);
    	realname=new JLabel("��ʵ����",JLabel.CENTER);
    	sexinput=new JTextField("",40);
    	sex=new JLabel("�Ա�",JLabel.CENTER);
    	schoolinput=new JTextField("",40);
    	school=new JLabel("ѧУ",JLabel.CENTER);
    	instrumentinput=new JTextField("",40);
    	instrument=new JLabel("����",JLabel.CENTER);
    	contact=new JLabel("��ϵ��ʽ",JLabel.CENTER);
    	contactinput=new JTextField("",40);
    	major=new JLabel("רҵ",JLabel.CENTER);
    	majorinput=new JTextField("",40);
    	instrumentbelonging=new JLabel("����������ѧУ/���ˣ�",JLabel.CENTER);
    	instrumentbelonginginput=new JTextField("",40);
    	save=new JButton("����");
    	back=new JButton("�˳��༭");
    	
    	try
    	{
    		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
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
					{   //�ж��Ƿ��п�����
						JOptionPane.showMessageDialog(null, "����д��ȫ");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
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
				    	JOptionPane.showMessageDialog(null, "����ɹ�");
				    	
				    	//���ص�������Ա����
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
    	
    	//���ص�������Ա����
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
    	//������ݲ�ͬ���ͬ����
    	//�ο������Ա������ͬ
    	//��Ա�����������������������Ŀ
    	if(identity==1)		//�������Ա
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

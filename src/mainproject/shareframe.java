package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//������������
public class shareframe 
{
	private JFrame f;
	private JTextArea t;		//�������ı���
	private JScrollPane s;		//������
	private JLabel l;
	private JTextField tf;		//��������뷨���ı���
	private JButton publish;
	private JButton back;
	private String username;
	private int identity;
	
	public shareframe(String name,int id)
	{
		username=new String(name);
		identity=id;
		f=new JFrame("������");
		t=new JTextArea("",10,10);
		s=new JScrollPane(t);
		l=new JLabel("��������뷨��");
		tf=new JTextField();
		publish=new JButton("����");
		back=new JButton("����");
		
		
		//���ý������ı�������
		//Ϊ���д�ҽ����ļ�¼
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
			Connection c
			=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			Statement s=c.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet r=s.executeQuery("select * From share");
			r.beforeFirst();
			while(r.next())
			{
				t.append(r.getString("username")+" ˵��"+r.getString("content")+"\n");
			}
			s.close();
			c.close();
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
		
		//������������
		publish.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getinput=new String(tf.getText());
					if(getinput.equals(""))
						JOptionPane.showMessageDialog(null, "����Ϊ��");
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						s.executeUpdate(
								"insert into share(username,content) values(\'"+username+"\',\'"+getinput+"\')");
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "�����ɹ�");
						f.dispose();
						shareframe a=new shareframe(username,identity);
						a.buildshareframe();
					}
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		
		//���ذ���������
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
	
	public void buildshareframe()
	{
		f.setSize(500,400);
		f.setLayout(new GridLayout(2,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setEditable(false);		//���ý������ı���Ϊ���ɱ༭
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(3,1));
		JPanel pp=new JPanel();
		pp.setLayout(new GridLayout(1,2));
		pp.add(publish);
		pp.add(back);
		p.add(l);
		p.add(tf);
		p.add(pp);
		f.add(s);
		f.add(p);
		f.setVisible(true);
	}

}

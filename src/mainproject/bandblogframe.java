package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//������־������
public class bandblogframe 
{
	private JFrame f;    			//����
	private JButton[] namelist;    	//��־���б�������ʽ������鿴
	private JButton write;			//д��־��
	private JButton back;			//���ؼ�
	private String username;		//��¼��ǰ�û���
	private int identity;			//��¼��ǰ�û����
	private int counter;			//��¼��ǰ�û���־������ʼ��������
	
	
	//���캯��
	public bandblogframe(String name,int id)	
	{
		username=new String(name);
		identity=id;
		f=new JFrame("�������鿴������־");
		write=new JButton("д��־");
		back=new JButton("����");
		counter=0;
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
			Connection c
			=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			Statement s=c.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet r=s.executeQuery("select * From bandblog");
			r.beforeFirst();
			while(r.next())
			{
				if(username.equals(r.getString("username")))		//ͳ�Ƶ�ǰ�û���־��
					counter++;
			}
			namelist=new JButton[counter];							//��ʼ����������
			counter=0;												//counter���㣬�������찴������
			r.beforeFirst();
			while(r.next())
			{
				if(username.equals(r.getString("username")))
				{
					namelist[counter]=new JButton(r.getString("title"));		//���찴������
					final int zz=r.getInt("id");					//finalֵ�����ã���¼��־id
					
					//���ð���������
					namelist[counter].addActionListener(new ActionListener() {   
						public void actionPerformed(ActionEvent e) {
							try
							{
								//�������鿴��־���´���
								checkbandblogframe a=new checkbandblogframe(username,identity,zz);		
								a.buildcheckbandblogframe();
								f.dispose();
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
		
		//д��־����������
		write.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					//�������д��־���´���
					writebandblogframe a=new writebandblogframe(username,identity);
					a.buildwritebandblogframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		
		//���ؼ�����������
		back.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					//����ǰ�û���ݺ��û���������Ӧ����
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
	
	//���ڴ����
	public void buildbandblogframe()
	{
		if(counter!=0)		//�����ǰ�û���־����Ϊ��
		{
			f.setSize(300,(counter+1)*50);
			f.setLayout(new GridLayout(counter+1,1));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel p=new JPanel();
			p.setLayout(new GridLayout(1,2));
			p.add(write);
			p.add(back);
			for(int i=0;i<counter;i++)
				f.add(namelist[i]);
			f.add(p);
			f.setVisible(true);
		}
		else			//�����ǰ�û���־��Ϊ��
		{
			f.setSize(300,100);
			f.setLayout(new GridLayout(2,1));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel p=new JPanel();
			p.setLayout(new GridLayout(1,2));
			p.add(write);
			p.add(back);
			JLabel l=new JLabel("��û��д��־",JLabel.CENTER);
			f.add(l);
			f.add(p);
			f.setVisible(true);
		}
	}

}

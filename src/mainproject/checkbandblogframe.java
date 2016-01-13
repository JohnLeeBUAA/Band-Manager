package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//�鿴��־������
public class checkbandblogframe 
{
	private JFrame f;				//����
	private JTextArea title;		//�����ı���
	private JTextArea content;		//�����ı���
	private JButton delete;			//ɾ����
	private JButton back;			//���ؼ�
	private JButton edit;			//�༭��
	private String username;		//��ǰ�û��û���
	private int identity;			//��ǰ�û����
	private int blogid;				//��־id
	private String gettitle;		//��¼������ַ���
	private String getcontent;		//��¼���ݵ��ַ���
	
	
	//���캯��
	public checkbandblogframe(String name,int id,int idblog)
	{
		username=new String(name);
		identity=id;
		blogid=idblog;
		f=new JFrame("�鿴��־");
		title=new JTextArea("",1,10);
		content=new JTextArea("",10,10);
		delete=new JButton("ɾ��");
		edit=new JButton("�༭");
		back=new JButton("����");
		gettitle=new String("");
		getcontent=new String("");
		
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
				if(blogid==r.getInt("id"))		//�ҵ���Ӧ��־id����־
				{
					//��������
					gettitle=r.getString("title");
					getcontent=r.getString("content");
					title.setText(gettitle);
					content.setText(getcontent);
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
		
		//ɾ��������������
		delete.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					int option = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����־ ��"
							+gettitle+"  ��","Attention", JOptionPane.YES_NO_OPTION);
					if(option==0)
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						s.executeUpdate("delete from bandblog where id="+blogid);
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
						bandblogframe a=new bandblogframe(username,identity);
						a.buildbandblogframe();
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
		
		//���ؼ�����������
		back.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					bandblogframe a=new bandblogframe(username,identity);
					a.buildbandblogframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		//�༭������������
		edit.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					//�����༭��־������
					editbandblogframe a=new editbandblogframe(username,identity,blogid);
					a.buildeditbandblogframe();
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
	public void buildcheckbandblogframe()
	{
		f.setSize(500,400);
		f.setLayout(new BorderLayout(0,10));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,3));
		p.add(edit);
		p.add(delete);
		p.add(back);
		title.setEditable(false);
		content.setEditable(false);
		f.add(title,BorderLayout.NORTH);
		f.add(content,BorderLayout.CENTER);
		f.add(p,BorderLayout.SOUTH);
		f.setVisible(true);

	}


}

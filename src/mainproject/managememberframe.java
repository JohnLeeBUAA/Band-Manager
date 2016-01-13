package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//������Ա������
public class managememberframe 
{
	private JFrame f;
	private JButton[] memberlist;		//��Ա������������
	private JButton[] evaluatelist;		//���ۼ���������
	private JButton[] deletelist;		//ɾ������������
	private JButton back;
	private int counter;				//��¼��Ա��������ʼ������������
	private String username;			//��¼��ǰ����Ա�û���
	private int identity;				//��¼��ݣ���ʵ����Ա��ݾ���2�����ֵû�ã�
	
	public managememberframe(String name,int id)
	{
		f=new JFrame("����û����鿴��Ա��������");
		back=new JButton("����");
		counter=0;
		username=new String(name);
		identity=id;
		
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
				if(r.getInt("identity")==1)			//�������Ա����������һ
					counter++;
			}
			
			//��ʼ����������
			memberlist=new JButton[counter];
			evaluatelist=new JButton[counter];
			deletelist=new JButton[counter];
			counter=0;
			r.beforeFirst();
			while(r.next())
			{
				if(r.getInt("identity")==1)
				{
					//������¼���ڲ�������Ա���û���
					final String temp=new String(r.getString("username"));
					
					//���찴�����鲢���ð���������
					memberlist[counter]=new JButton(temp);
					memberlist[counter].addActionListener(new ActionListener() {   
						public void actionPerformed(ActionEvent e) {
							try
							{
								//�������أ�ִ�и������ϴ����ຬ���������Ĺ��캯��
								//��������ʱ���ܷ��ص������
								personalframe a=new personalframe(temp,1,username);
								a.buildpersonalframe();
								f.dispose();
							}
							catch(Exception ex)
							{
								System.err.println("Exception :" + ex);
								ex.printStackTrace();
							}
						}
					});
					
					evaluatelist[counter]=new JButton("����");
					evaluatelist[counter].addActionListener(new ActionListener() {   
						public void actionPerformed(ActionEvent e) {
							try
							{
								evaluateframe a=new evaluateframe(username,temp);
								a.buildevaluateframe();
								f.dispose();
							}
							catch(Exception ex)
							{
								System.err.println("Exception :" + ex);
								ex.printStackTrace();
							}
						}
					});
					
					deletelist[counter]=new JButton("ɾ��");
					deletelist[counter].addActionListener(new ActionListener() {   
						public void actionPerformed(ActionEvent e) {
							try
							{
								int option = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����Ա��"
										+temp+"  ��","Attention", JOptionPane.YES_NO_OPTION);
								if(option==0)
								{
									Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
									Connection c
									=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
									Statement s=c.createStatement();
									s.executeUpdate("delete from personlist where username=\'"+temp+"\'");
									s.executeUpdate("delete from assess where username=\'"+temp+"\'");
									s.close();
									c.close();
									
									JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
									managememberframe a=new managememberframe(username,identity);
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
	
	public void buildmanagememberframe()
	{
		
		//������Ա��û����Ա���ͬ�Ĵ���
		if(counter!=0)
		{
			JPanel cen=new JPanel();
			cen.setLayout(new GridLayout(counter,3));
			for(int i=0;i<counter;i++)
			{
				cen.add(memberlist[i]);
				cen.add(evaluatelist[i]);
				cen.add(deletelist[i]);
			}
			JPanel sou=new JPanel();
			sou.setLayout(new FlowLayout());
			sou.add(back);
			f.setSize(300,(counter+1)*50);
			f.setLayout(new BorderLayout());
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.add(cen,BorderLayout.CENTER);
			f.add(sou,BorderLayout.SOUTH);
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
			sou.setLayout(new FlowLayout());
			sou.add(back);
			JLabel l=new JLabel("��û����Ա",JLabel.CENTER);
			f.add(l);
			f.add(sou);
			f.setVisible(true);
		}
	}

}

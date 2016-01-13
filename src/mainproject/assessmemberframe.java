package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//��Ա���ۡ�ǩ��������
public class assessmemberframe 
{
	private JFrame f;
	private JButton[] namelist;
	private JButton[] sign;
	private JButton assess;
	private JButton back;
	private String username;
	private int counter;
	
	public assessmemberframe(String name)
	{
		username=new String(name);
		assess=new JButton("����");
		back=new JButton("����");
		f=new JFrame("��Աǩ��������");
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
			Connection c
			=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			Statement s=c.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet r=s.executeQuery("select * From assess");
			r.last();
			counter=r.getRow();
			
			namelist=new JButton[counter];		//��ʼ��
			sign=new JButton[counter];			//��ʼ��
			
			counter=0;
			r.beforeFirst();
			while(r.next())
			{
				final String temp=new String(r.getString("username"));
				namelist[counter]=new JButton(temp);
				//���ð���������
				namelist[counter].addActionListener(new ActionListener() {   
					public void actionPerformed(ActionEvent e) {
						try
						{
							//�����鿴�ɼ�������
							checkgradeframe a=new checkgradeframe(username,temp);
							a.buildcheckgradeframe();
							f.dispose();
						}
						catch(Exception ex)
						{
							System.err.println("Exception :" + ex);
							ex.printStackTrace();
						}
					}
				});
				//���ð���������
				sign[counter]=new JButton("ǩ��");
				sign[counter].addActionListener(new ActionListener() {   
					public void actionPerformed(ActionEvent e) {
						try
						{
							int signnum=0;
							Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
							Connection c
							=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
							Statement s=c.createStatement(
									ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							ResultSet r=s.executeQuery("select * From assess");
							r.beforeFirst();
							while(r.next())
							{
								if(r.getString("username").equals(temp))		//�ҵ���Ӧ��Ա
								{
									signnum=r.getInt("sign");		//ȡ��ԭ��ǩ��ֵ
									signnum++;						//��һ
									break;
								}
							}
							//������Ϣ
							s.executeUpdate("update assess set sign="+signnum+" where username=\'"+temp+"\'");
							JOptionPane.showMessageDialog(null, "��Ա"+temp+"ǩ���ɹ�");
							s.close();
							c.close();
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
			s.close();
			c.close();
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
		
		assess.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String[] concertname;
					String[] membername;
					int[] concertweight;
					int skill=0,sign=0;
					int[] grade;
					int counter=0;		//��¼�ݳ���
					int countermember;	//��¼��Ա��
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
					Connection c
					=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
					Statement s=c.createStatement(
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					ResultSet r=s.executeQuery("select * From concert");
					r.last();
					counter=r.getRow();
					concertname=new String[counter];
					concertweight=new int[counter];
					counter=0;
					r.beforeFirst();
					while(r.next())
					{
						//��¼�ݳ������ƺ�Ȩ��
						concertname[counter]=new String(r.getString("concertname"));
						concertweight[counter]=r.getInt("weight");
						counter++;
					}
					r=s.executeQuery("select * from assess");
					r.last();
					countermember=r.getRow();
					membername=new String[countermember];
					grade=new int[countermember];
					countermember=0;
					r.beforeFirst();
					while(r.next())
					{
						//����ÿһ����Ա�ĳɼ�
						membername[countermember]=r.getString("username");
						skill=r.getInt("skill");
						sign=r.getInt("sign");
						grade[countermember]=sign*10+skill;
						for(int i=0;i<counter;i++)
							grade[countermember]+=r.getInt(concertname[i])*concertweight[i];
						countermember++;
					}
					//�������ݿ�
					for(int i=0;i<countermember;i++)
						s.executeUpdate("update assess set grade="+grade[i]+" where username=\'"+membername[i]+"\'");
					JOptionPane.showMessageDialog(null, "��Ա�������");
					
					//����һ����ʾ��Ϣ���ַ���
					StringBuffer showrank=new StringBuffer("����      ����      �ɼ�\n");
					r=s.executeQuery("select * from assess order by grade DESC");
					int rank=1;
					r.beforeFirst();
					while(r.next())
					{
						showrank.append(rank+"             "+r.getString("username")+"          "+r.getInt("grade")+"\n");
						rank++;
					}
					s.close();
					c.close();
					JOptionPane.showMessageDialog(null, showrank);		//��ʾ��Ϣ
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
	
	public void buildassessmemberframe()
	{
		
		//������Ա��û����Ա���ͬ�Ĵ���
		if(counter!=0)
		{
			f.setLayout(new GridLayout(counter+1,2));
			f.setSize(300,(counter+1)*50);
			for(int i=0;i<counter;i++)
			{
				f.add(namelist[i]);
				f.add(sign[i]);
			}
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.add(assess);
			f.add(back);
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
			sou.add(assess);
			sou.add(back);
			JLabel l=new JLabel("��û����Ա",JLabel.CENTER);
			f.add(l);
			f.add(sou);
			f.setVisible(true);
		}
	}

}

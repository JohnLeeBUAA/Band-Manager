package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//�鿴�ݳ�������
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
		f=new JFrame("�鿴�ݳ�");
		concertname=new JLabel("�ݳ�����",JLabel.CENTER);
		weight=new JLabel("������ռȨ�أ�1-100��",JLabel.CENTER);
		info=new JLabel("�ݳ���Ϣ",JLabel.CENTER);
		nameinput=new JTextField(tempname,40);
		weightinput=new JTextField("",40);
		infoinput=new JTextField("",40);
		edit=new JButton("�༭");
		delete=new JButton("ɾ��");
		back=new JButton("����");
		
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
			Connection c
			=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			Statement s=c.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet r=s.executeQuery("select * From concert");
			r.beforeFirst();
			while(r.next())
			{
				if(tempname.equals(r.getString("concertname")))		//�ҵ���Ӧtempname���ݳ�
				{
					//��������
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
		
		//ɾ��������������
		delete.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					int option = JOptionPane.showConfirmDialog(null, "ȷ��ɾ���ݳ� ��"
							+tempname+"  ��","Attention", JOptionPane.YES_NO_OPTION);
					if(option==0)
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						s.executeUpdate("delete from concert where concertname=\'"+tempname+"\'");
						s.executeUpdate("alter table assess drop "+tempname);
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
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
		
		//���ؼ�����������
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
		
		//�༭������������
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

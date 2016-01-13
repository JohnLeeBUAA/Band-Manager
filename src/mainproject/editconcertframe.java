package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//�༭�ݳ���Ϣ������
public class editconcertframe 
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
	private String tempname;
	
	public editconcertframe(String name1,String name2)
	{
		username=new String(name1);
		tempname=new String(name2);
		f=new JFrame("�༭�ݳ�");
		concertname=new JLabel("�ݳ�����",JLabel.CENTER);
		weight=new JLabel("������ռȨ�أ�1-100��",JLabel.CENTER);
		info=new JLabel("�ݳ���Ϣ",JLabel.CENTER);
		nameinput=new JTextField(tempname,40);
		nameinput.setEditable(false);
		weightinput=new JTextField("",40);
		infoinput=new JTextField("",40);
		save=new JButton("����");
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
		
		
		save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getweight=new String(weightinput.getText());
					String getinfo=new String(infoinput.getText());
					if(getweight.equals("")||getinfo.equals(""))
						JOptionPane.showMessageDialog(null, "����Ϊ��");
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						int we=Integer.valueOf(getweight);
						s.executeUpdate(
				    		"update concert set weight="+we+" where concertname=\'"+tempname+"\'");
						s.executeUpdate(
				    		"update concert set info=\'"+getinfo+"\'where concertname=\'"+tempname+"\'");
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "����ɹ�");
						checkconcertframe a=new checkconcertframe(username,tempname);
						a.buildcheckconcertframe();
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
					checkconcertframe a=new checkconcertframe(username,tempname);
					a.buildcheckconcertframe();
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
	
	public void buildeditconcertframe()
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

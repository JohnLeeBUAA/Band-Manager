package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;


//�����ݳ�������
public class manageconcertframe 
{
	private JFrame f;
	private JButton[] namelist;
	private JButton addconcert;
	private JButton back;
	private String username;
	private int counter;
	
	public manageconcertframe(String name)
	{
		f=new JFrame("�����ݳ�");
		addconcert=new JButton("����ݳ�");
		username=new String(name);
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
    		r.last();
    		counter=r.getRow();
    		namelist=new JButton[counter];
    		counter=0;
    		r.beforeFirst();
    		while(r.next())
    		{
    			final String temp=new String(r.getString("concertname"));
    			namelist[counter]=new JButton(temp);
    			namelist[counter].addActionListener(new ActionListener() {   
    				public void actionPerformed(ActionEvent e) {
    					try
    					{
    						checkconcertframe a=new checkconcertframe(username,temp);
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
		
		addconcert.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					addconcertframe a=new addconcertframe(username);
					a.buildaddconcertframe();
					f.dispose();
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
	
	public void buildmanageconcertframe()
	{
		if(counter!=0)		//�����ǰ�ݳ�����Ϊ��
		{
			f.setSize(300,(counter+1)*50);
			f.setLayout(new GridLayout(counter+1,1));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel p=new JPanel();
			p.setLayout(new GridLayout(1,2));
			p.add(addconcert);
			p.add(back);
			for(int i=0;i<counter;i++)
				f.add(namelist[i]);
			f.add(p);
			f.setVisible(true);
		}
		else			//�����ǰ�ݳ���Ϊ��
		{
			f.setSize(400,200);
			f.setLayout(new GridLayout(2,1));
			f.setLocationRelativeTo(null);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel p=new JPanel();
			p.setLayout(new GridLayout(1,2));
			p.add(addconcert);
			p.add(back);
			JLabel l=new JLabel("��û���ݳ���Ϣ");
			f.add(l);
			f.add(p);
			f.setVisible(true);
		}
	}
}

package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//�鿴�ɼ�������
//���캯�������������ڹ���Ա������
//���캯��һ������������Ա�鿴����
public class checkgradeframe 
{
	private JFrame f;
	private JLabel membername;
	private JLabel sign;
	private JLabel skill;
	private JLabel[] concertlist;
	private JLabel grade;
	private JTextField nameinput;
	private JTextField signinput;
	private JTextField skillinput;
	private JTextField[] concertgradeinput;
	private JTextField gradeinput;
	private JButton confirm;
	private JButton back;
	private String username;
	private String tempname;
	private int counter;
	private String[] concert;
	
	public checkgradeframe(String name,String name2)  //����assessmemberframe
	{
		username=new String(name);
		tempname=new String(name2);
		counter=0;
		f=new JFrame("�༭��Ա�ɼ�");
		membername=new JLabel("�û���",JLabel.CENTER);
		sign=new JLabel("ǩ������",JLabel.CENTER);
		skill=new JLabel("������",JLabel.CENTER);
		grade=new JLabel("���ճɼ�",JLabel.CENTER);
		nameinput=new JTextField(tempname,40);
		nameinput.setEditable(false);			//�����������ɱ༭
		signinput=new JTextField("",40);
		skillinput=new JTextField("",40);
		gradeinput=new JTextField("",40);
		confirm=new JButton("ȷ��");
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
    		concertlist=new JLabel[counter];
    		concertgradeinput=new JTextField[counter];
    		concert=new String[counter];
    		counter=0;
    		r.beforeFirst();
    		while(r.next())
    		{
    			//����
    			concert[counter]=new String(r.getString("concertname"));
    			concertlist[counter]=new JLabel(concert[counter],JLabel.CENTER);
    			concertgradeinput[counter]=new JTextField("",40);
    			counter++;
    		}
    		r=s.executeQuery("select * From assess");
    		r.beforeFirst();
    		while(r.next())
    		{
    			//�����ı���Ϣ
    			if(tempname.equals(r.getString("username")))
    			{
    				signinput.setText(r.getString("sign"));
    				skillinput.setText(r.getString("skill"));
    				if(r.getString("grade").equals("0"))
    					gradeinput.setText("δ���п���");
    				else
    					gradeinput.setText(r.getString("grade"));
    				
    				//���ճɼ���Ϊ���ɱ༭
    				gradeinput.setEditable(false);
    				
    				for(int i=0;i<counter;i++)
    					concertgradeinput[i].setText(r.getString(concert[i]));
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
		
		confirm.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getsigninput=new String(signinput.getText());
					String getskillinput=new String(skillinput.getText());
					String getgradeinput=new String(gradeinput.getText());
					if(getgradeinput.equals("δ���п���"))
						getgradeinput="0";
					String[] getconcertinput=new String[counter];
					for(int i=0;i<counter;i++)
						getconcertinput[i]=new String(concertgradeinput[i].getText());
					boolean flag=true;		//�ж��Ƿ��п�����
					if(getsigninput.equals("")||getskillinput.equals("")||getgradeinput.equals(""))
						flag=false;
					for(int i=0;i<counter;i++)
					{
						if(getconcertinput.equals(""))
						{
							flag=false;
							break;
						}
					}
					if(!flag)
					{
						JOptionPane.showMessageDialog(null, "����Ϊ��");
					}
					else
					{
						StringBuffer sqlorder=new StringBuffer("update assess set ");
						sqlorder.append("grade="+getgradeinput+", skill="+getskillinput+", sign="+getsigninput);
						for(int i=0;i<counter;i++)
							sqlorder.append(", "+concert[i]+"="+getconcertinput[i]);
						sqlorder.append(" where username=\'"+tempname+"\'");
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
			    		Connection c
			    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
			    		Statement s=c.createStatement();
			    		String sqlorder2=new String(sqlorder);
			    		
			    		s.executeUpdate(sqlorder2);
			    		s.close();
			    		c.close();
			    		JOptionPane.showMessageDialog(null, "�༭ "+tempname+" �ɼ��ɹ�");
			    		assessmemberframe a=new assessmemberframe(username);
						a.buildassessmemberframe();
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
					assessmemberframe a=new assessmemberframe(username);
					a.buildassessmemberframe();
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
	
	public checkgradeframe(String name)  //����memberframe
	{
		tempname=new String(name);
		counter=0;
		f=new JFrame("�鿴���˳ɼ�");
		membername=new JLabel("�û���",JLabel.CENTER);
		sign=new JLabel("ǩ������",JLabel.CENTER);
		skill=new JLabel("������",JLabel.CENTER);
		grade=new JLabel("���ճɼ�",JLabel.CENTER);
		nameinput=new JTextField(tempname,40);
		nameinput.setEditable(false);
		signinput=new JTextField("",40);
		skillinput=new JTextField("",40);
		gradeinput=new JTextField("",40);
		confirm=new JButton("ȷ��");
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
    		concertlist=new JLabel[counter];
    		concertgradeinput=new JTextField[counter];
    		concert=new String[counter];
    		counter=0;
    		r.beforeFirst();
    		while(r.next())
    		{
    			concert[counter]=new String(r.getString("concertname"));
    			concertlist[counter]=new JLabel(concert[counter],JLabel.CENTER);
    			concertgradeinput[counter]=new JTextField("",40);
    			counter++;
    		}
    		r=s.executeQuery("select * From assess");
    		r.beforeFirst();
    		while(r.next())
    		{
    			if(tempname.equals(r.getString("username")))
    			{
    				signinput.setText(r.getString("sign"));
    				skillinput.setText(r.getString("skill"));
    				if(r.getString("grade").equals("0"))
    					gradeinput.setText("δ����");
    				else
    					gradeinput.setText(r.getString("grade"));
    				
    				//��Ա�鿴�����޸ģ�����Ϊ���ɱ༭
    				signinput.setEditable(false);
    				skillinput.setEditable(false);
    				gradeinput.setEditable(false);
    				for(int i=0;i<counter;i++)
    				{
    					concertgradeinput[i].setText(r.getString(concert[i]));
    					concertgradeinput[i].setEditable(false);
    				}
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
		
		
		
		confirm.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					memberframe a=new memberframe(tempname);
					a.buildmemberframe();
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
					memberframe a=new memberframe(tempname);
					a.buildmemberframe();
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
	
	public void buildcheckgradeframe()
	{
		f.setSize(300,(counter+5)*50);
		f.setLayout(new GridLayout(counter+5,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(membername);
		f.add(nameinput);
		f.add(sign);
		f.add(signinput);
		f.add(skill);
		f.add(skillinput);
		for(int i=0;i<counter;i++)
		{
			f.add(concertlist[i]);
			f.add(concertgradeinput[i]);
		}
		f.add(grade);
		f.add(gradeinput);
		f.add(confirm);
		f.add(back);
		f.setVisible(true);
	}

}

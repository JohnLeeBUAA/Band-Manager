package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//������Ա������
public class evaluateframe 
{
	private JFrame f;
	private JButton save;
	private JButton back;
	private String username;
	private String membername;
	private JLabel l;
	private JTextField t;
	
	public evaluateframe(String name1,String name2)		//name1����ǰ����Ա�û���
														//name2���������۵���Ա�û���
	{
		username=new String(name1);
		membername=new String(name2);
		f=new JFrame("��д����");
		l=new JLabel("����Ա   "+membername+"  ������:");
		t=new JTextField("",10);
		save=new JButton("����");
		back=new JButton("����");
		
		save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String temp=new String(t.getText());
					if(temp.equals(""))
						JOptionPane.showMessageDialog(null, "����Ϊ��");
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
				    	Connection c
				    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
				    	Statement s=c.createStatement();
				    	s.executeUpdate(
				    			"update personlist set managerevaluate=\'"+temp+"\'where username=\'"+membername+"\'");
				    	s.close();
				    	c.close();
				    	JOptionPane.showMessageDialog(null, "���۳ɹ�");
				    	managememberframe a=new managememberframe(username,2);
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
		
		back.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					managememberframe a=new managememberframe(username,2);
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
	
	public void buildevaluateframe()
	{
		f.setSize(400,200);
		f.setLayout(new GridLayout(3,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(l);
		f.add(t);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,2));
		p.add(save);
		p.add(back);
		f.add(p);
		f.setVisible(true);
	}

}

package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//д��־������
public class writebandblogframe 
{
	private JFrame f;
	private JTextArea title;      	//�����ı���
	private JTextArea content;		//�����ı���
	private JButton save;
	private JButton back;
	private String username;
	private int identity;
	
	public writebandblogframe(String name,int id)
	{
		username=new String(name);
		identity=id;
		f=new JFrame("д��־");
		title=new JTextArea("��Ŀ��",1,10);
		content=new JTextArea("���ģ�",10,10);
		save=new JButton("����");
		back=new JButton("����");
		
		save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String gettitle=new String(title.getText());
					String getcontent=new String(content.getText());
					if(gettitle.equals("")||getcontent.equals("")
							||gettitle.equals("��Ŀ��")||getcontent.equals("���ģ�"))
						JOptionPane.showMessageDialog(null, "����Ϊ��");
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement();
						s.executeUpdate(
								"insert into bandblog(username,content,title) values(\'"+username+"\',\'"+getcontent+"\',\'"+gettitle+"\')");
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "����ɹ�");
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
	}
	
	public void buildwritebandblogframe()
	{
		f.setSize(500,400);
		f.setLayout(new BorderLayout(0,10));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,2));
		p.add(save);
		p.add(back);
		f.add(title,BorderLayout.NORTH);
		f.add(content,BorderLayout.CENTER);
		f.add(p,BorderLayout.SOUTH);
		f.setVisible(true);

	}

}

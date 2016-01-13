package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//����ݳ���Ϣ������
public class addconcertframe 
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
	
	public addconcertframe(String name)
	{
		username=new String(name);
		f=new JFrame("����ݳ�");
		concertname=new JLabel("�ݳ�����",JLabel.CENTER);
		weight=new JLabel("������ռȨ�أ�1-100��",JLabel.CENTER);
		info=new JLabel("�ݳ���Ϣ",JLabel.CENTER);
		nameinput=new JTextField("",40);
		weightinput=new JTextField("",40);
		infoinput=new JTextField("",40);
		save=new JButton("����");
		back=new JButton("����");
		
		save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getname=new String(nameinput.getText());
					String getweight=new String(weightinput.getText());
					String getinfo=new String(infoinput.getText());
					if(getname.equals("")||getweight.equals("")
							||getinfo.equals(""))
						JOptionPane.showMessageDialog(null, "����Ϊ��");
					else
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						ResultSet r=s.executeQuery("select * from concert");
						r.beforeFirst();
						boolean flag=true;		//�ж��ݳ��Ƿ��Ѿ����ڣ���ֹ����
						while(r.next())
						{
							if(getname.equals(r.getString("concertname")))
							{
								flag=false;
								break;
							}
						}
						if(!flag)
						{
							JOptionPane.showMessageDialog(null, "���ݳ��Ѵ���");
						}
						else
						{
							int we=Integer.valueOf(getweight);
							//concert��assess��������Ҫ����
							s.executeUpdate(
									"insert into concert values(\'"+getname+"\',"+we+",\'"+getinfo+"\')");
							s.executeUpdate(
									"alter table assess add "+getname+" int");
							s.executeUpdate(
					    			"update assess set "+getname+"=0");
						}
						
						s.close();
						c.close();
						
						JOptionPane.showMessageDialog(null, "����ɹ�");
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
	}
	
	public void buildaddconcertframe()
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

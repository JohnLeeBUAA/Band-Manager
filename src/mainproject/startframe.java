package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//��½���洰����
public class startframe 
{
	
	private JFrame f;
	private JLabel username;
	private JLabel password;
	private JLabel identity;
	private JTextField usernameinput;
	private JPasswordField passwordinput;
	private JComboBox identityselect;
	private JButton signin;
	private JButton register;
	
	public startframe()
	{             //���캯��
		
		f=new JFrame("��½����");
		username=new JLabel("�û���",JLabel.CENTER);
		password=new JLabel("����",JLabel.CENTER);
		identity=new JLabel("���",JLabel.CENTER);
		usernameinput=new JTextField("",40);
		passwordinput=new JPasswordField("",40);
		String[] s={"��Ա","����Ա","�ο�"};
		identityselect=new JComboBox(s);
		signin=new JButton("��¼");
		register=new JButton("ע��һ���˺�");
		
		signin.addActionListener(new ActionListener() {   //��¼��
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getusername=new String(usernameinput.getText());
					String getpassword=new String(passwordinput.getText());
					int getidentity=identityselect.getSelectedIndex()+1;   //1-��Ա 2-����Ա 3-�ο�
					if(getusername.equals("")||getpassword.equals(""))
					{   //�ж��Ƿ��п�����
						JOptionPane.showMessageDialog(null, "����Ϊ��");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
						Connection c
						=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
						Statement s=c.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						ResultSet r=s.executeQuery("select * From personlist");
						String temp=new String(getusernamepassword(r,getusername,getidentity));
						if(temp.equals(""))
						{
							JOptionPane.showMessageDialog(null, "�����ڸ��û�");
						}
						else 
						{
							if(temp.equals(getpassword))
							{
								if(getidentity==1)
								{
									memberframe a=new memberframe(getusername);
									a.buildmemberframe();
								}
								else if(getidentity==2)
								{
									managerframe a=new managerframe(getusername);
									a.buildmanagerframe();
								}
								else
								{
									guestframe a=new guestframe(getusername);
									a.buildguestframe();
								}
								f.dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "����������벻��ȷ");
							}
						}
						s.close();
						c.close();
					}
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		register.addActionListener(new ActionListener() {   //�����˺ż�
			public void actionPerformed(ActionEvent e) {
				try
				{
					registerframe a=new registerframe();
					a.buildregisterframe();
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
	
	public void buildstartframe()
	{
		
		f.setSize(400,200);
		f.setLayout(new GridLayout(4,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(username);
		f.add(usernameinput);
		f.add(password);
		f.add(passwordinput);
		f.add(identity);
		f.add(identityselect);
		f.add(signin);
		f.add(register);
		f.setVisible(true);
		
	}
	
	//�ú������������ж��û����Ƿ����
	//�������򷵻ض�Ӧ�������ڱȶ�
	//�������򷵻ؿ��ַ���
	public String getusernamepassword(ResultSet r,String name,int id)
	{
		
		try
		{
			r.beforeFirst();
			while(r.next())
			{
				if(name.equals(r.getString("username"))&&id==r.getInt("identity"))
					return r.getString("password");
			}
			return "";
		}
		catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		
		startframe a=new startframe();
		a.buildstartframe();
	}
	

}

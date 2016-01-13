package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//ע�ᴰ��
public class registerframe 
{
	
	private JFrame f;
	private JLabel username;
	private JLabel password;
	private JLabel password2;
	private JLabel identity;
	private JTextField usernameinput;
	private JPasswordField passwordinput;
	private JPasswordField passwordinput2;
	private JComboBox identityselect;
	private JButton next;
	
	public registerframe()
	{
		
		f=new JFrame("ע��һ���˺�");
		username=new JLabel("�û���",JLabel.CENTER);
		password=new JLabel("����",JLabel.CENTER);
		password2=new JLabel("ȷ������",JLabel.CENTER);
		identity=new JLabel("ѡ�����",JLabel.CENTER);
		usernameinput=new JTextField("",40);
		passwordinput=new JPasswordField("",40);
		passwordinput2=new JPasswordField("",40);
		String[] s={"��Ա","����Ա","�ο�"};
		identityselect=new JComboBox(s);
		next=new JButton("��һ��");
		
		next.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getusername=new String(usernameinput.getText());
					String getpassword=new String(passwordinput.getText());
					String getpassword2=new String(passwordinput2.getText());
					int getidentity=identityselect.getSelectedIndex()+1;   //1-��Ա 2-����Ա 3-�ο�
					if(getusername.equals("")||getpassword.equals("")||getpassword2.equals(""))
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
						ResultSet r=s.executeQuery("select username From personlist");
						r.beforeFirst();
						boolean flag=true;//��¼��Ҫ������û����Ƿ�Ϸ�
						while(r.next())
						{
							if(getusername.equals(r.getString("username")))
							{
								flag=false;
								break;
							}
						}
						if(!flag)
						{
							JOptionPane.showMessageDialog(null, "���û����Ѵ���");
						}
						else if(!(getpassword.equals(getpassword2)))
						{
							JOptionPane.showMessageDialog(null, "��������������벻��ͬ");
						}
						else
						{
							String empty=new String("");
							s.executeUpdate(
								"insert into personlist values(\'"+getusername+"\',\'"+getpassword+
								"\',\'"+empty+"\',\'"+empty+"\',\'"+empty+"\',\'"+empty+"\',\'"
								+empty+"\',"+getidentity+",\'"+empty+"\',\'"+empty+"\',\'"+empty+"\',"+1+")");
							
							//����assess���е���������sql���Ӽ�¼���temp��temp1
							r=s.executeQuery("select * from assess");
							ResultSetMetaData m=r.getMetaData();
							int n=m.getColumnCount(); //����
							
							StringBuffer temp=new StringBuffer("insert into assess values(\'"+getusername+"\'");
							
							for(int i=0;i<n-1;i++)
								temp.append(",0"); //��Ӧ�м�¼�����ֵ
							
							temp.append(")");
							String temp1=new String(temp);//ת����String����
							
							s.executeUpdate(temp1);		//��assess���������Ա��¼
							
							//ע��ɹ��������������ϴ�����
							JOptionPane.showMessageDialog(null, "ע��ɹ�������д��������");
							personalframe a=new personalframe(getusername,getidentity);
							a.buildpersonalframe();
							f.dispose();
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
	}
	
    public void buildregisterframe()
    {
		
		f.setSize(400,200);
		f.setLayout(new GridLayout(5,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(username);
		f.add(usernameinput);
		f.add(password);
		f.add(passwordinput);
		f.add(password2);
		f.add(passwordinput2);
		f.add(identity);
		f.add(identityselect);
		f.add(next);
		f.setVisible(true);
		
	}
    
}

package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.*;

//�༭������Ϣ������
//���������Ϣ������
//���캯��������������Ϊ�༭������Ϣ
//���캯����һ��������Ϊ���������Ϣ
public class editinstrumentframe 
{
	private JFrame f;
	private JTextField instrumentnameinput;			//����
	private JLabel instrumentname;
	private JTextField quantityinput;				//����
	private JLabel quantity;
	private JTextField lendinput;				//�Ƿ���
	private JLabel lend;
	private JTextField borrowerinput;			//������
	private JLabel borrower;
	private JButton save;
	private JButton back;
    private String username;
    private String insname;
    
    //�����������Ĺ��캯��
    public editinstrumentframe(String name,String name2)
    {
    	username=new String(name);
    	insname=new String(name2);
    	f=new JFrame("�༭������Ϣ");
    	instrumentnameinput=new JTextField(insname,40);
    	instrumentnameinput.setEditable(false);				//�����������Ʋ��ɱ༭
    	instrumentname=new JLabel("����",JLabel.CENTER);
    	quantityinput=new JTextField("",40);
    	quantity=new JLabel("����",JLabel.CENTER);
    	lendinput=new JTextField("",40);
    	lend=new JLabel("�Ƿ���",JLabel.CENTER);
    	borrowerinput=new JTextField("",40);
    	borrower=new JLabel("������",JLabel.CENTER);
    	save=new JButton("����");
    	back=new JButton("�˳��༭");
    	
    	try
    	{
    		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
    		Connection c
    		=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
    		Statement s=c.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
    		ResultSet r=s.executeQuery("select * From instrument");
    		r.beforeFirst();
    		while(r.next())
    		{
    			//��ʼ���ı����ݣ�ʹ�ô�ʱ�ɿ���������Ϣ
    			if(insname.equals(r.getString("instrumentname")))
    			{
    				quantityinput.setText(r.getString("quantity"));
    				lendinput.setText(r.getString("lend"));
    				borrowerinput.setText(r.getString("borrower"));
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
					String getquantity=new String(quantityinput.getText());
					String getlend=new String(lendinput.getText());
					String getborrower=new String(borrowerinput.getText());
					if(getlend.equals("")||getquantity.equals("")||getborrower.equals(""))
					{   //�ж��Ƿ��п�����
						JOptionPane.showMessageDialog(null, "����д��ȫ");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
				    	Connection c
				    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
				    	Statement s=c.createStatement();
				    	s.executeUpdate(
				    			"update instrument set quantity=\'"+getquantity+"\'where instrumentname=\'"+insname+"\'");
				    	s.executeUpdate(
				    			"update instrument set lend=\'"+getlend+"\'where instrumentname=\'"+insname+"\'");
				    	s.executeUpdate(
				    			"update instrument set borrower=\'"+getborrower+"\'where instrumentname=\'"+insname+"\'");
				    	
				    	s.close();
				    	c.close();
				    	JOptionPane.showMessageDialog(null, "����ɹ�");
				    	manageinstrumentframe a=new manageinstrumentframe(username);
				    	a.buildmanageinstrumentframe();
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
					manageinstrumentframe a=new manageinstrumentframe(username);
			    	a.buildmanageinstrumentframe();
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
    
    
    //��һ�������Ĺ��캯��
    public editinstrumentframe(String name)
    {
    	username=new String(name);
    	f=new JFrame("���������Ϣ");
    	instrumentnameinput=new JTextField("",40);
    	instrumentname=new JLabel("����",JLabel.CENTER);
    	quantityinput=new JTextField("",40);
    	quantity=new JLabel("����",JLabel.CENTER);
    	lendinput=new JTextField("",40);
    	lend=new JLabel("�Ƿ���",JLabel.CENTER);
    	borrowerinput=new JTextField("",40);
    	borrower=new JLabel("������",JLabel.CENTER);
    	save=new JButton("����");
    	back=new JButton("�˳��༭");
    	
    	save.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					String getinstrumentname=new String(instrumentnameinput.getText());
					String getquantity=new String(quantityinput.getText());
					String getlend=new String(lendinput.getText());
					String getborrower=new String(borrowerinput.getText());
					if(getinstrumentname.equals("")||getlend.equals("")||getquantity.equals(""))
					{   //�ж��Ƿ��п�����
						JOptionPane.showMessageDialog(null, "����д��ȫ");
				    }
					else
					{                                              
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");       //�������ݿ�
				    	Connection c
				    	=DriverManager.getConnection("jdbc:odbc:bandmanagerDatabase");
				    	Statement s=c.createStatement(
								ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						ResultSet r=s.executeQuery("select * From instrument");
						if(exist(r,getinstrumentname))
						{	//�ж��Ƿ�������
							JOptionPane.showMessageDialog(null, "�������Ѽ�¼");
						}
						else
						{
							s.executeUpdate(
									"insert into instrument values(\'"+getinstrumentname+"\',\'"+getquantity+
									"\',\'"+getlend+"\',\'"+getborrower+"\')");
						}
				    	s.close();
				    	c.close();
				    	JOptionPane.showMessageDialog(null, "��������ɹ�");
				    	manageinstrumentframe a=new manageinstrumentframe(username);
				    	a.buildmanageinstrumentframe();
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
					manageinstrumentframe a=new manageinstrumentframe(username);
			    	a.buildmanageinstrumentframe();
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
    
    //�ж������Ƿ���ڣ����ڷ���true
    public boolean exist(ResultSet r,String name)
    {
    	try
    	{
    		r.beforeFirst();
    		while(r.next())
    		{
    			if(name.equals(r.getString("instrumentname")))
    				return true;
			}
    		return false;
    	}
    	catch(Exception ex)
		{
			System.err.println("Exception :" + ex);
			ex.printStackTrace();
		}
    	return false;
    }
    
    
    //�����
    public void buildeditinstrumentframe()
    {
    	f.setSize(400,300);
		f.setLayout(new GridLayout(5,2));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(instrumentname);
		f.add(instrumentnameinput);
		f.add(quantity);
		f.add(quantityinput);
		f.add(lend);
		f.add(lendinput);
		f.add(borrower);
		f.add(borrowerinput);
		f.add(save);
		f.add(back);
		f.setVisible(true);
    }

}

package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//�οʹ����࣬�ο����Ϊ3
public class guestframe 
{
	
	private String username;		//��¼��ǰ�û��û���
	private JFrame f;
	private JButton culture;		//�����Ļ��������޹��ܣ�
	private JButton link;			//�������������޹��ܣ�
	private JButton personal;		//�������ϰ���
	private JButton share;			//����������
	private JButton logoff;			//ע������
	private JButton changepassword;	//�޸����밴��
	
	public guestframe(String name)
	{
		
		username=new String(name);
		f=new JFrame("���ã�"+username+"������������ο�");
		culture=new JButton("�����Ļ�");
		link=new JButton("������");
		personal=new JButton("��������");
		share=new JButton("������");
		logoff=new JButton("ע��");
		changepassword=new JButton("�޸�����");
		
		//�������ϰ���������
		personal.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					//�����������ϴ�����
					personalframe a=new personalframe(username,3);
					a.buildpersonalframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		logoff.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				try
				{
					startframe a=new startframe();
					a.buildstartframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		changepassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					passwordframe a=new passwordframe(username,3);
					a.buildpasswordframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		share.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					shareframe a=new shareframe(username,3);
					a.buildshareframe();
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
	
	public void buildguestframe()
	{
		
		f.setSize(400,400);
		f.setLayout(new GridLayout(6,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(culture);
		f.add(link);
		f.add(share);
		f.add(personal);
		f.add(changepassword);
		f.add(logoff);
		f.setVisible(true);
	}
	
}

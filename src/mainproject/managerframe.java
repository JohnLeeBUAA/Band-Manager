package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//����Ա������
public class managerframe 
{
	
	private String username;		//��¼�û���
	private JFrame f;
	private JButton publish;		//����������Ϣ
	private JButton personal;		//��������
	private JButton share;			//������
	private JButton bandblog;		//����־
	private JButton manage;			//������Ա
	private JButton manageinstrument;	//��������
	private JButton manageconcert;		//�����ݳ�
	private JButton logoff;			//ע��
	private JButton changepassword;	//�޸�����
	private JButton assessmember;	//��Ա����
	
	public managerframe(String name)
	{
		
		username=new String(name);
		f=new JFrame("���ã�"+username+"����������ǹ���Ա");
		bandblog=new JButton("����־");
		publish=new JButton("����������Ϣ");
		personal=new JButton("��������");
		share=new JButton("������");
		manage=new JButton("������Ա");
		logoff=new JButton("ע��");
		changepassword=new JButton("�޸�����");
		manageinstrument=new JButton("��������");
		manageconcert=new JButton("�����ݳ�");
		assessmember=new JButton("ǩ��������");
		
		publish.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					makeannouncementframe a=new makeannouncementframe(username);
					a.buildmakeannouncementframe();
					f.dispose();
				}
				catch(Exception ex)
				{
					System.err.println("Exception :" + ex);
					ex.printStackTrace();
				}
			}
		});
		
		bandblog.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					bandblogframe a=new bandblogframe(username,2);
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
		
		personal.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					personalframe a=new personalframe(username,2);
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
		
		manageconcert.addActionListener(new ActionListener() {   
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
		
		assessmember.addActionListener(new ActionListener() {   
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
		
		manageinstrument.addActionListener(new ActionListener() {   
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
		
		changepassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					passwordframe a=new passwordframe(username,2);
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
					shareframe a=new shareframe(username,2);
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
		
		manage.addActionListener(new ActionListener() {
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
	
	public void buildmanagerframe()
	{
		
		f.setSize(400,500);
		f.setLayout(new GridLayout(10,1));
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(publish);
		f.add(manage);
		f.add(manageinstrument);
		f.add(manageconcert);
		f.add(assessmember);
		f.add(bandblog);
		f.add(share);
		f.add(personal);
		f.add(changepassword);
		f.add(logoff);
		f.setVisible(true);
	}
	
}

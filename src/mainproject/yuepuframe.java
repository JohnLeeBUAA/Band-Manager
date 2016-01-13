package mainproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


//乐谱窗口类
public class yuepuframe 
{
	private JFrame f;
	private ImageIcon i;
	private String username;
	private JButton back;
	
	public yuepuframe(String name)
	{
		username=new String(name);
		f=new JFrame("查看乐谱");
		i=new ImageIcon("2.jpg");		//添加一个乐谱图片
		back=new JButton("返回");
		
		back.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				try
				{
					memberframe a=new memberframe(username);
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
	
	public void buildyuepuframe()
	{
		f.setSize(500,700);
		f.setLayout(new FlowLayout());
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel l=new JLabel(i);
		f.add(back);
		f.add(l);
		f.setVisible(true);
	}

}

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Server4 extends JFrame implements ActionListener,Runnable
{
	JButton bs,bc;
	JLabel lfc,ltc;
	JTextArea tfc,ttc;
	ServerSocket ss;
	Socket s;
	DataOutputStream dos;
	DataInputStream dis;
	String s1,s2;
	Thread th;

	Server4()throws Exception
	{
		super("Server");
		lfc=new JLabel("From Client");
		tfc=new JTextArea("",10,50);
		ltc=new JLabel("To Client");
		ttc=new JTextArea("0",10,50);
		bs=new JButton("Send");
		bc=new JButton("Clear");

		bs.addActionListener(this);
		bc.addActionListener(this);
		setLayout(null);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				try{dos.close();
				dis.close();
				s.close();
				ss.close();}
				catch(Exception err){}
				System.exit(0);
			}
		});

		lfc.setBounds(20,50,100,20);
		tfc.setBounds(20,80,150,100);
		ltc.setBounds(20,190,100,20);
		ttc.setBounds(20,220,150,100);
		bs.setBounds(20,330,70,20);
		bc.setBounds(100,330,70,20);

		add(lfc);
		add(tfc);
		add(ltc);
		add(ttc);
		add(bs);
		add(bc);
		tfc.setEditable(false);
		setSize(190,400);
		setVisible(true);

		ss=new ServerSocket(6061);
		s=ss.accept();
		dos=new DataOutputStream(s.getOutputStream());
		dis=new DataInputStream(s.getInputStream());
		th=new Thread(this);
		th.start();
	}
	public void run()
	{
		try
		{
			while(true)
			{
				s1=dis.readUTF();
				s2=tfc.getText()+"\n"+s1;
				tfc.setText(s2);
			}
		}
		catch(Exception re){}
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton b=(JButton)e.getSource();
		if(b==bc)
			ttc.setText("");
		else
		{
			try{
			s1=ttc.getText();
			dos.writeUTF(s1);
			ttc.setText("");}
			catch(Exception sd){}
		}
	}
	public static void main(String []args)throws Exception
	{
		Server4 sss=new Server4();
	}
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shanks
 */
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.print.PrinterJob;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.print.attribute.HashPrintRequestAttributeSet;
public class Notepad extends JFrame implements ActionListener
{
	static Notepad passWordFrame;
    byte[] encryptedData;
	JMenu m1,m2,m3,m4,m5;
	JMenuBar bar;
	JMenuItem mi[]=new JMenuItem[24];
	JTextArea t;
	JScrollPane jp;
	JFileChooser jfc=new JFileChooser();
	File path=null;
	static String pass;
	JTextField tf;
	JButton b1;
	//UndoManager undo=new UndoManager();
	
	Notepad(int i)
	{
		setTitle("Enter password");
		setLayout(null);
		tf=new JTextField();
		tf.setBounds(200, 100, 100, 30);
		add(tf);
		b1=new JButton("OK");
		add(b1);
		b1.setBounds(200, 200, 80, 30);
		b1.addActionListener(this);
		setBounds(800,300,400,500);
		JLabel l1=new JLabel("Enter password");
		l1.setBounds(50,100,100,30);
		add(l1);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getRootPane().setDefaultButton(b1);	// when user presses ENTER then it automatically clicks on OK button.

	}

	Notepad()
	{
		setTitle("Notepad");
		setLayout(new BorderLayout());	// so as to make textArea resizable
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{}

		bar=new JMenuBar();
		t=new JTextArea();
		jp=new JScrollPane(t);
		m1=new JMenu("File");
		m2=new JMenu("Edit");
		m3=new JMenu("Format");
		m4=new JMenu("Convert");
		m5=new JMenu("Help");
		mi[0]=new JMenuItem("New");
		mi[1]=new JMenuItem("Open");
		mi[2]=new JMenuItem("Save");
		mi[3]=new JMenuItem("Save As");
		mi[4]=new JMenuItem("Page Setup");
		mi[5]=new JMenuItem("Print");
		mi[6]=new JMenuItem("Exit");
		mi[7]=new JMenuItem("Undo");
		mi[8]=new JMenuItem("Cut");
		mi[9]=new JMenuItem("Copy");
		mi[10]=new JMenuItem("Paste");
		mi[11]=new JMenuItem("Delete");
		mi[12]=new JMenuItem("Find");
		mi[13]=new JMenuItem("Find Next");
		mi[14]=new JMenuItem("Replace");
		mi[15]=new JMenuItem("Goto");
		mi[16]=new JMenuItem("Select All");
		mi[17]=new JMenuItem("Time/Date");
		mi[18]=new JMenuItem("Word Wrap");
		mi[19]=new JMenuItem("Font");
		mi[20]=new JMenuItem("Encrypt");
		mi[21]=new JMenuItem("Decrypt");
		mi[22]=new JMenuItem("View Help");
		mi[23]=new JMenuItem("About Notepad");

		setJMenuBar(bar);
		bar.add(m1);
		bar.add(m2);
		bar.add(m3);
		bar.add(m4);
		bar.add(m5);
		add(jp);

		for(int i=0;i<7;i++)
		{
			m1.add(mi[i]);
			m1.add(new JSeparator());
		}
		for(int i=7;i<18;i++)
		{
			m2.add(mi[i]);
			m2.add(new JSeparator());
		}
		m3.add(mi[18]);
		m3.add(mi[19]);
		m4.add(mi[20]);
		m4.add(mi[21]);
		m5.add(mi[22]);
		m5.add(mi[23]);

		m1.setMnemonic(KeyEvent.VK_F);
		m2.setMnemonic(KeyEvent.VK_E);
		m3.setMnemonic(KeyEvent.VK_O);
		m4.setMnemonic(KeyEvent.VK_C);
		m5.setMnemonic(KeyEvent.VK_H);

		mi[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		mi[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		mi[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		mi[5].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
		mi[7].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
		mi[8].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		mi[9].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		mi[10].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		mi[11].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
		mi[12].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		mi[13].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
		mi[14].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
		mi[15].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
		mi[16].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		mi[17].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));

		for(int i=0;i<23;i++)
		{
			mi[i].addActionListener(this);
		}
		setBounds(0, 0, 700, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String...sa)
	{
		passWordFrame = new Notepad(1);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==mi[0])	//for new
		{
			t.setText("");
		}
		else if(ae.getSource()==mi[1])	//for open
		{
			this.setVisible(false);
			int x=jfc.showOpenDialog(null);
			if(x==JFileChooser.APPROVE_OPTION)
			{
				File f=jfc.getSelectedFile();
				try
				{
					BufferedReader input=new BufferedReader(new FileReader(f));
					StringBuffer str=new StringBuffer();
					String line;
					while((line=input.readLine())!=null)
						str.append(line+"\n");
					t.setText(str.toString());
					setTitle(f.getName()+"-Notepad");
				}
				
				catch(Exception e)
				{}
				//	System.out.println(f1);
			}
			else if(x==JFileChooser.CANCEL_OPTION)
				System.out.println("cancel button is pressed");
		}
		else if(ae.getSource()==mi[2])	//for save
		{
			if(path==null)
			{
				save_as();
				return;
			}
			try
			{
				FileWriter fw=new FileWriter(path);
				BufferedWriter output=new BufferedWriter(fw);
				String line=t.getText();
				output.write(line);
				output.flush();
			}
			catch(Exception e)
			{}
		}
		else if(ae.getSource()==mi[3])	//for save as
		{
			save_as();
		}
		else if(ae.getSource()==mi[4])	//for page setup
		{
			print();
		}
		else if(ae.getSource()==mi[5])	//for print
		{
			print();
		}
		else if(ae.getSource()==mi[6])	//for exit
		{
			if((t.getText().equals(""))&&(path==null))
			{}
			else
			{
				save_as();
			}
			System.exit(1);
		}
		else if(ae.getSource()==mi[20])
		{
                    try {
                      encrypt();
                    } catch (Exception ex) {
                        Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
		else if(ae.getSource()==mi[21])
		{
                    try {
                        decrypt();
                    } catch (Exception ex) {
                        Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
		else if(ae.getSource()==b1)
		{
			pass=tf.getText();
			if(pass.equals("test"))
			{
				passWordFrame.dispose();
				new Notepad();
			}
			else{
				JOptionPane sw=new JOptionPane();
				sw.showMessageDialog(null,"wrong password");
			}				
		}
			
	}

	public void save_as()
	{
		int x=jfc.showSaveDialog(null);
		if(x==JFileChooser.APPROVE_OPTION)
		{
			File f=jfc.getSelectedFile();
			if(f.exists())
				JOptionPane.showConfirmDialog(this,"file with same name exists");
			try
			{
				StringBuffer str=new StringBuffer();
				String line;
				line=t.getText();
				BufferedWriter output=new BufferedWriter(new FileWriter(f));
				output.write(line);
				output.flush();
			}
			catch(Exception e)
			{}
			path=f;
		}
		else if(x==JFileChooser.CANCEL_OPTION)
		{
			System.out.println("cancel button is pressed");
		}
	}

	public void print()
	{
		PrinterJob ptr=PrinterJob.getPrinterJob();
		HashPrintRequestAttributeSet attr=new HashPrintRequestAttributeSet();
		if(ptr.printDialog(attr))
		{
			try
			{
				ptr.print(attr);
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this,"failed to print"+e+"error");
			}
		}
	}
	public void encrypt() throws Exception
	{
            String st=t.getText();
            byte[] encryptedBytes = null;
            String key = "ThisIsASecretKey";
            byte[] raw = key.getBytes(Charset.forName("US-ASCII"));
            if (raw.length != 16) {
                throw new IllegalArgumentException("Invalid key size.");
            }
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec,new IvParameterSpec(new byte[16]));
            encryptedBytes =  cipher.doFinal(st.getBytes(Charset.forName("US-ASCII")));
            t.setText(Base64.encode(encryptedBytes));
	}

	public void decrypt() throws Exception
	{
            String st=t.getText();
            String key = "ThisIsASecretKey";
            byte[] raw = key.getBytes(Charset.forName("US-ASCII"));
            if (raw.length != 16) {
                 throw new IllegalArgumentException("Invalid key size.");
            }
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,new IvParameterSpec(new byte[16]));
            byte[] original = cipher.doFinal(Base64.decode(st));
            t.setText(new String(original, Charset.forName("US-ASCII")));
        }
}
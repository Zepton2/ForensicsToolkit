package LastAccessed;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

public class About extends JDialog
{

	private final JPanel	contentPanel	= new JPanel ( );//creates a jpanel to hold all content in jdialog


	
	public About ( )
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));//loads icon image from relative location
		setTitle("About Forensics Toolkit - 0.50");//sets title to about address book
		setBounds (100, 100, 607, 327);//sets the bounds x, y, followed by width and height used in absolute positioning
		getContentPane ( ).setLayout (new BorderLayout (15,15 ));//sets the border layout with a 15px horizontal and vertical gap
		contentPanel.setBorder (new EmptyBorder (5, 5, 5, 5));//set the border of the jpanel to 5px all the way around
		getContentPane ( ).add (contentPanel, BorderLayout.CENTER);//centers the border layout
		contentPanel.setLayout(null);//allows for an absolute layout
		
		JLabel lblAddressBookFor = new JLabel("Program:	Forensics Toolkit");//creates a new label
		lblAddressBookFor.setBounds(317, 11, 215, 29);//sets the bounds x, y, followed by width and height used in absolute positioning
		contentPanel.add(lblAddressBookFor);//adds label to the content panel
		
		JLabel lblCreatedForCsciproject = new JLabel("Created for Educational Purposes");//creates a new label
		lblCreatedForCsciproject.setBounds(317, 91, 177, 29);//sets the bounds x, y, followed by width and height used in absolute positioning
		contentPanel.add(lblCreatedForCsciproject);//adds label to the content panel
		
		JLabel lblCopyrightJarredLight = new JLabel("Copyright: Jarred Light, Wednesday, January 25, 2016");//creates a new label
		lblCopyrightJarredLight.setBounds(317, 131, 274, 29);//sets the bounds x, y, followed by width and height used in absolute positioning
		contentPanel.add(lblCopyrightJarredLight);//adds label to the content panel
		
		JLabel lblPicture = new JLabel("");//holds the picture to be displayed in the dialog
		lblPicture.setBounds(10, 11, 297, 231);//sets the bounds x, y, followed by width and height used in absolute positioning
		contentPanel.add(lblPicture);//adds label to the content panel
		
		lblPicture.setIcon(new ImageIcon("C:\\Users\\Jarred\\workspace\\FileManipulation\\Data\\Pictures\\computer_forensics.jpg"));//sets the icon of the label
		
		JLabel lblForAnyBugs = new JLabel(" For any bugs or suggestions email me the developer at lightdj@goldmail.etsu.edu");
		lblForAnyBugs.setBounds(10, 248, 412, 29);
		contentPanel.add(lblForAnyBugs);
		
		JLabel lblDoNotUse = new JLabel("DO NOT use on public machines!");
		lblDoNotUse.setBounds(317, 51, 165, 29);
		contentPanel.add(lblDoNotUse);
		
		JLabel lblVersion = new JLabel("Version 0.5");
		lblVersion.setBounds(517, 255, 64, 14);
		contentPanel.add(lblVersion);
	}
	
	/**
	 * returns an image icon of the passed image filename      
	 *
	 * <hr>
	 * Date created: Apr 26, 2015
	 *
	 * <hr>
	 * @param fileName
	 * @param desiredHeight
	 * @return
	 */
	public ImageIcon getPhotoIcon(String fileName, int desiredHeight)
	{
		ImageIcon icon = new ImageIcon(fileName);//creates an image icon of the passed filename
		Image picture = icon.getImage();//creates an image icon of the passed filename
		double ratio = (double) picture.getWidth(null) / picture.getHeight(null);
		
		BufferedImage buff = new BufferedImage((int)(desiredHeight*ratio),desiredHeight,BufferedImage.TYPE_INT_ARGB);
		Graphics g = buff.getGraphics ( );
		g.drawImage (picture,0,0,(int)(desiredHeight*ratio),desiredHeight,null);
		return new ImageIcon(buff);//returns the image icon of the contact selected
	}
}


package LastAccessed;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Toolkit;


/**
 * This program is completely open source.
 * Use this program at your own risk.
 * Do not use this program to perform malicious tasks.
 * 
 * This program will traverse directories and display
 * forensic information to help you investigate what's
 * going on in your system's file structure.
 *
 * <hr>
 * Date created: Jan 10, 2015
 * <hr>
 * @author Jarred Light
 */
public class LastAccessedGui extends JFrame {

	private JPanel contentPane;
	private JTextField txtPath;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JProgressBar progressBar;
	private JFileChooser chooser;
	private String choosertitle;

	public void setTextArea(JTextArea text)
	{
		this.textArea = text;
	}
	
	public void setProgressBar(JProgressBar progress)
	{
		this.progressBar = progress;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try 
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//gives windows control over look and feel
		} 
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LastAccessedGui frame = new LastAccessedGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LastAccessedGui() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(LastAccessedGui.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		setResizable(false);
		setTitle("Forensics Toolkit - 0.50");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 652, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPath = new JTextField();
		txtPath.setToolTipText("Enter a valid file path");
		txtPath.setBounds(106, 42, 297, 20);
		contentPane.add(txtPath);
		txtPath.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 613, 292);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.setToolTipText("Traverses the file path entered");
		btnCalculate.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				setTextArea(textArea);
				String pathName;
				pathName = txtPath.getText();
				File[] files = new File(pathName).listFiles();
				
				if(files != null)
				{
					try
					{
						showFiles(files);
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog (contentPane, "You must enter a valid file path. Use the \"Choose Path\" button if " +
									"you're having trouble.", "Application Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCalculate.setBounds(534, 41, 101, 23);
		contentPane.add(btnCalculate);
		
		JLabel lblFilePath = new JLabel("Enter File Path:");
		lblFilePath.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFilePath.setBounds(10, 41, 105, 23);
		contentPane.add(lblFilePath);
		
		JButton btnClearText = new JButton("Clear Text");
		btnClearText.setToolTipText("Clear the text field");
		btnClearText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				textArea.setText (" ");
			}
		});
		btnClearText.setBounds(10, 369, 101, 20);
		contentPane.add(btnClearText);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 645, 30);
		contentPane.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//exit the application
				System.exit (0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//implement a help message
				JOptionPane.showMessageDialog (contentPane, "Welcome to the Forensics Toolkit.\nInstructions:\nStep 1: Enter a valid file path into the textbox.(Select Folder to the Right)\nStep 2: Click the Calculate Button.\n" +
									"Step 3: Wait for the results(Depending on the file path. Some take longer than others.)\nStep 4: Review information.\nStep 5: Either Save" +
									" to text file by clicking the Save to File button or exit by clicking the Exit button.","Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About about = new About();//creates an object of the about class which inherits jdialog
				about.setVisible(true);//sets the jdialog to visible
			}
		});
		mnHelp.add(mntmAbout);
		
		JButton btnChooseFile = new JButton("Choose Path");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//creates a JFileChooser to get the selected directory to parse through
				chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(choosertitle);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    //
			    // disable the "All files" option.
			    //
			    chooser.setAcceptAllFileFilterUsed(false);

			    if (chooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) 
			    { 
			    	txtPath.setText (chooser.getSelectedFile().getPath());
			    }
			    else
			    {
			      JOptionPane.showMessageDialog (contentPane,"You did not select a directory. Try Again.","Application Error.", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
		btnChooseFile.setBounds(413, 41, 111, 23);
		contentPane.add(btnChooseFile);}
	
		/*
		 * showFiles
		 * @param File[] files
		 * 
		 * Functionality:
		 * This function drives most of the Forensics program.
		 * This function uses recursion to traverse the passed directory
		 * */
		public void showFiles(File[] files) throws Exception //throws out the IOException
		{
			String allDir = " ";//holds directory name
			String pathNameDir = " ";//holds directory path name
			String pathNameFile = " ";//holds file path name
			String allFiles = " ";//hold file name
			String fileTimeDir = " ";//holds directory last accessed time
			String fileTimeFiles = " ";//holds file last accessed time
			boolean sameDateDir, sameDateFile;//stores wether the file is existential
			String accessedDate = " ";//holds the last accessed date
			String todaysDate = " ";//hold the current date
			int progress = 0;
			
				try
				{
					
					for(File file : files)
					{	
						
						try
						{
							
							if(file.isDirectory()) 
							{		
								allDir = file.getName();//returns directory name
								pathNameDir = file.getPath();//returns directory path
								Path fileDir = Paths.get(pathNameDir);//passes directory path
								BasicFileAttributes attrs = Files.readAttributes(fileDir, BasicFileAttributes.class);//read attributes of dir
								fileTimeDir = attrs.lastAccessTime().toString();//cast lastAccessedTime to a string
								accessedDate = Formatting.lastAccessed(fileTimeDir);//formats filetime
								Date current = new Date();//current date object
								String currentDate = current.toString();//calls the toString
								todaysDate = Formatting.formatDate(currentDate);//formats current date
								sameDateDir = Formatting.compareDate(todaysDate,accessedDate);//compare today's date to the the last accessed date
								
								if(sameDateDir)
								{
									textArea.append("\nDirectory: 	   " + allDir + " was accessed today at " + pathNameDir);
								}
								showFiles(file.listFiles());//passes directory into method again to traverse for files
							}
							else
							{
								allFiles = file.getName();//returns file name
								pathNameFile = file.getPath();//returns file path
								Path fileName = Paths.get(pathNameFile);//returns 
								BasicFileAttributes attrs = Files.readAttributes(fileName, BasicFileAttributes.class);//read attributes of dir
								fileTimeFiles = attrs.lastAccessTime().toString();//cast lastAccessedTime to a string
								accessedDate = Formatting.lastAccessed(fileTimeFiles);//formats filetime
								Date current = new Date();//current date object
								String currentDate = current.toString();//calls the toString
								todaysDate = Formatting.formatDate(currentDate);//formats current date
								sameDateFile = Formatting.compareDate(todaysDate,accessedDate);//compare today's date to the the last accessed date
								if(sameDateFile)
								{
									textArea.append("\nFile:	   " + allFiles + " was accessed today at " + pathNameFile);	
								}
							}
						}
						catch(NullPointerException e)
						{
							continue;//catches null pointer and continues loop
						}
						catch(NoSuchFileException f)
						{
							System.out.print("No such file.");
						}
						catch(IllegalThreadStateException g)
						{
							//do nothing
						}
					}
				}
				catch(NullPointerException e)//catches null pointer and ignores
				{
					//do nothing
				}	
	}//end showFiles method
		
	/*
	 * saveFile() : void
	 * 
	 * Functionality:
	 * The method creates a JFileChooser to save the log after running
	 * the program.
	 * */
	public void saveFile()
	{
		chooser = new JFileChooser();
	    chooser.setDialogTitle(choosertitle);
	    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    
	    if (chooser.showSaveDialog (contentPane) == JFileChooser.APPROVE_OPTION) 
	    { 
	    	File fileToSave = chooser.getSelectedFile ( );
	    	
	    	try
			{
				FileWriter fw = new FileWriter(fileToSave+".txt");
				fw.write (textArea.getText ( ));
				fw.close ( );
			}
	    	catch(NullPointerException ex)
	    	{
	    		JOptionPane.showMessageDialog (contentPane, "Cannot save, You need to run the program first.", "Application Error", JOptionPane.ERROR_MESSAGE);
	    	}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog (contentPane, "Cannot save, The file couldn't write.", "Application Error", JOptionPane.ERROR_MESSAGE);
			}
	    }
	    else
	    {
	      JOptionPane.showMessageDialog (contentPane,"You did not save.", "Warning", JOptionPane.WARNING_MESSAGE);
	    }
	}
}

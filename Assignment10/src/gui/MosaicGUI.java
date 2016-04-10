package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import fileManagement.FileManager;
import image.Mosaic;

/**
 * User interface for the mosaic 
 * @author Martin
 *
 */
public class MosaicGUI {
	
	private static JFileChooser fileDialog = new JFileChooser(System.getProperty("user.dir")); //file selector

	private JFrame frame; 
	private BufferedImage image;  // the actual image we are working with 
	private DisplayPanel panel;   // Custom panel to display an image that fits to the screeen 
	private JLabel fileNameLabel, statusLabel; // labeltown 
	private JButton mosaicButton; // mosaic-ify button
	private Mosaic mosaic; // Mosaic object does all the work transforming image 
	
	
	public MosaicGUI(){ 
		this.frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		image = null; 
		init();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dim);
		frame.setResizable(true);
		frame.setVisible(true);
		this.mosaic = new Mosaic(this);
	}

	/**
	 * Initialize JComponents set the display  panel and add the JMenu 
	 */
	public void init() {
		frame = new JFrame("Photo Mosaic");
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setBorder(new EmptyBorder(10,10,10,10));
		
		createMenu();
		contentPane.setLayout(new BorderLayout(6,6));
		
		panel = new DisplayPanel();
		panel.setBorder(new EtchedBorder());
		contentPane.add(panel, BorderLayout.CENTER);
		
		fileNameLabel = new JLabel(); 
		contentPane.add(fileNameLabel, BorderLayout.SOUTH);
		
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new GridLayout(0,1));
		
		statusLabel = new JLabel();
		
		mosaicButton = new JButton("Mosaic-ify");
		mosaicButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				mosaicify();
			}

		});
		toolbar.add(mosaicButton);
		contentPane.add(toolbar, BorderLayout.WEST);
        showFilename(null);
        mosaicButton.setEnabled(false);
        frame.pack();
        
	}
	
	/**
	 * Created the toolbar menu with Open, Save, Close and Exit functionality. 
	 */
	public void createMenu() {
		
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		
		JMenu menu;
		JMenuItem item; 
		
		menu = new JMenu("File");
		menubar.add(menu);
		
		item = new JMenuItem("Open...");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		
	
	menu.add(item);

	
	item =  new JMenuItem("Save As...");
	item.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveAs();
		}
	});

	menu.add(item);

	
	item = new JMenuItem("Close");
	item.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			close();
		}
	});
	
	menu.add(item);
	menu.addSeparator();
	
	item = new JMenuItem("Quit");
	item.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	});
	
	menu.add(item);
	
	
	}
	
	/**
	 * Opens a file from the disk and loads it to the display panel 
	 */
	public void openFile(){
		
		int success = fileDialog.showOpenDialog(frame);
		
		if(success != JFileChooser.APPROVE_OPTION) {
			return; 
		}
		File file = fileDialog.getSelectedFile();
		image = FileManager.loadImage(file);
		if( image == null) {
			JOptionPane.showMessageDialog(frame, "The file was not a valid format.", "ERROR!!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Dimension dim  = frame.getSize();
		BufferedImage scaledImage = FileManager.resize(image, panel.getWidth(),panel.getHeight());
		panel.set(scaledImage);
		mosaicButton.setEnabled(true);
		showFilename(file.getPath());
		showStatus("File loaded");
		frame.pack();
		frame.setSize(dim);
		frame.repaint();
		
	}
	
	/**
	 * Save an image to the disk 
	 */
	public void saveAs(){ 
		
		if(image != null) {
			int success = fileDialog.showSaveDialog(frame);
			
			if(success != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = fileDialog.getSelectedFile();
			FileManager.saveImage(image, file, "png");
			
			showFilename(file.getPath());
		}
		
	}
	
	/**
	 * Shows the name of the file on the filename label 
	 * @param name - name of file 
	 */
	private void showFilename(String name) {
		
		if(name == null) {
			fileNameLabel.setText("No file is displayed.");
		}else{
			fileNameLabel.setText("File: " + name);
		}
	}
	
	/**
	 * Update the status label with new status 
	 * @param status - new status 
	 */
	private void showStatus(String status) {
		statusLabel.setText(status);
	}
	
	/**
	 * Close the current image 
	 */
	public void close() {
		image = null;
		panel.clearImage();
		showFilename(null);
		mosaicButton.setEnabled(false);
	}

	/**
	 * Mosaic-ifies the current loaded image 
	 */
	public void mosaicify() {
		if(!mosaicButton.isEnabled()){
			return;
		}
		this.image = mosaic.createMosaic(this.image);
		Dimension dim  = frame.getSize();
		BufferedImage scaledImage = FileManager.resize(image, panel.getWidth(),panel.getHeight());
		panel.set(scaledImage);
		mosaicButton.setEnabled(false);
		frame.pack();
		frame.setSize(dim);
		frame.repaint();
	}

	/**
	 * Get the frame 
	 * @return - frame 
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * Enables/Disables the mosaic button 
	 * @param b - true for enabled or false for disabled 
	 */
	public void enableButton(boolean b) {
		this.mosaicButton.setEnabled(b);
	}
}

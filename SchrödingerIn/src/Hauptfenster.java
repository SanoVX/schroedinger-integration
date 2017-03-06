import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Hauptfenster extends JFrame {

	private JPanel contentPane;
	private JLabel lblEnergies;
	private SchroedingerIntegration simulation;
	private Game g;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hauptfenster frame = new Hauptfenster();
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
	public Hauptfenster() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		setBounds(0, 0, width, height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		g = new Game();
		
		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu Datei = new JMenu("Datei");
		menuBar.add(Datei);
		
		JMenuItem scrShot = new JMenuItem("Screenshot speichern");
		scrShot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter fextf = new FileNameExtensionFilter("Bild", "png");
				fc.setFileFilter(fextf);
				if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
					File f = fc.getSelectedFile();
					if (!f.getPath().endsWith(".png"))
					    f = new File(f.getPath() + ".png");
					BufferedImage i = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
					g.paint(i.getGraphics());
					try {
						ImageIO.write(i, "png", f);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		Datei.add(scrShot);
		
		JSeparator separator = new JSeparator();
		Datei.add(separator);
		
		JMenuItem exit = new JMenuItem("Beenden");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		Datei.add(exit);
		
		JMenu mnEinstellungen = new JMenu("Einstellungen");
		menuBar.add(mnEinstellungen);
		
		JMenuItem setPot = new JMenuItem("Potential");
		setPot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Potentialeinstellungen potSet = new Potentialeinstellungen();
				potSet.setVisible(true);		
			}
		});
		mnEinstellungen.add(setPot);
		
		JMenuItem setSim = new JMenuItem("Simulation");
		setSim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Simulationseinstellungen simSet = new Simulationseinstellungen();
				simSet.setVisible(true);
			}
		});
		mnEinstellungen.add(setSim);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		g.setBounds(5, 5, width-200, height);
		contentPane.add(g);

		simulation = new SchroedingerIntegration(g);
		g.setLayout(new GridLayout(1, 0, 0, 0));
		
		final JButton btnStart = new JButton("Start");
		btnStart.setBounds(width-150, height-200, 100, 50);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					if(btnStart.getText().equals("Start")){
						menuBar.getMenu(1).getItem(0).setEnabled(false);;
						ArrayList<Double>energies = simulation.run();
						lblEnergies.setText("<html>Energieniveaus:");
						for(int i = 0; i<energies.size();i++){
							lblEnergies.setText(lblEnergies.getText()+"<br>"+
										new DecimalFormat("###.###").format(energies.get(i))+" eV");
						}
						lblEnergies.setText(lblEnergies.getText()+"</html>");
						menuBar.getMenu(1).getItem(0).setEnabled(true);;
						btnStart.setText("Stopp");
					}else{
						simulation.stop();
						btnStart.setText("Start");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		JButton btnclear = new JButton("Clear");
		btnclear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				g = new Game();
			}
		});
		btnclear.setBounds(width-150, height-270, 100, 50);
		contentPane.add(btnclear);
		contentPane.add(btnStart);
		
		lblEnergies = new JLabel("Energieniveaus:");
		lblEnergies.setVerticalAlignment(SwingConstants.TOP);
		lblEnergies.setBounds(width-170, 10, 150, height-200);
		contentPane.add(lblEnergies);
		
	}


}

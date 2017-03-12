import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JProgressBar;

public class Hauptfenster extends JFrame {

	private JPanel contentPane;
	private JLabel lblEnergies;
	private JButton btnclear, btnStart;
	private JProgressBar progressBar;
	private SchroedingerIntegration simulation;
	private ArrayList<Double>energies;
	private Game g;
	private int[] prevMousePosition = {0,0};
	private boolean init = false;

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
		setTitle("Schroedingerintegration");
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
		contentPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {

			}

			public void mouseExited(MouseEvent arg0) {

			}

			public void mouseEntered(MouseEvent arg0) {

			}

			public void mousePressed(MouseEvent arg0) {
				
				if(g.ks.size() > 0){
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					int x = (int) b.getX();
					int y = (int) b.getY();
					CoordinateSystem s = g.ks.get(1);
					int xpos  = s.xpos;
					int ypos  = s.ypos;
					int xsize = s.xsize;
					int ysize = s.ysize;
					if(MouseAction.inKs(xpos, ypos, xsize, ysize, x, y)){

						init = true;
						prevMousePosition[0] = x;
						prevMousePosition[1] = y;
						
					}
					
				}
			}

			public void mouseReleased(MouseEvent arg0) {
				if(g.ks.size() > 0){
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					int x = (int) b.getX();
					int y = (int) b.getY();
					CoordinateSystem s = g.ks.get(1);

					if(init){
						double[] vect = {prevMousePosition[0] - x, prevMousePosition[1] - y};
						s.xmin = s.xmin - (Math.abs(s.xmax-s.xmin)/((double)s.xsize))*vect[0];
						s.xmax = s.xmax - (Math.abs(s.xmax-s.xmin)/((double)s.xsize))*vect[0];
						s.ymin = s.ymin - (Math.abs(s.ymax-s.ymin)/((double)s.ysize))*vect[1];
						s.ymax = s.ymax - (Math.abs(s.ymax-s.ymin)/((double)s.ysize))*vect[1];
						prevMousePosition[0] = x;
						prevMousePosition[1] = y;
					}
					init = false;
				}
			}
		});
		

		g.setBounds(5, 5, width-200, height);
		contentPane.add(g);

		simulation = new SchroedingerIntegration(g);
		g.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnclear = new JButton("Clear");
		btnclear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnStart.setText("Start");
				simulation.clear();
			}
		});
		btnclear.setEnabled(false);
		btnclear.setBounds(width-150, height-270, 100, 50);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(width-150, height-200, 100, 50);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					if(btnStart.getText().equals("Start")){
						menuBar.getMenu(1).getItem(0).setEnabled(false);
						progressBar.setVisible(true);
						progressBar.setMaximum(Einstellungen.maxNiveaus);
						progressBar.setValue(0);
						new Thread(){
							public void run(){
								try {
									energies = simulation.run();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(energies == null){
									return;
								}
								lblEnergies.setText("<html>Energieniveaus:");
								for(int i = 0; i<energies.size();i++){
									lblEnergies.setText(lblEnergies.getText()+"<br>"+
												new DecimalFormat("###.###").format(energies.get(i))+" eV");
								}
								lblEnergies.setText(lblEnergies.getText()+"</html>");
								
								menuBar.getMenu(1).getItem(0).setEnabled(true);;
								btnStart.setEnabled(true);
								btnclear.setEnabled(true);
								progressBar.setVisible(false);
							}		
						}.start();
						btnStart.setText("Stopp");
						btnStart.setEnabled(false);
						btnclear.setEnabled(false);
						
						Einstellungen.allesGezeichnet = false;
						new Thread(){
							public void run(){
								while(!Einstellungen.allesGezeichnet){
									progressBar.setValue(Einstellungen.berechneteNiveaus);
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								btnStart.setText("Start");
								btnclear.setEnabled(false);
								simulation.clear();
							}
						}.start();
					}else{
						simulation.stop();
						btnStart.setText("Start");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		

		contentPane.add(btnclear);
		contentPane.add(btnStart);
		
		lblEnergies = new JLabel("Energieniveaus:");
		lblEnergies.setVerticalAlignment(SwingConstants.TOP);
		lblEnergies.setBounds(width-170, 10, 150, height-200);
		contentPane.add(lblEnergies);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(width-170, height - 130, 150, 20);
		progressBar.setVisible(false);
		contentPane.add(progressBar);
	}
}

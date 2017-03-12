import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Simulationseinstellungen extends JFrame {

	private JPanel contentPane;
	private JTextField textAnzSuchEW;
	private JCheckBox chckbxBerechnungAnzeigen, chckboxNorInt;
	private JTextField textMaxAmpl;
	private JLabel lblAmplitudengrenzeBeiEigenwertberechnung;


	/**
	 * Create the frame.
	 */
	public Simulationseinstellungen() {
		setAlwaysOnTop(true);
		setTitle("Einstellungen zur Simulation");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("Hier koennen Einstellungen zur Simulation eingestellt werden.");
		titleLabel.setBounds(5, 5, 424, 14);
		contentPane.add(titleLabel);
		
		chckbxBerechnungAnzeigen = new JCheckBox("Berechnung anzeigen");
		chckbxBerechnungAnzeigen.setSelected(true);
		
		JLabel lblAnzahlDerZu = new JLabel("Anzahl der zu suchenden Eigenwerte:");
		lblAnzahlDerZu.setBounds(5, 30, 242, 20);
		contentPane.add(lblAnzahlDerZu);
		chckbxBerechnungAnzeigen.setBounds(6, 120, 171, 23);
		contentPane.add(chckbxBerechnungAnzeigen);
		
		textAnzSuchEW = new JTextField();
		textAnzSuchEW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					double value = Integer.parseUnsignedInt(textAnzSuchEW.getText());
					if(value<=0){
						textAnzSuchEW.setText(Integer.toUnsignedString(Einstellungen.maxNiveaus));
					}
				}catch(Exception exception){
					textAnzSuchEW.setText(Integer.toUnsignedString(Einstellungen.maxNiveaus));
				}
			}
		});
		textAnzSuchEW.setText("5");
		textAnzSuchEW.setBounds(257, 30, 53, 20);
		contentPane.add(textAnzSuchEW);
		textAnzSuchEW.setColumns(10);
		
		JButton btnFertig = new JButton("Fertig");
		btnFertig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fertig();
			}
		});
		btnFertig.setBounds(10, 228, 89, 23);
		contentPane.add(btnFertig);
		
		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnAbbrechen.setBounds(109, 228, 107, 23);
		contentPane.add(btnAbbrechen);
		
		chckboxNorInt = new JCheckBox("Normiere mit Integral");
		chckboxNorInt.setBounds(5, 146, 172, 23);
		contentPane.add(chckboxNorInt);
		
		textMaxAmpl = new JTextField();
		textMaxAmpl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					double value = Integer.parseUnsignedInt(textAnzSuchEW.getText());
					if(value<=0){
						textAnzSuchEW.setText(Integer.toUnsignedString(Einstellungen.maxNiveaus));
					}
				}catch(Exception exception){
					textAnzSuchEW.setText(Integer.toUnsignedString(Einstellungen.maxNiveaus));
				}
			}
		});
		textMaxAmpl.setText("1000");
		textMaxAmpl.setBounds(257, 57, 53, 20);
		contentPane.add(textMaxAmpl);
		textMaxAmpl.setColumns(10);
		
		lblAmplitudengrenzeBeiEigenwertberechnung = new JLabel("Amplitudengrenze bei Eigenwertberechnung:");
		lblAmplitudengrenzeBeiEigenwertberechnung.setBounds(5, 57, 238, 20);
		contentPane.add(lblAmplitudengrenzeBeiEigenwertberechnung);
	}


	private void fertig() {
		Einstellungen.maxNiveaus = Integer.parseUnsignedInt(textAnzSuchEW.getText());
		Einstellungen.Amplitudengrenze = Integer.parseUnsignedInt(textMaxAmpl.getText());
		Einstellungen.normalizeIntegral = chckboxNorInt.isSelected();
		Einstellungen.showCalculation = chckbxBerechnungAnzeigen.isSelected();
		
		dispose();
	}
}

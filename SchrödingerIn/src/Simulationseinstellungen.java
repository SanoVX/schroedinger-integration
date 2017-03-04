import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Simulationseinstellungen extends JFrame {

	private JPanel contentPane;


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
		
		JCheckBox chckbxBerechnungAnzeigen = new JCheckBox("Berechnung anzeigen");
		chckbxBerechnungAnzeigen.setSelected(true);
		chckbxBerechnungAnzeigen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					Einstellungen.showCalculation = true;
				}else{
					Einstellungen.showCalculation = false;
				}
			}
		});
		chckbxBerechnungAnzeigen.setBounds(5, 57, 171, 23);
		contentPane.add(chckbxBerechnungAnzeigen);
	}
}

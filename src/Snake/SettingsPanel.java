package Snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.net.*;

class SettingsPanel extends JPanel {
	private MainFrame mainFrame;
	private Dimension d;
	private JButton b1, b2, b3, b4, b5;
	private JSlider speedSlider;
	
	public SettingsPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		d = new Dimension();
		d.setSize(120, 30);
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(2, 2));
		b1 = new JButton("Pause (P)");
		b2 = new JButton("Replay");
		b3 = new JButton("GitHub");
		b4 = new JButton("Back To Menu");
		b5 = new JButton("Exit");
		b1.setPreferredSize(d);
		b2.setPreferredSize(d);
		b3.setPreferredSize(d);
		b4.setPreferredSize(d);
		btnPanel.add(b1);
		btnPanel.add(b2);
		btnPanel.add(b3);
		btnPanel.add(b4);
		
		speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, 5);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setMajorTickSpacing(5);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setSnapToTicks(true);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mainFrame.setSpeed(speedSlider.getValue() + 1);
				speedSlider.repaint();
				mainFrame.requestFocus();
			}
		});

		add(speedSlider, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.CENTER);
		add(b5, BorderLayout.SOUTH);

		b1.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				mainFrame.pauseGame();
				if(b1.getText().equals("Pause (P)"))
					b1.setText("Continue (P)");
				else
					b1.setText("Pause (P)");
				mainFrame.requestFocus();
			}
		});
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				mainFrame.flipReplay();
				mainFrame.requestFocus();
			}
		});
		
		b3.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://github.com/I-AM-PROTO/The_Odd_Satisfier").toURI());
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		});
		
		b4.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				mainFrame.terminateGame();
				mainFrame.dispose();
				
			}
		});
		
		b5.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public void resetSetting() { b1.setText("Pause (P)"); }
	public JButton getPauseButton() { return b1; }
}
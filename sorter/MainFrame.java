package sorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;

public class MainFrame extends JFrame {
	SortPanel sortPanel;
	SettingsPanel settingsPanel;
	
	public MainFrame() {
		setTitle("SORT");
		setSize(750 + 350, 500 + 40);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	
		sortPanel = new SortPanel();
		settingsPanel = new SettingsPanel(this);
		add(sortPanel, BorderLayout.WEST);
		add(settingsPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public void test(int i) {
		sortPanel.resizePanel(i);
	}
	
	public static void main (String[] args) {
		MainFrame mf = new MainFrame();
		mf.test(6);
	}
}

class SortPanel extends JPanel {
	JPanel[] panel = new JPanel[6];
	
	public SortPanel() {
		setLayout(new GridLayout(2,3,5,5));
		setPreferredSize(new Dimension(745, 500));
		Dimension d = new Dimension(245, 245);
		//Border b = BorderFactory.createLineBorder(Color.WHITE, 3);
		for(int i=0; i<6; i++) {
			panel[i] = new JPanel();
			panel[i].setBackground(Color.WHITE);
			//panel.setBorder(b);
			panel[i].setPreferredSize(d);
			add(panel[i]);
		}
	}
	
	public void resizePanel(int size) {
		removeAll();
		for(int i=0; i<size; i++) {
			add(panel[i]);
		}
		revalidate(); repaint();
	}
}

class SettingsPanel extends JPanel {
	MainFrame mainframe;
	JRadioButton[] sortNumSetting = new JRadioButton[3];
	
	public SettingsPanel(MainFrame mainframe) {
		this.mainframe = mainframe;
		ButtonGroup radioGroup = new ButtonGroup();
		
		setLayout(null);
		JLabel title = new JLabel("Settings", SwingConstants.CENTER);
		title.setSize(100, 30);
		title.setLocation(125, 35);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 20));
		title.setOpaque(true);
		this.add(title);
		
		JPanel center = new JPanel();
		center.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		center.setSize(300, 420);
		center.setLocation(22, 50);
		center.setOpaque(true);
		this.add(center);
		
		center.setLayout(new FlowLayout());
		center.add(new JLabel("¡¡"));
		center.add(new JLabel("Sort Number", SwingConstants.CENTER));
		for(int i=0; i<3; i++) {
			sortNumSetting[i] = new JRadioButton(Integer.toString(2*i+2));
			radioGroup.add(sortNumSetting[i]);
			center.add(sortNumSetting[i]);
		}
	}
}
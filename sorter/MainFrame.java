package sorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class MainFrame extends JFrame {
	private SortPanel sortPanel;
	private SettingsPanel settingsPanel;
	
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
	
	public void resizeBoxes(int i) {
		sortPanel.resizePanel(i);
	}
	
	public static void main (String[] args) {
		MainFrame mf = new MainFrame();
		mf.resizeBoxes(6);
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
	private MainFrame mainFrame;
	private JRadioButton[] sortNumSetting = new JRadioButton[3];
	private JSlider speedSetting = new JSlider();
	private JSlider elementSetting = new JSlider();
	private JButton[] btnSetting = new JButton[4];
	private final String[] btnText = {"Pause (P)", "Replay", "GitHub", "Back to Menu"};
	private JLabel[] ghostLabel = new JLabel[3];
	
	public SettingsPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		//Big title & Panel
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
		
		//Panel Layout & Components
		GridBagLayout g = new GridBagLayout();
		center.setLayout(g);
		
		GridBagConstraints newLine = new GridBagConstraints();
		GridBagConstraints keepLine = new GridBagConstraints();
		GridBagConstraints btnLine = new GridBagConstraints();
		newLine.gridwidth = GridBagConstraints.REMAINDER;
		keepLine.gridwidth = GridBagConstraints.BASELINE;
		keepLine.weightx = 1.0;
		newLine.weightx = 1.0;
		
		for(int i=0; i<3; i++) {
			ghostLabel[i] = new JLabel(" ¡¤ ");
			ghostLabel[i].setFont(new Font(ghostLabel[i].getFont().getFontName(), Font.PLAIN, 20));
			g.setConstraints(ghostLabel[i], newLine);
		}
		
		JLabel numLabel = new JLabel("[Box Number]", SwingConstants.CENTER);
		numLabel.setFont(new Font(numLabel.getFont().getFontName(), Font.PLAIN, 18));
		g.setConstraints(numLabel, newLine);
		center.add(numLabel);
		ButtonGroup radioGroup = new ButtonGroup();
		for(int i=0; i<3; i++) {
			sortNumSetting[i] = new JRadioButton(Integer.toString(2*i+2));
			radioGroup.add(sortNumSetting[i]);
			center.add(sortNumSetting[i]);
			g.setConstraints(sortNumSetting[i], i==2 ? newLine : keepLine);
			sortNumSetting[i].addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					mainFrame.resizeBoxes(Integer.parseInt(((JRadioButton)e.getSource()).getText()));
				}
			});
		}
		sortNumSetting[2].setSelected(true);

		center.add(ghostLabel[0]);

		JLabel speedLabel = new JLabel("[Sort Speed]", SwingConstants.CENTER);
		speedLabel.setFont(new Font(speedLabel.getFont().getFontName(), Font.PLAIN, 18));
		g.setConstraints(speedLabel, newLine);
		center.add(speedLabel);

		speedSetting.setMinimum(0);
		speedSetting.setMaximum(20);
		speedSetting.setMajorTickSpacing(5);
		speedSetting.setMinorTickSpacing(1);
		speedSetting.setSnapToTicks(true);
		speedSetting.setPaintTicks(true);
		speedSetting.setPaintLabels(true);
		//TODO add change listener
		g.setConstraints(speedSetting, newLine);
		center.add(speedSetting);
		
		center.add(ghostLabel[1]);
		
		JLabel elementLabel = new JLabel("[Element Number]", SwingConstants.CENTER);
		elementLabel.setFont(new Font(elementLabel.getFont().getFontName(), Font.PLAIN, 18));
		g.setConstraints(elementLabel, newLine);
		center.add(elementLabel);
		
		elementSetting.setMinimum(5);
		elementSetting.setMaximum(20);
		elementSetting.setMajorTickSpacing(5);
		elementSetting.setMinorTickSpacing(1);
		elementSetting.setSnapToTicks(true);
		elementSetting.setPaintTicks(true);
		elementSetting.setPaintLabels(true);
		//TODO add change listener
		g.setConstraints(elementSetting, newLine);
		center.add(elementSetting);
		
		center.add(ghostLabel[2]);
		
		JLabel menuLabel = new JLabel("[Menu]", SwingConstants.CENTER);
		menuLabel.setFont(new Font(menuLabel.getFont().getFontName(), Font.PLAIN, 18));
		g.setConstraints(menuLabel, newLine);
		center.add(menuLabel);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(2,2));
		g.setConstraints(menuPanel, newLine);
		Dimension buttonSize = new Dimension(140, 40);

		for(int i=0; i<4; i++) {
			btnSetting[i] = new JButton(btnText[i]);
			btnSetting[i].setPreferredSize(buttonSize);
			menuPanel.add(btnSetting[i]);
		}
		
		//TODO add menu event
		center.add(menuPanel);
	}
}
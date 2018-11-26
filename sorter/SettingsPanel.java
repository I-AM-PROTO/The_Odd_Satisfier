package sorter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsPanel extends JPanel {
	public final static int minElement = 10;
	public final static int maxElement = 120; 
	private MainFrame mainFrame;
	private JRadioButton[] sortNumSetting = new JRadioButton[4];
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
		
		JLabel numLabel = new JLabel("[Mode]", SwingConstants.CENTER);
		numLabel.setFont(new Font(numLabel.getFont().getFontName(), Font.PLAIN, 18));
		g.setConstraints(numLabel, newLine);
		center.add(numLabel);
		ButtonGroup radioGroup = new ButtonGroup();
		for(int i=0; i<4; i++) {
			sortNumSetting[i] = new JRadioButton(Integer.toString(i+1));
			radioGroup.add(sortNumSetting[i]);
			center.add(sortNumSetting[i]);
			g.setConstraints(sortNumSetting[i], i==3 ? newLine : keepLine);
			sortNumSetting[i].addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					//TODO notify sortpanel of the mode
					System.out.println(((JRadioButton)(e.getSource())).getText());
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
		speedSetting.setValue(10);
		speedSetting.setSnapToTicks(true);
		speedSetting.setPaintTicks(true);
		speedSetting.setPaintLabels(true);
		speedSetting.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// TODO notify sortpanel of speed
			}
		});
		g.setConstraints(speedSetting, newLine);
		center.add(speedSetting);
		
		center.add(ghostLabel[1]);
		
		JLabel elementLabel = new JLabel("[Element Number]", SwingConstants.CENTER);
		elementLabel.setFont(new Font(elementLabel.getFont().getFontName(), Font.PLAIN, 18));
		g.setConstraints(elementLabel, newLine);
		center.add(elementLabel);
		
		elementSetting.setMinimum(minElement);
		elementSetting.setMaximum(maxElement);
		elementSetting.setMajorTickSpacing(10);
		elementSetting.setMinorTickSpacing(1);
		elementSetting.setValue(SortPanel.INITIAL_ELEMENT_NUM);
		elementSetting.setPreferredSize(new Dimension(270, 50));
		elementSetting.setSnapToTicks(true);
		elementSetting.setPaintTicks(true);
		elementSetting.setPaintLabels(true);
		elementSetting.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int elementNum = ((JSlider)e.getSource()).getValue();
				if(elementNum == 41 || elementNum == 62 || elementNum == 82)
					mainFrame.notifyElementNum(elementNum-1);
				else if(elementNum == 83)
					mainFrame.notifyElementNum(elementNum+1);
				else
					mainFrame.notifyElementNum(elementNum);
			}
		});
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
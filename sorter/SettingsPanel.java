package sorter;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsPanel extends JPanel {
	public final static int minElement = 10;
	public final static int maxElement = 120; 
	public final static int DEFAULT_SORT_SPEED = 5;
	public final static int DEFAULT_COLOR_PRESET = 1;
	private final String[] btnText = {"Start", "Exit", "GitHub", "Back to Menu"};
	private final String[] sortText = {"BUBBL", "SLCTN", "INSRTN", "MRGE", "QUCK", "SHLL"};
	private int[] sortChoice = {0,1,2,3,4,5};
	private int page = 1;
	private MainFrame mainFrame;
	private JLabel title, numLabel, speedLabel, elementLabel, menuLabel;
	private JPanel center , menuPanel;
	private ButtonGroup radioGroup;
	private GridBagLayout g = new GridBagLayout();
	private GridBagConstraints newLine = new GridBagConstraints();
	private GridBagConstraints keepLine = new GridBagConstraints();
	private GridBagConstraints btnLine = new GridBagConstraints();
	private JRadioButton[] sortNumSetting = new JRadioButton[4];
	private JSlider speedSetting = new JSlider();
	private JSlider elementSetting = new JSlider();
	private JButton[] btnSetting = new JButton[4];
	private JButton nextPage = new JButton("[Next Page]");
	private JLabel[] ghostLabel = new JLabel[4];
	private JLabel[] sortLabel = new JLabel[7];
	private JLabel[] blockIndexLabel = new JLabel[6];
	private ButtonGroup[] buttonGroup = new ButtonGroup[6];
	private JToggleButton[][] sortBtn = new JToggleButton[6][6];
	private JPanel sortBtnPanel = new JPanel();
	
	public SettingsPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		//Big title & Panel
		setLayout(null);
		title = new JLabel("Settings", SwingConstants.CENTER);
		title.setSize(100, 30);
		title.setLocation(125, 5);
		title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 20));
		title.setOpaque(true);
		
		center = new JPanel();
		center.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		center.setSize(300, 470);
		center.setLocation(22, 20);
		center.setOpaque(true);
		
		//Panel Layout & Components
		
		center.setLayout(g);
		newLine.gridwidth = GridBagConstraints.REMAINDER;
		keepLine.gridwidth = GridBagConstraints.BASELINE;
		keepLine.weightx = 1.0;
		newLine.weightx = 1.0;
		
		for(int i=0; i<4; i++) {
			ghostLabel[i] = new JLabel(" ¡¤ ");
			ghostLabel[i].setFont(new Font(ghostLabel[i].getFont().getFontName(), Font.PLAIN, i < 2 ? 20 : 15));
		}
		
		nextPage.setBackground(Color.WHITE);
		nextPage.setOpaque(true);
		nextPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayNextPage();
			}
		});
		
		numLabel = new JLabel("[Color Preset]", SwingConstants.CENTER);
		numLabel.setFont(new Font(numLabel.getFont().getFontName(), Font.PLAIN, 18));
		radioGroup = new ButtonGroup();
		for(int i=0; i<4; i++) {
			sortNumSetting[i] = new JRadioButton(Integer.toString(i+1));
			radioGroup.add(sortNumSetting[i]);
			sortNumSetting[i].addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					mainFrame.setColorPreset(Integer.parseInt(((JRadioButton)(e.getSource())).getText()));
				}
			});
		}
		sortNumSetting[0].setSelected(true);
		

		speedLabel = new JLabel("[Sort Speed]", SwingConstants.CENTER);
		speedLabel.setFont(new Font(speedLabel.getFont().getFontName(), Font.PLAIN, 18));

		speedSetting.setMinimum(0);
		speedSetting.setMaximum(30);
		speedSetting.setMajorTickSpacing(5);
		speedSetting.setMinorTickSpacing(1);
		speedSetting.setValue(DEFAULT_SORT_SPEED);
		speedSetting.setSnapToTicks(true);
		speedSetting.setPaintTicks(true);
		speedSetting.setPaintLabels(true);
		speedSetting.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mainFrame.setSortSpeed(((JSlider)(e.getSource())).getValue() + 1);
			}
		});

		elementLabel = new JLabel("[Element Number]", SwingConstants.CENTER);
		elementLabel.setFont(new Font(elementLabel.getFont().getFontName(), Font.PLAIN, 18));
		
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
					mainFrame.setElementNum(elementNum-1);
				else if(elementNum == 83)
					mainFrame.setElementNum(elementNum+1);
				else
					mainFrame.setElementNum(elementNum);
			}
		});
		
		menuLabel = new JLabel("[Menu]", SwingConstants.CENTER);
		menuLabel.setFont(new Font(menuLabel.getFont().getFontName(), Font.PLAIN, 18));
		
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(2,2));
		Dimension buttonSize = new Dimension(140, 30);
		for(int i=0; i<4; i++) {
			btnSetting[i] = new JButton(btnText[i]);
			btnSetting[i].setPreferredSize(buttonSize);
		}
		
		btnSetting[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = ((JButton)e.getSource()).getText();
				if(str.equals("Continue") || str.equals("Start")) {
					((JButton)e.getSource()).setText("Pause");
					mainFrame.pause();
				} else if(str.equals("Pause")) {
					((JButton)e.getSource()).setText("Continue");
					mainFrame.pause();
				}
			}
		});
		
		btnSetting[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnSetting[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://github.com/I-AM-PROTO/The_Odd_Satisfier").toURI());
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		});
		
		btnSetting[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO return to menu
			}
		});
		for(int i=0; i<4; i++) menuPanel.add(btnSetting[i]);
		
		//page 2
		Dimension gridSize = new Dimension(40, 40);
		sortBtnPanel.setLayout(new GridLayout(7, 7));
		
		for(int i=0; i<7; i++) {
			sortLabel[i] = new JLabel(i == 0 ? "-" : sortText[i-1], SwingConstants.CENTER);
			sortLabel[i].setFont(new Font(menuLabel.getFont().getFontName(), Font.PLAIN, i == 0 ? 20 : 10));
			sortLabel[i].setPreferredSize(gridSize);
			sortLabel[i].setMaximumSize(gridSize);
			sortBtnPanel.add(sortLabel[i]);
		}
		
		for(int i=0; i<6; i++) {
			blockIndexLabel[i] = new JLabel(Integer.toString(i+1), SwingConstants.CENTER);
			blockIndexLabel[i].setFont(new Font(menuLabel.getFont().getFontName(), Font.PLAIN, 20));
		}
		
		for(int i=0; i<6; i++) {
			buttonGroup[i] = new ButtonGroup();
			for(int j=0; j<6; j++) {
				sortBtn[i][j] = new JToggleButton(Integer.toString(i*6+j));
				sortBtn[i][j].setFont(new Font(menuLabel.getFont().getFontName(), Font.PLAIN, 0));
				sortBtn[i][j].setBackground(Color.WHITE);
				sortBtn[i][j].setForeground(new Color(255, 255, 255, 0));
				sortBtn[i][j].setPreferredSize(gridSize);
				sortBtn[i][j].setMaximumSize(gridSize);
				sortBtn[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						int index = Integer.parseInt(((JToggleButton)e.getSource()).getText());
						sortChoice[index/6] = index%6;
					}

				});
				buttonGroup[i].add(sortBtn[i][j]);
			}
			sortBtn[i][i].setSelected(true);
		}
		
		for(int i=0; i<6; i++) {
			sortBtnPanel.add(blockIndexLabel[i]);
			for (int j=0; j<6; j++) {
				sortBtnPanel.add(sortBtn[i][j]);
			}
		}
		
		//TODO add menu event
		
		setPageOne();
	}
	
	public void displayNextPage() {
		if(page == 1) {
			setPageTwo();
		} else {
			setPageOne();
		}
	}
	
	private void setPageOne() {
		page = 1;
		this.removeAll();
		center.removeAll();
		
		for(int i=0; i<4; i++) {
			g.setConstraints(ghostLabel[i], newLine);
			g.setConstraints(sortNumSetting[i], i==3 ? newLine : keepLine);
		}
		g.setConstraints(nextPage, newLine);
		g.setConstraints(numLabel, newLine);
		g.setConstraints(speedLabel, newLine);
		g.setConstraints(speedSetting, newLine);
		g.setConstraints(elementLabel, newLine);
		g.setConstraints(elementSetting, newLine);
		g.setConstraints(menuLabel, newLine);
		g.setConstraints(menuPanel, newLine);
		
		this.add(title);
		this.add(center);
		center.add(numLabel);
		for(int i=0; i<4; i++) center.add(sortNumSetting[i]);
		center.add(ghostLabel[0]);
		center.add(speedLabel);
		center.add(speedSetting);
		center.add(ghostLabel[1]);
		center.add(elementLabel);
		center.add(elementSetting);
		center.add(ghostLabel[2]);
		center.add(nextPage);
		center.add(ghostLabel[3]);
		center.add(menuLabel);
		center.add(menuPanel);
		this.revalidate(); this.repaint();
	}
	
	private void setPageTwo() {
		page = 2;
		this.removeAll();
		center.removeAll();
		
		g.setConstraints(sortBtnPanel, newLine);
		g.setConstraints(menuLabel, newLine);
		g.setConstraints(menuPanel, newLine);
		g.setConstraints(nextPage, newLine);
		for(int i=0; i<4; i++) {g.setConstraints(ghostLabel[i], newLine);}
		
		this.add(title);
		this.add(center);
		center.add(sortBtnPanel);
		center.add(ghostLabel[2]);
		center.add(nextPage);
		center.add(ghostLabel[3]);
		center.add(menuLabel);
		center.add(menuPanel);
		this.revalidate(); this.repaint();
	}
	
	public int[] getSortChoice() { return sortChoice; }
	
	public void resetSettings() {
		//TODO reset settings
	}
}
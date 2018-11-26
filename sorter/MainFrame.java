package sorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class MainFrame extends JFrame {
	private SortPanel sortPanel = new SortPanel();
	private SettingsPanel settingsPanel = new SettingsPanel(this);
	
	public MainFrame() {
		setTitle("SORT");
		setSize((SortPanel.panelSize + 5) * 3 + 350, 500 + 40);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./res/bup.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		add(sortPanel, BorderLayout.WEST);
		add(settingsPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public static void main (String[] args) {
		MainFrame mf = new MainFrame();
	}
	
	public void notifyElementNum(int elementNum) {
		sortPanel.setElementNum(elementNum);
	}
	
	public void notifySwap(int a, int b) { sortPanel.notifySwap(a, b); } //vector shuffle
	public void bufferSwap(int a, int index) { sortPanel.bufferSwap(a, index); } //buffer swap elements mid-sort
	public void notifySwap(int index) { sortPanel.notifySwap(index); } //swap buffered elements
}
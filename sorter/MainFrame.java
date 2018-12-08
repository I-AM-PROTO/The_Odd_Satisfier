package sorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

public class MainFrame extends JFrame {
	private SortPanel sortPanel = new SortPanel();
	private SettingsPanel settingsPanel = new SettingsPanel(this);
	public boolean newColorPreset = false;
	public boolean newSortSpeed = false;
	public boolean newElementNum = false;
	public boolean paused = true;
	private int colorPreset = SettingsPanel.DEFAULT_COLOR_PRESET;
	private int sortSpeed = SettingsPanel.DEFAULT_SORT_SPEED;
	private int elementNum = SortPanel.INITIAL_ELEMENT_NUM;
	
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
	
	public void rebuild() {
		sortPanel.resetBoxes();
	}
	
	public void notifySwap(int a, int b) { sortPanel.notifySwap(a, b); } //vector shuffle
	public void bufferSwap(int a, int index) { sortPanel.bufferSwap(a, index); } //buffer swap elements mid-sort
	public void notifySwap(int index) { sortPanel.notifySwap(index); } //swap buffered elements
	public void recalibrate(int index, Vector<Integer> v) { sortPanel.recalibrate(index, v); }
	public void setColorPreset(int mode) { if(colorPreset != mode) {colorPreset = mode; newColorPreset = true;} }
	public void getColorPreset() { newColorPreset = false; sortPanel.setColorPreset(colorPreset); }
	public void setSortSpeed(int speed) { sortSpeed = speed; newSortSpeed = true; }
	public int getSortSpeed() { newSortSpeed = false; return sortSpeed;}
	public void setElementNum(int elementNum) { this.elementNum = elementNum; newElementNum = true; }
	public int getElementNum() { newElementNum = false; sortPanel.setElementNum(elementNum); return elementNum; }
	public void pause() { paused = !paused; }
	public int[] getSortChoice() { return settingsPanel.getSortChoice(); }
}
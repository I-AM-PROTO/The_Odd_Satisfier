package sorter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

class SortPanel extends JPanel {
	public static final int panelSize = 245;
	SortBox[] sortBoxes = new SortBox[6];
	public static final int INITIAL_ELEMENT_NUM = 120;
	private int elementNum = INITIAL_ELEMENT_NUM;
	
	public SortPanel() {
		setLayout(new GridLayout(2,3,5,5));
		setPreferredSize(new Dimension((panelSize+5) * 3 - 5, (panelSize+5)*2 - 5));
		//Border b = BorderFactory.createLineBorder(Color.WHITE, 3);
		for(int i=0; i<6; i++) {
			sortBoxes[i] = new SortBox(elementNum);
			sortBoxes[i].setBackground(Color.WHITE);
			//panel.setBorder(b);
			sortBoxes[i].setPreferredSize(new Dimension(panelSize, panelSize));
			add(sortBoxes[i]);
		}
		//sortBoxes[2].randomize();
	}
	
	public void setElementNum(int elementNum) { this.elementNum = elementNum; }
	
	public void resetBoxes() {
		for(int i=0; i<6; i++) {
			sortBoxes[i].reset(elementNum);
		}
	}
	
	public void notifySwap(int a, int b) {
		for(int i=0; i<6; i++)
			sortBoxes[i].swap(a, b);
	}
	
	public void bufferSwap(int a, int index) { sortBoxes[index].bufferSwap(a); }
	public void notifySwap(int index) { sortBoxes[index].swap(); }
	public void recalibrate(int index, Vector<Integer> v) { sortBoxes[index].recalibrate(v); }
}

class SortBox extends JPanel {
	private int elementNum;
	private double inter;
	int[] buffer = {-1, -1};
	Color[] bufferColor = new Color[2];
	JPanel[] array = new JPanel[SettingsPanel.maxElement];
	GridBagLayout g = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	
	public SortBox (int elementNum) {
		this.elementNum = elementNum;
		this.inter = SortPanel.panelSize / elementNum;
		
		//create layout
		setLayout(g);
		c.weighty = 2.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		//create color for all possible labels
		int color = 0;
		for(int i=0; i<SettingsPanel.maxElement; i++) {
			array[i] = new JPanel();
			//array[i].setBackground(Color.decode("#" + Integer.toHexString(color += 1500000).toUpperCase()));
			array[i].setBackground(Color.BLACK);
		}
		
		initializeBox();
	}
	
	public void reset (int elementNum) {
		this.elementNum = elementNum;
		this.inter = SortPanel.panelSize / elementNum;
		initializeBox();
	}
	
	private void initializeBox () {
		removeAll();
		for(int i=0; i<elementNum; i++) {
			g.setConstraints(array[i], c);
			array[i].setPreferredSize(new Dimension((int)(inter * (i + 1)), (int)inter));
			array[i].setMaximumSize(new Dimension((int)(inter * (i + 1)), (int)inter));
			add(array[i]);
		}
		revalidate(); repaint();
	}
	
	public void bufferSwap (int elementIndex) {
		if(elementIndex == -1) System.out.println("flag2");
		
		if(buffer[0] == -1) {
			bufferColor[0] = array[elementIndex].getBackground();
			array[elementIndex].setBackground(Color.RED);
			buffer[0] = elementIndex;
		}
		else {
			bufferColor[1] = array[elementIndex].getBackground();
			array[elementIndex].setBackground(Color.RED);
			buffer[1] = elementIndex;
		}
	}
	
	public void swap () {
		Dimension aDim = array[buffer[0]].getSize();
		Dimension bDim = array[buffer[1]].getSize();
		
		array[buffer[0]].setPreferredSize(bDim);
		array[buffer[0]].setMaximumSize(bDim);
		array[buffer[0]].setBackground(bufferColor[1]);
		array[buffer[1]].setPreferredSize(aDim);
		array[buffer[1]].setMaximumSize(aDim);
		array[buffer[1]].setBackground(bufferColor[0]);
		revalidate(); repaint();
		
		buffer[0] = buffer[1] = -1;
	}
	
	public void swap (int a, int b) {
		Dimension aDim = array[a].getSize();
		Dimension bDim = array[b].getSize();
		Color aColor = array[a].getBackground();
		Color bColor = array[b].getBackground();
		
		array[a].setPreferredSize(bDim);
		array[a].setMaximumSize(bDim);
		array[a].setBackground(bColor);
		array[b].setPreferredSize(aDim);
		array[b].setMaximumSize(aDim);
		array[b].setBackground(aColor);
		revalidate(); repaint();
	}
	
	public void recalibrate(Vector<Integer> v) {
		for(int i=0; i<elementNum; i++) {
			if(inter * (v.get(i) + 1) != array[i].getWidth()) {
				//array[i].setBackground(Color.PINK);
				array[i].setPreferredSize(new Dimension((int)(inter * (v.get(i) + 1)), (int)inter));
				array[i].setMaximumSize(new Dimension((int)(inter * (v.get(i) + 1)), (int)inter));
			}
		}
		revalidate(); repaint();
	}
}

/*
class SortBox extends JPanel {
	private int elementNum;
	JPanel[] array = new JPanel[SettingsPanel.maxElement];
	
	public SortBox (int elementNum) {
		this.elementNum = elementNum;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		int j = Integer.parseInt("FFFFFF", 16) / elementNum;
		int color = 0;
		double inter = SortPanel.panelSize / elementNum;
		for(int i=0; i<SettingsPanel.maxElement; i++)
			array[i] = new JPanel();
		
		for(int i=0; i<elementNum; i++) {
			array[i].setPreferredSize(new Dimension((int)(inter * i), (int)inter));
			array[i].setMaximumSize(new Dimension((int)(inter * i), (int)inter));
			array[i].setBackground(Color.decode("#" + Integer.toHexString(color += 1500000).toUpperCase()));
			add(array[i]);
		}
	}
	
	public void randomize () {
		int width;
		for(int i=0; i<elementNum; i++) {
			width = (int)(Math.random()*240);
			array[i].setPreferredSize(new Dimension(width, 3));
			array[i].setMaximumSize(new Dimension(width, 3));
			array[i].setBackground(Color.WHITE);
			add(array[i]);
		}
		array[20].setBackground(Color.RED);
		array[34].setBackground(Color.RED);
		revalidate(); repaint();
	}
	
	public void resize (int elementNum) {
		this.elementNum = elementNum;
		
		removeAll();
		int j = Integer.parseInt("FFFFFF", 16) / elementNum;
		int color = 0;
		int inter = SortPanel.panelSize / elementNum;
		for(int i=0; i<elementNum; i++) {
			array[i].setPreferredSize(new Dimension(inter * i, inter));
			array[i].setMaximumSize(new Dimension(inter * i, inter));
			array[i].setBackground(Color.decode("#" + Integer.toHexString(color += 1500000).toUpperCase()));
			add(array[i]);
		}
		revalidate(); repaint();
	}
}
*/
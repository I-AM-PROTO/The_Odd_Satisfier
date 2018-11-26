package sorter;

import java.util.Vector;

public class Sort {
	private int vectorSize;
	private Vector<Integer>[] vectorArr = new Vector[6];
	MainFrame mainFrame = new MainFrame();
	
	public Sort () {
		this.vectorSize = SortPanel.INITIAL_ELEMENT_NUM;
		for(int i=0; i<6; i++)
			vectorArr[i] = new Vector<Integer>();
		initializeVector();
	}
	
	private void initializeVector() {
		resetVectors();
		for(int i=0; i<vectorSize; i++)
			vectorArr[0].add(i);
		shuffleVector();
		copyVectors();
	}
	
	public void resetVectors() {
		for(int i=0; i<6; i++)
			vectorArr[i].removeAllElements();
	}
	
	public void copyVectors() {
		for(int i=1; i<6; i++)
			vectorArr[i] = (Vector<Integer>)vectorArr[0].clone();
	}
	
	public void printVector(int index) {
		for(int i=0; i<vectorSize; i++)
			System.out.print(vectorArr[index].get(i) + " " + (i % 5 == 4 ? '\n' : ' '));
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	private void shuffleVector() {
		int a, b;
		for(int i=0; i<vectorSize*4; i++) {
			a = (int)(Math.random()*vectorSize);
			b = (int)(Math.random()*vectorSize);
			if(a != b) {
				swapVector(a, b);
				mainFrame.notifySwap(a, b);
				interval(50);
			}
		}
	}
	
	private void swapVector(int a, int b) {
		int aElement = vectorArr[0].get(a);
		int bElement = vectorArr[0].get(b);
		vectorArr[0].setElementAt(bElement, a);
		vectorArr[0].setElementAt(aElement, b);
	}
	
	void interval() {
		try {
			Thread.sleep(500);//fix
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	void interval(int time) {
		try {
			Thread.sleep(time);	
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main (String[] args) {
		Sort sort = new Sort();
		for(int i=0; i<6; i++)
			sort.printVector(i);
	}
}
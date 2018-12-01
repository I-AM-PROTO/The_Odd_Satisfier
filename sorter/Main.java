package sorter;

import java.util.Vector;

public class Main {
	private int vectorSize = SortPanel.INITIAL_ELEMENT_NUM;
	private final int defaultInterval = 500;
	private int sortSpeed = SettingsPanel.DEFAULT_SORT_SPEED;
	private long sortInterval;
	private Vector<Integer> origin = new Vector<>(SettingsPanel.maxElement);
	private Sort[] sorter = new Sort[6];
	MainFrame mainFrame = new MainFrame();

	public Main () {}

	private void run() {
		while(true) {
			mainFrame.reset(); // reset Frame
			initializeVector(); // initialize/reset vectors
			runSorting();
			interval(10000);
		}
	}

	private void runSorting() {
		for(int i=0; i<6; i++) {
			sorter[i] = new BubbleSort((Vector<Integer>)origin.clone(), vectorSize);
			//make sort factory that gives vector,size,mode as input and gives sorter as output
		}
		//then repeat (getting step -> swapping) until all vectors are sorted
		//don't forget to get new speed
		while(!sorter[0].isSorted()) {
			sorter[0].takeAStep();
			mainFrame.bufferSwap(sorter[0].getCandidate(0), 0);
			interval(40);
			mainFrame.bufferSwap(sorter[0].getCandidate(1), 0);
			interval(40);
			mainFrame.notifySwap(0);
		}
		
		sorter[0].printVector();

	}
	
	private void initializeVector() {
		origin.removeAllElements();
		for(int i=0; i<vectorSize; i++)
			origin.add(i);
		shuffleVector();
	}
	
	public void printOriginalVector(int index) {
		for(int i=0; i<vectorSize; i++)
			System.out.print(origin.get(i) + " " + (i % 5 == 4 ? '\n' : ' '));
		System.out.println();
		System.out.println();
		System.out.println();
	}

	private void shuffleVector() {
		int a, b;
		for(int i=0; i<vectorSize; i++) { // *3 omitted
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
		int aElement = origin.get(a);
		int bElement = origin.get(b);
		origin.setElementAt(bElement, a);
		origin.setElementAt(aElement, b);
	}

	private void interval() {
		try {
			Thread.sleep(500);//fix
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	private void interval(int time) {
		try {
			Thread.sleep(time);	
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main (String[] args) {
		Main sort = new Main();
		sort.run();
	}
}

package sorter;

import java.util.Vector;

import main_hub.MainHubFrame;

public class Main {
	private int vectorSize = SortPanel.INITIAL_ELEMENT_NUM;
	private int newVectorSize = SortPanel.INITIAL_ELEMENT_NUM;
	private final int defaultInterval = 120;
	private int sortSpeed = SettingsPanel.DEFAULT_SORT_SPEED;
	private int sortInterval = defaultInterval / sortSpeed;
	private Vector<Integer> origin = new Vector<>(SettingsPanel.maxElement);
	private int[] sortChoice = new int[6];
	private Sort[] sorter = new Sort[6];
	private MainFrame mainFrame = new MainFrame();
	private MainHubFrame mhf;

	public Main (MainHubFrame mhf) {this.mhf = mhf;}

	public void run() {
		while(true) {
			if(interval(1000)) break;
			initialPauseSession();
			vectorSize = newVectorSize;
			mainFrame.rebuild(); // rebuild Frame
			sortChoice = mainFrame.getSortChoice();
			initializeVector(); // initialize/reset vectors
			runSorting();
		}
		mhf.reEnter();
	}

	private void runSorting() {
		int cnt = 0;
		for(int i=0; i<6; i++) {
			sorter[i] = SortFactory.createSorter((Vector<Integer>)origin.clone(), vectorSize, sortChoice[i]);
		}
		recalibrate();		
		
		while(!allSorted()) {
			for(int i=0; i<6; i++) {
				if(sorter[i].isSorted()) continue;	
				sorter[i].takeAStep();
			}
			if(interval(sortInterval)) break;
			for(int i=0; i<6; i++) {
				if(sorter[i].isSorted()) continue;	
				mainFrame.bufferSwap(sorter[i].getCandidate(0), i);
			}
			if(interval(sortInterval)) break;
			for(int i=0; i<6; i++) {
				if(sorter[i].isSorted()) continue;
				mainFrame.bufferSwap(sorter[i].getCandidate(1), i);
			}
			if(interval(sortInterval)) break;
			for(int i=0; i<6; i++) {
				if(sorter[i].isSorted()) continue;
				mainFrame.notifySwap(i);
			}
			if(interval(sortInterval)) break;
			
			if(cnt++ % 5 == 0)
				recalibrate();
			
			if(mainFrame.paused)
				pauseSession();
			getNewSettings();
		}
		recalibrate();
	}
	
	private void getNewSettings() {
		if(mainFrame.newColorPreset) {
			mainFrame.getColorPreset();
			interval(200);
		}
		if(mainFrame.newSortSpeed) {
			sortSpeed = mainFrame.getSortSpeed();
			sortInterval = defaultInterval / sortSpeed;
		}
		if(mainFrame.newElementNum) {
			newVectorSize = mainFrame.getElementNum();
		}
	}
	
	private void getInitialSettings() {
		if(mainFrame.newColorPreset) {
			mainFrame.getColorPreset();
			mainFrame.rebuild();
			interval(200);
		}
		if(mainFrame.newSortSpeed) {
			sortSpeed = mainFrame.getSortSpeed();
			sortInterval = defaultInterval / sortSpeed;
		}
		if(mainFrame.newElementNum) {
			mainFrame.rebuild();
			newVectorSize = mainFrame.getElementNum();
		}
	}

	private void pauseSession() {
		while(mainFrame.paused) {
			if(interval(50)) break;
			getNewSettings();
		}
	}
	
	private void initialPauseSession() {
		while(mainFrame.paused) {
			if(interval(50)) break;
			getInitialSettings();
		}
	}
	
	private void recalibrate() {
		for(int i=0; i<6; i++)
			mainFrame.recalibrate(i, sorter[i].getVector());
	}
	
	private boolean allSorted() {
		for(int i=0; i<6; i++) {
			//System.out.print(sorter[i].isSorted + (i==5?" ":"\n"));
			if(!sorter[i].isSorted())
				return false;
		}
		return true;
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
		for(int i=0; i<vectorSize*2; i++) {
			a = (int)(Math.random()*vectorSize);
			b = (int)(Math.random()*vectorSize);
			if(a != b) {
				swapVector(a, b);
				mainFrame.notifySwap(a, b);
				if(interval(10)) break;
			}
			if(mainFrame.newColorPreset) {
				mainFrame.getColorPreset();
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

	private boolean interval(int time) {
		for(int i=0; i<time; i++) {
			try {
				Thread.sleep(1);	
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
			if(mainFrame.getTerminate())
				return true;
		}
		return false;
	}
}

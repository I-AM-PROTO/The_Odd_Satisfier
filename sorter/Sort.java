package sorter;

import java.util.Stack;
import java.util.Vector;

public abstract class Sort {
	protected Vector<Integer> v;
	protected int size;
	protected int[] candidate = {-1, -1};
	protected boolean isSorted = false;
	
	public Sort(Vector<Integer> v, int size) {this.v = v; this.size = size;}
	
	public void takeAStep() {}
	
	public int getCandidate(int index) { return candidate[index]; } 
	
	public void printVector() {
		for(int i=0; i<size; i++)
			System.out.print(v.get(i) + " " + (i % 5 == 4 ? '\n' : ' '));
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	public Vector<Integer> getVector() { return v; }
	
	protected void swap(int a, int b) {
		candidate[0] = a;
		candidate[1] = b;
		int aElement = v.get(a);
		int bElement = v.get(b);
		v.setElementAt(bElement, a);
		v.setElementAt(aElement, b);
	}
	
	public boolean isSorted() { return isSorted; }
}

class BubbleSort extends Sort {
	private int i = size - 1, j = 0;
	public BubbleSort(Vector<Integer> v, int size) { super(v, size);}
	
	public void takeAStep() {
		for(; i>0; i--) {
			for(; j<i; j++) {
				if(v.get(j) > v.get(j+1)) {
					swap(j, j+1);
					if(candidate[0] == -1) System.out.println("flag1");
					return;
				}
			}
			j = 0;
		}
		
		//if managed to exit two loops
		isSorted = true;
		return;
	}
	
}

class SelectionSort extends Sort {
	private int i = 0, j = 0;
	public SelectionSort(Vector<Integer> v, int size) { super(v, size); }

	public void takeAStep() {
		int min = i;
		for(j=i; j<size; j++) {
			if(v.get(min) > v.get(j))
				min = j;
		}
		
		swap(i, min);
		i++;
		
		if(i == size-1)
			isSorted = true;
	}
}

class InsertionSort extends Sort {
	private int i = 1, j = 1;
	public InsertionSort(Vector<Integer> v, int size) { super(v, size); }
	
	public void takeAStep() {
		for(; j>0; j--) {
			if(v.get(j) < v.get(j-1)) {
				swap(j-1, j);
				return;
			}
		}
		
		i++; j = i;
		if(i == size) {
			isSorted = true;
			return;
		}
		else
			takeAStep();
	}
}

class MergeSort extends Sort {
	private int i = -1, j;
	private int maxLayer = 0, layerIndex = 1, boxIndex = 1, limit = 1;
	public MergeSort(Vector<Integer> v, int size) {
		super(v, size);
		while(Math.pow(2, maxLayer) < size)
			maxLayer++;
	}
	
	public void takeAStep() {
		//System.out.printf("Layer:%d Box:%d i:%d limit:%d\n", layerIndex, boxIndex, i, limit);
		int min;
		
		do {
			i++;
			if(i == limit) {
				i++;
				boxIndex++;
				limit = (int)Math.pow(2, layerIndex) * boxIndex - 1;

				if(limit >= size) {
					i = 0;
					boxIndex = 1;
					layerIndex++;
					limit = Math.min(size - 1, (int)Math.pow(2, layerIndex) * boxIndex - 1);

					if(layerIndex > maxLayer) {
						isSorted = true;
						return;
					}
				}
			}
			min = i;
			for(j = i + 1; j <= limit; j++) {
				if(v.get(min) > v.get(j))
					min = j;
			}
		}while(min == i);

		swap(min, i);

	}
}

class QuickSort extends Sort{
	private int i, j;
	private Bound currBound;
	private Stack<Bound> stack = new Stack<>();
	
	class Bound {
		int l,r;
		public Bound(int l, int r) {this.l = l; this.r = r;}
		public String toString() { return "[" + l + " " + r + "]"; }
	}
	
	public QuickSort(Vector<Integer> v, int size) {
		super(v, size);
		stack.add(new Bound(0, size-1));
		getNewBound();
	}
	
	private void addBound(int l, int r) { stack.add(new Bound(l, r)); }
	private void getNewBound() {
		if(stack.isEmpty()){
			currBound = new Bound(0, 0); //doesn't matter
			i = j = 0;
			isSorted = true;
		} else {
			currBound = stack.pop();
			i = currBound.l;
			j = currBound.r - 1;
		}
	}
	
	private void printStatus() {
		System.out.println(stack.toString());
		System.out.println(i + " " + j);
		System.out.printf("l:%d r:%d\n\n", currBound.l, currBound.r);
	}

	public void takeAStep() {
		//printStatus();
		try {
			Thread.sleep(0);
		}catch(InterruptedException e) {
			
		}
		while(currBound.r - currBound.l == 1) {
			if(v.get(currBound.l) > v.get(currBound.r)) {
				swap(currBound.l, currBound.r);
				getNewBound();
				return;
			} else
				getNewBound();
		}
		
		while(i < currBound.r - 1 && v.get(i) < v.get(currBound.r))	{
			i++;
		}
		while(j > currBound.l && v.get(j) > v.get(currBound.r)) {
			j--;
		}
		
		if(i == j) {
			if(i == currBound.l) {
				swap(currBound.l, currBound.r);
				addBound(currBound.l + 1, currBound.r);
				//printStatus();
				getNewBound();
			} else if(i == currBound.r - 1) {					
				addBound(currBound.l, currBound.r - 1);
				//printStatus();
				getNewBound();
				takeAStep();
			}
		} else if(j < i) {
			i = i + j; j = i - j; i = i - j;
			swap(j, currBound.r);
			if(i != currBound.l) addBound(currBound.l, i);
			if(j+1 != currBound.r) addBound(j+1, currBound.r);
			//printStatus();
			getNewBound();
		} else {	
			swap(i, j);
		}
	}
}

class ShellSort extends Sort {
	private int i, j;
	private int interval, index = 0;

	public ShellSort(Vector<Integer> v, int size) {
		super(v, size);
		interval = size / 2;
		i = -interval;
	}
	
	public void takeAStep() {
		int min;

		do {
			//System.out.println("interval:" + interval + " index:" + index + " i:" + i);
			i += interval;
			
			if(i >= size) {
				index++;
				i = index;

				if(index >= interval) {
					if(interval == 2) {
						interval = 1;
						index = i = 0;
					}else if(interval == 1) {
						isSorted = true;
						return;
					} else {
						interval = interval / 2 + 1;
						index = i = 0;
					}
				}
			}
			
			min = i;
			for(j = i; j < size; j += interval) {
				if(v.get(min) > v.get(j))
					min = j;
			}
		}while(min == i);
		swap(i, min);
	}
}
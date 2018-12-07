package sorter;

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
	
	protected void swap(int a, int b) {
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
					candidate[0] = j;
					candidate[1] = j+1;
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
		
		candidate[0] = i;
		candidate[1] = min;
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
				candidate[0] = j-1;
				candidate[1] = j;
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
	private int i = 0, j;
	private int maxLayer = 0, layerIndex = 1, boxIndex = 1, limit = 1;
	public MergeSort(Vector<Integer> v, int size) {
		super(v, size);
		while(Math.pow(2, maxLayer) < size)
			maxLayer++;
	}
	
	public void takeAStep() {
		System.out.printf("Layer:%d Box:%d i:%d limit:%d\n", layerIndex, boxIndex, i, limit);
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

		candidate[0] = i;
		candidate[1] = min;
		swap(min, i);

	}
}
package sorter;

import java.util.Vector;

public class SortFactory {
	public static final int BUBBLE = 0, SELECTION = 1, INSERTION = 2, MERGE = 3;
	
	public static Sort createSorter(Vector<Integer> v, int size, int mode) {
		switch(mode) {
		case BUBBLE:
			return new BubbleSort(v, size);
		case SELECTION:
			return new SelectionSort(v, size);
		case INSERTION:
			return new InsertionSort(v, size);
		case MERGE:
			return new MergeSort(v, size);
		default:
			return null;
		}
	}
}
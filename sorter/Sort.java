package sorter;

import java.util.Vector;

public class Sort {
	
	public static void main (String[] args) {
		VectorFactory sort = new VectorFactory(5);
		sort.printVector();
	}
}

class VectorFactory {
	private int size;
	Vector<Integer> v = new Vector<>();
	
	public VectorFactory(int size) {
		this.size = size;
		initializeVector();
	}
	
	private void initializeVector() {
		for(int i=0; i<size; i++)
			v.add(i);
	}
	
	public void printVector() {
		for(int i=0; i<size; i++)
			System.out.print(v.get(i) + " ");
	}
	
	//might have to change to private
	public void shuffleVector() {
		
	}
}

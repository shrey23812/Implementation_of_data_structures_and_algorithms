// Starter code for SP9

// Change to your netid
package sxs190184;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class BinaryHeap<T extends Comparable<? super T>> {
    Comparable[] pq;
    int size;

    /** Constructor for building an empty priority queue using natural ordering of T
     *
     * @param maxCapacity	Capacity of the priority queue
     */
    public BinaryHeap(int maxCapacity) {
	pq = new Comparable[maxCapacity];
	size = 0;
    }

    /** add method:		Inserts specified element into the priority queue and resizes if priority queue is full
     *
     * @param x			Element to be added to the priority queue
     * @return			True after successfully inserting
     */
    public boolean add(T x) {
        if(this.size==pq.length){
            resize();
        }
	    pq[this.size] = x;
        percolateUp(this.size);
        this.size++;
	    return true;
    }

    /** offer method:	Inserts specified element into the priority queue by calling the add method
     *
     * @param x			Element to be added to the priority queue
     * @return			True after successfully inserting
     */
    public boolean offer(T x) {
	return add(x);
    }

    /** remove method:	Removes the head element of the priority queue (throws exception if priority queue is empty)
     *
     * @return			Removed element from priority queue or Throws exception if priority queue is empty
     * @throws 			NoSuchElementException If priority queue is empty
     */
    public T remove() throws NoSuchElementException {
	T result = poll();
	if(result == null) {
	    throw new NoSuchElementException("Priority queue is empty");
	} else {

	    return result;
	}
    }

    /** poll method:	Retrieves and removes the head of the priority queue or returns null if this queue is empty
     *
     * @return			The head of the queue or null if this queue is empty
     */
    public T poll() {
        if(isEmpty())
	        return null;
        T x = (T) pq[0];
        pq[0] = pq[this.size-1];
        this.size = this.size-1;
        percolateDown(0);
        return x;
    }

    /**	min method:		Retrieves, but does not remove, the head of the priority queue or returns null if this queue is empty
     *
     * @return			The head of the queue or null if this queue is empty
     */
    public T min() { 
	return peek();
    }

    /** peek method:	Retrieves, but does not remove, the head of the priority queue or returns null if this queue is empty
     *
     * @return			The head of the queue or null if this queue is empty
     */
    public T peek() {
        if(isEmpty())
	        return null;
//        T x = (T) pq[0];
        return (T)pq[0];
    }

    /** parent method:	Returns the index of the parent of the specified index
     *
     * @param i			Index of element that whose parent index is required
     * @return			Index of parent for the specified index
     */
    int parent(int i) {
	return (i-1)/2;
    }

    /** leftChild method:	Returns the index of the left child of the specified index
     *
     * @param i				Index of element that whose left child index is required
     * @return				Index of left child for the specified index
     */
    int leftChild(int i) {
	return 2*i + 1;
    }

    /** percolateUp method: 	Percolates up from a given node in the priority queue. (pq[index] may violate heap order with parent)
     *
     * @param i				The index of the node at which the percolate begins
     */
    int rightChild(int i){
        return 2*i+2;
    }

    /** percolateUp method: 	Percolates up from a given node in the priority queue. (pq[index] may violate heap order with parent)
     *
     * @param index				The index of the node at which the percolate begins
     */
    void percolateUp(int index) {
        T x = (T)pq[size];
        while(index>0 && compare(pq[parent(index)],x)==1){
//            pq[index] = pq[parent(index)];
            move(index,pq[parent(index)]);
            index = parent(index);
        }
        pq[index] = x;
    }

    /** percolateDown method:	Percolates down from a given node in the priority queue. (pq[index] may violate heap order with children)
     *
     * @param index				The index of the node at which the percolate begins
     */
    void percolateDown(int index) {
        int left = leftChild(index);
        int right = rightChild(index);
        int smallest = index;
        if(left<size && compare(pq[left],pq[smallest])==-1)
            smallest = left;
        if(right<size && compare(pq[right],pq[smallest])==-1)
            smallest = right;
        if(index!=smallest){
            T temp = (T) pq[smallest];
            pq[smallest] = pq[index];
            pq[index] = temp;
            percolateDown(smallest);
        }
    }

    /** move method:	Assigns a specified value to the specified index of priority queue
     *
     * @param dest		Index where the specified element is assigned
     * @param x			Element to be assigned
     */
    void move(int dest, Comparable x) {
	pq[dest] = x;
    }

    /** compare method:		Compares the two arguments for order
     *
     * @param a				The first object to be compared
     * @param b				The second object to be compared
     * @return				A negative integer, zero, or a positive integer when the first argument is less than,
     * 						equal to, or greater than the second respectively
     */
    int compare(Comparable a, Comparable b) {
	return ((T) a).compareTo((T) b);
    }
    
    /** Create a heap.  Precondition: none. */
    void buildHeap() {
	for(int i=parent(size-1); i>=0; i--) {
	    percolateDown(i);
	}
    }

    /** isEmpty method:		Check if the priority queue is empty
     *
     * @return				True if empty, False otherwise
     */
    public boolean isEmpty() {
	return size() == 0;
    }

    /** size method:	Returns the number of elements in the priority queue
     *
     * @return			The number of elements in the priority queue
     */
    public int size() {
	return size;
    }

    /** resize method:	Resize array to double the current size
     *
     */
    void resize() {
        Comparable[] temp = new Comparable[2*pq.length];
        for(int i = 0;i<pq.length;i++)
            temp[i] = pq[i];
        pq = temp;
    }
    
    public interface Index {
        public void putIndex(int index);
        public int getIndex();
    }

    public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {
        /** Build a priority queue with a given array */
        IndexedHeap(int capacity) {
            super(capacity);
	}

        /** restore heap order property after the priority of x has decreased */
        void decreaseKey(T x) {
            percolateUp(x.getIndex());
        }

	@Override
        void move(int i, Comparable x) {
            super.move(i, x);
            T x1 = (T) x;
            x1.putIndex(i);
        }
    }

    public static void main(String[] args) {
	Integer[] arr = {0,9,7,5,3,1,8,6,4,2};
	BinaryHeap<Integer> h = new BinaryHeap(arr.length);

	System.out.print("Before:");
	for(Integer x: arr) {
	    h.offer(x);
	    System.out.print(" " + x);
	}
	System.out.println();

	for(int i=0; i<arr.length; i++) {
	    arr[i] = h.poll();
	}

	System.out.print("After :");
	for(Integer x: arr) {
	    System.out.print(" " + x);
	}
	System.out.println();
    }
}

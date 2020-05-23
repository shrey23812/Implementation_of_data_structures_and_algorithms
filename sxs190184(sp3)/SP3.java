//Shrey Shah(sxs190184) and Deepanshu Sharma(dxs190018) 
package sxs190184;

import java.util.Arrays;
import java.util.Random;

public class SP3 {
    public static Random random = new Random();
    public static int numTrials = 50;
    public static void main(String[] args) {
	int n = 10;
	// int choice = 1;
	int choice = 1 + random.nextInt(4);
	if(args.length > 0) { n = Integer.parseInt(args[0]); }
	if(args.length > 1) { choice = Integer.parseInt(args[1]); }
        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
	    arr[i] = i;
	}
	Timer timer = new Timer();
	switch(choice) {
	case 1:
		for(int i=0; i<numTrials; i++) {
		Shuffle.shuffle(arr);
		mergeSort_take2(arr);
	    }
	    break;
	case 2:
	    for(int i=0; i<numTrials; i++) {
		Shuffle.shuffle(arr);
		mergeSort_take3(arr);
	    }
	    break;  // etc
	case 3:
		for (int i=0; i<numTrials; i++){
			Shuffle.shuffle(arr);
			mergeSort_take4(arr);
		}
		break;
	case 4:
		for (int i=0; i<numTrials; i++){
			Shuffle.shuffle(arr);
			mergeSort_take6(arr);
		}
		break;
	}
	timer.end();
	timer.scale(numTrials);

	System.out.println("Choice: " + choice + "\n" + timer);
    }

    public static void mergeSort_take2(int[] arr) {
		int[] arr_b = new int[arr.length];
		// System.out.println("Before");
		// for(int a :arr){
		// 	System.out.print(a+" ");
		// }
		// System.out.println();
		mergeSort_take2(arr,arr_b,0,arr.length-1);
		// System.out.println("After");
		// for(int a :arr){
		// 	System.out.print(a+" ");
		// }
		// System.out.println();

	}
	public static void mergeSort_take2(int[] arr,int[] arr_b,int start, int end){
    	if(start<end){
    		int mid = start+(end-start)/2;
    		mergeSort_take2(arr,arr_b,start,mid);
    		mergeSort_take2(arr,arr_b,mid+1,end);
    		merge_take2(arr,arr_b,start,mid,end);
		}
	}
	public static void merge_take2(int[] arr,int[] arr_b, int start, int mid, int end){
    	System.arraycopy(arr,start,arr_b,start,end-start+1);
    	int i = start;
    	int k = start;
    	int j = mid+1;
    	while((i<=mid) && (j<=end)){
    		if (arr_b[i] <= arr_b[j])
    			arr[k++] = arr_b[i++];
    		else
    			arr[k++] = arr_b[j++];
		}
    	while (i<=mid)
			arr[k++] = arr_b[i++];
    	while (j<=end)
    		arr[k++] = arr_b[j++];
	}

	public static void mergeSort_take3(int[] arr) {
		int[] arr_b = new int[arr.length];
		System.arraycopy(arr,0,arr_b,0,arr.length);
		mergeSort_take3(arr,arr_b,0,arr.length-1);
	}
	public static void mergeSort_take3(int[] arr,int[] arr_b,int start, int end){
		if(start<end){
			int mid = start+(end-start)/2;
			mergeSort_take3(arr_b,arr,start,mid);
			mergeSort_take3(arr_b,arr,mid+1,end);
			merge_take3(arr,arr_b,start,mid,end);
		}
	}
	public static void merge_take3(int[] arr,int[] arr_b, int start, int mid, int end){
		int i = start;
		int k = start;
		int j = mid+1;
		while((i<=mid) && (j<=end)){
			if (arr_b[i] <= arr_b[j])
				arr[k++] = arr_b[i++];
			else
				arr[k++] = arr_b[j++];
		}
		while (i<=mid)
			arr[k++] = arr_b[i++];
		while (j<=end)
			arr[k++] = arr_b[j++];
	}
	public static void mergeSort_take4(int[] arr) {
		int[] arr_b = new int[arr.length];
		// System.out.println(numTrials);
		// System.out.println("Before");
		// for(int a :arr){
		// 	System.out.print(a+" ");
		// }
		// System.out.println();
		System.arraycopy(arr,0,arr_b,0,arr.length);
		mergeSort_take4(arr,arr_b,0,arr.length-1);
		// System.out.println("After");
		// for(int a :arr){
		// 	System.out.print(a+" ");
		// }
		// System.out.println();
	}
	public static void mergeSort_take4(int[] arr,int[] arr_b,int start, int end){
//		int T = 5;
		int T = 32;
		if(end-start+1<T){
			insertionSort(arr,start,end);
			return;
		}
		if(start<end){
			int mid = start+(end-start)/2;
			mergeSort_take4(arr_b,arr,start,mid);
			mergeSort_take4(arr_b,arr,mid+1,end);
			merge_take4(arr,arr_b,start,mid,end);
		}
	}
	public static void merge_take4(int[] arr,int[] arr_b, int start, int mid, int end){
		int i = start;
		int k = start;
		int j = mid+1;
		while((i<=mid) && (j<=end)){
			if (arr_b[i] <= arr_b[j])
				arr[k++] = arr_b[i++];
			else
				arr[k++] = arr_b[j++];
		}
		while (i<=mid)
			arr[k++] = arr_b[i++];
		while (j<=end)
			arr[k++] = arr_b[j++];
	}
	public static void insertionSort(int[] arr,int start, int end){

		for (int i = start; i <= end; i++) {
			int key = arr[i];
			int j = i - 1;

			while (j >= start && arr[j] > key) {
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = key;
		}
	}
	public static void mergeSort_take6(int[] arr){
		int[] arr_b = new int[arr.length];
		int n = arr.length;
		int T = 32;
		int[] inp = arr;
//		int[] temp = new int[arr.length];
		// System.out.println(numTrials);
		// System.out.println("Before");
		// for(int a :arr){
		// 	System.out.print(a+" ");
		// }
		// System.out.println();

		for(int j = 0; j<n; j = j+T)
			insertionSort(arr,j,Math.min(j+T-1,n-1));
		// System.out.println("Insertion");
		// for(int a :arr){
		// 	System.out.print(a+" ");
		// }
		// System.out.println();
		for(int i = T; i < n; i = 2*i) {
			for (int j = 0; j < n; j = j + 2 * i) {
				merge_take6(arr_b, inp, j, Math.min(j+i-1,n-1), Math.min(j+2*i-1,n-1));
			}
			int[] temp = inp;
			inp = arr_b;
			arr_b = temp;
		}
		if(!Arrays.equals(arr,inp)){
			System.arraycopy(inp,0,arr,0,inp.length);
		}
		// System.out.println("After");
		// for(int a :arr){
		// 	System.out.print(a+" ");
		// }
		// System.out.println();

	}
	public static void merge_take6(int[] arr,int[] arr_b, int start, int mid, int end){
		int i = start;
		int k = start;
		int j = mid+1;
		while((i<=mid) && (j<=end)){
			if (arr_b[i] <= arr_b[j])
				arr[k++] = arr_b[i++];
			else
				arr[k++] = arr_b[j++];
		}
		while (i<=mid)
			arr[k++] = arr_b[i++];
		while (j<=end)
			arr[k++] = arr_b[j++];
	}

   /** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() { if(!ready) { end(); }  return elapsedTime; }

        public long memory()   { if(!ready) { end(); }  return memUsed; }

	public void scale(int num) { elapsedTime /= num; }
	
        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }
    
    /** @author rbk : based on algorithm described in a book
     */


    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {
	
	public static void shuffle(int[] arr) {
	    shuffle(arr, 0, arr.length-1);
	}

	public static<T> void shuffle(T[] arr) {
	    shuffle(arr, 0, arr.length-1);
	}

	public static void shuffle(int[] arr, int from, int to) {
	    int n = to - from  + 1;
	    for(int i=1; i<n; i++) {
		int j = random.nextInt(i);
		swap(arr, i+from, j+from);
	    }
	}

	public static<T> void shuffle(T[] arr, int from, int to) {
	    int n = to - from  + 1;
	    Random random = new Random();
	    for(int i=1; i<n; i++) {
		int j = random.nextInt(i);
		swap(arr, i+from, j+from);
	    }
	}

	static void swap(int[] arr, int x, int y) {
	    int tmp = arr[x];
	    arr[x] = arr[y];
	    arr[y] = tmp;
	}
	
	static<T> void swap(T[] arr, int x, int y) {
	    T tmp = arr[x];
	    arr[x] = arr[y];
	    arr[y] = tmp;
	}

	public static<T> void printArray(T[] arr, String message) {
	    printArray(arr, 0, arr.length-1, message);
	}

	public static<T> void printArray(T[] arr, int from, int to, String message) {
	    System.out.print(message);
	    for(int i=from; i<=to; i++) {
		System.out.print(" " + arr[i]);
	    }
	    System.out.println();
	}
    }
}
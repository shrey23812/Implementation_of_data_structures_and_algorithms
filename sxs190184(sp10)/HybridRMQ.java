package sxs190184;

/**
 * @author Shrey Shah (sxs190184)
 */
import java.util.Random;

/**
 *Implementation of  Hybrid Range Minimum Query - Approach 1
 * < O(n), O(log n) >
 */

public class HybridRMQ {
    int[] block_minima_array;
    int[] orig_array;
    int [][] sparseTable;
    int len;

    /**
     * Initializing the block minima array
     */
    void initialize_block_minima_array(){
        int b = (int)(Math.log(len)/Math.log(2));
        int blocks = (int)Math.ceil((double)(len)/b);
        block_minima_array = new int[blocks];
        for(int block=0; block<blocks; block++){
            int start = block*b;
            int min = orig_array[start];
            for(int i = start; i<Math.min(start+b,len);i++)
                min = Math.min(min,orig_array[i]);
            block_minima_array[block] = min;
        }
    }

    /**
     * Builds sparse table of top layer dynamically in linear time.
     */
    void buildSparseTable(){
        int m = block_minima_array.length;
        if(m==0) return;
        int logm = (int)(Math.log(m)/Math.log(2));
        sparseTable = new int[m][logm+1];
        for (int i=0; i<m; i++)
            sparseTable[i][0] = block_minima_array[i];
        for(int j=1; j<logm+1;j++){
            int interval = (int)Math.pow(2,j);
            for(int start=0;start<m-interval+1;start++){
                int next = start + interval/2;
                sparseTable[start][j] = Math.min(sparseTable[start][j-1],sparseTable[next][j-1]);
            }
        }
    }

    /**
     * Creates a new HybridRMQ structure to answer queries about the array
     * @param array The array over which RMQ should be computed.
     */
    public HybridRMQ(int[] array){
        len = array.length;
        orig_array = array;
        initialize_block_minima_array();
        buildSparseTable();
        block_minima_array = null;
    }

    /**
     * Finds the minimum of the bottom layer for query between two indices.
     * @param i start index
     * @param j end index
     * @param b block size
     * @return the min
     */
    int block_level_minima(int i, int j, int b){
        int min = orig_array[i];
        int firstBlock = (int)(i/b)*b + b;
        for(int k=i; k<Math.min(firstBlock,j+1); k++)
            min = Math.min(min,orig_array[k]);
        int lastBlock = (int)(j/b)*b;
        for(int k=Math.max(lastBlock,i);k<=j;k++)
            min = Math.min(min,orig_array[k]);
        return min;
    }

    /**
     * Finds the minimum of the top layer between block indices.
     * @param i index of start block
     * @param j index of bottom block
     * @return the minimum
     */
    int block_minima_array_min(int i, int j){
        int k = (int)Math.floor(Math.log(j-i+1)/Math.log(2));
        int power_of_2 = (int)Math.pow(k,2);
        int min = Math.min(sparseTable[i][k],sparseTable[j-power_of_2+1][k]);
        return min;
    }

    /**
     * Evaluates RMQ(i, j) over the array,
     * returning the index of the minimum value in that range.
     * @param i the start index
     * @param j the end index
     * @return minimum
     */
    public int rmq(int i, int j){
        int b = (int)(Math.log(len)/Math.log(2));
        int block_level_minima = block_level_minima(i,j,b);
        int topi = (int)(i/b) + 1;
        int topj = (int)(j/b) - 1;
        if(topj<topi) return block_level_minima;
        int block_minima_array_min = block_minima_array_min(topi,topj);
        return Math.min(block_level_minima,block_minima_array_min);
    }

    /**
     * Helper method to shuffle an array from the starting index to the ending index
     * @param arr takes an array
     * @param from start index
     * @param to end index
     */
    public static void shuffle(int[] arr, int from, int to){
        int n = to - from  + 1;
        Random random = new Random();
        for(int i=1; i<n; i++) {
            int j = random.nextInt(i);
            swap(arr, i+from, j+from);
        }
    }

    /**
     * Helper method to swap the positions of two values in an array
     * @param arr takes an array
     * @param x index 1
     * @param y index 2
     */
    static void swap(int[] arr, int x, int y) {
        int tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }

    /**
     * Helper class to calculate the time taken by the program
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

    /**
     * Main function
     */
    public static void main(String[] args) {
        int[] array = new int[]{24,32,58,6,94,86,16,2,99,28,39,40,64,13,70,47};
        HybridRMQ rmqObj = new HybridRMQ(array);
        int ans = rmqObj.rmq(1,14);
        System.out.println("RMQ: "+ans);

        int size = 256000000;
        int[] arr = new int[size];
        for(int i=0; i<size;i++)
            arr[i] = i;

        shuffle(arr,0,arr.length-1);

        Random random = new Random();
        int index1 = random.nextInt(size);
        int index2 = random.nextInt(size);

        System.out.println("Range: "+ index1+" "+index2);

        Timer timer1 = new Timer();
        HybridRMQ rmqObject = new HybridRMQ(arr);
        timer1.end();
        System.out.println("Preprocessing time: "+timer1);
        Timer timer2 = new Timer();
        int ans1 = rmqObject.rmq(index1<index2?index1:index2,index1<index2?index2:index1);
        timer2.end();
        System.out.println("Minimum value: " + ans1);
        System.out.println("Query Time: "+timer2);
    }
}

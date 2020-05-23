//Shrey Shah(sxs190184) and Deepanshu Sharma(dxs190018)
package sxs190184;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HashDriver {

    static Cuckoo_Hash<Integer> ch = new Cuckoo_Hash<>();
    static Set<Integer> hs = new HashSet<>();
    public static Random random = new Random();

    public static void shuffle(int[] arr, int from, int to){
        int n = to - from  + 1;
        int temp;
        for(int i=1; i<n; i++) {
            int j = random.nextInt(i);
            temp = arr[i+from];
            arr[i+from] = arr[j+from];
            arr[j+from] = temp;
        }
    }

    public static void main(String[] args) {
        int n = 1000000;
        int[] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = i;
        }
        shuffle(arr,0,arr.length-1);

        Timer timer = new Timer();
        for(int i = 0;i<n;i++)
            ch.insert(arr[i]);
        timer.end();
        System.out.println("Cuckoo Hash: "+n+" add operations: time: "+timer);
        Timer timer1 = new Timer();
        for(int i = 0;i<n;i++)
            hs.add(arr[i]);
        timer1.end();
        System.out.println("HashSet: "+n+" add operations: time: "+timer1);

        Timer timer2 = new Timer();
        for(int i = 0;i<n;i++)
            ch.contains(arr[i]);
        timer.end();
        System.out.println("Cuckoo Hash: "+n+" contains operations: time: "+timer2);
        Timer timer3 = new Timer();
        for(int i = 0;i<n;i++)
            hs.contains(arr[i]);
        timer1.end();
        System.out.println("HashSet: "+n+" contains operations: time: "+timer3);

        Timer timer4 = new Timer();
        for(int i = 0;i<n;i++)
            ch.remove(arr[i]);
        timer.end();
        System.out.println("Cuckoo Hash: "+n+" remove operations: time: "+timer4);
        Timer timer5 = new Timer();
        for(int i = 0;i<n;i++)
            hs.remove(arr[i]);
        timer1.end();
        System.out.println("HashSet: "+n+" remove operations: time: "+timer5);
    }
}

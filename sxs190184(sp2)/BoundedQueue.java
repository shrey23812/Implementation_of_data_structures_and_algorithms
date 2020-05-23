//Shrey Shah and Deepanshu Sharma
package sxs190184;

import java.util.Scanner;

public class BoundedQueue<T> {
    private int size, front, rear, count;
    private T[] queue;

    BoundedQueue(int size) {
        front = count = 0;
        rear = -1;
        this.size = size;
        queue = (T[]) new Object[size];
    }

    boolean offer(T x) {
        if (count == size) {
            System.out.println("Cannot add any more elements as the queue is full");
            return false;
        }
        rear = (rear+1)%size;
        queue[rear]=x;
        count++;
        return true;
    }

    T poll(){
        if(isEmpty()){
            System.out.println("Queue is empty");
            return null;
        }
        T x = queue[front];
        front = (front+1)%size;
        count--;
        return x;
    }
    T peek(){
        if(isEmpty()){
            System.out.println("Queue is empty");
            return null;
        }
        T x = queue[front];
        return x;
    }

    int size(){
        return count;
    }

    boolean isEmpty(){
        return (count==0);
    }

    void clear(){
        front = count = 0;
        rear = -1;
    }

    void toArray(T[] a){
        a = (T[]) new Object[count];
        int j = front;
        for(int i=0;i<count;i++){
            a[i]=queue[j];
            j = (j+1)%size;
        }
        for(int k=0;k<count;k++){
            System.out.println(a[k]);
            System.out.println();
        }
    }

    void printQueue(){
        if(isEmpty()){
            System.out.println("Queue is empty");
            return;
        }
        int i = front;
        int j = 0;
        while(j<count){
            System.out.println(queue[i]);
            j++;
            i = (i+1)%size;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter capacity of queue");
        int size = sc.nextInt();
        BoundedQueue<Integer> queue1 = new BoundedQueue<>(size);
        System.out.println("1.offer() 2.poll() 3.clear() 4.peek() 5.isEmpty() 6.size() 7.toArray() 8.printQueue()");
        whileloop:
        while(sc.hasNext()){
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Enter element to add");
                    queue1.offer(sc.nextInt());
                    break;
                case 2:
                    queue1.poll();
                    break;
                case 3:
                    queue1.clear();
                    break;
                case 4:
                    System.out.println(queue1.peek());
                    break;
                case 5:
                    System.out.println(queue1.isEmpty());
                    break;
                case 6:
                    System.out.println(queue1.size());
                    break;
                case 7:
                    Object[] a = null;
                    queue1.toArray((Integer[]) a);
                    break;
                case 8:
                    queue1.printQueue();
                    break;
                default:
                    break whileloop;
            }
        }
    }

}
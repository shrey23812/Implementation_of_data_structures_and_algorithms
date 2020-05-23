/** @author
 *  Shrey Shah(sxs190184) Deepanshu Sharma(dxs190018)
 *  Binary search tree (starter code)
 **/

package sxs190184;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
	        this.left = left;
	        this.right = right;
        }
        //Constructor for Entry without any children
        public Entry(T x){
            this.element = x;
            this.left = null;
            this.right = null;
        }
    }
    
    Entry<T> root;
    int size;

    Stack<Entry<T>> stack = new Stack<>();

    public BinarySearchTree() {
	root = null;
	size = 0;
    }

    /*
    * Helper function to compare two entries
    */
    int compare(Comparable a, Comparable b) {
        return ((T) a).compareTo((T) b);
    }

    /**
     * finds an entry that has its element = x. or
     * finds an entry at which we failed to find x.
     * @param x the element to be searched
     * @return the Entry e where e.element = x
     */
    private Entry<T> find(T x){
        stack.push(null);
        return find(root,x);
    }

    /**
     * O(depth(x)) worst case O(log n)
     * Helper find(x) method: starts the search of x
     * from the tree rooted at t (generally root)
     * @param t the Entry from which the search begins
     * @param x the element to be searched
     * @return the Entry when search ends, when element = x is found
     */
    private Entry<T> find(Entry<T> root,T x){
        if(root == null || root.element == x)
            return root;
        while(true){
            if(compare(x,root.element)==-1){
                if(root.left == null)
                    break;
                stack.push(root);
                root = root.left;
            }
            else if (compare(x,root.element) == 0)
                break;
            else if (root.right == null)
                break;
            else{
                stack.push(root);
                root = root.right;
            }
        }
        return root;
    }

    /** TO DO: Is x contained in tree?
     * @param x
     * @return
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        if(t == null || t.element != x)
            return false;
        return true;
    }



    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     * @param x the element we re looking for
     * @return if found, return the element, null otherwise
     */
    public T get(T x) {
        Entry<T> t = find(x);
        if(t == null || t.element != x)
            return null;
        return t.element;
    }

    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     * @param x the element to be added
     * @return true if addition is successful, false if the element already exists
     */
    public boolean add(T x) {
        if(size == 0){
            root = new Entry<T>(x);
            size ++;
        }
        else{
            Entry<T> t = find(x);
            if(compare(x,t.element)==0){
                t.element = x;
                return false;
            }
            else if(compare(x,t.element)==-1)
                t.left = new Entry<T>(x);
            else
                t.right = new Entry<T>(x);
            size++;
        }
	    return true;
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     * @param x the element to be removed
     * @return removed element
     */
    public T remove(T x) {
        if(root == null)
            return null;
        Entry<T> t = find(x);
        if(compare(x,t.element)!=0)
            return null;
        T found = t.element;
        if(t.left == null || t.right == null)
            splice(t);
        else{
            stack.push(t);
            Entry<T> minRight = find(t.right,x);
            t.element = minRight.element;
            splice(minRight);
        }
        size--;
	    return found;
    }

    /**
     * Helper function to remove parent from the subtree -grandParent-parent-child-
     * attaching only child to grandParent
     *
     * Precondition:
     * 	1. t has at-most one child
     * 	2. Stack has path from root to parent of t
     * @param t the input element
     */
    private void splice(Entry<T> t){
        Entry<T> parent = stack.peek();
        Entry<T> child = (t.right == null) ? t.left : t.right;
        if(parent == null)
            root = child;
        else{
            if(parent.left == t)
                parent.left = child;
            else
                parent.right = child;
        }
    }

    // Returns minimum element in BST
    public T min() {
	Entry<T> t = root;
	if(t == null)
        return null;
	while (t.left != null)
	    t = t.left;
	return t.element;
    }

    // Returns maximum element in BST
    public T max() {
        Entry<T> t = root;
        if(t == null)
            return null;
        while (t.right != null)
            t = t.right;
        return t.element;
    }

    private int index = 0; //global index element used in toArray
    //Creates an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
	Comparable[] arr = new Comparable[size];
	inorder(root,arr);
	return arr;
    }

    /**
    * Helper function to implement in-order traversal
    */
    private void inorder(Entry<T> root, Comparable[] arr){
        if(root == null)
            return;
        inorder(root.left,arr);
        arr[index++] = root.element;
        inorder(root.right,arr);

    }

// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
	return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

// End of Optional problem 2

    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }           
        }
    }


    public void printTree() {
	System.out.print("[" + size + "]");
	printTree(root);
	System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
	if(node != null) {
	    printTree(node.left);
	    System.out.print(" " + node.element);
	    printTree(node.right);
	}
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/

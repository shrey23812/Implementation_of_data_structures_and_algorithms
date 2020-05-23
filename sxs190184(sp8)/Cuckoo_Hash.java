//Shrey Shah(sxs190184) and Deepanshu Sharma(dxs190018)
package sxs190184;

public class Cuckoo_Hash<T> {
    int k;
    int capacity;
    Entry<T>[][] hashTable;

    int size;
    double loadFactor = 0.9;
    int threshold;

    class  Entry<E>{
        E element;
        public Entry(E element){
            this.element = element;
        }
    }

    public Cuckoo_Hash(){
        size = 0;
        k = 3; //2 for Cuckoo HashTable and 1 extra as secondary HashTable
        capacity = 1024;
        hashTable = new Entry[capacity][k];
        threshold = (int) Math.log((double)capacity);
    }

    //Taken from Java HashMap SourceCode
    static int hash(int h){
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Generates a Hash value for an i in {1, .., k}
     *
     * @param i the number of hash function from k hash functions.
     * @param x the element for which hash value is to be computed
     * @return hash value as an integer
     */
    private int hashFunction(int i, T x){
        if(i == 1)
            return hash(x.hashCode()) & hashTable.length-1;
        else
            return (hashFunction(1,x) + i * (1 + x.hashCode() % 6)) & hashTable.length-1;
    }

    /**
     * If x is there is the HashTable.
     * @param x the input element
     * @return true if present, false otherwise
     */
    public boolean contains(T x) {
        int i = 1;
        int cell;
        int key;

        while (i <= k) {
            cell = i - 1;
            key = hashFunction(i++, x);

            if ((hashTable[key][cell] != null) && (x.equals(hashTable[key][cell].element)))
                return true;
        }
        return false;
    }

    /**
     * Adds the specified element to this HashTable if it is not already present.
     * @param x the element to be added
     * @return true if successful insertion, false otherwise
     */
    public boolean insert(T x){

        double key_fraction = 0;

        if(contains(x))
            return false;

        int i = 1;
        int cell;
        int key;

        while(i<=k-1){
            cell = i-1;
            key = hashFunction(i++,x);

            if(hashTable[key][cell] == null){
                hashTable[key][cell] = new Entry<>(x);
                size++;

                key_fraction = (double)size/(capacity*2);
                if(key_fraction>loadFactor)
                    rehash();
                return true;
            }
        }
        //if both cells are occupied then we start displacing elements until we find a free cell or we reach
        //reach threshold displacements.
        i = 1;
        int count = 0;
        while(count<threshold){
            count++;
            cell = i-1;
            key = hashFunction(i,x);

            if(hashTable[key][cell] == null) {
                hashTable[key][cell] = new Entry<>(x);
                size++;
                key_fraction = (double) size /(capacity*2);
                if (key_fraction > loadFactor)
                    rehash();
                return true;
            }
            else{
                T temp = (T)hashTable[key][cell].element;
                hashTable[key][cell].element = x;
                x = temp;
            }
            i = (i==k-1) ? 1 : (i+1);
        }
        //When threshold is reached and still the element hasn't been inserted due to collisions
        //then that element is inserted in secondary HashTable
        i=k;
        cell = i-1;
        key = hashFunction(i,x);
        if(hashTable[key][cell]==null){
            hashTable[key][cell] = new Entry<>(x);
            size++;
            key_fraction = (double) size/(capacity*2);
            if(key_fraction>loadFactor)
                rehash();
            return true;
        }
        return false;
    }

    // Rehashing will double the table size, re-inserting the elements
    private void rehash(){
        Entry<T>[][] temp = hashTable;
        size = 0;
        capacity = capacity*2;
        hashTable = new Entry[capacity][k];
        threshold = (int) Math.log((double)capacity);

        int key = 0;
        int cell;
        Entry<T> e;

        while(key<temp.length){
            cell = 0;
            while(cell<k){
                e = temp[key][cell++];
                if(e!=null)
                    insert(e.element);
            }
            key++;
        }
    }

    //Number of elements present in the hashtable
    public int size(){
        return size;
    }

    /**
     * Removes the specified element from HashTable if it is present.
     * @param x the element to be removed
     * @return true, if successfully removed, false otherwise
     */
    public boolean remove(T x){
        int i = 1;
        int cell;
        int key;

        while(i<=k){
            cell = i - 1;
            key = hashFunction(i++,x);
            if((hashTable[key][cell]!=null) && x.equals(hashTable[key][cell].element)){
                hashTable[key][cell] = null;
                size--;
                return true;
            }
        }
        return false;
    }
}

Shrey Shah(sxs190184) Deepanshu Sharma(dxs190018)

CS 6301.011 Implementation of Data Structures and Algorithm
Short project 3 MergeSort

Run command : javac SP3.java to compile the file.
Run command : java SP3 array_size choice_of_algorithm to run the file.
Example to run the algorithm : java SP3 16000000 3

Choice: 
1 : MergeSort Take 2
2 : MergeSort Take 3
3 : MergeSort Take 4
4 : MergeSort Take 6

Report for running time for each algorithms:

       |    16M    |     32M	|    64M     |    128M	  |    256M
-------------------------------------------------------------------------	         
Take 2 | 4234 msec | 10521 msec | 16895 msec | 31688 msec | Out of memory
Take 3 | 3193 msec | 6454 msec  | 13640 msec | 32030 msec | Out of memory
Take 4 | 2716 msec | 5521 msec  | 11904 msec | 24371 msec | Out of memory
Take 6 | 2568 msec | 5449 msec  | 11573 msec | 23488 msec | Out of memory


Report for memory used for each algorithms:

       |    16M     |     32M	 |    64M     |    128M	    |    256M
---------------------------------------------------------------------------	         
Take 2 | 125/127 MB | 247/249 MB | 491/616 MB | 979/1104 MB | Out of memory
Take 3 | 125/127 MB | 247/249 MB | 491/616 MB | 979/1104 MB | Out of memory
Take 4 | 125/189 MB | 247/249 MB | 491/861 MB | 979/1104 MB | Out of memory
Take 6 | 125/127 MB | 247/250 MB | 491/616 MB | 979/1104 MB | Out of memory


It is evident from the running time analysis that as we optimize the algorithm more from take 2 to take 6 the time taken by algorithm to sort the same number of elements decreases. 

The memory used is almost the same for algorithms and thus the optimization help in reducing the time taken and not the memory. This is because we still store the elements of array atleast two times in every variation of the algorithm.
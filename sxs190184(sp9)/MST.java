// Starter code for SP9

package sxs190184;

import sxs190184.Graph.Vertex;
import sxs190184.Graph.Edge;
import sxs190184.Graph.GraphAlgorithm;
import sxs190184.Graph.Factory;
import sxs190184.Graph.Timer;

import sxs190184.BinaryHeap.Index;
import sxs190184.BinaryHeap.IndexedHeap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.File;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
	String algorithm;
	public long wmst;
	List<Edge> mst;
	Graph forest;

	MST(Graph g) {
		super(g, new MSTVertex((Vertex) null));
		wmst =0;
		mst = new LinkedList<>();
	}

	public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {

		boolean seen;
		MSTVertex parent;
		int dist;
		Vertex vertex;
		Edge incidentEdge;
		int primIndex;
		int component;

		MSTVertex(Vertex u) {
			seen = false;
			parent = null;
			incidentEdge = null;
			dist = Integer.MAX_VALUE;
			vertex = u;
			primIndex = 0;
		}

		MSTVertex(MSTVertex u) {  // for prim2

			vertex = u.vertex;
			seen = u.seen;
			parent = u.parent;
			dist = u.dist;
			incidentEdge = u.incidentEdge;
			primIndex = u.primIndex;		

		}

		public MSTVertex make(Vertex u) { return new MSTVertex(u); }

		public void putIndex(int index) { 
			this.primIndex = index;
		}

		public int getIndex() { 
			return this.primIndex; }

		/**
		 * Ordering MSTVertices on the distance attribute.
		 * @param other		the vertex to be compared
		 * @return 		the value 0 if vertex is equal to the argument vertex; -1 if this is
		 *  			less than the argument; and +1 if this vertex greater than the argument vertex
		 */
		public int compareTo(MSTVertex other) {
			if (other == null || this.dist > other.dist)
				return 1;
			else if (this.dist == other.dist)
				return 0;
			else
				return -1;
		}
	}

	/**
	 * Counts and labels the components in the graph
	 * @param forest		forest which is converted to MST
	 * @return		total number of components in the forest
	 */
	public int countandLabel(Graph forest) {
		int count =0;
		for (Vertex u : forest) {
			get(u).seen = false;
		}
		for (Vertex u : forest) {
			if(get(u).seen == false) {
				count ++;
				bfs(u,count,forest);
			}
		}
		return count;
	}

	/**
	 * BFS traversal on the graph and labels the vertices according to the component it belongs to.
	 * @param u			starting vertex
	 * @param count		total no. of components
	 * @param forest			the forest on which BFS traversal is performed
	 */
	private void bfs(Vertex u, int count,Graph forest) {
		Queue<Vertex> q = new LinkedList<>();
		q.add(u);
		while(!q.isEmpty()) {
			Vertex v = q.remove();
			if(!get(v).seen){
				get(v).seen = true;
				get(v).component=count;
				for(Edge e : forest.incident(v)){
					Vertex w = e.otherEnd(v);
					q.add(w);
				}
			}
		}
	}

	/**
	 * Implementation of Boruvka's Algorithm
	 * @return total weight of MST
	 */
	public long boruvka() {
		algorithm = "Boruvka";

		forest = new Graph(g.size());
		int countLabel = countandLabel(forest);
		Edge [] E = g.getEdgeArray();

		while(countLabel >1) {
			addSafeEdges(E,forest,countLabel);
			countLabel = countandLabel(forest);
		}
		Edge []forestEdges = forest.getEdgeArray();
		for(Edge e :forestEdges) {
			wmst += e.getWeight();
		}	

		return wmst;
	}

	/**
	 * Adds safe edges to the forest to make it MST
	 * @param E			list of all the edges in the original graph
	 * @param forest			forest in which safe edges are added
	 * @param count		no. of components in the forest
	 */
	public void addSafeEdges(Edge[] E,Graph forest, int count) {
		Edge[] safe = new Edge[count+1];
		for (int i =1; i< count; i++) {
			safe[i]=null;
		}
		for(Edge e : E) {
			Vertex u = e.from;
			Vertex v = e.to;

			if (get(u).component != get(v).component) {
				if (safe[get(u).component] == null || e.compareTo(safe[get(u).component]) < 0) {
					safe[get(u).component] = e;
				}
				if (safe[get(v).component] == null || e.compareTo(safe[get(v).component]) < 0) {
					safe[get(v).component] = e;
				}
			}    		
		}

		for(int i =1; i <count; i++) {
			if(!mst.contains(safe[i])) {
				forest.addEdge(safe[i].from.getIndex(), safe[i].to.getIndex(), safe[i].weight);
				mst.add(safe[i]);
			}
		}
	}

	/**
	 * Prims Algorithm based on IndexedHeap
	 * @param s		starting vertex
	 * @return		weight of the MST
	 */
	public long prim2(Vertex s) {
		algorithm = "Prims2 : Indexed heaps";
		IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());

		for (Vertex u : g) {
			get(u).seen = false;
			get(u).parent = null;
			get(u).dist = Integer.MAX_VALUE;
		}
		get(s).dist = 0;

		for (Vertex u : g) {
			q.add(get(u)); 
		}

		while(!q.isEmpty()) {
			MSTVertex u = q.remove();
			u.seen = true;
			wmst += u.dist;

			 if(u.parent != null) {
				 mst.add(u.incidentEdge); 
			 }

			for(Edge e : g.incident(u.vertex)){
				MSTVertex v = get(e.otherEnd(u.vertex));
				if(!v.seen && e.getWeight() < v.dist){
					v.dist = e.getWeight();
					v.parent = u;
					v.incidentEdge = e;
					q.decreaseKey(v);					
				}
			}
		}

		return wmst;
	}


	public static MST mst(Graph g, Vertex s, int choice) {
		MST m = new MST(g);
		switch(choice) {
		case 0:
			m.boruvka();
			break;
		case 1:
			m.prim2(s);
			break;
		default:

			break;
		}
		return m;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		int choice = 1;  // prim2
		if (args.length == 0 || args[0].equals("-")) {
			in = new Scanner(System.in);
		} else {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		}

		if (args.length > 1) { choice = Integer.parseInt(args[1]); }

		Graph g = Graph.readGraph(in);
		Vertex s = g.getVertex(1);

		Timer timer = new Timer();
		MST m = mst(g, s, choice);
		System.out.println(m.algorithm + "\n" + m.wmst);
		System.out.println(timer.end());
	}
}

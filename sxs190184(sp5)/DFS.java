/** Starter code for SP5
 *  @author rbk
 */
/**
 * Shrey Shah(sxs190184) Deepanshu Sharma(dxs190018)
 */
// change to your netid
package sxs190184;

import sxs190184.Graph.Vertex;
import sxs190184.Graph.Edge;
import sxs190184.Graph.GraphAlgorithm;
import sxs190184.Graph.Factory;
import sxs190184.Graph.Timer;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * The class represents the implementation of Depth first Search
 *
 */
public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    private LinkedList<Vertex> topOrder;
    private boolean isCyclic;
    private int topNum;
    public static class DFSVertex implements Factory {
	int cno;
	boolean visited;
	Vertex parent;
	int topRank;
	/**
     * @param u
     * converting vertex u to DFSVertex
     */
	public DFSVertex(Vertex u) {
	    this.visited = false;
	    this.parent = null;
	    this.topRank = 0;
	}
	public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    /**
     * @param g
     * Initialing g with numberOfConnectedComponents and isCyclic
     */
    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        this.isCyclic = false;
        topOrder = new LinkedList<Vertex>();
    }

    /**
     * @param g
     * @return DFS object with the objects ordered
     */
    public static DFS depthFirstSearch(Graph g) {
	    DFS d = new DFS(g);
	    d.dfs();
        return d;
    }

    /**
     * Dfs method to run depth first search
     */
    public void dfs(){
        topNum = g.size();
        for (Vertex u :g) {
            get(u).visited = false;
            get(u).parent = null;
            get(u).topRank = 0;
        }
        topOrder = new LinkedList<Vertex>();
        for (Vertex u : g){
            if(!get(u).visited)
                DFS_visit(u);
        }
    }
    /**
     * @param u
     * Visiting each node adjacent to Vertex u
     */
    void DFS_visit(Vertex u){
        get(u).visited = true;
        for(Edge e : g.outEdges(u)){
            Vertex v = e.otherEnd(u);
            if(!get(v).visited){
                get(v).parent = u;
                DFS_visit(v);
            }
            else{
                if(get(v).topRank==0)
                    isCyclic = true;
            }
        }
        get(u).topRank = topNum--;
        topOrder.addFirst(u);
    }

    /**
     * @return list of vertices in topological order
     */
    public List<Vertex> topologicalOrder1() {
        dfs();
        if(isCyclic) return null;
        return topOrder;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
	return 0;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
	return get(u).cno;
    }
    
    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
	DFS d = new DFS(g);
	return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
	return null;
    }

    public static void main(String[] args) throws Exception {
	String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
//    String string = "7 7   1 2 0   1 3 0   2 4 0   3 4 0   4 5 0   5 6 0   6 7 0";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
	
	// Read graph from input
    Graph g = Graph.readDirectedGraph(in);
	g.printGraph(false);

	DFS d = new DFS(g);
	List<Vertex> topologicalOrder = d.topologicalOrder1();
	if(topologicalOrder == null)
        System.out.println("Cyclic Graph");
	else{
	    for(Vertex u : topologicalOrder)
            System.out.println(u);
    }
	
    }
}
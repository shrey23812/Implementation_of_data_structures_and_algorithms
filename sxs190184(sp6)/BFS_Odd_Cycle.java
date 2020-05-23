//Shrey Shah(sxs190184) and Deepanshu Sharma(dxs190018)
package sxs190184;

import sxs190184.Graph.Vertex;
import sxs190184.Graph.Edge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * BFS - finding odd length cycle
 * This program implements uses BFS from BFSOO and finds odd
 * length cycle if there exists one.
 * */

public class BFS_Odd_Cycle {

    /**
     * This is a helper method to find the least common ancestor of the
     * nodes u,v which makes up the odd length cycle
     * @param bfs: bfs object which has the the bfs traversal properties of a graph g
     * @param u: the vertex which was found to be contributing to the odd cycle
     * @param v: the incident vertex in edge u-v which was part of odd cycle
     * @return List of vertices in a odd length cycle
     */
    public List<Vertex> leastCommonAncestor(BFSOO bfs,Vertex u, Vertex v){
        List<Vertex> parent_fromU = new ArrayList<>();
        List<Vertex> parent_fromV = new ArrayList<>();

        while(u != v){
            parent_fromU.add(u);
            parent_fromV.add(0,v);
            u = bfs.getParent(u);
            v = bfs.getParent(v);
        }
        parent_fromU.add(u);
        parent_fromV.add(0,v);
        parent_fromV.addAll(parent_fromU);

        return parent_fromV;
    }

    /**
     * This method takes graph as an input and returns odd length
     * cycle as a list of vertices
     * @param g: Graph to use to find odd length cycle
     * @return List of vertices in a odd length cycle
     */
    public List<Vertex> oddCycle(Graph g){
        Vertex[] vertexArray = g.getVertexArray();
        BFSOO bfs = null;
        for(Vertex src : vertexArray ){
        if(bfs == null || !bfs.getSeen(src)){ //Runs bfs on all the components of graph
            bfs = BFSOO.breadthFirstSearch(g,src);
            for(Vertex u : vertexArray){
                if(bfs.getSeen(u)){
                    for(Edge e : g.outEdges(u)){
                        Vertex v = e.otherEnd(u);
                        if(bfs.getDistance(u) == bfs.getDistance(v)){ //If we get an edge which makes the cycle odd length than find the path by backtracking until we find least common ancestor of (u,v)
                            List<Vertex> oddCycle = leastCommonAncestor(bfs,u,v);
                            return oddCycle;
                        }
                    }
                }
            }
        }

        }
        return null; //return null if there is no odd length cycle
    }

    public static void main(String[] args) throws Exception {
        String string = "8 9   1 2 2   1 3 3   2 3 5   3 4 4   4 5 1   4 1 -7   6 7 1   6 8 1   7 8 1 1";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        // Read graph from input
        Graph g = Graph.readGraph(in);

        BFS_Odd_Cycle b = new BFS_Odd_Cycle();
        List<Vertex> ans = b.oddCycle(g);
        if(ans!=null)
            System.out.print("The odd length cycle in graph is "+ans);
        else
            System.out.println("The graph is bipartite");
    }
}
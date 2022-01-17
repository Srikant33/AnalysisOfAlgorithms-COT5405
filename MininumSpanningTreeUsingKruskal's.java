// Java program for Kruskal's algorithm to
// find Minimum Spanning Tree of a given
//connected, undirected and  weighted graph

import java.util.Arrays;

class Question2MST {
    int vertices, Edges; // vertices-> no. of vertices & Edges->no.of edges
    // A class to represent a subset for
    // union-find
    class subset
    {
        int parent, rank;
    }

    // A class to represent a graph edge
    class Edge implements Comparable<Edge>
    {
        int Source, destination, weight;

        // Comparator function used for
        // sorting edgesbased on their weight
        public int compareTo(Edge check)
        {
            return this.weight - check.weight;
        }
    }

    Edge edge[]; // collection of all edges

    // Creates a graph with vertices vertices and Edges edges
    Question2MST(int v, int e)
    {
        vertices = v;
        Edges = e;
        edge = new Edge[Edges];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    // A utility function to find set of an
    // element i (uses path compression technique)
    int fnd_subset(subset subsets[], int i)
    {
        // find root and make root as parent of i
        // (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent
                    = fnd_subset(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    // A function that does union of two sets
    // of x and y (uses union by rank)
    void Union(subset subsets[], int x, int y)
    {
        int xroot = fnd_subset(subsets, x);
        int yroot = fnd_subset(subsets, y);

        // Attach smaller rank tree under root
        // of high rank tree (Union by Rank)
        if (subsets[xroot].rank
                < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank
                > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

            // If ranks are same, then make one as
            // root and increment its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // The main function to construct MST using Kruskal's
    // algorithm
    void kruskal()
    {
        // Tnis will store the resultant MST
        Edge result[] = new Edge[vertices];

        // An index variable, used for result[]
        int e = 0;

        // An index variable, used for sorted edges
        int i = 0;
        for (i = 0; i < vertices; ++i)
            result[i] = new Edge();

        // Step 1:  Sort all the edges in non-decreasing
        // order of their weight.  If we are not allowed to
        // change the given graph, we can create a copy of
        // array of edges
        Arrays.sort(edge);

        // Allocate memory for creating vertices ssubsets
        subset subsets[] = new subset[vertices];
        for (i = 0; i < vertices; ++i)
            subsets[i] = new subset();

        // Create V subsets with single elements
        for (int v = 0; v < vertices; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0; // Index used to pick next edge
        int element=1;
        // Number of edges to be taken is equal to V-1
        while (e < vertices - 1)
        {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = edge[i++];

            int x = fnd_subset(subsets, next_edge.Source);
            int y = fnd_subset(subsets, next_edge.destination);

            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }
        int minimumCost = 0;
        for (i = 0; i < e; ++i)
        {
            minimumCost += result[i].weight;
            element++;
        }
    }

    public static void main(String[] args)
    {   for (int x=100;x <=10000; x=x+100)
    {   System.out.print(x);
        int SIZE = x;
        int Vertices = SIZE; // Number of vertices in graph
        int Edges = SIZE+8; // Number of edges in graph
        Question2MST graph = new Question2MST(Vertices, Edges);

        int arr[]=new int[SIZE];
        int arr2[]=new int[SIZE];
        for (int i=0;i<SIZE;i++) {
            arr[i]=i;
            arr2[i]=i;
        }

        int first = 0;
        int next=0;
        int pos=0;
        int count=0;
        arr[0]=0;

        for (int i=0;i<SIZE; i++) {     // add edges
            if (count < SIZE-1) {
                graph.edge[i].Source = first;
                do{
                    pos=arr[(int) (Math.random()*SIZE)];
                    next= pos;
                }
                while (next==0);
                graph.edge[i].destination = next;
                first=arr[next];
                arr[pos]=0;
                count++;
            }
            else{
                graph.edge[i].Source = (int) (Math.random()*SIZE);
                graph.edge[i].destination = (int) (Math.random()*SIZE);
            }
            graph.edge[i].weight = (int) (Math.random()*((50-20)+20));
        }
        // Function call
        long sTime = System.nanoTime();
        graph.kruskal();
        long eTime   = System.nanoTime();
        long Time = eTime - sTime;
        System.out.println(", "+Time);
        }
    }
}

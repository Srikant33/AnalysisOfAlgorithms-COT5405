import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Question1Cycle {

    // number of vertices
    private static int N;

    private static List edges[];

    private static Graph<String, DefaultEdge> undirectedGraph;


    // Adjacency List Representation
    private LinkedList<Integer> adj[];

    Question1Cycle(int v)
    {
        N = v;
        adj = new LinkedList[v];
        for(int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    // Function to add an edge into the graph
    void addE(int v,int w)
    {
        adj[v].add(w);
        adj[w].add(v);
    }

    // A recursive function that uses visited[] and parent to detect cycle in subgraph reachable from vertex v.
    Boolean isCyclicUtil(int v, Boolean visited[], int parent)
    {
        // Mark the current node as visited
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> it =
                adj[v].iterator();
        while (it.hasNext())
        {
            i = it.next();

            // If an adjacent is not visited, then recur for tha adjacent
            if (!visited[i])
            {
                if (isCyclicUtil(i, visited, v))
                    return true;
            }

            // If an adjacent is visited and not parent of current vertex, then there is a cycle.
            else if (i != parent)
                return true;
        }
        return false;
    }

    // Returns true if the graph contains a cycle, else false.
    Boolean isCyclic()
    {

        // Mark all the vertices as not visited and not part of recursion stack
        Boolean visited[] = new Boolean[N];
        for (int i = 0; i < N; i++)
            visited[i] = false;

        // Call the recursive helper function to detect cycle in different DFS trees
        for (int u = 0; u < N; u++)
        {
            // Don't recur for u if already visited
            if (!visited[u])
                if (isCyclicUtil(u, visited, -1))
                    return true;
        }
        return false;
    }

    public static void main(String args[])
    {
        for (int numberOfNodes=50;numberOfNodes<500;numberOfNodes=numberOfNodes+50)
        {
            //int numberOfNodes = sc.nextInt();
            N = numberOfNodes;
            edges = new List[N];

            Question1Cycle graph1 = new Question1Cycle(N);

            undirectedGraph = GraphTypeBuilder.undirected().allowingMultipleEdges(false).allowingSelfLoops(false).weighted(true).edgeSupplier(SupplierUtil.createDefaultEdgeSupplier()).vertexSupplier(SupplierUtil.createStringSupplier(1)).buildGraph();

            GnmRandomGraphGenerator<String, DefaultEdge> completeGenerator = new GnmRandomGraphGenerator<>(N, (N * (N - 1)) / 2);

            completeGenerator.generateGraph(undirectedGraph);
            Set<String> vertex = undirectedGraph.vertexSet();

            for (String u : vertex) {
                edges[Integer.parseInt(u) - 1] = Graphs.neighborListOf(undirectedGraph, u).stream().map(Integer::parseInt).map(number -> number - 1).collect(Collectors.toList());
            }

            for (int i = 0; i < N; i++) {
                List<Integer> list = edges[i];
                for (int j = 0; j < list.size(); j++) {
                    if (i < list.get(j))
                        graph1.addE(i, list.get(j));
                }
            }
            long sTime = System.nanoTime();
            graph1.isCyclic();
            System.out.println("Nodes: "+numberOfNodes + ", " + "Time in NanoSec: "+(System.nanoTime() - sTime));
        }
    }
}

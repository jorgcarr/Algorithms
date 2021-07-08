// Java program to print DFS
//mtraversal from a given given
// graph
import java.util.*;

// This class represents a
// directed graph using adjacency
// list representation
class Graph2 {

    static class Vertex {

        int weight;
        List<Integer> adjacentIndexList;
        int parentsWeight;

        public Vertex(int weight, List<Integer> adjacent) {
            this.weight = weight;
            this.adjacentIndexList = adjacent;
        }
    }

    // Array of lists for
    // Adjacency List Representation
    private Vertex vertices[];
    int minSum =0;

    // Constructor
    Graph(int v)
    {
        vertices = new Vertex[v];
    }

    // Function to add an edge into the graph
    void addEdge(int v, Vertex vertex)
    {
        vertices[v] =vertex; // Add w to v's list.
    }

    // A function used by DFS
    void DFSUtil(Vertex vertex, int sum)
    {
        // Mark the current node as visited and print it

        System.out.print(vertex.weight + " + ");

        // Recur for all the vertices adjacent to this
        // vertex
        sum = sum +vertex.weight;
        if(vertex.adjacentIndexList.size() > 0) {
            Iterator<Integer> i = vertex.adjacentIndexList.listIterator();
            while (i.hasNext())
            {
                int nextId = i.next();
                Vertex nestAdj = vertices[nextId];

                DFSUtil(nestAdj, sum);
            }
        } else {
            //end of path
            if(minSum ==0) {
                minSum = sum;
            }
            if(sum < minSum) {
                minSum = sum;
            }
            System.out.print( " = " + sum);
            System.out.println( "");
            System.out.println( " SIGUIENTE CAMINO ");

        }

    }

    // The function to do DFS traversal.
    // It uses recursive
    // DFSUtil()
    void DFS(int v)
    {

        // Call the recursive helper
        // function to print DFS
        // traversal
        DFSUtil(vertices[v], 0);
    }

    // Driver Code
    public static void main(String args[])
    {
        /*
        int[][] grid = {{1,2,3},
                        {4,5,6},
        };*/

        int[][] grid = {{1,3,1},
                        {1,5,1},
                        {4,2,1}};
        /*int[][] grid = {{1,2,3},
                        {4,5,6},
                        {7,8,9}};
*/
        int vectorLength = grid[0].length;
        Graph g = new Graph(grid.length * vectorLength);

        int index = 0;
        for (int i = 0; i< grid.length; i++) {

            for (int j = 0; j < grid[i].length;j++) {

                //System.out.println(grid[i][j]);
                int adjR = j+1;
                int adjD = i+1;

                List<Integer> adjacentIndexList = new ArrayList<>();


                if(adjR < vectorLength) {
                    //String key = new StringBuilder().append(i).append(adjR).toString();
                    adjacentIndexList.add(index+1);
                    //a.add(grid[i][adjR]);
                }
                if(adjD < grid.length) {
                    //String key2 = new StringBuilder().append(adjD).append(j).toString();
                    adjacentIndexList.add(index +vectorLength);
                    //a.add(grid[adjD][j]);
                }

                g.addEdge(index, new Vertex(grid[i][j], adjacentIndexList));
                index = index+1;

            }
        }

        g.stackImplementation();
        System.out.println("-------------");
        g.DFS(0);
        System.out.println(
                "Following is Depth First Traversal "
                        + "the minSum is " + g.minSum);
    }

    void stackImplementation() {
        Stack<Vertex> verticesStack = new Stack<>();
        verticesStack.push(vertices[0]);

        while(!verticesStack.isEmpty()) {

            Vertex currentNode = verticesStack.pop();
            System.out.print(currentNode.weight + " + ");

            if(currentNode.adjacentIndexList.isEmpty()) {
                int pathSum = currentNode.parentsWeight + currentNode.weight;
                System.out.println("FINAL DEL PATH: "+ pathSum);

                if(minSum ==0) {
                    minSum = pathSum;
                }
                if(pathSum < minSum) {
                    minSum = pathSum;
                }
            }

            for (Integer adjIndex :currentNode.adjacentIndexList) {

                Vertex newVertex = vertices[adjIndex];
                newVertex.parentsWeight=currentNode.weight+currentNode.parentsWeight;
                verticesStack.push(newVertex);
            }
        }
        System.out.println(
                "Stack implementation "
                        + "the minSum is " + minSum);
    }
}


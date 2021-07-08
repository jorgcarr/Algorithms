import java.lang.module.FindException;
import java.util.*;

class Node{
    List<Integer> adjacent;
    int index = 0;

    public Node(int index) {
        this.index = index;
        this.adjacent = new ArrayList<>();
    }

    public void addAdjacent(int index) {
        adjacent.add(index);
    }

    public List<Integer> getAdjacent() {
        return adjacent;
    }
}

public class GridConnections {

    static List flatGridIndex = new ArrayList();

    static Map<Integer, Integer> counts = new HashMap<>();

    public static void main(String[] args) {
        /*
        int grid[][] = {{1,1,1,1,1,1,0,1,0},
                {1,1,1,1,1,0,0,0,0},
                {1,1,1,1,0,0,1,1,0},
                {1,1,1,0,0,0,1,1,0},
                {1,1,0,1,1,1,0,0,0},
                {1,0,0,1,1,1,0,0,0}};
           */
        int grid[][] = {{1,1,1,0},
                        {1,1,0,0},
                        {1,0,1,1},
                        {0,1,0,0},
                };

        List<Node> connections = new ArrayList<>();
        int gridLength = grid.length;

        for (int i =0; i< gridLength; i++) {
            for (int j=0; j< grid[i].length; j++) {
                int val = grid[i][j];

                if(val == 1) {
                    putConnectionIfExist(connections,grid, i,j);
                }

            }
        }

        System.out.println(connections);

        Stack<Node> verticesStack = new Stack<>();
        verticesStack.push(connections.get(0));

        while(! verticesStack.isEmpty()) {
            Node node = verticesStack.pop();
            List<Integer> adjacents = node.getAdjacent();

            for (Integer adjacent: adjacents) {

            }
            //nodes_to_visit.prepend( currentnode.children );
        }//do something
    }

    public static int getFlattenIndex(int i, int j, int gridLength) {
        return j+i*gridLength;
    }

    private static void putConnectionIfExist(List<Node> connections, int[][] grid, int i, int j) {
        int nextJ = j+1;
        int nextI = i+1;
        Node node = new Node(getFlattenIndex(i,j, grid.length));
        //TODO: DONDE CONSIGO EL INDICE DEL NODO O DEBO SIEMPRE VOLVERLO A CONSTRUIR?? O ES
        // EL INDICE DE LA GRILLA ORIGINAL, SI ES ASÍ DEBERIA CONSTRUIR NUEVAMENTE EL NODO
        flatGridIndex.add(getFlattenIndex(i,j, grid.length));

        if(nextI < grid.length){
            if(grid[nextI][j] == 1) {
                node.addAdjacent(getFlattenIndex(nextI,j, grid.length));
            }
        }

        if(nextJ < grid.length){
            if(grid[j][nextJ] ==1) {
                node.addAdjacent(getFlattenIndex(i,nextJ, grid.length));
            }
        }
        connections.add(node);
    }

    public static int getIMatrixIndex(int flattenIndex, int gridLength) {
        return flattenIndex/gridLength;
    }
    public static int getJMatrixIndex(int flattenIndex, int gridLength) {
        return flattenIndex % gridLength;
    }
}

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AdjacentStack {

    private LinkedList<Integer>[] adj = new LinkedList[9];

    void addAdjacent(int index, int adjacent) {
        LinkedList<Integer> list = adj[index];
        if (list == null) {
            list = new LinkedList<>();
        }
        if (adjacent != 0) {
            
            list.add(adjacent);
        }

    }

    public static void main(String[] args) {

        int[][] matrix = {
                {1, 1, 1, 1, 1, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 0, 0, 0, 1, 1, 0},
                {1, 1, 1, 0, 0, 0, 1, 1, 0, 0},
                {1, 1, 0, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 1},
                {0, 0, 1, 0, 0, 1, 0, 0, 1, 1},
                {0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 1, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 1, 1, 0, 1, 1}
        };
        AdjacentStack adjacentStack = new AdjacentStack();
        int[] above = new int[matrix.length];
        int prev = 0;


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int val = matrix[i][j];
                int flatIndex = i * j + j;
                int prevFlatIndex = i * j - 1 + j;
                int aboveFlatIndex = (i - 1) * j + j;
                if (prev == 1) {
                    adjacentStack.addAdjacent(prevFlatIndex, flatIndex);

                    if (val == 1) {
                        adjacentStack.addAdjacent(prevFlatIndex, prevFlatIndex);

                        if (above[j] == 1) {
                            adjacentStack.addAdjacent(prevFlatIndex, aboveFlatIndex);
                        }
                    }

                }
                prev = val;
                above = matrix[i];
            }
        }

        System.out.println(howManyAdjacentOf(matrix, 1));
        System.out.println(howManyAdjacentOf(matrix, 2));
        System.out.println(howManyAdjacentOf(matrix, 3));
        System.out.println(howManyAdjacentOf(matrix, 4));
        System.out.println(howManyAdjacentOf(matrix, 6));
        System.out.println(howManyAdjacentOf(matrix, 15));


    }

    public static int howManyAdjacentOf(int[][] matrix, int query) {
        return 0;

    }


}

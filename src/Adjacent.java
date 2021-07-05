import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Adjacent {

    static class Range { int start; int end; int count;

        public Range(int start, int end, int count) {
            this.start = start;
            this.end = end;
            this.count = count;
        }
    }

    public static void main(String[] args) {

        int matrix[][] = {
                {1,1,1,1,1,0,1,0,0,1},
                {1,1,1,1,0,0,0,1,1,0},
                {1,1,1,0,0,0,1,1,0,0},
                {1,1,0,0,1,0,0,0,1,1},
                {1,0,0,0,1,0,0,0,1,1},
                {0,0,1,0,0,1,0,0,1,1},
                {0,0,1,0,0,0,1,0,0,0},
                {1,1,0,0,0,0,0,0,0,0},
                {1,1,0,0,0,1,1,0,0,1},
                {0,0,1,0,0,1,1,0,1,1}
        };

        System.out.println(howManyAdjacentOf(matrix, 1));
        System.out.println(howManyAdjacentOf(matrix, 2));
        System.out.println(howManyAdjacentOf(matrix, 3));
        System.out.println(howManyAdjacentOf(matrix, 4));
        System.out.println(howManyAdjacentOf(matrix, 6));
        System.out.println(howManyAdjacentOf(matrix, 15));



    }

    public static int howManyAdjacentOf(int[][] matrix, int query) {
        Map<Integer, Integer> counts = new HashMap<>();

        LinkedList<Range> prevListOfRanges = new LinkedList<>();
        for (int i = 0; i< matrix.length; i++) {
            int vector[] = matrix[i];
            LinkedList<Range> currentListOfRanges = new LinkedList<>();
            boolean hasStarted = false;

            for (int j = 0; j< matrix.length; j++) {

                if(vector[j] == 1) {
                    if(!hasStarted) {
                        hasStarted = true;
                        currentListOfRanges.add(new Range(j,0,0));
                    }
                    Range range = currentListOfRanges.getLast();
                    range.count = range.count +1;
                } else {
                    if(hasStarted) {
                        hasStarted =false;
                        currentListOfRanges.getLast().end = j-1;
                        searchInPrevRangesToJoinOrAdd(counts, prevListOfRanges, currentListOfRanges.getLast());
                    }
                }
            }
            if(hasStarted) {
                currentListOfRanges.getLast().end = vector.length-1;
                searchInPrevRangesToJoinOrAdd(counts, prevListOfRanges, currentListOfRanges.getLast());
            }
            prevListOfRanges = currentListOfRanges;
        }

        Integer result = counts.get(query);
        return result == null?0:result;

    }

    private static void searchInPrevRangesToJoinOrAdd(Map<Integer, Integer> counts, LinkedList<Range> prevListOfRanges, Range current) {
        boolean joined = false;
        for (Range aboveRange: prevListOfRanges) {
            //validar si se solapa en alguno de los rangos de la fila anterior
            if(current.start >= aboveRange.start && current.start <=aboveRange.end
                || current.end >= aboveRange.start && current.end <=aboveRange.end ) {

                joined = true;

                int newCount = aboveRange.count + current.count;
                //subtract from old count and add to de new
                counts.computeIfPresent(aboveRange.count, (key, oldValue) ->oldValue-1);
                counts.computeIfPresent(newCount, (key, oldValue) ->oldValue+1);
                counts.putIfAbsent(newCount, 1);

                current.count = newCount;
            }
        }
        if(!joined) {
            counts.computeIfPresent(current.count, (key, oldValue) ->oldValue+1);
            counts.putIfAbsent(current.count, 1);
        }
    }
}

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.IntStream;


public class Test {

    public static void main(String[] args) {
        //Solution s = new Solution();

        //int A[] = { 1, 2,3,4,5};
        //System.out.println("faltó: "+s.solution2(A));
        Square s = new Square(4);

        Shape shape = Square::calculateArea;
        System.out.println(shape.getArea(s));
    }




}
class Solution {
    public int solution2(int[] A) {

        List<Integer> ints = Arrays.stream(A).
                distinct().
                sorted().
                filter(a -> a >0).
                boxed()
                .collect(Collectors.toList());

        int minInt =0;

        if(ints.size() >1) {

            for (int i = 0; i< ints.size(); i++) {
                minInt++;
                if(ints.get(i) != minInt) {
                    break;
                }
            }
        } else {
            return 1;
        }
        if(minInt == ints.get(ints.size()-1)) {
            minInt++;
            return minInt;
        }
        return minInt;
    }
    public int solution(int[] A) {

        Arrays.sort(A);
        int minInt =1;
        int prev = 1;
        Arrays.stream(A).forEach(System.out::println);

        for (int i = 0; i< A.length; i++) {

            System.out.println("A["+i+"] "+A[i]);
            System.out.println("prev "+prev);
            System.out.println("----- ");

            if(prev != A[i] && A[i]>0) {
                minInt++;
                System.out.println("min "+minInt);

                if(A[i] != minInt) {
                    break;
                }
            }
            prev =A[i];
        }
        if(minInt == A[A.length-1]) {
            return minInt+1;
        }
        return minInt;
    }
}

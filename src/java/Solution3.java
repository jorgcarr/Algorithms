import java.util.Arrays;
import java.util.List;

public class Solution3 {

    public static void main(String[] args) {

        String s = "11100";

        int n  = Integer.parseInt(s, 2);
        System.out.println("Initial: "+n);
        int result = 0;
        while(n != 0) {
            if(n%2 ==0 ) {
                n=n/2;
            } else {
                n = n-1;
            }
            System.out.println(n);
            result = result+1;
        }

        System.out.println("result: "+result);

//        assertThat(result).isEqualTo(21);
    }
}

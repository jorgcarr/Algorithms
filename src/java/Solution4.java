import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution4 {

    public static void main(String[] args) {
        String text = "dsdsddd ddsdsd  sds ds d d d  sssd dee ee eeeeee eeeeeee";

        String[] a = text.split(" ");
        Set<String> wr = Arrays.stream(a).filter(word -> word.length() < 5).map(w -> w.toUpperCase(Locale.ROOT)).collect(Collectors.toSet());
        System.out.println(wr);
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * Create the Filter and Mapper classes here.
 */
class Filter {

    static Predicate<String> nameStartingWithPrefix(String prefix) {
        return str -> str.startsWith(prefix);
    }
}

class Mapper {

    static Function<String, CharactersCount> getDistinctCharactersCount() {


        return str ->  {
            List<Character> chars = str.chars()              // IntStream
                    .mapToObj(e -> (char)e) // Stream<Character>
                    .collect(Collectors.toList());
            return new CharactersCount(str, chars.size());
        };
    }
}

class CharactersCount {
    private final String name;
    private final Integer distinctCharacterCount;

    public CharactersCount(String name, Integer distinctCharacterCount) {
        this.name = name;
        this.distinctCharacterCount = distinctCharacterCount;
    }

    @Override
    public String toString() {
        return "\"" + this.name + "\" has " + this.distinctCharacterCount + " distinct characters.";
    }
}

public class Solution3 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> names = Arrays.asList(
                "aaryanna",
                "aayanna",
                "airianna",
                "alassandra",
                "allanna",
                "allannah",
                "allessandra",
                "allianna",
                "allyanna",
                "anastaisa",
                "anastashia",
                "anastasia",
                "annabella",
                "annabelle",
                "annebelle"
        );

        names.stream()
                .filter(Filter.nameStartingWithPrefix(scanner.nextLine()))
                .map(Mapper.getDistinctCharactersCount())
                .forEachOrdered(System.out::println);
    }
}
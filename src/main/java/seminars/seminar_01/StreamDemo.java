package seminars.seminar_01;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {

        List<String> langs = List.of("java", "c#", "c++", "python", "kotlin", "go", "assembler", "pascal", "rust", "javascript", "ruby", "php", "delphi", "basic");
        printLangsWithLengthFor(langs);
        System.out.println();
        printLangsWithLengthStream(langs);
        System.out.println();

        Integer sum = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);
        Integer multiplication = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).reduce(1, (a, b) -> a * b);
        System.out.println(multiplication);
        Optional<Integer> result = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream().reduce(0, (a, b) -> a + b).describeConstable();
        System.out.println(result.isPresent());

        String joining = langs.stream().reduce( "START: ", (a, b) -> a + "\\|/" + b);
        System.out.println(joining + "\n");
    }

    static void printLangsWithLengthFor(List<String> langs) {
        for (String lang : langs) {
            if (lang.length() > 4) {
                System.out.println(lang);
            }
        }
    }

    // x x y x x y y x x -> |filter| -> x x x x x x
    static void printLangsWithLengthStream(List<String> langs) {
        langs.stream()
                .filter(s -> s.length() > 4)
                .map(s -> s.toUpperCase())
                .forEach(s -> System.out.println(s));
    }
}

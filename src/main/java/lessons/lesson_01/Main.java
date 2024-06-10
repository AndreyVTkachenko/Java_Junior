package lessons.lesson_01;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        PlainInterfaceString plainInterfaceString01 = new PlainInterfaceString() {

            @Override
            public String action(int x, int y) {
                return String.valueOf(x + y);
            }
        };

        PlainInterfaceString plainInterfaceString02 = (x, y) -> String.valueOf(x + y);

        PlainInterfaceString plainInterfaceString03 = (x, y) -> {
            return String.valueOf(x + y);
        };

        System.out.println(plainInterfaceString01.action(4, 2));
        System.out.println(plainInterfaceString02.action(5, 3));
        System.out.println(plainInterfaceString03.action(6, 4));

        PlainInterfaceString plainInterfaceString04 = (x, y) -> String.valueOf(Integer.compare(x, y));

        System.out.println(plainInterfaceString04.action(2, 6));
        System.out.println("________________________________________________");


        PlainInterfaceInt plainInterfaceInt01 = Integer::sum;
        PlainInterfaceInt plainInterfaceInt02 = Integer::compare;

        System.out.println(plainInterfaceInt01.action(4, 2));
        System.out.println(plainInterfaceInt02.action(2, 6));
        System.out.println("________________________________________________");


        List<String> list01 = Arrays.asList("Привет", "мир", "!", "я", "родился", "!");

        list01 = list01.stream().filter(str -> str.length() > 4).collect(Collectors.toList());
        for (String item : list01) {
            System.out.println(item);
        }

        List<String> list02 = Arrays.asList("Привет", "мир", "!", "я", "родился", "!");
        list02.stream().filter(str -> str.length() > 4).forEach(System.out::println);

        List<String> list03 = Arrays.asList("Привет", "мир", "!", "я", "родился", "!");
        list03.stream().filter(str -> str.length() > 4).filter(str -> str.contains("о")).forEach(System.out::println);
        System.out.println("________________________________________________");


        Arrays.asList(1, 2, 3, 4, 5).stream().map(chislo -> chislo * chislo).forEach(System.out::println);
        Arrays.asList(1, 2, 3, 4, 5).stream().map(chislo -> "число: " + chislo + ". квадрат числа - " + chislo * chislo).forEach(System.out::println);
        Arrays.asList(1, 10, 0, 7, 5).stream().sorted().forEach(System.out::println);
        Arrays.asList(1, 10, 0, 5, 7, 1, 5).stream().sorted().distinct().forEach(System.out::println);
        System.out.println();
        System.out.println(Arrays.asList(1, 10, 0, 7, 5).stream().sorted().findFirst().get());
        System.out.println("________________________________________________");


        List<User> stringList = Arrays.asList(new User("Bob", 25), new User("John", 41), new User("Andrew", 32));
        stringList.stream().map(user -> new User(user.name, user.age - 5)).forEach(System.out::println);
        System.out.println();
        stringList.stream().map(user -> new User(user.name, user.age - 5)).filter(user -> user.age <= 35).forEach(System.out::println);
    }
}

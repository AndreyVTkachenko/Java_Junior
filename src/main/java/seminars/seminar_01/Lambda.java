package seminars.seminar_01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

public class Lambda {

    public static void main(String[] args) {

        Runnable helloPrinter01 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        helloPrinter01.run();

        Runnable helloPrinter02 = () -> {
            System.out.println("World");
        };
        helloPrinter02.run();

        // TODO () -> () - runnable ничего не принимает и ничего не возвращает
        Runnable helloPrinter = () -> {
            System.out.print("Hello, ");
            System.out.println("world!");
        };

        System.out.println();
/* ================================================================================================================== */

        Consumer<String> stringPrinter01 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        // TODO (t) -> () - consumer (потребитель) принимает нечто и ничего не возвращает
        Consumer<String> stringPrinter = (s) -> System.out.println(s);

        System.out.println();
/* ================================================================================================================== */

        Supplier<Integer> randIntProvider01 = () -> {
            return ThreadLocalRandom.current().nextInt(0, 10);
        };

        // TODO () -> T - supplier (поставщик) потребляет данные и ничего не возвращает
        Supplier<Integer> randIntProvider02 = () -> ThreadLocalRandom.current().nextInt(10, 100);

        System.out.println(randIntProvider01.get());
        System.out.println(randIntProvider01.get());
        System.out.println(randIntProvider01.get());

        System.out.println(randIntProvider02.get());
        System.out.println(randIntProvider02.get());

        System.out.println();
/* ================================================================================================================== */

        // TODO (T) -> R - function (функция) принимает объекты одного типа и возвращает объекты другого типа
        Function<String, Integer> stringLengthFunction01 = s -> {
            return s.length();
        };
        int x = stringLengthFunction01.apply("hello"); // 5
        System.out.println(x);

        Function<String, Integer> stringLengthFunction02 = g -> g.length();
        int m = stringLengthFunction02.apply("документооборот"); // 15
        System.out.println(m);

        Function<String, String> toUpperCaseFunction = str -> str.toUpperCase();
        String value = toUpperCaseFunction.apply("Hello, World!");
        System.out.println(value);

        // То же что и Function, но только с одним дженериком (принимаемый и возвращаемый объект одного типа)
        UnaryOperator<String> toUpperCaseUnaryOperator = str -> str.toUpperCase();
        String stroka = toUpperCaseUnaryOperator.apply("Hello, World!");
        System.out.println(stroka);

        System.out.println();
/* ================================================================================================================== */

        // (s) -> boolean - predicate тестер (фильтр)
        Predicate<Integer> isEvenPredicate = b -> b % 2 == 0;

        System.out.println(isEvenPredicate.test(2)); // true
        System.out.println(isEvenPredicate.test(3)); // false
        System.out.println(isEvenPredicate.test(12)); // true
        System.out.println(isEvenPredicate.test(27)); // false
        System.out.println(isEvenPredicate.test(0)); // true

        System.out.println();
/* ================================================================================================================== */

        List<String> strings = new ArrayList<>(List.of("java", "c#", "c++", "python", "kotlin", "go", "assembler"));
        Collections.sort(strings); // сортировка по умолчанию - по алфавиту
        System.out.println(strings);

        Collections.sort(strings, (a, b) -> { // сортировка по длине
            if (a.length() < b.length()) {
                return -1;
            } else if (a.length() > b.length()) {
                return 1;
            } else return 0;
        });
        System.out.println(strings);

        Collections.sort(strings, (a, b) -> { // такая же сортировка по длине
            return a.length() - b.length();
        });
        System.out.println(strings);

        Collections.sort(strings, (a, b) -> a.length() - b.length()); // такая же сортировка по длине
        System.out.println(strings);

        System.out.println();
/* ================================================================================================================== */

        int q = List.of(1, 2, 3, 4, 5).stream().reduce(0, Integer::sum);
        System.out.println(q);

        Predicate<String> isJava = "java"::equals;
        System.out.println(isJava.test("java"));
        System.out.println(isJava.test("python"));

        System.out.println();
/* ================================================================================================================== */
        foo();
    }

    static Runnable foo() {
        // effectively final
        String[] x = {"value"};
        Runnable xUpdater = () -> {
            x[0] = "updated";
        };

        xUpdater.run();
        System.out.println(Arrays.toString(x));
        return xUpdater;
    }
}

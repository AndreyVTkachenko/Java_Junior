package seminars.seminar_02.lib;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Random {

    int min() default 0;

    int max() default 100;

    //  String value(); // может быть только одно и только указанных ниже типов
/*
    String s();

    Gender gender();

    Class<?> class();

    String[] string();
*/

    // все примитивы:  int, long, double...
    // String
    // любой enum
    // массив всего что выше
}

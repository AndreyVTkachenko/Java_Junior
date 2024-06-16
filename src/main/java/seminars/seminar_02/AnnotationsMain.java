package seminars.seminar_02;

import seminars.seminar_02.lib.ObjectCreator;
import seminars.seminar_02.lib.Random;

public class AnnotationsMain {

    public static void main(String[] args) {
/*
        Person person = new Person();
        System.out.println(person.getAge());

        Person rndPerson = ObjectCreator.createObj(Person.class);
        // RandomAnnotationProcessor.processAnnotation(person);
        System.out.println(person.getAge());

 */

        Person rndPerson = ObjectCreator.createObj(Person.class);
        System.out.println("age1: " + rndPerson.age1);
        System.out.println("age2: " + rndPerson.age2);
        System.out.println("age3: " + rndPerson.age3);
        System.out.println("age4: " + rndPerson.age4);

        System.out.println();
        Object s = "12345";
        if (s instanceof String) {
            System.out.println("STRING!!!");
        }
        if (String.class.isInstance(s)) {
            System.out.println("STRING!!!");
        }
        if (s.getClass().isAssignableFrom(String.class)) {
            System.out.println("STRING!!!");
        }
        System.out.println();

        Number x = new Integer(1);
        System.out.println(x.getClass().isAssignableFrom(Integer.class));
        System.out.println(x.getClass().isAssignableFrom(Number.class));

        // extPerson.isAssignableFrom(ExtPerson.class) // true
        // extPerson.isAssignableFrom(Person.class) // false
        // person.isAssignableFrom(ExtPerson.class) // true

        Person p = new Person();
        Person ep = new ExtPerson();

        System.out.println(p.getClass().isAssignableFrom(Person.class)); // true
        System.out.println(p.getClass().isAssignableFrom(ExtPerson.class)); // true

        System.out.println(ep.getClass().isAssignableFrom(Person.class)); // false
        System.out.println(ep.getClass().isAssignableFrom(ExtPerson.class)); // true
    }





    public static class ExtPerson extends Person {

    }





    public static class Person {

        @Random(min = 18, max = 22) // случайное число в диапазоне [18, 22)
        private int age1;

        @Random(min = 50, max = 51) // случайное число в диапазоне [50, 51) => 50
        private int age2;

        @Random // дефолтное значение в диапазоне [0, 100)
        private int age3;

        @Random(min = 50) // случайное число в диапазоне [50, дефолтное значение)
        private int age4;
/*
        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    '}';
        }

 */
    }
}

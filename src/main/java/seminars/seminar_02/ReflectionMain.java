package seminars.seminar_02;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectionMain {

    // Reflection (рефлексия) - способность ЯП в момент выполнения программы узнавать структуру самого себя

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Person unnamed = new Person();
        Person person = new Person("personName");
        System.out.println(unnamed);
        System.out.println(person);

//        Class<? extends Person> aClass = unnamed.getClass();

//        Class<String> stringClass = String.class;
        Class<Person> personClass = Person.class;

        System.out.println();
        System.out.println("Декларированные методы класса Person: ");
        Constructor<?>[] declaredConstructors = personClass.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            System.out.println(declaredConstructor);
        }
        System.out.println();

        Constructor<Person> constructor1 = personClass.getConstructor();
        Person person1 = constructor1.newInstance();
        System.out.println(person1);
        Constructor<Person> constructor2 = personClass.getConstructor(String.class);
        Person viaReflectPerson = constructor2.newInstance("via reflect");
        System.out.println(viaReflectPerson);
        System.out.println();

        System.out.println(unnamed.getName());
        Method getName = Person.class.getMethod("getName");
        System.out.println(getName.invoke(unnamed)); // unnamed
        System.out.println(getName.invoke(person)); // personName
        System.out.println();

        Method setAge = Person.class.getMethod("setAge", int.class);
        setAge.invoke(unnamed, 10); // = unnamed.setAge(10));
        System.out.println(unnamed);
        System.out.println();

        System.out.println("Counter = " + Person.getCounter());
        Method getCounter = Person.class.getMethod("getCounter");
        System.out.println("Counter (via reflect) = " + getCounter.invoke(null));


        Field name = Person.class.getDeclaredField("name");
        System.out.println("Name field = " + name.get(unnamed)); // unnamed

        // name.set(unnamed, "new name"); // IllegalAccessException из-за того что поле final
        System.out.println("Name field = " + name.get(unnamed)); // new name
        System.out.println();

        // Method declaredConstructor1 = ExtPerson.class.getDeclaredMethod("getName"); // NoSuchMethodException из-за того что метод продекларирован в классе-родителе
        Method declaredConstructor2 = ExtPerson.class.getMethod("getName");

        ExtPerson.class.getSuperclass(); // Person.class

        Class<Person> personClass1 = Person.class;
        Class<? super ExtPerson> superclass = ExtPerson.class.getSuperclass();
        System.out.println(personClass1); // class seminars.seminar_02.ReflectionMain$Person
        System.out.println(superclass); // class seminars.seminar_02.ReflectionMain$Person

        ExtPerson extPerson = new ExtPerson();
        extPerson.getName();

        System.out.println();
        Class<?>[] interfaces = ArrayList.class.getInterfaces();
        Arrays.stream(interfaces).forEach(System.out::println);


    }

    private static class ExtPerson extends Person {

    }

    private static class Person {

        private static long counter = 0L;
        private final String name;
        private int age;

        public Person() {
            this("unnamed");
        }

        public Person(String name) {
            this.name = name;
            counter++;
        }

        public static long getCounter() {
            return counter;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}

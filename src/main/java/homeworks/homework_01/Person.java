package homeworks.homework_01;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Person {
    private String name;
    private int age;
    private double salary;
    private Department depart;

    public Person(String name, int age, double salary, Department depart) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.depart = depart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepart() {
        return depart;
    }

    public void setDepart(Department depart) {
        this.depart = depart;
    }

    private static <T> T getRandom(List<? extends T> items) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(randomIndex);
    }

    public static Person generateRandomPerson(List<String> lastNames, List<String> firstNames, List<Department> departments) {
        String lastName = getRandom(lastNames);
        String firstName = getRandom(firstNames);
        int age = ThreadLocalRandom.current().nextInt(18, 45);
        double salary = ThreadLocalRandom.current().nextInt(100000, 250000) * 1.0;
        Department department = getRandom(departments);
        return new Person(firstName.trim() + " " + lastName, age, salary, department);
    }

    @Override
    public String toString() {
        return "Сотрудник: " + name +
                ", Возраст: " + age +
                ", Зарплата: " + salary +
                ", Отдел: " + depart.getName();
    }

    /**
     * Найти самого молодого сотрудника
     */
    static Optional<Person> findMostYoungestPerson(List<Person> people) {
        return people.stream()
                .min((o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static Optional<Department> findMostExpensiveDepartment(List<Person> people) {
        return people.stream()
                .max((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary()))
                .map(person -> person.getDepart());
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(item -> item.getDepart()));
    }

    /**
     * Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(person -> person.getDepart().getName()));
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        return people.stream()
                .collect(Collectors.toMap(
                        b -> b.getDepart().getName(),
                        b -> b,
                        (a, b) -> {
                            if (a.getAge() > b.getAge()) {
                                return a;
                            } else {
                                return b;
                            }
                        }
                ));
    }

    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе
     * (примечание можно реализовать в два запроса)
     */
    static List<Person> cheapPersonsInDepartment(List<Person> people) {
        Map<String, List<Person>> gBDN = groupByDepartmentName(people);

        return gBDN.values().stream()
                .map(department -> department.stream()
                        .min((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary()))
                        .orElse(null))
                .filter(person -> person != null)
                .collect(Collectors.toList());
    }
}

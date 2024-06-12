package homeworks.homework_01;

import java.util.ArrayList;
import java.util.List;

import static homeworks.homework_01.Person.*;

public class Main {

    public static void main(String[] args) {

        List<String> personsLastName = List.of("Иванов", "Петров", "Сидоров", "Кузнецов", "Попов", "Смирнов", "Васильев", "Соколов", "Михайлов", "Морозов");
        List<String> personsFirstName = List.of("Иван", "Дмитрий", "Кирилл", "Егор", "Илья", "Алексей", "Артём", "Фёдор", "Роман", "Даниил");
        List<String> departmentNames = List.of("Бухгалтерия", "Юр. отдел", "Продажи", "Делопроизводство", "Логистика", "Склад", "Тех. отдел", "Фин. отдел", "Снабжение", "Отдел кадров");

        List<Department> departments = Department.generateDepartments(departmentNames, 10);

        List<Person> persons = generatePersons(personsLastName, personsFirstName, departments, 20);
        for (Person person : persons) {
            System.out.println("Работник: " + person);
        }

        System.out.println("=========================================================================================");
        System.out.println("Самый молодой сотрудник: " + findMostYoungestPerson(persons) + "\n");
        System.out.println("Отдел с самой большой зарплатой: " + findMostExpensiveDepartment(persons) + "\n");
        System.out.println("Группировка по отделам: \n" + groupByDepartment(persons) + "\n");
        System.out.println("Группировка по названиям отделов: \n" + groupByDepartmentName(persons) + "\n");
        System.out.println("Самый старший сотрудник отдела: \n" + getDepartmentOldestPerson(persons) + "\n");
        System.out.println("Сотрудник с минимальной зарплатой в своём отделе: \n" + cheapPersonsInDepartment(persons) + "\n");
    }

    public static List<Person> generatePersons(List<String> lastNames, List<String> firstNames, List<Department> departments, int count) {
        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            persons.add(Person.generateRandomPerson(lastNames, firstNames, departments));
        }

        return persons;
    }
}

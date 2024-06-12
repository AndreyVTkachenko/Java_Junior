package homeworks.homework_01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Department {
    private String name;

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Отдел: " +
                "{название: " + name + "}";
    }

    private static <T> T getRandom(List<? extends T> items) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(randomIndex);
    }

    public static Department generateRandomDepartment(List<String> departmentNames) {
        String name = getRandom(departmentNames);
        return new Department(name);
    }

    public static List<Department> generateDepartments(List<String> departmentNames, int count) {
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            departments.add(generateRandomDepartment(departmentNames));
        }
        return departments;
    }
}

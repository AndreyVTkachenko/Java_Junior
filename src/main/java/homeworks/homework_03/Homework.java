package homeworks.homework_03;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Homework {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            createTableDepartment(connection);
            insertDataDepartment(connection);
            createTablePerson(connection);
            insertDataPerson(connection);
            System.out.println("================================================================");

            int personId = 5;
            String departmentName = getPersonDepartmentName(connection, personId);
            System.out.println("Имя департамента для Person с id " + personId + ": " + departmentName);
            System.out.println("================================================================");

            Map<String, String> personDepartments = getPersonDepartments(connection);
            for (Map.Entry<String, String> entry : personDepartments.entrySet()) {
                System.out.println("Person: " + entry.getKey() + ", Department: " + entry.getValue());
            }
            System.out.println("================================================================");

        } catch (SQLException e) {
            System.err.println("Во время подключения произошла ошибка: " + e.getMessage());
        }
    }

    private static void createTablePerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                        create table person (
                        id bigint primary key,
                        name varchar(256),
                        age integer,
                        active boolean,
                        department_id bigint,
                        foreign key (department_id) references department(id)
                    )
                    """);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    private static void insertDataPerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertQuery = new StringBuilder("insert into person(id, name, age, active, department_id) values\n");
            for (int i = 1; i <= 10; i++) {
                int age = ThreadLocalRandom.current().nextInt(20, 60);
                boolean active = ThreadLocalRandom.current().nextBoolean();
                int departmentId = ThreadLocalRandom.current().nextInt(1, 4);
                insertQuery.append(String.format("(%s, '%s', %s, %s, %s)", i, "Person #" + i, age, active, departmentId));
                if (i != 10) {
                    insertQuery.append(",\n");
                }
            }
            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("Вставлено строк в таблицу Person: " + insertCount);
        }
    }

    private static void createTableDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                        create table department (
                        id bigint primary key,
                        name varchar(128) not null
                    )
                    """);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    private static void insertDataDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertQuery = new StringBuilder("insert into department(id, name) values\n");
            for (int i = 1; i <= 3; i++) {
                insertQuery.append(String.format("(%s, '%s')", i, "Department #" + i));
                if (i != 3) {
                    insertQuery.append(",\n");
                }
            }
            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("Вставлено строк в таблицу Department: " + insertCount);
        }
    }

    private static String getPersonDepartmentName(Connection connection, long personId) throws SQLException {
        String query = """
            select d.name
            from person p
            join department d on p.department_id = d.id
            where p.id = ?
            """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    throw new SQLException("Person с идентификатором " + personId + " не найден");
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке имени департамента: " + e.getMessage());
            throw e;
        }
    }

    private static Map<String, String> getPersonDepartments(Connection connection) throws SQLException {
        String query = """
            select p.name as person_name, d.name as department_name
            from person p
            join department d on p.department_id = d.id
            """;
        Map<String, String> personDepartments = new HashMap<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String personName = resultSet.getString("person_name");
                String departmentName = resultSet.getString("department_name");
                personDepartments.put(personName, departmentName);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке сопоставлений person -> department: " + e.getMessage());
            throw e;
        }
        return personDepartments;
    }
}

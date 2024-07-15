package lessons.lesson_04;

import org.hibernate.Session;

import java.util.List;

public class DataBaseThree {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void con() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            List<Magic> books = session.createQuery("FROM Magic", Magic.class).getResultList();
            books.forEach(b -> {
                System.out.println("Book of Magic : " + b);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

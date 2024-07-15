package lessons.lesson_04;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DataBaseFive {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void con() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            Transaction tx = session.beginTransaction();
            List<Magic> magicList = session.createQuery("FROM Magic", Magic.class).getResultList();
            magicList.forEach(m -> {
                session.delete(m);
            });
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

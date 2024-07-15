package lessons.lesson_04;

import org.hibernate.Session;
import org.hibernate.query.Query;


public class DataBaseFour {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void con() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
           String sql = "from Magic where id = :id";
           Query<Magic> query = session.createQuery(sql, Magic.class);
           query.setParameter("id", 4);
           Magic magic = query.getSingleResult();
           System.out.println(magic);
           magic.setAttBonus(100);
           magic.setName("Ярость");
           session.beginTransaction();
           session.update(magic);
           session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

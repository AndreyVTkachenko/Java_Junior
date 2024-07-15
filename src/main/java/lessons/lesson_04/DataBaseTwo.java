package lessons.lesson_04;

import org.hibernate.Session;

public class DataBaseTwo {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void con(){
        Connector connector = new Connector();
        Session session = connector.getSession();
        Magic magic = new Magic("Волшебная стрела", 10, 0, 0);
        session.beginTransaction();
        session.save(magic);
        magic = new Magic("Молния", 25, 0, 0);
        session.save(magic);
        magic = new Magic("Каменная кожа", 0, 0, 6);
        session.save(magic);
        magic = new Magic("Жажда крови", 0, 6, 0);
        session.save(magic);
        magic = new Magic("Жажда крови", 0, 6, 0);
        session.save(magic);
        magic = new Magic("Проклятие", 0, -3, 0);
        session.save(magic);
        magic = new Magic("Лечение", -30, 0, 0);
        session.save(magic);
        session.getTransaction().commit();
        session.close();
    }
}

package seminars.seminar_04;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import seminars.seminar_04.entity.Author;

public class JPAMain {
    // ORM - Object Relation Model
    // JPA - Jakarta Persistence API - абстракция, которая позволяет взаимодействовать с базой данных на языке объектов
    // Hibernate, EclipseLink - реализация JPA

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); // !!! иначе cfg.xml не прочитается

        try (SessionFactory sessionFactory = new Configuration().buildSessionFactory()) {
            // sessionFactory <-> connection

            withSession(sessionFactory);
            // withSessionCRUD(sessionFactory);
        }
    }

    private static void withSession(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            // session <-> statement

            Author author = session.find(Author.class, 1L);
            System.out.println("Author(1) = " + author);
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Author author = new Author();
            author.setId(22L);
            author.setName("Author #22");

            session.persist(author); // INSERT
            transaction.commit();
        }

        try (Session session = sessionFactory.openSession()) {
            Author toUpdate = session.find(Author.class, 22L);
            toUpdate.setName("UPDATED");

            Transaction transaction = session.beginTransaction();
            session.merge(toUpdate); // UPDATE
            transaction.commit();
        }

        try (Session session = sessionFactory.openSession()) {
            Author toDelete = session.find(Author.class, 1L);

            Transaction transaction = session.beginTransaction();
            session.remove(toDelete); // DELETE
            transaction.commit();
        }
    }
}

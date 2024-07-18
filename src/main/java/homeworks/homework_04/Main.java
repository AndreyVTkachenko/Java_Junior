package homeworks.homework_04;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user1 = new User();
        user1.setName("Иван Иванов");
        User user2 = new User();
        user2.setName("Пётр Петров");
        User user3 = new User();
        user3.setName("Сергей Сергеев");
        User user4 = new User();
        user4.setName("Андрей Андреев");
        User user5 = new User();
        user5.setName("Николай Николаев");

        session.save(user1);
        session.save(user2);
        session.save(user3);
        session.save(user4);
        session.save(user5);

        List<String> commentsList = Arrays.asList(
                "Отлично",
                "Хорошо",
                "Понравилось",
                "Отвратно",
                "Такое себе...",
                "Невероятно",
                "Ври больше!",
                "Куда смотрит модератор?..");

        List<String> postTitles = Arrays.asList(
                "Автомобили",
                "Фотографии",
                "Недвижимость",
                "Природа",
                "Политика",
                "Живопись",
                "Экономика");

        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            Post post = new Post();
            post.setTitle(postTitles.get(random.nextInt(postTitles.size())));
            post.setTimestamp(LocalDateTime.now());
            User postUser = getRandomUser(Arrays.asList(user1, user2, user3, user4, user5), random);
            post.setUser(postUser);

            int numComments = random.nextInt(5) + 2;
            for (int j = 0; j < numComments; j++) {
                PostComment comment = new PostComment();
                comment.setText(commentsList.get(random.nextInt(commentsList.size())));
                comment.setTimestamp(LocalDateTime.now());
                comment.setPost(post);
                User commentUser = getRandomUser(Arrays.asList(user1, user2, user3, user4, user5), random);
                comment.setUser(commentUser);
                post.getComments().add(comment);
            }
            session.save(post);
        }

        session.getTransaction().commit();

        loadPostComments(session, 1L);
        loadPostsByUserId(session, 3L);
        loadCommentsByUserId(session, 5L);

        session.close();
        sessionFactory.close();
    }

    private static User getRandomUser(List<User> users, Random random) {
        return users.get(random.nextInt(users.size()));
    }

    /**
     * Загрузить все комментарии публикации
     * @param session
     * @param postId
     */
    private static void loadPostComments(Session session, Long postId) {
        Post post = session.get(Post.class, postId);
        System.out.println("=======================================================================================");
        System.out.println("Комментарии для поста \"" + post.getTitle() + "\" (созданного пользователем " + post.getUser().getName() + "):");
        post.getComments().forEach(comment ->
                System.out.println("- " + comment.getText() + " (оставил " + comment.getUser().getName() + ")"));
        System.out.println("=======================================================================================");
    }

    /**
     * Загрузить все публикации по идентификатору юзера
     * @param session
     * @param userId
     */
    private static void loadPostsByUserId(Session session, Long userId) {
        User user = session.get(User.class, userId);
        List<Post> posts = session.createQuery("select p from Post p where p.user.id = :userId", Post.class)
                .setParameter("userId", userId)
                .getResultList();
        System.out.println("=======================================================================================");
        System.out.println("Публикации пользователя " + user.getName() + ":");
        posts.forEach(post -> System.out.println("- " + post.getTitle()));
        System.out.println("=======================================================================================");
    }

    /**
     * Загрузить все комментарии по идентификатору юзера
     * @param session
     * @param userId
     */
    private static void loadCommentsByUserId(Session session, Long userId) {
        User user = session.get(User.class, userId);
        List<PostComment> comments = session.createQuery("select c from PostComment c where c.user.id = :userId", PostComment.class)
                .setParameter("userId", userId)
                .getResultList();
        System.out.println("=======================================================================================");
        System.out.println("Комментарии пользователя " + user.getName() + ":");
        comments.forEach(comment -> System.out.println("- " + comment.getText() + " (для поста \"" + comment.getPost().getTitle() + "\")"));
        System.out.println("=======================================================================================");
    }
}

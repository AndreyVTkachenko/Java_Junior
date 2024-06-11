package seminars.seminar_01;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Tasks {

    private static <T> T getRandom(List<? extends T> items) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(randomIndex);
    }

    public static void main(String[] args) {

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tags.add("Tag #" + i);
        }

        List<String> author = List.of("Tolstoy", "Bulgakov", "Dostoevsky", "Gogol", "Martin", "Hugo", "Dumas");

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Book book = new Book();
            book.setName("Book #" + i);
            book.setAuthor(getRandom(author));

            LocalDate today = LocalDate.now();
            LocalDate randomDate = today.minusYears(ThreadLocalRandom.current().nextInt(100));
            book.setDate(randomDate);

            book.setPages(ThreadLocalRandom.current().nextInt(100, 2500));
            book.setPrice(ThreadLocalRandom.current().nextInt(1000, 100000) * 1.0);

            int tagsCount = ThreadLocalRandom.current().nextInt(6); // [0...5]
            List<String> bookTags = new ArrayList<>();
            for (int j = 0; j < tagsCount; j++) {
                bookTags.add(getRandom(tags));
            }
            book.setTags(bookTags);

            books.add(book);
        }

        System.out.println(findMostExpensiveBookOne(books));
        System.out.println(findMostExpensiveBookTwo(books));
        System.out.println(printBooksWithLessPages(books));
        System.out.println(findTags(books));
        System.out.println(groupByAuthor(books));
        Map<String, List<Book>> groupByAuthor = groupByAuthor(books);
        System.out.println(groupByAuthor.get("Bulgakov")); // Книги только Булгакова
        System.out.println(getHighPriceBook(books));
    }

    /**
     * Найти самую дорогую книгу
     */
    static Book findMostExpensiveBookOne(List<Book> books) {
        Optional<Book> first = books.stream()
                .sorted((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()))
                .findFirst();
        return first.get();
    }

    /**
     * Найти самую дорогую книгу
     */
    static Book findMostExpensiveBookTwo(List<Book> books) {
        Optional<Book> first = books.stream()
                .max((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
        Book book =  first.get();
        return book;
    }

    /**
     * Найти 5 самых коротких книг
     */
    static List<Book> printBooksWithLessPages(List<Book> books) {
        List<Book> list = books.stream()
                .sorted((o1, o2) -> Integer.compare(o1.getPages(), o2.getPages()))
                .limit(5)
                .toList();
        return list;
    }

    /**
     * Найти теги пяти самых "молодых" книг
     */
    static Set<String> findTags(List<Book> books) {
        return books.stream()
                .sorted((x, y) -> x.getDate().compareTo(y.getDate()))
                .limit(5) // Stream<Book>
                .flatMap(b -> b.getTags().stream()) // Stream<List<String>> -> Stream<String>
                .collect(Collectors.toSet());
    }

    /**
     * Cгруппировать книги по авторам
     */
    static Map<String, List<Book>> groupByAuthor(List<Book> books) {
        return books.stream()
                .collect(Collectors.groupingBy(item -> item.getAuthor()));
    }

    /**
     * Для каждого автора найти его самую дорогую книгу
     */
    static Map<String, Book> getHighPriceBook(List<Book> books) {
        return books.stream()
                .collect(Collectors.toMap(
                        b -> b.getAuthor(),
                        b -> b,
                        (a, b) -> {
                            if (a.getPrice() > b.getPrice()){
                                return a;
                            } else {
                                return b;
                            }
                        }
                ));
    }






    static class Book {

        private String name;
        private String author;
        private LocalDate date;
        private int pages;
        private double price;
        private List<String> tags;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", author='" + author + '\'' +
                    ", date=" + date +
                    ", pages=" + pages +
                    ", price=" + price +
                    ", tags=" + tags +
                    '}';
        }
    }
}

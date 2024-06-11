package seminars.seminar_01;

public class Main {

    public static void main(String[] args) {

    }

    /*
    Остаётся функциональным интерфейсом потому что абстрактный метод остаётся один.
    Дефолтных и статических методов может быть сколько угодно
     */
    @FunctionalInterface
    interface Runnable {
        void run();
        default void doRun() {
            run();
        }
    }
}

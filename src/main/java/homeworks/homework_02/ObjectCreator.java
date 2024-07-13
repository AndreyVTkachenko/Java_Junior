package homeworks.homework_02;

import java.lang.reflect.Constructor;

public class ObjectCreator {

    public static <T> T createObj(Class<T> tClass) {
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T obj = constructor.newInstance();
            RandomDateAnnotationProcessor.processAnnotations(obj);
            return obj;
        } catch (Exception e) {
            System.err.println("Ничего не получилось: " + e.getMessage());
            return null; // или throw new IllegalStateException
        }
    }
}


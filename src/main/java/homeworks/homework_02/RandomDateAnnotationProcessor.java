package homeworks.homework_02;

import java.lang.reflect.Field;
import java.util.Date;

public class RandomDateAnnotationProcessor {

    public static void processAnnotations(Object obj) {
        java.util.Random random = new java.util.Random();
        Class<?> objClass = obj.getClass();

        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomDate.class) && field.getType().isAssignableFrom(Date.class)) {
                RandomDate annotation = field.getAnnotation(RandomDate.class);
                long max = annotation.max();
                long min = annotation.min();
                if (min >= max) {
                    throw new IllegalArgumentException("min должно быть меньше, чем max");
                }
                long randomTime = min + (long) (random.nextDouble() * (max - min));
                try {
                    field.setAccessible(true);
                    field.set(obj, new Date(randomTime));
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            }
        }
    }
}

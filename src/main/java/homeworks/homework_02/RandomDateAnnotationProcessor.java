package homeworks.homework_02;

import java.lang.reflect.Field;

public class RandomDateAnnotationProcessor {

    public static void processAnnotation(Object obj) {
        java.util.Random random = new java.util.Random();
        Class<?> objClass = obj.getClass();

        Long startDate = null;

        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomDate.class) && field.getType().isAssignableFrom(long.class)) {
                RandomDate annotation = field.getAnnotation(RandomDate.class);
                long min = annotation.min();
                long max = annotation.max();

                if (field.getName().equals("expirationDate") && startDate != null) {
                    min = startDate + 1L;
                }

                long randomValue = min + (long) (random.nextDouble() * (max - min));
                try {
                    field.setAccessible(true);
                    field.set(obj, randomValue);
                    if (field.getName().equals("startDate")) {
                        startDate = randomValue;
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            }
        }
    }
}

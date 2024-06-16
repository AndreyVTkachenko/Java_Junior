package seminars.seminar_02.lib;

import java.lang.reflect.Field;

public class RandomAnnotationProcessor {

    public static void processAnnotation(Object obj) {
        // найти все поля класса над которыми стоит аннотация @Random
        // вставить туда случайное число в диапазоне {0, 100)
        java.util.Random random = new java.util.Random();
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
//            obj instanceOf Person =
//            = AnnotationsMain.Person.class.isInstance(obj); =
//            = obj.getClass().isAssignableFrom(AnnotationsMain.Person.class);

            if (field.isAnnotationPresent(Random.class) && field.getType().isAssignableFrom(int.class)) {
                Random annotation = field.getAnnotation(Random.class);
                int max = annotation.max();
                int min = annotation.min();
                try {
                    field.setAccessible(true); // чтобы можно было изменить поля с модификатором final
                    field.set(obj, random.nextInt(min, max));
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            }
        }
    }
}

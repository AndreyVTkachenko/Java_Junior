package lessons.lesson_03;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream("serialized_object");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String loadedString = (String) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(loadedString);
    }
}

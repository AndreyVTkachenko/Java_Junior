package lessons.lesson_03;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main1 {

    public static void main(String[] args) throws IOException {

        String str = "Всем привет!";
        FileOutputStream fileOutputStream = new FileOutputStream("serialized_object");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(str);
        objectOutputStream.close();

    }
}

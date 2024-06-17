package lessons.lesson_03;

import java.io.*;

public class Main5 {

    public static void main(String[] args) throws IOException {

        MyFCs myFCs = new MyFCs("Ivanov", "Ivan", "Ivanovich");
        serialObj(myFCs, "serialized_object");
    }

    public static void serialObj(Object o, String file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new
                ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
    }

    public static Object deSerialObj(String file) throws IOException,
            ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return objectInputStream.readObject();
    }
}

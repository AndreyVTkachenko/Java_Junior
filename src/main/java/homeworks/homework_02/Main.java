package homeworks.homework_02;

public class Main {

    public static void main(String[] args) {

        Event event = ObjectCreator.createObj(Event.class);

//        System.out.println(event);

        System.out.println("Дата начала события: " + event.getStartDateString());
        System.out.println("Дата окончания события: " + event.getExpirationDateString());
    }
}

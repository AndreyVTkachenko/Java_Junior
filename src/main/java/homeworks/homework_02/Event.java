package homeworks.homework_02;

import java.util.Date;

public class Event {

    @RandomDate
    private long startDate;

    @RandomDate
    private long expirationDate;

    public long getStartDate() {
        return startDate;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public String getStartDateString() {
        return new Date(startDate).toString();
    }

    public String getExpirationDateString() {
        return new Date(expirationDate).toString();
    }

    @Override
    public String toString() {
        return "Событие{" +
                "дата начала: " + getStartDateString() +
                ", дата окончания: " + getExpirationDateString() +
                '}';
    }
}

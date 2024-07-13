package homeworks.homework_02;

import java.util.Date;

public class Event {
    @RandomDate(min = 1704067200000L, max = 1735689600000L)
    private Date randomDate;

    @Override
    public String toString() {
        return "Event{" +
                "randomDate=" + randomDate +
                '}';
    }
}

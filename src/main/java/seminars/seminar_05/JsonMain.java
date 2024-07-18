package seminars.seminar_05;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.List;

public class JsonMain {

    public static void main(String[] args) throws JsonProcessingException {
        ListResponse response = new ListResponse();

        User user1 = new User();
        user1.setLogin("anonymous");

        User user2 = new User();
        user2.setLogin("nagibator");

        User user3 = new User();
        user3.setLogin("admin");

        response.setUsers(List.of(user1, user2, user3));

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String s = writer.writeValueAsString(response);
        System.out.println(s);
    }

}
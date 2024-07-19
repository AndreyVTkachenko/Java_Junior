package homeworks.homework_05;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UsersResponse extends AbstractResponse {
    @JsonProperty
    private List<String> users;

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}

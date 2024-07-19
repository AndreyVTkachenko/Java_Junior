package homeworks.homework_05;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest extends AbstractRequest {
    public static final String TYPE = "LoginRequest";

    @JsonProperty
    private String login;

    public LoginRequest() {
        super(TYPE);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

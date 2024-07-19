package homeworks.homework_05;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse extends AbstractResponse {
    @JsonProperty
    private boolean connected;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}

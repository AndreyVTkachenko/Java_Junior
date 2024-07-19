package homeworks.homework_05;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BroadcastMessageRequest extends AbstractRequest {
    public static final String TYPE = "BroadcastMessageRequest";

    @JsonProperty
    private String message;

    @JsonProperty
    private String sender;

    public BroadcastMessageRequest() {
        super(TYPE);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}

package homeworks.homework_05;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageRequest extends AbstractRequest {
    public static final String TYPE = "SendMessageRequest";

    @JsonProperty
    private String recipient;

    @JsonProperty
    private String message;

    @JsonProperty
    private String sender;

    public SendMessageRequest() {
        super(TYPE);
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
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

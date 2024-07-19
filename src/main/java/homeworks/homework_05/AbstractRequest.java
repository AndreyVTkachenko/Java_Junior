package homeworks.homework_05;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginRequest.class, name = "LoginRequest"),
        @JsonSubTypes.Type(value = SendMessageRequest.class, name = "SendMessageRequest"),
        @JsonSubTypes.Type(value = BroadcastMessageRequest.class, name = "BroadcastMessageRequest"),
        @JsonSubTypes.Type(value = UsersRequest.class, name = "UsersRequest"),
        @JsonSubTypes.Type(value = DisconnectRequest.class, name = "DisconnectRequest")
})
public abstract class AbstractRequest {
    private String type;

    public AbstractRequest() {}

    public AbstractRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

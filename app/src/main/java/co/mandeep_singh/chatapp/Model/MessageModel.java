package co.mandeep_singh.chatapp.Model;

public class MessageModel {
    private String text, sender, conversationId, _id, createdAt;

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public MessageModel(String text, String sender, String conversationId, String _id, String createdAt) {
        this.text = text;
        this.sender = sender;
        this.conversationId = conversationId;
        this._id = _id;
        this.createdAt = createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

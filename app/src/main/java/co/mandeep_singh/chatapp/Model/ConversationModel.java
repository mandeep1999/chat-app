package co.mandeep_singh.chatapp.Model;

public class ConversationModel {
    private String toID, fromID;

    public String getToID() {
        return toID;
    }

    public ConversationModel(String toID, String fromID) {
        this.toID = toID;
        this.fromID = fromID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }
}

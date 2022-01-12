package co.mandeep_singh.chatapp.Model;

public class ConversationModel {
    private String toID, fromID, business, socket1, socket2, name1, name2;

    public String getToID() {
        return toID;
    }


    public ConversationModel(String toID, String fromID, String business, String socket1, String socket2, String name1, String name2) {
        this.toID = toID;
        this.fromID = fromID;
        this.business = business;
        this.socket1 = socket1;
        this.socket2 = socket2;
        this.name1 = name1;
        this.name2 = name2;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getSocket1() {
        return socket1;
    }

    public void setSocket1(String socket1) {
        this.socket1 = socket1;
    }

    public String getSocket2() {
        return socket2;
    }

    public void setSocket2(String socket2) {
        this.socket2 = socket2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
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

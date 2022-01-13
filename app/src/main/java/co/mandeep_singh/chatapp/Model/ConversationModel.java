package co.mandeep_singh.chatapp.Model;

public class ConversationModel {
    private String _id,jobType,name,createdAt, receiverId;

    public ConversationModel(String _id, String jobType, String name, String createdAt, String receiverId) {
        this._id = _id;
        this.jobType = jobType;
        this.name = name;
        this.createdAt = createdAt;
        this.receiverId = receiverId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}

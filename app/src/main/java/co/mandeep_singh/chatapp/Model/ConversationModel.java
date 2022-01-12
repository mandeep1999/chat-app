package co.mandeep_singh.chatapp.Model;

public class ConversationModel {
    private String _id,userId1, userId2, jobType, name1, name2, createdAt;

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ConversationModel(String _id,String userId1, String userId2, String jobType, String name1, String name2, String createdAt) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.jobType = jobType;
        this.name1 = name1;
        this.name2 = name2;
        this.createdAt = createdAt;
        this._id = _id;
    }
}

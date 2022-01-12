package co.mandeep_singh.chatapp.Model;

public class JobModel {

    public JobModel(String _id, String name, String jobType, String location, String salary, String lastdate, String userId) {
        this._id = _id;
        this.name = name;
        this.jobType = jobType;
        this.location = location;
        this.salary = salary;
        this.lastdate = lastdate;
        this.userId = userId;
    }

    private String _id, name, jobType, location, salary, lastdate, userId;

    public String get_id() {
        return _id;
    }

    public String getJobType() {
        return jobType;
    }

    public String getLocation() {
        return location;
    }

    public String getSalary() {
        return salary;
    }

    public String getLastdate() {
        return lastdate;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}

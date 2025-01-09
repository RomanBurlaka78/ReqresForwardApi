package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleUserPojo {

    private String name;
    private String job;
    private String id;
    private String createdAt;

    public SingleUserPojo() {}

    public SingleUserPojo(String name, String job, String id, String createdAt) {
        this.name = name;
        this.job = job;
        this.id = id;
        this.createdAt = createdAt;
    }

    public SingleUserPojo(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public SingleUserPojo(String name, String job, String createdAt) {
        this.name = name;
        this.job = job;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SingleUserPojo{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }


}

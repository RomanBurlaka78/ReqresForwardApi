package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSingleUserPojo {

    private String name;
    private String job;
    private String id;
    private String createdAt;

    public ResponseSingleUserPojo() {}

    public ResponseSingleUserPojo(String name, String job, String id, String createdAt) {
        this.name = name;
        this.job = job;
        this.id = id;
        this.createdAt = createdAt;
    }

    public ResponseSingleUserPojo(String name, String job) {
        this.name = name;
        this.job = job;
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

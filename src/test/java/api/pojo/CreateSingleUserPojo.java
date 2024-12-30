package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CreateSingleUserPojo {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String name;
    private String job;

    public CreateSingleUserPojo() {}

    public CreateSingleUserPojo(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}

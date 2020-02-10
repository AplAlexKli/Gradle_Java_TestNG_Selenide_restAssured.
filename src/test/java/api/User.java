package api;

import java.util.Objects;

/**
 * Created by admin on 10.02.2020.
 *
 * Для маппинга json из request\response
 * с объектами этого класса
 */
public class User {

    private String name;
    private String job;
    private String createdAt;
    private int id;

    public User(){
    }

    User(String name, String job) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString(){
        return "User #" + this.id + ": " + this.name
                + ", " + this.job + "; " + this.createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        else if (!(o instanceof User))
            return false;
        else {
            User user = (User) o;
            return Objects.equals(name, user.name)
                    && Objects.equals(job, user.job);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, job);
    }
}


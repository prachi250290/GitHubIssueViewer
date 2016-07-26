package com.test.githubissueviewer.myapplication;
import com.google.gson.annotations.SerializedName;

/**
 * Created by prachi on 25/07/16.
 */
public class Issue {

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String description;

    @SerializedName("number")
    private int number;

    @SerializedName("user")
    private IssueReporter reporter;

    @SerializedName("state")
    private String status;

    @SerializedName("updated_at")
    private String updatedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueReporter getReporter() {
        return reporter;
    }

    public void setReporter(IssueReporter reporterName) {
        this.reporter = reporterName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}

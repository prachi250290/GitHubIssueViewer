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

    @SerializedName("user")
    private IssueReporter reporter;

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
}

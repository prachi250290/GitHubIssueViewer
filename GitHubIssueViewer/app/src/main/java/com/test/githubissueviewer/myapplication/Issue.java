package com.test.githubissueviewer.myapplication;

/**
 * Created by prachi on 25/07/16.
 */
public class Issue {

    private String title;
    private String description;
    private String reporterName;

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

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
}

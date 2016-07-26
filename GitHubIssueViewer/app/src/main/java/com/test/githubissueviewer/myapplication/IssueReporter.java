package com.test.githubissueviewer.myapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prachi on 25/07/16.
 */
public class IssueReporter {
    @SerializedName("login")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

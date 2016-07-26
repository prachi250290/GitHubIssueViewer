package com.test.githubissueviewer.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IssueDescriptionActivity extends AppCompatActivity {

    private TextView title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_description);

        title = (TextView) findViewById(R.id.issue_description_title_textview);
        description = (TextView) findViewById(R.id.issue_description_detail_textview);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title.setText(extras.getString(Constants.INTENT_KEY_ISSUE_TITLE));
            description.setText(extras.getString(Constants.INTENT_KEY_ISSUE_DESCRIPTION));
        }
    }
}

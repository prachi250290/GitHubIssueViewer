package com.test.githubissueviewer.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class IssueDescriptionActivity extends AppCompatActivity {

    private TextView title, description, reporterName;
    private ImageView reporterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_description);

        title = (TextView) findViewById(R.id.issue_description_title_textview);
        description = (TextView) findViewById(R.id.issue_description_detail_textview);
        reporterName = (TextView) findViewById(R.id.issue_description_reporter_name_textview);
        reporterImage = (ImageView) findViewById(R.id.issue_description_reporter_image_view);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title.setText(extras.getString(Constants.INTENT_KEY_ISSUE_TITLE));
            description.setText(extras.getString(Constants.INTENT_KEY_ISSUE_DESCRIPTION));
            reporterName.setText(extras.getString(Constants.INTENT_KEY_REPORTER_NAME));
            Picasso.with(this).load(extras.getString(Constants.INTENT_KEY_REPORTER_AVATAR_URL)).into(reporterImage);

        }
    }
}

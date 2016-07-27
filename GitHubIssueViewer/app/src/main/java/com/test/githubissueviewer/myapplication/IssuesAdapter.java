package com.test.githubissueviewer.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by prachi on 25/07/16.
 */
public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssueViewHolder> {

    private List<Issue> issueList;
    private Context context;
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public IssuesAdapter(List<Issue> issueList, Context context) {
        this.issueList = issueList;
        this.context =  context;
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.issue_list_row, parent, false);

        return new IssueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {
        Issue issue = issueList.get(position);
        holder.title.setText(issue.getTitle());
        holder.reporterName.setText(issue.getReporter().getName());
        holder.status.setText(issue.getStatus());
        Picasso.with(context).load(issue.getReporter().getAvatarURL()).into(holder.reporterImage);

        if(issue.getUpdatedAt()!= null && issue.getUpdatedAt()!= "") {
            holder.updatedTime.setText(DateUtils.getRelativeTimeSpanString(getTimeInMillis(issue.getUpdatedAt()),System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS));

        }
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder {
        public TextView title, reporterName, updatedTime, status;
        public ImageView reporterImage;

        public IssueViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.issue_title_textview);
            reporterName = (TextView) view.findViewById(R.id.reporter_name_textview);
            updatedTime = (TextView) view.findViewById(R.id.updated_time_textview);
            status =(TextView) view.findViewById(R.id.issue_status_textview);
            reporterImage = (ImageView) view.findViewById(R.id.reporter_image_view);
        }
    }

    private long getTimeInMillis (String dateString) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {

            Date date = formatter.parse(dateString);
            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

}

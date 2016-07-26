package com.test.githubissueviewer.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by prachi on 25/07/16.
 */
public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssueViewHolder> {

    private List<Issue> issueList;
    private Context context;

    public class IssueViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, reporterName;

        public IssueViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.issue_title_textview);
            description = (TextView) view.findViewById(R.id.issue_description_textview);
            reporterName = (TextView) view.findViewById(R.id.reporter_name_textview);
        }
    }


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
        holder.description.setText(issue.getDescription());
        holder.reporterName.setText(this.context.getString(R.string.posted_by) + issue.getReporter().getName());
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }
}

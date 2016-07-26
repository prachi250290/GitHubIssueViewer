package com.test.githubissueviewer.myapplication;

import android.app.Activity;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private List<Issue> issueList = new ArrayList<>();
    private RecyclerView recyclerView;
    private IssuesAdapter issueAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.issue_list_view);

        issueAdapter = new IssuesAdapter(issueList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(issueAdapter);

        fetchIssues();
    }

    private void fetchIssues() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put(Constants.QUERY_PARAM_KEY_SORT, Constants.QUERY_PARAM_VALUE_UPDATED); //Sort by most recently updated issue
        //By default, the 'state' is 'open' so it will fetch all the 'open' issues

        Call<List<Issue>> call = apiService.getIssues(queryMap);
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>>call, Response<List<Issue>> response) {

                issueList = response.body();
                issueAdapter.setIssueList(issueList);
                System.out.println(issueList);
                issueAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Issue>>call, Throwable t) {
                // Log error here since request failed
                System.out.println(t);
            }
        });
    }
}

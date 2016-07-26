package com.test.githubissueviewer.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;

public class MainActivity extends Activity {

    LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private IssuesAdapter issueAdapter;
    private List<Issue> issueList = new ArrayList<>();

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int pageNumber = 0;
    private static final int PAGE_SIZE = 30;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.issue_list_view);

        issueAdapter = new IssuesAdapter(issueList, this);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(issueAdapter);
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                openIssueDescriptionScreen(issueList.get(position));
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Utility.showProgressDialog(this, getString(R.string.progress_dialog_msg));
        fetchIssues();

    }

    private void openIssueDescriptionScreen(Issue issue) {
        Intent issueDescriptionIntent = new Intent(MainActivity.this, IssueDescriptionActivity.class);
        issueDescriptionIntent.putExtra(Constants.INTENT_KEY_ISSUE_TITLE, issue.getTitle());
        issueDescriptionIntent.putExtra(Constants.INTENT_KEY_ISSUE_DESCRIPTION, issue.getDescription());
        startActivity(issueDescriptionIntent);
    }

    /*
    * Fetches issues from the Server
    * */
    private void fetchIssues() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        pageNumber++;
        isLoading = true;

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put(Constants.QUERY_PARAM_KEY_SORT, Constants.QUERY_PARAM_VALUE_UPDATED); //Sort by most recently updated issue
        queryMap.put(Constants.QUERY_PARAM_KEY_PAGE, String.valueOf(pageNumber));
        //By default, the 'state' is 'open' so it will fetch all the 'open' issues

        Call<List<Issue>> call = apiService.getIssues(queryMap);
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                Utility.hideProgressDialog();
                isLoading = false;
                if (response != null) {
                    if(response.isSuccessful()) {
                        List<Issue> issues = response.body();

                        if (issues != null) {

                            //Update the issue list
                            issueList.addAll(issues);
                            issueAdapter.setIssueList(issueList);
                            issueAdapter.notifyDataSetChanged();
                            if (issues.size() < PAGE_SIZE) {
                                isLastPage = true;
                            }
                        }
                    }
                    else {
                        //Handle failure
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }



    /**** RECYCLER VIEW METHODS ****/

    private RecyclerView.OnScrollListener
            recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView,
                                         int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    fetchIssues();
                }
            }
        }
    };





    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}

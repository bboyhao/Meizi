package com.example.bboyhao.meizi;

import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        initView();
        setListener();

        new DataGetter().execute("http://gank.io/api/data/福利/10/1");
    }
    private void initView() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipeFresh);



    }
    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                new DataGetter().execute("http://gank.io/api/data/福利/10/1");
            }
        });
    }

    private class DataGetter extends AsyncTask<String, Integer, String>{
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}

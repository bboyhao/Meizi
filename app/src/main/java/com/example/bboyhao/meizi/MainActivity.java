package com.example.bboyhao.meizi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridAdapter myAdapter;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static RecyclerView recyclerView;
    private int page = 1;
    private List<Meizi> meizis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        initView();
        setListener();

        new DataGetter().execute("http://gank.io/api/data/福利/10/1");
        page++;
    }


    private void initView() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_layout);
        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipeFresh);
        //TypedValue.applyDimension()方法的功能就是把非标准尺寸转换成标准尺寸
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DataGetter().execute("http://gank.io/api/data/福利/10/"+page);
                page++;
            }
        });
    }

    private class DataGetter extends AsyncTask<String, Integer, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            return MyOkHttp.get(strings[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject;
                Gson gson = new Gson();
                String jsonData = null;
                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (meizis == null || meizis.size() == 0) {
                    //what is TypeToken?
                    meizis = gson.fromJson(jsonData, new TypeToken<List<Meizi>>() {
                    }.getType());
                    Meizi pages = new Meizi();
                    meizis.add(pages);
                } else {
                    List<Meizi> more = gson.fromJson(jsonData, new TypeToken<List<Meizi>>() {
                    }.getType());
                    meizis.addAll(0, more);

                }
                if (myAdapter == null) {
                    myAdapter = new GridAdapter(MainActivity.this, meizis);
                    recyclerView.setAdapter(myAdapter);

                    myAdapter.setOnItemClickListener(new GridAdapter.OnRecyclerViewClickListener() {
                        @Override
                        public void onItemClick(View view) {
//                            Snackbar.make(coordinatorLayout, "你还敢点进去看？", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MeiziActivity.class);
                            ImageButton btn = view.findViewById(R.id.meizi_photo);
                        }

                        @Override
                        public void onItemLongClick(View view) {

                        }
                    });
                }
                else {
                    myAdapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}

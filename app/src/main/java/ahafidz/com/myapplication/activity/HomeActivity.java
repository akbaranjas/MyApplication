package ahafidz.com.myapplication.activity;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.adapter.HomeAdapter;
import ahafidz.com.myapplication.bean.Record;
import ahafidz.com.myapplication.presenter.HomePresenter;
import ahafidz.com.myapplication.presenter.HomePresenterImpl;
import ahafidz.com.myapplication.util.Utils;
import ahafidz.com.myapplication.view.HomeView;

public class HomeActivity extends AppCompatActivity implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;
    private HomePresenter presenter;
    private HomeAdapter adapter;
    private List<Record> record = new ArrayList<>();
    private ConstraintLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = findViewById(R.id.progress_bar_home);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.home_recycler_view);
        swipeRefresh = findViewById(R.id.home_swipe_refresh);
        view = findViewById(R.id.home_layout);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setOnRefreshListener(this);

        adapter = new HomeAdapter(this,record);
        recyclerView.setAdapter(adapter);

        presenter = new HomePresenterImpl(this);

        presenter.getData();

    }

    @Override
    public void showLoading() {
        Utils.showProgressBar(progressBar);
    }

    @Override
    public void hideLoading() {
        Utils.showProgressBar(progressBar);
    }

    @Override
    public void showList(List<Record> records) {
        swipeRefresh.setRefreshing(false);
        record.clear();
        record.addAll(records);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        presenter.getData();
    }
}

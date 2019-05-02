package ahafidz.com.myapplication.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.adapter.HomeAdapter;
import ahafidz.com.myapplication.bean.CreateDataRequest;
import ahafidz.com.myapplication.bean.Record;
import ahafidz.com.myapplication.dao.RealmHelper;
import ahafidz.com.myapplication.listener.ConfirmDialogListener;
import ahafidz.com.myapplication.listener.OnSelectListListener;
import ahafidz.com.myapplication.presenter.HomePresenter;
import ahafidz.com.myapplication.presenter.HomePresenterImpl;
import ahafidz.com.myapplication.util.Constant;
import ahafidz.com.myapplication.util.Utils;
import ahafidz.com.myapplication.view.HomeView;

public class HomeActivity extends AppCompatActivity implements HomeView, SwipeRefreshLayout.OnRefreshListener,
        ConfirmDialogListener, Validator.ValidationListener, OnSelectListListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;
    private HomePresenter presenter;
    private HomeAdapter adapter;
    private List<Record> record = new ArrayList<>();
    private ConstraintLayout view;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private Validator validator;
    @NotEmpty
    private EditText editKode, editNama, editKeterangan;
    private Button btnSubmitDialog, btnCancelDialog;
    private TextView textTitle;
    private String titles;
    private CreateDataRequest dataRequest = null;

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

        validator = new Validator(this);
        validator.setValidationListener(this);

        adapter = new HomeAdapter(this,record, this);
        recyclerView.setAdapter(adapter);

//        RealmHelper helper = new RealmHelper();
//        jwt = helper.getJwt();
//        Log.d("JWT", jwt);

        presenter = new HomePresenterImpl(this);

        presenter.getData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForm(Constant.TITLE_INSERT, null);
            }
        });

    }

    @Override
    public void showLoading() {
        Utils.showProgressBar(progressBar);
    }

    @Override
    public void hideLoading() {
        Utils.hideProgressBar(progressBar);
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
    public void logout() {
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void onSuccess(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        presenter.getData();
    }

    @Override
    public void onRefresh() {
        presenter.getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_logout:
                Utils.showConfirmDialog(this,this, Constant.TITLE_LOGOUT);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void confirmListener(String title)
    {
        if(title.equals(Constant.TITLE_LOGOUT)) {
            presenter.logout();
        }else if(title.equals(Constant.TITLE_DELETE)){
            presenter.deleteData(dataRequest);
        }
    }

    private void dialogForm(final String title, final CreateDataRequest createDataRequest) {
        dialog = new AlertDialog.Builder(this).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.input_dialog, null);
        dialog.setView(dialogView);

        textTitle = dialogView.findViewById(R.id.txt_dialog_title);
        editKode = dialogView.findViewById(R.id.text_kode);
        editNama = dialogView.findViewById(R.id.text_nama);
        editKeterangan = dialogView.findViewById(R.id.text_keterangan);
        btnSubmitDialog = dialogView.findViewById(R.id.btn_submit);
        btnCancelDialog = dialogView.findViewById(R.id.btn_cancel);

        textTitle.setText(title);
        titles = title;

        dataRequest = createDataRequest;
        if(title.equals(Constant.TITLE_UPDATE)){
            editKode.setText(createDataRequest.getKode());
            editNama.setText(createDataRequest.getNama());
            editKeterangan.setText(createDataRequest.getKeterangan());
        }

        btnSubmitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    @Override
    public void onValidationSucceeded() {
        CreateDataRequest request = new CreateDataRequest();
        request.setKode(editKode.getText().toString().trim());
        request.setKeterangan(editKeterangan.getText().toString().trim());
        request.setNama(editNama.getText().toString().trim());

        if(titles.equals(Constant.TITLE_INSERT)){
            dialog.dismiss();
            presenter.insertData(request);
        }else if(titles.equals((Constant.TITLE_UPDATE))){
            dialog.dismiss();
            request.setId(dataRequest.getId());
            presenter.updateData(request);
        }
        presenter.getData();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onSelect(String type, Record record) {
        CreateDataRequest request = new CreateDataRequest();
        RealmHelper helper = new RealmHelper();
        if(type.equals(Constant.TITLE_UPDATE)){
            request.setId(record.getId());
            request.setNama(record.getNama());
            request.setKeterangan(record.getKeterangan());
            request.setKode(record.getKode());
            request.setJwt(helper.getJwt());
            dialogForm(Constant.TITLE_UPDATE, request);
        }else if(type.equals(Constant.TITLE_PRINT)){

        }else if(type.equals(Constant.TITLE_DELETE)){
            dataRequest.setJwt(helper.getJwt());
            dataRequest.setId(record.getId());
            Utils.showConfirmDialog(this,this, Constant.TITLE_DELETE);
        }
    }
}

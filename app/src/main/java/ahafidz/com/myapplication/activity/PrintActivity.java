package ahafidz.com.myapplication.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.adapter.HomeAdapter;
import ahafidz.com.myapplication.bean.Record;
import ahafidz.com.myapplication.listener.ConfirmDialogListener;
import ahafidz.com.myapplication.listener.OnSelectListListener;
import ahafidz.com.myapplication.util.Constant;
import ahafidz.com.myapplication.util.PrinterCommands;
import ahafidz.com.myapplication.util.RecyclerItemClickListener;
import ahafidz.com.myapplication.util.Utils;

public class PrintActivity extends AppCompatActivity implements ConfirmDialogListener, OnSelectListListener {

    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    boolean isMultiSelect = false;
    private List<Record> record = new ArrayList<>();
    private List<Record> selected_record = new ArrayList<>();
    private ConstraintLayout view;
    private Button btnPrint;
    private ActionMode mActionMode;
    private Menu context_menu;
    private TextView tv_printStatus;
    private ProgressDialog mProgressDialog = null;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        recyclerView = findViewById(R.id.recycler_view_print);
        btnPrint = findViewById(R.id.btn_print);
        view = findViewById(R.id.print_layout);
        tv_printStatus = findViewById(R.id.printer_status);

        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Print");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent i = getIntent();
        record = (List<Record>) i.getSerializableExtra("LIST");

        adapter = new HomeAdapter(1,this,record, this.selected_record, this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multi_select(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    selected_record = new ArrayList<>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);

            }
        }));

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(record.size() == 0){
                    Snackbar.make(view, "Tidak ada data untuk di cetak", Snackbar.LENGTH_LONG).show();
                }else{
                    printBill();
                }
            }
        });


    }

    @Override
    public void confirmListener(String title) {
        if(title.equals(Constant.TITLE_DELETE)){
            if(selected_record.size()>0)
            {
                for(int i=0;i<selected_record.size();i++)
                    record.remove(selected_record.get(i));

                adapter.notifyDataSetChanged();

                if (mActionMode != null) {
                    mActionMode.finish();
                }
            }
        }
    }

    @Override
    public void onSelect(String type, Record record) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (selected_record.contains(record.get(position)))
                selected_record.remove(record.get(position));
            else
                selected_record.add(record.get(position));

            if (selected_record.size() > 0)
                mActionMode.setTitle("" + selected_record.size());
            else
                mActionMode.setTitle("Pilih data");

            refreshAdapter();

        }
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.multiselect_menu_delete, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    Utils.showConfirmDialog(PrintActivity.this,PrintActivity.this, Constant.TITLE_DELETE);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            selected_record = new ArrayList<>();
            refreshAdapter();
        }
    };

    void refreshAdapter()
    {
        adapter.selected_records=selected_record;
        adapter.records=record;
        adapter.notifyDataSetChanged();
    }

    private class doPrintBill extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(PrintActivity.this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Printing. . .");
            Utils.showDialog(mProgressDialog);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            PrinterCommands commands = new PrinterCommands();
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();
                byte[] printformat = new byte[]{0x1B,0x21,0x03};
                outputStream.write(printformat);
                for (int i = 0; i < record.size();i++){
                    commands.printCustom(outputStream, "ID : " + record.get(i).getId(),0,0);
                    commands.printCustom(outputStream, "Kode : " + record.get(i).getKode(),0,0);
                    commands.printCustom(outputStream, "Nama : ",0,0);
                    commands.printCustom(outputStream, record.get(i).getNama(),0,0);
                    commands.printCustom(outputStream, "Keterangan : ",0,0);
                    commands.printCustom(outputStream, record.get(i).getKeterangan(),0,0);
                    commands.printCustom(outputStream, "Dibuat Oleh : ",0,0);
                    commands.printCustom(outputStream, record.get(i).getDibuatOleh(),0,0);
                    commands.printCustom(outputStream, "Dirubah Oleh : ",0,0);
                    commands.printCustom(outputStream, record.get(i).getDibuatOleh(),0,0);
                    commands.printCustom(outputStream, "Tanggal Dibuat : ",0,0);
                    commands.printCustom(outputStream, record.get(i).getTglDibuat(),0,0);
                    commands.printCustom(outputStream, "Tanggal Dirubah : ",0,0);
                    commands.printCustom(outputStream, record.get(i).getTglDirubah(),0,0);
                    commands.printNewLine(outputStream);
                }

                System.out.println(outputStream);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Utils.hideDialog(mProgressDialog);
        }
    }

    protected void printBill(){
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), BTDeviceListActivity.class);
            this.startActivityForResult(BTIntent, BTDeviceListActivity.REQUEST_CONNECT_BT);
        }else{
            new doPrintBill().execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = BTDeviceListActivity.getSocket();
            if(resultCode == RESULT_OK && requestCode == BTDeviceListActivity.REQUEST_CONNECT_BT) {
                Bundle bundle = data.getExtras();
                tv_printStatus.setText("Connected to " + bundle.getString("printer_name"));
                tv_printStatus.setTextColor(Color.GREEN);
                tv_printStatus.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

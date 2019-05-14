package ahafidz.com.myapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.bean.Record;
import ahafidz.com.myapplication.bean.UploadResponse;
import ahafidz.com.myapplication.presenter.UploadPresenter;
import ahafidz.com.myapplication.presenter.UploadPresenterImpl;
import ahafidz.com.myapplication.util.FileUtils;
import ahafidz.com.myapplication.util.Utils;
import ahafidz.com.myapplication.view.UploadView;
import okhttp3.RequestBody;

public class UploadActivity extends AppCompatActivity implements UploadView {
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;
    private Uri uri;
    ProgressDialog progressDialog = null;
    View view;
    Spinner spinnerCategory;
    EditText editKeterangan;
    Button btnUpload, btnChoose;
    ImageView imgThumb;
    UploadPresenter presenter;
    TextView txtKode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        spinnerCategory = findViewById(R.id.spinner_category);
        editKeterangan = findViewById(R.id.input_keterangan);
        btnUpload = findViewById(R.id.btn_upload);
        btnChoose = findViewById(R.id.btn_choose);
        imgThumb = findViewById(R.id.img_thumb);
        txtKode = findViewById(R.id.txt_kode);
        progressDialog = new ProgressDialog(UploadActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Uploading...");
        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("UPLOAD");

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Record record = (Record) parent.getItemAtPosition(position);
                txtKode.setText(record.getKode());
                Toast.makeText(UploadActivity.this, "KODE : " + record.getKode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri != null) {
                    File file = FileUtils.getFile(UploadActivity.this, uri);
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("keterangan", Utils.createPartFromString(editKeterangan.getText().toString().trim()));
                    map.put("kategori", Utils.createPartFromString(txtKode.getText().toString().trim()));
                    presenter.doUpload(file, map);
//                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("cycle", file.getName(), reqFile);
                }else{
                    Toast.makeText(UploadActivity.this, "Anda harus memilih gambar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        presenter = new UploadPresenterImpl(this);

        presenter.getKategori();


    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);

        }else{
            openGallery();
        }
    }


    @Override
    public void showLoading() {
        Utils.showDialog(progressDialog);
    }

    @Override
    public void hideLoading() {
        Utils.hideDialog(progressDialog);
    }

    @Override
    public void showList(List<Record> records) {
        ArrayAdapter<Record> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, records);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, "Upload berhasil, file name : " + message, Toast.LENGTH_LONG).show();
        Intent i = new Intent(UploadActivity.this, ImagePreviewActivity.class);
        i.putExtra("filename", message);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void upload(UploadResponse response) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                uri = data.getData();
                imgThumb.setVisibility(View.VISIBLE);
                imgThumb.setImageURI(uri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openGallery();
                }

                return;
            }
        }
    }
}

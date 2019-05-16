package ahafidz.com.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.bean.Uploaded;

public class ImagePreviewActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        final TextView textView = findViewById(R.id.txt_filename);
        ImageView imageView = findViewById(R.id.img_preview);
        progressBar = findViewById(R.id.img_progress);

        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("PREVIEW");

        Intent intent = getIntent();
        Uploaded result = (Uploaded) intent.getSerializableExtra("filename");
        textView.setVisibility(View.GONE);
        textView.setText("Nama File : " + result.getFile());
        progressBar.setVisibility(View.VISIBLE);
        Picasso.with(this).load(result.getUrl()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                textView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ImagePreviewActivity.this, "Gambar gagal dimuat", Toast.LENGTH_LONG).show();
            }
        });



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
}

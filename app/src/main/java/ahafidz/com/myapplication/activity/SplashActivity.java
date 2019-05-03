package ahafidz.com.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.presenter.SplashPresenter;
import ahafidz.com.myapplication.presenter.SplashPresenterImpl;
import ahafidz.com.myapplication.util.RequestPermissionHandler;
import ahafidz.com.myapplication.view.SplashView;

public class SplashActivity extends AppCompatActivity implements SplashView {

    SplashPresenter presenter;
    private RequestPermissionHandler mRequestPermissionHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenterImpl(this);
        mRequestPermissionHandler = new RequestPermissionHandler();

        mRequestPermissionHandler.requestPermission(this, new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                presenter.doCheck();
            }

            @Override
            public void onFailed() {
                Toast.makeText(SplashActivity.this, "Aplikasi ini memerlukan izin untuk dapat bekerja dengan baik.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void isLogin() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    @Override
    public void isNotLogin() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }
}

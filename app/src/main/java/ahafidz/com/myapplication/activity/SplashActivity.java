package ahafidz.com.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.presenter.SplashPresenter;
import ahafidz.com.myapplication.presenter.SplashPresenterImpl;
import ahafidz.com.myapplication.view.SplashView;

public class SplashActivity extends AppCompatActivity implements SplashView {

    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenterImpl(this);
        presenter.doCheck();
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
}

package ahafidz.com.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.presenter.LoginPresenter;
import ahafidz.com.myapplication.presenter.LoginPresenterImpl;
import ahafidz.com.myapplication.util.Utils;
import ahafidz.com.myapplication.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText textEmail;
    private EditText textPassword;
    private Button loginButton;
    private ProgressDialog progressDialog = null;
    private View view;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textEmail = findViewById(R.id.input_email);
        textPassword = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        view = findViewById(R.id.login_layout);
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");

        loginPresenter = new LoginPresenterImpl(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                loginPresenter.login(email, password);
            }
        });
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public void validationError(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccess() {
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void loginFailed(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        Utils.showDialog(progressDialog);

    }

    @Override
    public void dismissLoading() {
        Utils.hideDialog(progressDialog);
    }
}

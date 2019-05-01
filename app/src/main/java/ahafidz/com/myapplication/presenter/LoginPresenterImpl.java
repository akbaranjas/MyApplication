package ahafidz.com.myapplication.presenter;

import ahafidz.com.myapplication.bean.Login;
import ahafidz.com.myapplication.bean.LoginResponse;
import ahafidz.com.myapplication.bean.ValidateResponse;
import ahafidz.com.myapplication.bean.ValidateToken;
import ahafidz.com.myapplication.client.ApiClient;
import ahafidz.com.myapplication.client.ApiInterface;
import ahafidz.com.myapplication.dao.RealmHelper;
import ahafidz.com.myapplication.util.TextUtils;
import ahafidz.com.myapplication.view.LoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private static String TAG = LoginPresenterImpl.class.getSimpleName();

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(String email, String password) {
        if (email.isEmpty() || !TextUtils.isValidEmail(email)) {
            loginView.validationError("Email Not Valid");
        }

        if (password.isEmpty() || password.length() < 4 ) {
            loginView.validationError("Password Not Valid");
        }

        loginView.showLoading();

        final ApiInterface apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.doLogin(new Login(email,password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                int statusCode = response.code();
                if(statusCode == 200) {
                    if (response.body() != null) {
                        final LoginResponse loginResponse = response.body();
                        if(loginResponse.getJwt() != null || !loginResponse.getJwt().isEmpty()){
                            Call<ValidateResponse> callValidate = apiInterface.doValidateToken(new ValidateToken(loginResponse.getJwt()));
                            callValidate.enqueue(new Callback<ValidateResponse>() {
                                @Override
                                public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                                    loginView.dismissLoading();
                                    if(response.code() == 200){

                                        if (response.body() != null) {
                                            ValidateResponse validateResponse = response.body();
                                            if(validateResponse.getData() != null){
                                                RealmHelper realmHelper = new RealmHelper();
                                                if(realmHelper.getUser() != null){
                                                    realmHelper.deleteUser();
                                                }
                                                realmHelper.insertUser(validateResponse.getData(), loginResponse.getJwt());
                                                loginView.loginSuccess();
                                            }
                                        }else{
                                            loginView.dismissLoading();
                                            loginView.loginFailed("Error, empty body");
                                        }
                                    }else{
                                        loginView.dismissLoading();
                                        loginView.loginFailed("Error, " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ValidateResponse> call, Throwable t) {
                                    loginView.dismissLoading();
                                }
                            });
                        }else{
                            loginView.dismissLoading();
                            loginView.loginFailed("Error, failed to get JWT");
                        }
                    }else{
                        loginView.dismissLoading();
                        loginView.loginFailed("Error, empty body");
                    }
                }else{
                    loginView.dismissLoading();
                    loginView.loginFailed("Error " + statusCode);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loginView.dismissLoading();
                loginView.loginFailed("Error, " + t.getMessage());

            }
        });

    }

}

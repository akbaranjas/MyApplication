package ahafidz.com.myapplication.view;

public interface LoginView {

    void validationError(String message);
    void loginSuccess();
    void loginFailed(String message);
    void showLoading();
    void dismissLoading();

}

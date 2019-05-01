package ahafidz.com.myapplication.presenter;

import ahafidz.com.myapplication.dao.RealmHelper;
import ahafidz.com.myapplication.view.SplashView;

public class SplashPresenterImpl implements SplashPresenter{

    private SplashView view;

    public SplashPresenterImpl(SplashView view) {
        this.view = view;
    }

    @Override
    public void doCheck() {

        RealmHelper realmHelper = new RealmHelper();
        if(realmHelper.getUser() != null){
            view.isLogin();
        }else{
            view.isNotLogin();
        }

    }
}

package ahafidz.com.myapplication.presenter;

import ahafidz.com.myapplication.bean.CategoryReadResponse;
import ahafidz.com.myapplication.client.ApiClient;
import ahafidz.com.myapplication.client.ApiInterface;
import ahafidz.com.myapplication.view.HomeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenterImpl implements HomePresenter {

    HomeView view;

    public HomePresenterImpl(HomeView view) {
        this.view = view;
    }

    @Override
    public void getData() {
        view.showLoading();
        ApiInterface apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<CategoryReadResponse> call = apiInterface.doReadCategory();
        call.enqueue(new Callback<CategoryReadResponse>() {
            @Override
            public void onResponse(Call<CategoryReadResponse> call, Response<CategoryReadResponse> response) {
                view.hideLoading();
                int statusCode = response.code();
                if(statusCode == 200) {
                    if (response.body() != null) {
                        if(response.body().getRecords() != null){
                            view.showList(response.body().getRecords());
                        }
                    }else{
                        view.showError("Error, " + "Empty Body.");
                    }
                }else{
                    view.showError("Error, " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<CategoryReadResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());

            }
        });

    }
}

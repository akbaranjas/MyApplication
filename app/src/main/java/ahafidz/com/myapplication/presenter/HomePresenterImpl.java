package ahafidz.com.myapplication.presenter;

import android.util.Log;

import ahafidz.com.myapplication.bean.CategoryReadResponse;
import ahafidz.com.myapplication.bean.CreateDataRequest;
import ahafidz.com.myapplication.bean.CreateResponse;
import ahafidz.com.myapplication.client.ApiClient;
import ahafidz.com.myapplication.client.ApiInterface;
import ahafidz.com.myapplication.dao.RealmHelper;
import ahafidz.com.myapplication.util.Constant;
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

    @Override
    public void logout() {
        RealmHelper helper = new RealmHelper();
        helper.deleteUser();
        view.logout();
    }

    @Override
    public void insertData(CreateDataRequest createDataRequest) {
        view.showLoading();
        ApiInterface apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<CreateResponse> call = apiInterface.doCreateCategory(createDataRequest);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                view.hideLoading();
                if(response.code() == Constant.STATUS_CREATED){
                    view.onSuccess(response.body().getMessage());
                }else{
                    view.onSuccess("Tambah data gagal");
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                view.hideLoading();
                view.showError("Error, " + t.getMessage());
            }
        });
    }

    @Override
    public void updateData(CreateDataRequest createDataRequest) {
        view.showLoading();
        ApiInterface apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<CreateResponse> call = apiInterface.doUpdateCategory(createDataRequest);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                view.hideLoading();
                if(response.code() == 200 && response.body().getMessage().equals(Constant.UPDATE_SUCCESS)){
                    view.onSuccess(response.body().getMessage());
                }else{
                    view.onSuccess("Rubah data gagal");
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                view.hideLoading();
                view.showError("Error, " + t.getMessage());
                Log.e("updateData", t.getMessage());
            }
        });
    }

    @Override
    public void deleteData(CreateDataRequest createDataRequest) {
        view.showLoading();
        ApiInterface apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<CreateResponse> call = apiInterface.doDeleteCategory(createDataRequest);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                view.hideLoading();
                if(response.code() == 200 && response.body().getMessage().equals(Constant.DELETE_SUCCESS)){
                    view.onSuccess(response.body().getMessage());
                }else{
                    view.onSuccess("Hapus data gagal");
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                view.hideLoading();
                view.showError("Error, " + t.getMessage());
            }
        });
    }
}

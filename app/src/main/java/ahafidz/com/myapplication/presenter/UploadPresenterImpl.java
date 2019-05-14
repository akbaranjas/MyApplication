package ahafidz.com.myapplication.presenter;

import java.io.File;
import java.util.HashMap;

import ahafidz.com.myapplication.bean.CategoryReadResponse;
import ahafidz.com.myapplication.bean.UploadResponse;
import ahafidz.com.myapplication.client.ApiClient;
import ahafidz.com.myapplication.client.ApiInterface;
import ahafidz.com.myapplication.client.UploadInterface;
import ahafidz.com.myapplication.view.UploadView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPresenterImpl implements UploadPresenter{

    private UploadView view;
    public UploadPresenterImpl(UploadView view) {
        this.view = view;
    }

    @Override
    public void getKategori() {
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
    public void doUpload(File file, HashMap<String, RequestBody> map) {
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("userfile[]", file.getName(), reqFile);
        view.showLoading();
        UploadInterface apiInterface = ApiClient.getRetrofitClient().create(UploadInterface.class);
        Call<UploadResponse> call = apiInterface.uploadImage(body, map);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                view.hideLoading();
                int statusCode = response.code();
                if(statusCode == 200) {
                    if (response.body() != null) {
                        if(response.body().getUploaded() != null){
                            view.onSuccess(response.body().getUploaded().get(0));
                        }
                    }else{
                        view.showError("Error, " + "Empty Body.");
                    }
                }else{
                    view.showError("Error, " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }


}


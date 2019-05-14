package ahafidz.com.myapplication.client;

import java.util.Map;

import ahafidz.com.myapplication.bean.CategoryReadResponse;
import ahafidz.com.myapplication.bean.CreateDataRequest;
import ahafidz.com.myapplication.bean.CreateResponse;
import ahafidz.com.myapplication.bean.Login;
import ahafidz.com.myapplication.bean.LoginResponse;
import ahafidz.com.myapplication.bean.UploadResponse;
import ahafidz.com.myapplication.bean.ValidateResponse;
import ahafidz.com.myapplication.bean.ValidateToken;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @Headers({
            "Content-Type:application/json"
    })

    @POST("auth/login.php")
    Call<LoginResponse> doLogin(@Body Login login);

    @POST("auth/validate_token.php")
    Call<ValidateResponse> doValidateToken(@Body ValidateToken validateToken);

    @GET("kategori/read.php")
    Call<CategoryReadResponse> doReadCategory();

    @POST("kategori/create.php")
    Call<CreateResponse> doCreateCategory(@Body CreateDataRequest request);

    @POST("kategori/update.php")
    Call<CreateResponse> doUpdateCategory(@Body CreateDataRequest request);

    @POST("kategori/delete.php")
    Call<CreateResponse> doDeleteCategory(@Body CreateDataRequest request);

    @Multipart
    @POST("upload")
    Call<UploadResponse> uploadImage(@Part MultipartBody.Part photo,
                                     @PartMap Map<String, RequestBody> text);

}

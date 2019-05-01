package ahafidz.com.myapplication.client;

import java.util.List;

import ahafidz.com.myapplication.bean.CategoryReadResponse;
import ahafidz.com.myapplication.bean.Login;
import ahafidz.com.myapplication.bean.LoginResponse;
import ahafidz.com.myapplication.bean.ValidateResponse;
import ahafidz.com.myapplication.bean.ValidateToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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

}

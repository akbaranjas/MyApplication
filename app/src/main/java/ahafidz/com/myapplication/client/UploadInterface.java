package ahafidz.com.myapplication.client;

import java.util.Map;

import ahafidz.com.myapplication.bean.UploadResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UploadInterface {

    @Multipart
    @POST("upload/")
    Call<UploadResponse> uploadImage(@Part MultipartBody.Part photo,
                                     @PartMap Map<String, RequestBody> text);
}

package ahafidz.com.myapplication.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import ahafidz.com.myapplication.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (retrofit == null) {
            //Defining the Retrofit using Builder
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).build();
            Gson gson = new GsonBuilder().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL) //This is the only mandatory call on Builder object.
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Convertor library used to convert response into POJO
                    .client(client)
                    .build();
        }

        return retrofit;
    }

}

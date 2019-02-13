package br.com.udacity.ruyano.recipes.networking;

import com.google.gson.Gson;

import br.com.udacity.ruyano.recipes.networking.services.IAPIService;
import br.com.udacity.ruyano.recipes.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static RetrofitConfig ourInstance;
    private final Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private RetrofitConfig() {

        okHttpClient = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient)
                .build();

    }

    public static RetrofitConfig getInstance() {
        if (ourInstance == null) {
            ourInstance = new RetrofitConfig();
        }
        return ourInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public IAPIService getApi() {
        return retrofit.create(IAPIService.class);
    }
}

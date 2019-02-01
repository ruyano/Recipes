package br.com.udacity.ruyano.recipes.networking;

import com.google.gson.Gson;

import br.com.udacity.ruyano.recipes.networking.services.IAPIService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static RetrofitConfig ourInstance;
    private final Retrofit retrofit;

    private RetrofitConfig() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(httpClient.build())
                .build();

    }

    public static RetrofitConfig getInstance() {
        if (ourInstance == null) {
            ourInstance = new RetrofitConfig();
        }
        return ourInstance;
    }

    public IAPIService getApi() {
        return retrofit.create(IAPIService.class);
    }
}

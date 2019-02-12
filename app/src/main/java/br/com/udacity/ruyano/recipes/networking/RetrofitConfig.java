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

    private RetrofitConfig() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
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

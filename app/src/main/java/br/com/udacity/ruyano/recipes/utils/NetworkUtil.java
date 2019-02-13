package br.com.udacity.ruyano.recipes.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {

    public static boolean isTestingDesconnection = false;

    public static boolean isConected(Context context) {
        boolean isConected;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        isConected = conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isConnected();
        return !isTestingDesconnection && isConected;

    }

}

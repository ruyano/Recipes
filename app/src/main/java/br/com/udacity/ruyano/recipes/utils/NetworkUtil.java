package br.com.udacity.ruyano.recipes.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {

    public static boolean isTestingDisconnection = false;

    public static boolean isConnected(Context context) {
        boolean isConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        isConnected = connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        return !isTestingDisconnection && isConnected;
    }
}
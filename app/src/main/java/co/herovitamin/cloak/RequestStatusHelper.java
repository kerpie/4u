package co.herovitamin.cloak;

import android.content.Context;
import android.preference.PreferenceManager;

public class RequestStatusHelper {

    public static final String REQUEST_STATUS = "REQUEST_STATUS";

    public static void setRequestStatus(Context context, boolean status){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(REQUEST_STATUS, status).apply();
    }

    public static boolean getRequestStatus(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(REQUEST_STATUS, false);
    }
}

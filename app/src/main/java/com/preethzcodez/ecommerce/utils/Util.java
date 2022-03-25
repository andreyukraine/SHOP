package com.preethzcodez.ecommerce.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
import com.preethzcodez.ecommerce.R;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;

// Utilities Class
public class Util {

    // Format Double Value To Remove Unnecessary Zero
    public static String formatDouble(double num) {
        if (num == (long) num)
            return String.format(Locale.US, "%d", (long) num);
        else
            return String.format(Locale.US, "%s", num);
    }

    public static String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return ""; //или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static boolean is_equal_bouble(double x, double y) {
        if ((x - y) < 0.00001){
            return true;
        }else {
            return false;
        }
    }

    public static ProgressDialog showProgress(Activity activity){
        ProgressDialog dialogProgress = ProgressDialog.show(activity,"","Завантаження...",true);
        dialogProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogProgress.setContentView(R.layout.custom_progressdialog);
        dialogProgress.setCancelable(false);

        return dialogProgress;
    }

    public static void hideProgress(ProgressDialog dialogProgress){
        if (dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.hide();
        }
    }

    public static String getBase64Auth(String name, String password){
        byte[] data = new byte[0];
        try {
            data = (name + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }

    public static int detPercentPrices(double client_price, double base_price){
        double percent = (1 - (base_price/client_price)) * 100;
        return (int) Math.round(percent);
    }

    public static String toString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "{}";

        StringBuilder b = new StringBuilder();
        b.append('{');
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.append('}').toString();
            b.append(", ");
        }
    }

    public static void infoMsg(Context context, String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    // Get inClause String For Array Parameters In DB
    public static String getInClause(List<String> array) {
        String inClause = array.toString();

        //replace the brackets with parentheses
        inClause = inClause.replace("[", "(");
        inClause = inClause.replace("]", ")");

        return inClause;
    }

    // Get inClause String For Array Parameters In DB
    public static String getInClauseString(List<String> array) {

        String inClause = "('"+array.toString().replace("[","").replace("]", "").replace(" ","").replace(",","','")+"')";;

        return inClause;
    }

    // Check email valid or not
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Get Error Message
    public static String getErrorMessage(Throwable t, Context context) {
        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException || t instanceof ConnectException) {
            return context.getResources().getString(R.string.NoInternet);
        } else {
            return context.getResources().getString(R.string.Error500);
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }

}

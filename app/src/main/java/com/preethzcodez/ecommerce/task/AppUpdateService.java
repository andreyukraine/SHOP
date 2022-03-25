package com.preethzcodez.ecommerce.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.preethzcodez.ecommerce.BuildConfig;
import com.preethzcodez.ecommerce.database.DB_Handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppUpdateService {
    private String TAG = "AppUpdateService";
    private Context _context = null;
    private String _strApkFileUrl = "";
    private DB_Handler db_handler;

    public interface runUpdateApp {
        void runUpdateApp(Boolean run);
    }

    public boolean DoUpdate(Context context, String server, int port, String pathServer) {

        this._context = context;
        db_handler = new DB_Handler(context);

        final String[] strUpdateUrl = {"https://" + server + ":" + port + pathServer};

        Retrofit retrofit = new Retrofit.Builder().baseUrl(strUpdateUrl[0]).addConverterFactory(GsonConverterFactory.create()).build();
        IAppUpdateService intAppUpdate = retrofit.create(IAppUpdateService.class);

        Call<AppFileData> pServerFileInfo = intAppUpdate.getLatestAppInfo("extraKey", "extraValue");
        pServerFileInfo.enqueue(new Callback<AppFileData>() {
            @Override
            public void onResponse(Call<AppFileData> call, Response<AppFileData> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponce " + response.code());
                }

                _strApkFileUrl = "https://" + server + ":" + port + pathServer + "detta.apk";

                AppFileData pFileInfoServer = response.body();

                int versionServer = pFileInfoServer.getVersionCode();
                int versionBase = Integer.parseInt(db_handler.getParamByCode("VERSION_APP"));

                if (versionServer != versionBase) {
                    Log.d(TAG, "Update start");
                    DownloadDialog();
                }else{
                    if (_context instanceof AppUpdateService.runUpdateApp) {
                        ((AppUpdateService.runUpdateApp) _context).runUpdateApp(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<AppFileData> call, Throwable t) {
                Log.d(TAG, "onResponce Error" + t.getMessage());
            }
        });

        return true;
    }

    private void DownloadDialog() {
        final boolean[] action = {true};
        AlertDialog pOld = new AlertDialog.Builder(this._context).setTitle("Доступна нова версія").setMessage("Будь ласка, обновіть додаток").setPositiveButton("Обновить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    redirectStore(_strApkFileUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).setNegativeButton("Не сейчас", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (_context instanceof AppUpdateService.runUpdateApp) {
                    ((AppUpdateService.runUpdateApp) _context).runUpdateApp(false);
                }
            }
        }).create();
        pOld.show();
    }

    private void redirectStore(String strUrl) throws IOException {
        try {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        URL url = new URL(strUrl);
                        HttpURLConnection c = (HttpURLConnection) url.openConnection();
                        c.setRequestMethod("GET");
                        c.setDoOutput(true);
                        c.connect();

                        String PATH = String.valueOf(_context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
                        File fileArk = new File(PATH);
                        fileArk.mkdirs();
                        File outputFile = new File(fileArk, "detta.apk");
                        if (outputFile.exists()) {
                            outputFile.delete();
                        }
                        FileOutputStream fos = new FileOutputStream(outputFile);

                        InputStream is;
                        int status = c.getResponseCode();
                        if (status != HttpURLConnection.HTTP_OK)
                            is = c.getErrorStream();
                        else
                            is = c.getInputStream();

                        byte[] buffer = new byte[1024];
                        int len1 = 0;
                        while ((len1 = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len1);
                        }
                        fos.flush();
                        fos.close();
                        is.close();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        File fileInstall = new File(PATH,   "detta.apk");
                        Uri data = FileProvider.getUriForFile(_context, BuildConfig.APPLICATION_ID +".provider",fileInstall);
                        intent.setDataAndType(data,"application/vnd.android.package-archive");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        _context.startActivity(intent);

                        db_handler.insertParamsOrUpdate("VERSION_APP", db_handler.getParamByCode("VERSION_APP"));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

            if (_context instanceof AppUpdateService.runUpdateApp) {
                ((AppUpdateService.runUpdateApp) _context).runUpdateApp(false);
            }

        } catch (Exception e) {
            Log.e("UpdateAPP", "Exception " + e);
        }
    }
}

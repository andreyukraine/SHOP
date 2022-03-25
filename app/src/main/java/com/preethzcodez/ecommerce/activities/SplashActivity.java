package com.preethzcodez.ecommerce.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.task.AppUpdateService;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.fragments.SignIn;
import com.preethzcodez.ecommerce.interfaces.FinishActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SplashActivity extends AppCompatActivity implements FinishActivity, AppUpdateService.runUpdateApp {

    private static final int PERMISSION_REQUEST_CODE = 123;
    private DB_Handler db_handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db_handler = new DB_Handler(this);

        setContentView(R.layout.activity_splash);

        if (db_handler.getParamByCode("VERSION_APP") == "") {
            db_handler.insertParamsOrUpdate("VERSION_APP", "1");
        }

        AppUpdateService pAppSvc = new AppUpdateService();
        pAppSvc.DoUpdate(SplashActivity.this, "zoougolok.com.ua", 443, "/upload/glovo/");

    }

    private void loadSignInActivity() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        ft.replace(R.id.fragment, new SignIn());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void finishActivity() {
        overridePendingTransition(0, 0);
        finish();
    }


    //--------------------------------------------//Permishion--------------------------------------------------------

    public void showNoStoragePermissionSnackbar() {
        Snackbar.make(SplashActivity.this.findViewById(R.id.coordinatorLayout), "Дозвіл на зберігання не надано", Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSettings();

                        Toast.makeText(getApplicationContext(),
                                "Відкрийте дозволи та надайте дозвіл на зберігання",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .show();
    }

    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            loadSignInActivity();
            System.out.println("Продовжуємо завантаження програми");
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean allowed = true;

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults) {
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;

            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed) {
            //user granted all permissions we can perform our task.
            loadSignInActivity();
        } else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "У дозволах зберігання відмовлено.", Toast.LENGTH_SHORT).show();

                } else {
                    showNoStoragePermissionSnackbar();
                }

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "У дозволах зберігання відмовлено.", Toast.LENGTH_SHORT).show();
                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        }

    }

    @Override
    public void runUpdateApp(Boolean run) {
        if (run == false){
            loadSignInActivity();
        }
    }
}

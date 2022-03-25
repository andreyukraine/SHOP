package com.preethzcodez.ecommerce.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.preethzcodez.ecommerce.MainActivity;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.GallaryListAdapter;
import com.preethzcodez.ecommerce.adapters.PicsDataAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.fragments.Clients;
import com.preethzcodez.ecommerce.objects.PicsDate;
import com.preethzcodez.ecommerce.pojo.Pics;
import com.preethzcodez.ecommerce.utils.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;

public class ClientGallary extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static String client_code;
    Toolbar toolbar;
    ListView listviewGallary;
    FloatingActionButton fab;
    Bitmap imageBitmap;
    TextView titleToolbar, notOrders;
    DB_Handler db_handler;
    List<PicsDate> picsList;
    ImageView cart, backButton;
    String lat;
    String lon;
    LocationManager locationManager;
    Location location = null;
    Context mContext;

    private static final String TAG = ClientGallary.class.getName();

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_gallary);
        db_handler = new DB_Handler(this);
        mContext = this;

        setId();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        //ASK HERE...
        if (shouldAskPermissions()) {
            askPermissions();
        }

        // Hide Title
        titleToolbar.setText("Фото звіти");

        cart.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);

        Bundle arguments = getIntent().getExtras();
        client_code = arguments.getString(Constants.SESSION_CLIENT_CODE);

        // Set Toolbar
        setSupportActionBar(toolbar);

        setOnClickListener();

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000, 10, locationListenerGPS);

        getLocations();

        isLocationEnabled();

        fillGridView();
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            lat = Double.toString(location.getLatitude());
            lon = Double.toString(location.getLongitude());
            String msg = "New Latitude: " + lat + "\n" + "New Longitude: " + lon;
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    public void getLocations() {
        // getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120000, 10, locationListener);
                Log.d("Network", "Network");
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        lat = Double.toString(location.getLatitude());
                        lon = Double.toString(location.getLongitude());
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                if (location == null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 10, locationListener);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            lat = Double.toString(location.getLatitude());
                            lon = Double.toString(location.getLongitude());
                        }
                    }
                }
            }
        }
    }


    private void isLocationEnabled() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        } else {
        }
    }

    private void setOnClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fillGridView() {
        picsList = db_handler.getPicsList(client_code);
        if (picsList.size() > 0) {
            listviewGallary.setAdapter(new PicsDataAdapter(this, picsList));
            notOrders.setVisibility(View.GONE);
            listviewGallary.setVisibility(View.VISIBLE);
        }else{
            notOrders.setVisibility(View.VISIBLE);
            listviewGallary.setVisibility(View.GONE);
        }
    }

    //// ASK METHODS
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.ACCESS_FINE_LOCATION"
        };
        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    private void setId(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        listviewGallary = findViewById(R.id.listviewGallary);
        toolbar = findViewById(R.id.toolbar);
        cart = findViewById(R.id.cart);
        backButton = findViewById(R.id.backButton);
        titleToolbar = findViewById(R.id.titleToolbar);
        notOrders = findViewById(R.id.notOrders);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            try {
                savebitmap(imageBitmap, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void savebitmap(Bitmap bmp, Context context) throws IOException {

        Date date = Calendar.getInstance().getTime();
        // Вывод текущей даты и времени с использованием toString()
        DateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String file_name_img = lat + "_" + lon + "-" + formatter.format(date);
        //File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "pics_client" + File.separator + client_code);

        File dir;

        if (Build.VERSION_CODES.R > Build.VERSION.SDK_INT) {
            dir = new File(Environment.getExternalStorageDirectory().getPath()
                    + "//B2B//" + client_code);
        } else {
            dir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).getPath()
                    + "//B2B//" + client_code);
        }

        if (!dir.exists()) {
            dir.mkdir();
        }
        if (dir.exists()){
            String str_f = dir.getPath()+"/"+ file_name_img + ".jpg";
            File f = new File(str_f);
            f.createNewFile();
            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            Calendar calendar = Calendar.getInstance();
            String dataPic = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
            String timePic = new SimpleDateFormat("HH:mm:ss").format(calendar.getTime());

            db_handler.insertPics(client_code, str_f, lat, lon, dataPic, timePic);
            fillGridView();

        } else {
            // Do something else on failure
        }

    }

}
package com.preethzcodez.ecommerce.activities;
import androidx.fragment.app.FragmentActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.databinding.ActivityMapsBinding;
import com.preethzcodez.ecommerce.objects.Marker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private TextView titleToolbar;
    private ImageView cart, backButton;
    private DB_Handler db_handler;
    private List<Marker> mPoints;
    private Button submitMap;
    EditText startDay, endDay;
    SimpleDateFormat format;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateS, dateE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db_handler = new DB_Handler(this);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        setId();

        // Hide Title
        titleToolbar.setText("Маршрут");

        // Back Button
        backButton.setVisibility(View.VISIBLE);

        cart.setVisibility(View.GONE);

        format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

        Calendar calendar = Calendar.getInstance();
        String DateStart = new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());

        startDay.setText(DateStart);
        endDay.setText(DateStart);

        getMarkers();

        dateS = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelS();
            }

        };

        dateE = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelE();
            }

        };

        setOnClickListeners();

    }

    private void setOnClickListeners() {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        startDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MapsActivity.this, dateS, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MapsActivity.this, dateE, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submitMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMarkers();
                setMarkers();
            }
        });
    }

    private void getMarkers() {
        String stDay = startDay.getText().toString().replaceAll("\\.","");
        String edDay = endDay.getText().toString().replaceAll("\\.","");
        mPoints = db_handler.getMarkers(stDay, edDay);
    }

    private void updateLabelS() {
        startDay.setText(format.format(myCalendarStart.getTime()));
    }

    private void updateLabelE() {
        endDay.setText(format.format(myCalendarEnd.getTime()));
    }

    private void setId() {
        titleToolbar = findViewById(R.id.titleToolbar);
        cart = findViewById(R.id.cart);
        startDay = (EditText) findViewById(R.id.startDay);
        endDay = (EditText) findViewById(R.id.endDay);
        submitMap = findViewById(R.id.submitMap);
        backButton = findViewById(R.id.backButton);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMarkers();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void setMarkers() {

        if (mPoints.size() > 0) {

            PolylineOptions line = new PolylineOptions();
            line.width(4f).color(R.color.colorAccent);
            LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

            for (int i = 0; i < mPoints.size(); i++) {

                String titleMarker = mPoints.get(i).getName() + "\n" + mPoints.get(i).getTime();

                if (i == 0) {
                    MarkerOptions startMarkerOptions = new MarkerOptions().position(mPoints.get(i).getLatLng()).title(titleMarker).snippet(titleMarker);
                    mMap.addMarker(startMarkerOptions);
                } else if (i > 0 && i < mPoints.size() - 1) {
                    MarkerOptions MarkerOptions = new MarkerOptions().position(mPoints.get(i).getLatLng()).title(titleMarker).snippet(titleMarker);
                    mMap.addMarker(MarkerOptions);
                } else if (i == mPoints.size() - 1) {
                    MarkerOptions endMarkerOptions = new MarkerOptions().position(mPoints.get(i).getLatLng()).title(titleMarker).snippet(titleMarker);
                    mMap.addMarker(endMarkerOptions);
                }
                line.add(mPoints.get(i).getLatLng());
                latLngBuilder.include(mPoints.get(i).getLatLng());
            }

            mMap.addPolyline(line);
            int size = getResources().getDisplayMetrics().widthPixels;
            LatLngBounds latLngBounds = latLngBuilder.build();
            //CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
            //mMap.moveCamera(track);
        } else {
            infoMsg(this, "Точки для маршрута не найдены");

            mMap.addMarker(new MarkerOptions().position(new LatLng(50.4019514, 30.3926091)).title("Marker in Киев"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.4019514, 30.3926091), 7));
        }
    }
}
package com.preethzcodez.ecommerce.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.activities.OrderDetails;
import com.preethzcodez.ecommerce.adapters.CitySearchAdapter;
import com.preethzcodez.ecommerce.adapters.PointSearchAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.pojo.CityNP;
import com.preethzcodez.ecommerce.pojo.PointNP;
import com.preethzcodez.ecommerce.utils.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NewPost extends Fragment {

    private View view;
    private String selectCityName, selectPointName;
    private String selectCityId, selectPointId;
    private Button btnOk, clearCity, clearPoint;
    private TextView pointText, countPoint;
    private DB_Handler db_handler;
    Context context;
    private String order_number;
    AutoCompleteTextView auto_city, auto_point;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public interface IselectNP{
        void selectNP(String selectCityName, String selectCityId, String selectPointName, String selectPointId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.np, container, false);
        db_handler = new DB_Handler(context);


        order_number = getArguments().getString(Constants.ORDER_ID);

        setIds(view);

        viewCity();

        setClickListeners();

        return view;
    }

    // Set Click Listeners
    private void setClickListeners() {
        auto_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityNP city_obj = (CityNP) parent.getItemAtPosition(position);
                selectCityName = city_obj.getName();
                selectCityId = city_obj.getCode_np();
                clearCity.setVisibility(View.VISIBLE);
                auto_city.setEnabled(false);
                auto_point.setEnabled(true);
                viewPoint();

            }
        });

        auto_point.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PointNP point_obj = (PointNP) parent.getItemAtPosition(position);
                selectPointName = point_obj.getName();
                selectPointId = point_obj.getCode_np();
                clearPoint.setVisibility(View.VISIBLE);
                countPoint.setVisibility(View.VISIBLE);
                auto_point.setEnabled(false);
                viewButton(true);
            }
        });

        clearCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCityId = "";
                viewPoint();
                viewButton(false);

                auto_city.setText("");
                auto_point.setText("");

                auto_city.setEnabled(true);

                clearCity.setVisibility(View.GONE);
                clearPoint.setVisibility(View.GONE);
                countPoint.setVisibility(View.GONE);
            }
        });

        clearPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPointName = "";
                auto_point.setText("");
                auto_point.setEnabled(true);
                clearPoint.setVisibility(View.GONE);
                countPoint.setVisibility(View.GONE);
                viewButton(false);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context instanceof IselectNP) {
                    ((IselectNP) context).selectNP(selectCityName, selectCityId, selectPointName, selectPointId);
                }

                db_handler.setNewPostOrder(order_number, 1, selectCityId, selectPointId);
                db_handler.setLogistOrder(order_number,1, "");

                Intent intent = new Intent(context, OrderDetails.class);
                intent.putExtra(Constants.ORDER_ID, order_number);

                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    private void viewButton(Boolean isView) {
        if (isView) {
            btnOk.setVisibility(View.VISIBLE);
        }else{
            btnOk.setVisibility(View.GONE);
        }
    }

    // Set Ids
    private void setIds(View view) {
        pointText = view.findViewById(R.id.pointText);
        countPoint = view.findViewById(R.id.countPoint);
        auto_city = view.findViewById(R.id.autocomplete_city);
        auto_point = view.findViewById(R.id.autocomplete_point);
        btnOk = view.findViewById(R.id.btnOk);
        clearCity = view.findViewById(R.id.clearCity);
        clearPoint = view.findViewById(R.id.clearPoint);
    }

    private void viewCity(){
        CitySearchAdapter adapterCity = new CitySearchAdapter(getActivity(), R.layout.city_item, db_handler.getListCity());
        auto_city.setAdapter(adapterCity);
    }

    private void viewPoint(){
        if (selectCityId != null && !selectCityId.isEmpty()) {

            ArrayList<PointNP> points =  db_handler.getListPoint(selectCityId);

            pointText.setVisibility(View.VISIBLE);
            auto_point.setVisibility(View.VISIBLE);
            countPoint.setVisibility(View.VISIBLE);
            countPoint.setText(String.valueOf(points.size()));
            PointSearchAdapter adapterPoint = new PointSearchAdapter(getActivity(), R.layout.point_item, points);
            auto_point.setAdapter(adapterPoint);
        }else{
            pointText.setVisibility(View.GONE);
            auto_point.setVisibility(View.GONE);
            countPoint.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEnterSharedElementCallback(@Nullable SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }
}



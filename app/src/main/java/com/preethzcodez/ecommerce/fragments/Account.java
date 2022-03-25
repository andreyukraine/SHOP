package com.preethzcodez.ecommerce.fragments;

import static java.sql.DriverManager.println;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.activities.SplashActivity;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.interfaces.FinishActivity;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.utils.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Account extends Fragment {

    DB_Handler db_handler;
    TextInputEditText name, email, mobile;
    RelativeLayout logoutLay;
    FinishActivity finishActivityCallback;
    SwitchMaterial type_catalog_views;
    EditText inputPin;
    User user;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        finishActivityCallback = (FinishActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.account, container, false);

        inputPin = new EditText(getActivity());

        // Get User
        db_handler = new DB_Handler(getActivity());
        user = db_handler.getFirstUser();

        // Set Values
        setIds(view);

        setValues(user);

        setClickListeners();

        return view;
    }

    // Set Ids
    private void setIds(View view) {
        logoutLay = view.findViewById(R.id.logoutLay);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        type_catalog_views = view.findViewById(R.id.type_catalog_views);
    }

    // Set Values
    private void setValues(User user) {
        // Name
        name.setText(user.getName());

        String typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);
        if (typeCatalog.equals("true")){
            type_catalog_views.setChecked(true);
        }

        // Mobile
        mobile.setText(user.getMobile());
    }

    // Set Click Listeners
    private void setClickListeners() {
        // Logout
        logoutLay.setOnClickListener(view -> {
            SessionManager sessionManager = new SessionManager(context);
            sessionManager.clearPreferences();
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishActivityCallback.finishActivity();
        });

        type_catalog_views.setOnCheckedChangeListener((buttonView, isChecked) -> {
            db_handler.insertParamsOrUpdate(Constants.TYPE_CATALOG, Boolean.toString(isChecked));
        });

    }
}

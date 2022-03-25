package com.preethzcodez.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.activities.MapsActivity;
import com.preethzcodez.ecommerce.activities.Orders;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.utils.Constants;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Home extends Fragment {

    TextView check_client, updateDate, myOrders, countOrder, debt_client, myRoads;
    DB_Handler db_handler;
    Client client;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);
        // get search text
        db_handler = new DB_Handler(getContext());
        sessionManager = new SessionManager(getActivity());

        setId(view);

        Bundle args = getArguments();
        assert args != null;

        // get client
        if (db_handler.getCartItemCount() > 0){
            client = db_handler.getClientsById(db_handler.getCartClient());
        }else{
            client = db_handler.getClientsById(sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE));
        }

        check_client.setText(client.getName());
        db_handler.insertParamsOrUpdate(Constants.TYPE_CATALOG, Boolean.toString(true));

        String dept = Double.toString(client.getDebt());
        debt_client.setText(dept + " ₴");
        if (client.getDebt() > 0){
            debt_client.setTextColor(this.getResources().getColor(R.color.colorAccent));
        }

        setValue();

        setClickListeners();

        return view;
    }

    private void setClickListeners() {
        // My Orders
        myOrders.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Orders.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
            }
        });

        myRoads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setValue();
    }

    private void setValue(){
        String text_update = "Дата обновление каталога: " + db_handler.getParamByCode(Constants.DATA_UPDATE);
        updateDate.setText(text_update);
        countOrder.setText(String.valueOf(db_handler.getCountOrders()));
    }

    private void setId(View view){
        check_client = view.findViewById(R.id.check_client);
        debt_client = view.findViewById(R.id.debt_client);
        updateDate = view.findViewById(R.id.dataUpdate);
        myOrders = view.findViewById(R.id.myOrders);
        myRoads = view.findViewById(R.id.myRoads);
        countOrder = view.findViewById(R.id.countOrder);
    }
}

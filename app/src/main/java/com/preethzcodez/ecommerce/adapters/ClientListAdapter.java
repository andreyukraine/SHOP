package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.preethzcodez.ecommerce.activities.ClientGallary;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.interfaces.ShowBackButton;
import com.preethzcodez.ecommerce.json.ResponseJSON;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Price;
import com.preethzcodez.ecommerce.pojo.PriceVariant;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.Util;
import com.preethzcodez.ecommerce.webservice.RetrofitBuilder;
import com.preethzcodez.ecommerce.webservice.RetrofitInterface;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.preethzcodez.ecommerce.utils.Util.getBase64Auth;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;

public class ClientListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Client> clientList;
    private String mSelectedPositionCode = "";
    private SessionManager sessionManager;
    DB_Handler db_handler;
    Holder holder;
    View rowView;
    int isChecked = 0;
    int isShow = 0;
    Handler handler;
    int count = 0;
    int sizeCount = 0;
    RetrofitBuilder retrofitBuilder;
    OkHttpClient httpClient;
    String client_code;
    String position_client, xml_code;
    Bundle args;
    private User user;
    private String basicAuth;

    public ClientListAdapter(Context context, List<Client> clientList) {
        this.context = context;
        this.clientList = clientList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        holder = new Holder();
        rowView = inflater.inflate(R.layout.client_grid_item, null);
        db_handler = new DB_Handler(context);
        sessionManager = new SessionManager(context);
        handler = new Handler();
        retrofitBuilder = new RetrofitBuilder(context);
        httpClient = retrofitBuilder.setClient();
        client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);
        args = new Bundle();

        //User
        user = db_handler.getFirstUser();
        basicAuth = getBase64Auth(user.getName(), user.getPassword());

        setId(rowView);

        holder.name.setText(clientList.get(position).getName());
        holder.address.setText(clientList.get(position).getAddress());

        if (mSelectedPositionCode == clientList.get(position).getXml_id()) {
            sessionManager.saveSession(Constants.SESSION_CLIENT_CODE, clientList.get(position).getXml_id());
            sessionManager.saveSession(Constants.SESSION_CLIENT_POSITION, Integer.toString(position));
            holder.switch_client.setVisibility(View.VISIBLE);

            if (isChecked == 1){
                holder.switch_client.setChecked(true);
            }else{
                holder.switch_client.setChecked(false);
            }

            if (isShow == 1){
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.percent.setVisibility(View.VISIBLE);
                holder.progressBar.setMax(sizeCount);
                holder.progressBar.setProgress(count);

                holder.percent.setVisibility(View.VISIBLE);
                if (sizeCount > 0) {
                    double s = (double)count / (double)sizeCount * 100;
                    double percent = Math.round(s);
                    String strProgress = String.valueOf((int) percent) + " %";
                    holder.percent.setText(strProgress);
                }
                if (sizeCount == count){
                    sizeCount = 0;
                    count = 0;
                    holder.progressBar.setVisibility(View.GONE);
                    holder.percent.setVisibility(View.GONE);
                    handler.removeCallbacks(upData);
                    //записываем в сессию что загрузили цены
                    sessionManager.saveSession(Constants.SESSION_LOADING_PRICE, "Y");
                }

            }else{
                holder.progressBar.setVisibility(View.GONE);
                holder.percent.setVisibility(View.GONE);
            }

        }else{
            holder.switch_client.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);
            holder.percent.setVisibility(View.GONE);
        }

        if(mSelectedPositionCode == ""){
            if (clientList.get(position).getXml_id().equals(client_code)) {
                mSelectedPositionCode = xml_code;
                holder.switch_client.setVisibility(View.VISIBLE);
                holder.name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                holder.name.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.switch_client.setChecked(false);

            }
            holder.progressBar.setVisibility(View.GONE);
            holder.percent.setVisibility(View.GONE);
        }

        double dept_client = clientList.get(position).getDebt();
        holder.dept.setText(String.valueOf(dept_client) + " ₴");

        if (dept_client > 0){
            holder.dept.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }

        int price_id = Integer.parseInt(clientList.get(position).getPrice_id());
        holder.typePrice.setText(db_handler.getNamePriceByCode(price_id));

        if (clientList.get(position).getXml_id() == mSelectedPositionCode) {
            holder.name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }

        setOnClickListener(position);

        return rowView;
    }

    private void setOnClickListener(int pos) {
        holder.switch_client.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                if (checked){
                    isShow = 1;
                    isChecked = 1;
                    //загружаем цены
                    getPrice();
                }else{
                    isShow = 0;
                    isChecked = 0;
                    handler.removeCallbacks(upData);
                }
                notifyDataSetChanged();
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db_handler.getCartItemCount() == 0) {
                    mSelectedPositionCode = clientList.get(pos).getXml_id();
                } else {
                    mSelectedPositionCode = "";
                    infoMsg(context, "Корзина не пустая");
                }
                isChecked = 0;
                isShow = 0;
                sessionManager.saveSession(Constants.SESSION_LOADING_PRICE, "N");
                notifyDataSetChanged();
            }
        });

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, view);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_client);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.foto_repost:
                                LoadActivityPics(pos);
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }

        });
    }

    public void LoadActivityPics(int pos){
        Intent i = new Intent(context, ClientGallary.class);
        args.putString(Constants.SESSION_CLIENT_CODE, clientList.get(pos).getXml_id());
        i.putExtras(args);
        context.startActivity(i);
        ((Activity) context).overridePendingTransition(0, 0);
    }

    public class Holder {
        RelativeLayout itemLay;
        TextView name, dept, typePrice, percent, buttonViewOption, address;
        Switch switch_client;
        ProgressBar progressBar;
    }

    private void setId(View rowView) {
        holder.name = rowView.findViewById(R.id.name);
        holder.itemLay = rowView.findViewById(R.id.itemLay);
        holder.dept = rowView.findViewById(R.id.debt);
        holder.switch_client = rowView.findViewById(R.id.switch_client);
        holder.progressBar = (ProgressBar)rowView.findViewById(R.id.progressBar);
        holder.typePrice = rowView.findViewById(R.id.typePrice);
        holder.percent = rowView.findViewById(R.id.percent);
        holder.buttonViewOption = (TextView) rowView.findViewById(R.id.textViewOptions);
        holder.address = rowView.findViewById(R.id.address);
    }

    // Получаем prices товаров
    private void getPrice() {

        // Initialize Retrofit
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.PRODUCTS_PRICE_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Call Web Service
        Call<ResponseJSON> call = retrofitInterface.getPrices(basicAuth, client_code);
        call.request();
        call.enqueue(new Callback<ResponseJSON>() {
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                try {
                    if (response.body() != null) {

                        //get price
                        List<PriceVariant> priceVariants = response.body().getPriceVariants();
                        sizeCount = priceVariants.size();
                        holder.progressBar.setProgress(0);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < sizeCount; i++) {

                                    String var_id = priceVariants.get(i).getVar_id();
                                    List<Price> priceList = priceVariants.get(i).getPriceList();

                                        for (int j = 0; j < priceList.size(); j++) {

                                            int type_id = priceList.get(j).getType();
                                            Double value = priceList.get(j).getValue();
                                            db_handler.insertPriceVariant(var_id, type_id, value);

                                        }

                                    handler.post(upData);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();

                    }
                } catch (Exception e) {
                    infoMsg(context, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                infoMsg(context, Util.getErrorMessage(t, context));
            }
        });
    }

    Runnable upData = new Runnable() {
        @Override
        public void run() {
            count = count + 1;
            notifyDataSetChanged();
        }
    };


}

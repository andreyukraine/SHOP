package com.preethzcodez.ecommerce.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.OrdersDataAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.json.OrderInfoJSON;
import com.preethzcodez.ecommerce.objects.OrdersDate;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.Util;
import com.preethzcodez.ecommerce.webservice.RetrofitBuilder;
import com.preethzcodez.ecommerce.webservice.RetrofitInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import static com.preethzcodez.ecommerce.utils.Util.getBase64Auth;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;
import static com.preethzcodez.ecommerce.utils.Util.isNetworkAvaliable;
import static com.preethzcodez.ecommerce.utils.Util.showProgress;

public class Orders extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private DB_Handler db_handler;
    private List<OrdersDate> orderList;
    private ImageView cart;
    private Toolbar toolbar;
    private TextView titleToolbar, count, notOrders, textDialog;
    private ImageView backButton;
    private ListView listView;
    private Bundle args;
    private int cartCount = 0;
    private SwipeRefreshLayout swipeLayout;
    private RetrofitBuilder retrofitBuilder;
    private OkHttpClient httpClient;
    private User user;
    private String basicAuth;
    private ProgressDialog dialogProgress;
    private Dialog dialog;
    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);

        db_handler = new DB_Handler(this);
        retrofitBuilder = new RetrofitBuilder(this);
        httpClient = retrofitBuilder.setClient();
        args = new Bundle();

        //Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_message_view);

        //User
        user = db_handler.getFirstUser();
        basicAuth = getBase64Auth(user.getName(), user.getPassword());

        setId();

        swipeLayout.setOnRefreshListener(this);

        // Set Toolbar
        setSupportActionBar(toolbar);

        // Set Title
        titleToolbar.setText(R.string.my_orders);

        // Back Button
        backButton.setVisibility(View.VISIBLE);

        setOnClickListener();

        fillGridView();

        setToolbarIconsClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.clearOrderDate){
            //получаем последний заказ
            String lastDateOrder = db_handler.getOrders().listIterator().next().getDate();
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -5);
            String dataStart = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());
            if (Integer.valueOf(lastDateOrder) < Integer.valueOf(dataStart)) {
                int isDeleteComplet = db_handler.getOrderByPeriod(dataStart, lastDateOrder);
                if (isDeleteComplet == 1) {
                    infoMsg(this, item.toString());
                }
            }else{
                infoMsg(this, "Немає замовлень для видалення");
            }
            fillGridView();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setOnClickListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    // Set Toolbar Icons Click Listeners
    private void setToolbarIconsClickListeners() {
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartCount > 0) {
                    startActivity(new Intent(getApplicationContext(), ShoppingCart.class));
                    overridePendingTransition(0,0);
                } else {
                    //infoMsg(getApplicationContext(), getString(R.string.cart_empty));
                    textDialog.setText(getString(R.string.cart_empty));
                    dialog.show();
                }
            }
        });
    }

    private void setId() {
        toolbar = findViewById(R.id.toolbar);
        titleToolbar = findViewById(R.id.titleToolbar);
        cart = findViewById(R.id.cart);
        backButton = findViewById(R.id.backButton);
        listView = findViewById(R.id.order_list);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        count = findViewById(R.id.count);
        notOrders = findViewById(R.id.notOrders);
        textDialog = dialog.findViewById(R.id.textDialog);
    }

    private void fillGridView() {
        // Get Orders From DB
        orderList = db_handler.getOrders();
        // Fill ListView
        listView.setAdapter(new OrdersDataAdapter(this, orderList));
        cartCount = db_handler.getCartItemCount();
        if (cartCount > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(cartCount));
        }

        if(orderList.size() > 0){
            listView.setVisibility(View.VISIBLE);
            notOrders.setVisibility(View.GONE);
        }else {
            listView.setVisibility(View.GONE);
            notOrders.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillGridView();
    }

    @Override
    public void onRefresh() {

        //скрываем заставку прокрутка
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }

        dialogProgress = showProgress(Orders.this);

        //проверяем есть интернет или нет
        if (isNetworkAvaliable(this)) {
            for (int i = 0; i < orderList.size(); i++) {
                for (int j = 0; j < orderList.get(i).getOrderList().size(); j++) {
                    String order1c = orderList.get(i).getOrderList().get(j).getNumber_1c();
                    if (order1c != null) {
                        getOrders(order1c);
                    }
                }
            }
            dialogProgress.hide();
        }else{
            dialogProgress.hide();
            infoMsg(this, "Немає інтернету");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("myLogsOrders", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Получаем заказы
    public void getOrders(String orderId){
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.ORDER_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<OrderInfoJSON> call = retrofitInterface.getInfoOrder(basicAuth, orderId);
        call.request();
        call.enqueue(new Callback<OrderInfoJSON>() {
            @Override
            public void onResponse(Call<OrderInfoJSON> call, Response<OrderInfoJSON> response) {
                try {
                    if (response.body() != null) {
                        orderData(response.body());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<OrderInfoJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                Util.getErrorMessage(t, getApplicationContext());
            }
        });
    }

    //Загружаем JSON с клиентами и сохраняем в локальную базу DB
    private void orderData(OrderInfoJSON responseJSON) {
        db_handler.updateStatusOrderById(responseJSON.getNumber(), Integer.valueOf(responseJSON.getStatus()), responseJSON.getDesc());
        fillGridView();
    }

    public void cancelDialog(View view) {
        dialog.cancel();
    }

    public void okDialog(View view) {
        dialog.cancel();
    }
}

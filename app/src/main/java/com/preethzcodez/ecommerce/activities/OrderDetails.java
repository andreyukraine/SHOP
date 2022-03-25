package com.preethzcodez.ecommerce.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.DiscontGridAdapter;
import com.preethzcodez.ecommerce.adapters.DiscontList;
import com.preethzcodez.ecommerce.adapters.GiftListAdapter;
import com.preethzcodez.ecommerce.adapters.OrderListAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.fragments.NewPost;
import com.preethzcodez.ecommerce.json.DiscontProducts;
import com.preethzcodez.ecommerce.json.OrderGiftsJSON;
import com.preethzcodez.ecommerce.json.OrderInfoJSON;
import com.preethzcodez.ecommerce.json.OrderResponse;
import com.preethzcodez.ecommerce.objects.Address;
import com.preethzcodez.ecommerce.objects.Delivery;
import com.preethzcodez.ecommerce.objects.SelectGift;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Discont;
import com.preethzcodez.ecommerce.pojo.Order;
import com.preethzcodez.ecommerce.json.OrderJSON;
import com.preethzcodez.ecommerce.pojo.ProductDiscont;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.pojo.VariantOrder;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.Util;
import com.preethzcodez.ecommerce.webservice.RetrofitBuilder;
import com.preethzcodez.ecommerce.webservice.RetrofitInterface;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import static com.preethzcodez.ecommerce.utils.Util.getBase64Auth;
import static com.preethzcodez.ecommerce.utils.Util.hideProgress;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;
import static com.preethzcodez.ecommerce.utils.Util.showProgress;

public class OrderDetails extends AppCompatActivity implements NewPost.IselectNP, GiftListAdapter.IupdateGifts, DiscontGridAdapter.IselectDiscont, SwipeRefreshLayout.OnRefreshListener {

    private Order order;
    private Client client;
    private DB_Handler db_handler;
    private ImageView backButton;
    private EditText clientOrder, storage, shippingDate, comment, address_order;
    private TextView titleToolbar, totalOrder, cl_bank, textDialog;
    private Toolbar toolbar;
    private TextInputLayout cl_discont, cl_comment, cl_cityNP, cl_pointNP, cl_logist, cl_address_order;
    private TextInputEditText editDiscont, editDelivery, cityNP, pointNP;
    private Button send1CButton, editOrder, modOk, saveOrderDetailBtn;
    private List<VariantOrder> variantList;
    private ListView listView;
    private ImageView cart;
    private Dialog dialog;
    private TabLayout tabs;
    private Discont discont;
    private String ortderTitle, textOrderTotal, orderId;
    private DecimalFormat formatter;
    private Spinner disconts, delivery;
    private RetrofitBuilder retrofitBuilder;
    private OkHttpClient httpClient;
    private String idDiscont;
    private SwipeRefreshLayout swipeLayout;
    private List<SelectGift> selectGift = new ArrayList<>();
    private RetrofitInterface retrofitInterface;
    private User user;
    private Context thisContext;
    private CheckBox checkBoxIsBank;
    private String basicAuth;
    private ProgressDialog dialogProgress;
    private List<String> discontItems;
    private Bundle args;
    private View v;
    private LayoutInflater vi;
    private Delivery deliveryOrder;
    private ViewGroup parent;
    private Address addressOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vi = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //setContentView(R.layout.order_detail);

        db_handler = new DB_Handler(this);
        retrofitBuilder = new RetrofitBuilder(this);
        httpClient = retrofitBuilder.setClient();

        thisContext = this;
        discontItems = db_handler.getDiscounts();
        args = new Bundle();

        //Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_message_view);
        textDialog = dialog.findViewById(R.id.textDialog);

        //User
        user = db_handler.getFirstUser();
        basicAuth = getBase64Auth(user.getName(), user.getPassword());

        formatter = new DecimalFormat("#,###.00");

        // Set Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //updateOrder();
        setValues();

        onRefresh();

        //setUpdateProps();

    }

    private void setUpdateProps(){

        v = vi.inflate(R.layout.order_detail_props, null);
        setContentView(v);

        storage = findViewById(R.id.storag);
        clientOrder = findViewById(R.id.clientOrder);
        shippingDate = findViewById(R.id.shippingDate);
        comment = findViewById(R.id.comment);
        address_order = findViewById(R.id.address_order);
        delivery = findViewById(R.id.delivery);
        disconts = findViewById(R.id.disconts);
        editDiscont = findViewById(R.id.editDiscont);
        cl_discont = findViewById(R.id.cl_discont);
        cl_comment = findViewById(R.id.cl_comment);
        checkBoxIsBank = findViewById(R.id.checkBoxIsBank);
        modOk = dialog.findViewById(R.id.buttonOk);
        saveOrderDetailBtn = findViewById(R.id.saveOrderDetailBtn);
        cl_cityNP = findViewById(R.id.cl_cityNP);
        cl_pointNP = findViewById(R.id.cl_pointNP);
        cl_logist = findViewById(R.id.cl_logist);
        cl_address_order = findViewById(R.id.cl_address_order);
        cityNP = findViewById(R.id.cityNP);
        pointNP = findViewById(R.id.pointNP);
        swipeLayout = findViewById(R.id.swipe_container);

        parent = (ViewGroup)disconts.getParent();

        swipeLayout.setOnRefreshListener(this);

        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }

        setToolbarView();

        setViewTabs("Информация");

        clientOrder.setText(client.getName());
        shippingDate.setText(order.getShipping_date());
        storage.setText(db_handler.getNameStore(order.getStore_id()));

        //адрес доставки
        addressOrder = db_handler.getAddresOrderByClient(client.getXml_id(), order.getAddress_code());

        //комментарий
        if (!order.getComment().equals("")) {
            cl_comment.setVisibility(View.VISIBLE);
            comment.setText(order.getComment());
        }else{
            cl_comment.setVisibility(View.GONE);
            comment.setText("Нет комментария");
        }

        delivery.setSelection(0,true);

        if (order.getNumber_1c() != null) {
            setDiscontView(true);
            setBankView(true);
            setDeliveryView(true);
        } else {
            setDiscontView(false);
            setBankView(false);
            setDeliveryView(false);
        }

        //setCommentView();

        setViewButton();

        setTotalOrder();

        setOnClickListener();

        setOnClickListenerAll();
    }

    private void setCommentView(){
        if (order.getNumber_1c() == null) {
            comment.setEnabled(true);
            comment.setClickable(true);
        }else{
            comment.setEnabled(false);
            comment.setClickable(false);
        }
    }

    private void setDiscontView(boolean view){
        if (view) {

            cl_discont.setVisibility(View.VISIBLE);
            if (order.getStatus() == 1) {
                if (deliveryOrder.getIsLogist() == 0 && deliveryOrder.getIsPickup() == 0) {
                    //делаем проверку на акцию в заказе

                    if (!order.getDiscont().equals("")) {
                        editDiscont.setText(discont.getName());
                        editDiscont.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        editDiscont.setEnabled(false);
                    } else {
                        editDiscont.setText("Выберите акцию ...");
                        DiscontList adapter = new DiscontList(this, R.layout.discont_list, R.id.title, db_handler.getListDiscounts());
                        disconts.setAdapter(adapter);
                        editDiscont.setEnabled(true);
                    }
                }else{
                    String discontName = "Выберите акцию ...";
                    if (discont.getName() != null){
                        discontName = discont.getName();
                    }
                    editDiscont.setText(discontName);
                    editDiscont.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    editDiscont.setEnabled(false);
                }
            } else {
                if (discont.getName() != null) {
                    editDiscont.setText(discont.getName());
                }else{
                    editDiscont.setText("Нет акции ...");
                }
                editDiscont.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                editDiscont.setEnabled(false);
            }
        } else {
            cl_discont.setVisibility(View.GONE);
        }
    }

    private void setDeliveryView(boolean view){

        editDelivery = findViewById(R.id.editDelivery);
        editDelivery.setVisibility(View.GONE);
        cl_logist.setVisibility(View.GONE);

        address_order.setText(addressOrder.getName());
        cl_address_order.setVisibility(View.VISIBLE);

        String deliveryName = "";

        if (order.getIsBase() == 1) {

            editDelivery.setEnabled(false);
            editDelivery.setVisibility(View.VISIBLE);
            cl_logist.setVisibility(View.VISIBLE);

            if (deliveryOrder.getIsLogist() == 1 && deliveryOrder.getIsNewPost() != 1) {
                deliveryName = "Наша доставка";
                address_order.setText(addressOrder.getName());
                cl_address_order.setVisibility(View.VISIBLE);
            } else if (deliveryOrder.getIsPickup() == 1) {
                deliveryName = "Самовывоз";
                address_order.setVisibility(View.GONE);
                cl_address_order.setVisibility(View.GONE);
            } else if (deliveryOrder.getIsNewPost() == 1) {
                deliveryName = "Новая почта";

                address_order.setVisibility(View.GONE);
                cl_address_order.setVisibility(View.GONE);

                cl_cityNP.setVisibility(View.VISIBLE);
                cl_pointNP.setVisibility(View.VISIBLE);
                cityNP.setVisibility(View.VISIBLE);
                pointNP.setVisibility(View.VISIBLE);
                cityNP.setText(db_handler.getCityByCode(deliveryOrder.getCity()));
                pointNP.setText(db_handler.getPointByCode(deliveryOrder.getPoint()));
            }else{
                deliveryName = "Доставка не указана";
            }

            editDelivery.setText(deliveryName);

            if (view && order.getStatus() == 1) {
                if (deliveryOrder.getIsLogist() == 0 && deliveryOrder.getIsPickup() == 0 && deliveryOrder.getIsNewPost() == 0) {
                    editDelivery.setEnabled(true);
                    editDelivery.setVisibility(View.VISIBLE);

                    editDelivery.setText("Выберите доставку ... ");
                    String arrayDelivery[] = {"----Выберите тип доставки----", "Наша доставка", "Самовывоз", "Новая почта"};
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.delivery_spinner_item, arrayDelivery);
                    delivery.setAdapter(spinnerArrayAdapter);
                }else{
                    editDelivery.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }else{
                editDelivery.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }

    private void setBankView(boolean view){

        if (view){
            if (order.getStatus() == 1) {
                if (deliveryOrder.getIsLogist() != 1) {

                    checkBoxIsBank.setVisibility(View.VISIBLE);

                    if (order.getIs_bank() == 1) {
                        checkBoxIsBank.setChecked(true);
                        checkBoxIsBank.setEnabled(false);
                    } else {
                        checkBoxIsBank.setChecked(false);
                        checkBoxIsBank.setEnabled(true);
                    }
                }else{
                    if (order.getIs_bank() == 1) {
                        checkBoxIsBank.setChecked(true);
                    } else {
                        checkBoxIsBank.setChecked(false);
                    }
                    checkBoxIsBank.setEnabled(false);
                }
            }else{
                if (order.getIs_bank() == 1) {
                    checkBoxIsBank.setChecked(true);
                } else {
                    checkBoxIsBank.setChecked(false);
                }
                //cl_bank.setTextColor(this.getResources().getColor(R.color.not_empty_text));
                checkBoxIsBank.setEnabled(false);
            }
        }else{
            checkBoxIsBank.setVisibility(View.GONE);
        }
    }

    private void setTotalOrder(){
        totalOrder = findViewById(R.id.totalOrder);
        textOrderTotal = formatter.format(order.getTotal());
        if (order.getTotal_1c() > 0) {
            textOrderTotal = formatter.format(order.getTotal_1c());
        }
        totalOrder.setText(textOrderTotal + " ₴");
    }

    private void setToolbarView(){

        cart = findViewById(R.id.cart);
        backButton = findViewById(R.id.backButton);
        cart.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);

        //убираем менеджера из номера заказа
        String[] numberSplit = order.getNumber().split("-");
        titleToolbar = findViewById(R.id.titleToolbar);
        ortderTitle = numberSplit[0];

        if (order.getNumber_1c() != null) {
            ortderTitle = ortderTitle + "/" + order.getNumber_1c();
        }

        titleToolbar.setText("№" + ortderTitle);

    }

    private void setUpdateProducts(){

        v = vi.inflate(R.layout.order_detail_list, null);
        setContentView(v);

        variantList = db_handler.getVariantByOrderId(order.getNumber());

        setToolbarView();

        String titleTab = "Товары (" + Integer.toString(variantList.size()) + ")";
        setViewTabs(titleTab);

        listView = v.findViewById(R.id.order_product_list);
        listView.setAdapter(new OrderListAdapter(getApplicationContext(), variantList, order));

        setViewButton();

        setTotalOrder();

        setOnClickListenerAll();

    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            if (fragmentManager.findFragmentByTag("NP_TAG").isVisible()) {
                Intent intent = new Intent(this, OrderDetails.class);
                intent.putExtra(Constants.ORDER_ID, order.getNumber());
                this.startActivity(intent);
                ((Activity)this).finish();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

            Intent orders = new Intent(this, Orders.class);
            startActivity(orders);
            overridePendingTransition(0, 0);
        }

        super.onBackPressed();
    }

    @Override
    public void updateGift(List<SelectGift> selectGift, int countGift, String idDiscont, int position) {
        this.selectGift = selectGift;
        this.idDiscont = idDiscont;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        dialogProgress = showProgress(this);
        if (order.getNumber_1c() != null) {
            Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.ORDER_URL);
            retrofitInterface = retrofit.create(RetrofitInterface.class);
            // Call Web Service
            Call<OrderInfoJSON> call = retrofitInterface.getInfoOrder(basicAuth, order.getNumber_1c());
            call.request();
            call.enqueue(new Callback<OrderInfoJSON>() {
                @Override
                public void onResponse(Call<OrderInfoJSON> call, Response<OrderInfoJSON> response) {
                    try {
                        if (response.body() != null) {
                            hideProgress(dialogProgress);
                            orderData(response.body());
                            updateOrder();
                        }else{
                            hideProgress(dialogProgress);
                            updateOrder();
                        }
                    } catch (Exception e) {
                        hideProgress(dialogProgress);
                        infoMsg(getApplicationContext(), e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<OrderInfoJSON> call, Throwable t) {
                    //тут смотрим что за ошибка при парсинге json
                    hideProgress(dialogProgress);
                    Util.getErrorMessage(t, getApplicationContext());
                }

            });
            //infoMsg(this, "Тестируем обработчик обновления заказа");
        }else{
            hideProgress(dialogProgress);
            updateOrder();
        }
    }

    private void setViewButton() {

        send1CButton = findViewById(R.id.send1CButton);
        editOrder = findViewById(R.id.editOrder);

        if (order.getBase() == 0) {
            if (order.getTotal() > 0.0) {
                send1CButton.setVisibility(View.VISIBLE);
                editOrder.setVisibility(View.VISIBLE);
            } else {
                send1CButton.setVisibility(View.GONE);
                editOrder.setVisibility(View.GONE);
            }
        } else {
            send1CButton.setVisibility(View.GONE);
            editOrder.setVisibility(View.GONE);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void setViewTabs(String selectName){

        variantList = db_handler.getVariantByOrderId(order.getNumber());
        String titleTab = "Товары (" + Integer.toString(variantList.size()) + ")";
        tabs = findViewById(R.id.tabs);


        if (selectName.equals(titleTab)){
            tabs.addTab(tabs.newTab().setText("Информация"));
            tabs.addTab(tabs.newTab().setText(titleTab), true);
        }else{
            tabs.addTab(tabs.newTab().setText("Информация"), true);
            tabs.addTab(tabs.newTab().setText(titleTab));
        }

    }

    private void setOnClickListenerAll(){
        //при клике на таб
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        //Props
                        setUpdateProps();
                        break;
                    case 1:
                        // Products
                        setUpdateProducts();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrderNew();
            }
        });

        send1CButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendOrder1C();
                    }
                }, 1000);
            }
        });

    }

    private void setOnClickListener() {

        editDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delivery.performClick();
            }
        });

        saveOrderDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("записываем комментарий");
            }
        });

        delivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                switch (position) {
                    case 1:
                        setLogist();
                        break;
                    case 2:
                        setPickup();
                        break;
                    case 3:
                        if (deliveryOrder.getIsLogist() == 0) {

                            NewPost np = new NewPost();
                            args.putString(Constants.ORDER_ID, orderId);
                            np.setArguments(args);

                            // Prevent Reload Same Fragment
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.setCustomAnimations(R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out);
                            ft.add(R.id.order_prop_block, np, "NP_TAG");
                            ft.commit();
                            break;
                        }else{
                            infoMsg(thisContext, "Заказ уже в работе у логиста");
                        }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editDiscont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconts.performClick();
            }
        });

        checkBoxIsBank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxIsBank.isChecked()) {
                    setBank();
                }
            }
        });

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("before " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("on changed " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("after " + s);
            }
        });

    }

    private void setPickup() {
        // Initialize Retrofit
        httpClient = retrofitBuilder.setClient();
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.ORDER_URL);
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<OrderResponse> call = retrofitInterface.setPickupOrder(basicAuth, order.getNumber_1c());
        call.request();
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                try {
                    if (response.body() != null) {

                        String code = response.body().getStatus().get(0).getCode();
                        String text_status = response.body().getStatus().get(0).getText();
                        dialog = new Dialog(OrderDetails.this);
                        switch (code) {
                            case "502":
                                textDialog.setText(text_status);
                                dialog.show();
                                break;
                            case "200":
                                //записываем в базу ответ из 1с
                                db_handler.setPickupOrder(order.getNumber(), 1);
                                onRefresh();
                                break;
                        }

                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                // выводим значения если проблемы с интернетом
                infoMsg(getApplicationContext(), Util.getErrorMessage(t, OrderDetails.this));
            }

        });
    }

    private void setLogist() {
        // Initialize Retrofit
        httpClient = retrofitBuilder.setClient();
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.ORDER_URL);
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<OrderResponse> call = retrofitInterface.setLogistOrder(basicAuth, order.getNumber_1c());
        call.request();
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                try {
                    if (response.body() != null) {

                        String code = response.body().getStatus().get(0).getCode();
                        String text_status = response.body().getStatus().get(0).getText();
                        dialog = new Dialog(OrderDetails.this);
                        switch (code) {
                            case "502":
                                textDialog.setText(text_status);
                                dialog.show();
                                break;
                            case "200":
                                //записываем в базу ответ из 1с
                                db_handler.setLogistOrder(order.getNumber(), 1, "");
                                onRefresh();
                                break;
                        }

                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                // выводим значения если проблемы с интернетом
                infoMsg(getApplicationContext(), Util.getErrorMessage(t, OrderDetails.this));
            }

        });
    }

    private void setBank() {
        // Initialize Retrofit
        httpClient = retrofitBuilder.setClient();
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.ORDER_URL);
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<OrderResponse> call = retrofitInterface.setBankOrder(basicAuth, order.getNumber_1c());
        call.request();
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                try {
                    if (response.body() != null) {

                        String code = response.body().getStatus().get(0).getCode();
                        String text_status = response.body().getStatus().get(0).getText();
                        dialog = new Dialog(OrderDetails.this);
                        switch (code) {
                            case "502":
                                dialog.setContentView(R.layout.dialog_message_view);
                                textDialog.setText(text_status);
                                dialog.show();
                                checkBoxIsBank.setEnabled(true);
                                break;
                            case "200":
                                //записываем в базу ответ из 1с
                                db_handler.setBankOrder(orderId);
                                onRefresh();
                                break;
                        }

                    }
                } catch (Exception e) {
                    Util.getErrorMessage(e, getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                // выводим значения если проблемы с интернетом
                infoMsg(getApplicationContext(), Util.getErrorMessage(t, OrderDetails.this));
            }

        });
    }

    private void editOrderNew() {
        //нужно проверить чтобы корзина была пустая
        if (db_handler.getCartItemCount() == 0) {
            Order order = db_handler.getOrderId(orderId);
            //потом загрузим заказ в корзину
            db_handler.ImportOrderToCart(orderId);
            //переходим в корзину
            finish();
            Intent home = new Intent(this, ShoppingCart.class);
            home.putExtra(Constants.ORDER_COMMENT, order.getComment());
            home.putExtra(Constants.ORDER_CLIENT, order.getClient_code());
            home.putExtra(Constants.ORDER_SHIPPING_DATE, order.getShipping_date());
            home.putExtra(Constants.ORDER_ADDRESS_CODE, order.getAddress_code());
            home.putExtra(Constants.ORDER_IS_EDIT, "1");
            startActivity(home);
        } else {
            //infoMsg(this, "Корзина не пустая, закончите заказ или очистите корзину");
            textDialog.setText("Корзина не пустая, закончите заказ или очистите корзину");
            dialog.show();
        }
    }

    private void sendOrder1C() {
        //list and count products
        String listProducts = "";

        dialogProgress = showProgress(OrderDetails.this);

        variantList = db_handler.getVariantByOrderId(order.getNumber());

        for (int i = 0; i < variantList.size(); i++) {
            String barcode = variantList.get(i).getBarcode();
            int count_order = db_handler.getQuantityByVariantId(variantList.get(i).getVariant_id(), order.getNumber());

            String item = barcode + ":" + count_order + ",";
            if (i == variantList.size() - 1) {
                item = barcode + ":" + count_order;
            }
            listProducts = listProducts + item;
        }

        // Initialize Retrofit
        httpClient = retrofitBuilder.setClient();
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.ORDER_URL);
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<ArrayList<OrderJSON>> call = retrofitInterface.addOrder(basicAuth, order.getClient_code(), listProducts, order.getStore_id(), order.getNumber(), order.getComment(), order.getShipping_date(), order.getAddress_code());
        call.request();
        call.enqueue(new Callback<ArrayList<OrderJSON>>() {

            @Override
            public void onResponse(Call<ArrayList<OrderJSON>> call, Response<ArrayList<OrderJSON>> response) {
                try {
                    if (response.body() != null) {
                        String code = response.body().get(0).getResult().get(0).getCode();
                        String text_status = response.body().get(0).getResult().get(0).getText();

                        switch (code) {
                            case "502":
                                hideProgress(dialogProgress);
                                dialog.setContentView(R.layout.dialog_message_view);
                                textDialog.setText(text_status);
                                dialog.show();
                                send1CButton.setClickable(true);
                                break;
                            case "200":
                                //записываем в базу ответ из 1с
                                db_handler.UpdateOrderBy1C(order, response.body());
                                hideProgress(dialogProgress);

                                //update
                                onRefresh();

                                setViewButton();

                                textDialog.setText(text_status);
                                dialog.show();
                                break;
                        }

                    }else{
                        hideProgress(dialogProgress);
                        textDialog.setText("Нет ответа от сервера");
                        dialog.show();
                    }
                } catch (Exception e) {
                    send1CButton.setClickable(true);
                    hideProgress(dialogProgress);
                    textDialog.setText(e.getMessage());
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderJSON>> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                // выводим значения если проблемы с интернетом
                send1CButton.setClickable(true);
                hideProgress(dialogProgress);
                infoMsg(getApplicationContext(), Util.getErrorMessage(t, OrderDetails.this));

            }

        });
    }

    public void updateOrder() {
        setValues();
        setUpdateProps();
    }

    private void setValues() {

        // Get Order Id
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);

        order = db_handler.getOrderId(orderId);
        client = db_handler.getClientsById(order.getClient_code());

        ///////db_handler.getPriceTypeByClient(client.getXml_id(), order.getAddress_code());

        deliveryOrder = db_handler.getDeliveryOrderNumber(order.getNumber());

        if (order.getDiscont() != null) {
            discont = db_handler.getDiscontByCode(order.getDiscont());
        }

    }

    private void setDiscounts(String orderId, String discontName) {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.SET_DISCONTS_PRODU_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        String discontCode = db_handler.getDiscontCode(discontName);

        Call<DiscontProducts> call = retrofitInterface.setDiscoutOrder(basicAuth, orderId, discontCode);
        call.request();
        call.enqueue(new Callback<DiscontProducts>() {
            @Override
            public void onResponse(Call<DiscontProducts> call, Response<DiscontProducts> response) {
                try {
                    if (response.body() != null) {
                        String code = response.body().getStatus().get(0).getCode();
                        String text_status = response.body().getStatus().get(0).getText();
                        hideProgress(dialogProgress);
                        switch (code) {
                            case "502":
                                textDialog.setText(text_status);
                                break;
                            case "3_1":
                                selectGift(response.body());
                                onRefresh();
                                break;
                            case "1_1":
                                textDialog.setText(text_status);
                                onRefresh();
                                break;
                            case "1_2":
                                textDialog.setText(text_status);
                                onRefresh();
                                break;
                            case "2_1":
                                textDialog.setText(text_status);
                                onRefresh();
                                break;
                            case "2_2":
                                textDialog.setText(text_status);
                                onRefresh();
                                break;
                            case "0_0":
                                textDialog.setText(text_status);
                                break;
                            default:
                        }
                        dialog.show();
                    } else {
                        hideProgress(dialogProgress);
                    }
                } catch (Exception e) {
                    hideProgress(dialogProgress);
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DiscontProducts> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                // выводим значения если проблемы с интернетом
                hideProgress(dialogProgress);
                infoMsg(getApplicationContext(), Util.getErrorMessage(t, OrderDetails.this));
            }
        });
    }

    private void selectGift(DiscontProducts discontProducts) {
        dialog.setContentView(R.layout.gifts_list_view);
        ListView listViewDialog = dialog.findViewById(R.id.gifts_list_view);

        //количество подарков для выбора
        TextView countAllGift = dialog.findViewById(R.id.countAll);
        countAllGift.setText(Integer.toString(discontProducts.colgift));


        //убираем подарки у которых нет остатка
        List<ProductDiscont> newListProducts = new ArrayList<>();
        List<ProductDiscont> products = discontProducts.products;
        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getOstatok() > 0) {
                ProductDiscont prt = new ProductDiscont();
                String art = products.get(i).getArt();
                String barcode = products.get(i).getBarcode();
                int code = products.get(i).getCode();
                int count = products.get(i).getCount();
                String name = products.get(i).getName();
                String option = products.get(i).getOption();
                int ostatok = products.get(i).getOstatok();
                double price = products.get(i).getPrice();

                prt.setArt(art);
                prt.setBarcode(barcode);
                prt.setCode(code);
                prt.setCount(count);
                prt.setName(name);
                prt.setOption(option);
                prt.setOstatok(ostatok);
                prt.setPrice(price);

                newListProducts.add(prt);
            }
        }
        listViewDialog.setAdapter(new GiftListAdapter(this, newListProducts, discontProducts.getColgift(), discontProducts.getIdDiscont()));

        listViewDialog.setDividerHeight(1);
        listViewDialog.setFocusable(true);
        listViewDialog.setClickable(true);
        listViewDialog.setFocusableInTouchMode(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.show();

        dialog.getWindow().setAttributes(lp);
    }

    public void cancelDialog(View view) {
        dialog.cancel();
    }

    public void okDialog(View view) {
        dialogProgress = showProgress(this);
        sendGifts1C();
        dialog.cancel();
    }

    private void sendGifts1C() {
        // Initialize Retrofit
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.ORDER_URL);
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        //list and count products
        String listProducts = "";
        for (int i = 0; i < selectGift.size(); i++) {
            String barcode = selectGift.get(i).getProduct().getBarcode();
            int count_order = selectGift.get(i).getCountGift();

            String item = barcode + ":" + count_order + ",";
            if (i == selectGift.size() - 1) {
                item = barcode + ":" + count_order;
            }
            listProducts = listProducts + item;
        }

        if (selectGift.size() > 0) {
            updateOrderGifts(retrofitInterface, listProducts);
        }else{
            hideProgress(dialogProgress);
            infoMsg(this, "Выберите подарки");
        }
    }

    private void updateOrderGifts(RetrofitInterface retrofitInterface, String listProducts) {
        // Call Web Service
        Call<ArrayList<OrderGiftsJSON>> call = retrofitInterface.setGiftsOrder(basicAuth, order.getNumber_1c(), idDiscont, listProducts);
        call.request();
        call.enqueue(new Callback<ArrayList<OrderGiftsJSON>>() {

            @Override
            public void onResponse(Call<ArrayList<OrderGiftsJSON>> call, Response<ArrayList<OrderGiftsJSON>> response) {
                try {
                    if (response.body() != null) {
                        //записываем в базу ответ из 1с
                        db_handler.UpdateOrderGiftsBy1C(order, response.body());
                        hideProgress(dialogProgress);
                        onRefresh();
                    }
                } catch (Exception e) {
                    hideProgress(dialogProgress);
                    infoMsg(OrderDetails.this, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderGiftsJSON>> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                // выводим значения если проблемы с интернетом
                hideProgress(dialogProgress);
                infoMsg(getApplicationContext(), Util.getErrorMessage(t, OrderDetails.this));
            }

        });
    }

    private void orderData(OrderInfoJSON responseJSON) {
        db_handler.updateAllOrderById(
                responseJSON.getNumber(),
                Integer.valueOf(responseJSON.getStatus()),
                responseJSON.getDesc(),
                responseJSON.getDiscont(),
                responseJSON.getProducts(),
                order.getNumber(),
                order.getClient_code(),
                responseJSON.getSum_order(),
                responseJSON.getIs_bank(),
                responseJSON.getShipping_date(),
                responseJSON.getAddress_code()
        );

        db_handler.setLogistOrder(order.getNumber(), responseJSON.getLogist(), "");
        db_handler.setPickupOrder(order.getNumber(),responseJSON.getPickup());
        db_handler.setNewPostOrder(order.getNumber(), responseJSON.getDelivery_np(), responseJSON.getCity_code_np(), responseJSON.getPost_code_np());

        //setUpdateProps();
    }

    @Override
    public void selectNP(String selectCityName, String selectCityId, String selectPointName, String selectPointId) {

        // Initialize Retrofit
        httpClient = retrofitBuilder.setClient();
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.ORDER_URL);
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<OrderResponse> call = retrofitInterface.setDeliveryNPOrder(basicAuth, order.getNumber_1c(), selectCityId, selectPointId);
        call.request();
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                try {
                    if (response.body() != null) {

                        String code = response.body().getStatus().get(0).getCode();
                        String text_status = response.body().getStatus().get(0).getText();
                        dialog = new Dialog(OrderDetails.this);
                        switch (code) {
                            case "502":
                                dialog.setContentView(R.layout.dialog_message_view);
                                textDialog.setText(text_status);
                                dialog.show();
                                break;
                            case "200":
                                //записываем в базу ответ из 1с
                                db_handler.setNewPostOrder(order.getNumber(), 1, selectCityId, selectPointId);
                                onRefresh();
                                //cityNP.setText(selectCityName);
                                //pointNP.setText(selectPointName);
                                //setUpdateProps();
                                break;
                        }

                    }
                } catch (Exception e) {
                    Util.getErrorMessage(e, getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                // выводим значения если проблемы с интернетом
                infoMsg(getApplicationContext(), Util.getErrorMessage(t, OrderDetails.this));
            }

        });
    }

    @Override
    public void selectDiscont(String code, String name){
        parent.removeView(disconts);
        setDiscounts(order.getNumber_1c(), name);
    }
}

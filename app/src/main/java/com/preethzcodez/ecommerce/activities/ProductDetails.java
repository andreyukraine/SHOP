package com.preethzcodez.ecommerce.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.ExpListAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.json.ProductJSON;
import com.preethzcodez.ecommerce.json.ResponseJSON;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Count;
import com.preethzcodez.ecommerce.pojo.Price;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.pojo.ProductOption;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.pojo.Variant;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.Util;
import com.preethzcodez.ecommerce.webservice.RetrofitBuilder;
import com.preethzcodez.ecommerce.webservice.RetrofitInterface;
import com.squareup.picasso.Picasso;
import org.apmem.tools.layouts.FlowLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import static android.view.Gravity.CENTER;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static com.preethzcodez.ecommerce.utils.Util.getBase64Auth;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;
import static com.preethzcodez.ecommerce.utils.Util.isNetworkAvaliable;
import static com.preethzcodez.ecommerce.utils.Util.showProgress;

public class ProductDetails extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Product product;
    private DB_Handler db_handler;
    private String selectedSku, selectedBarcode, selected = null, selectedItemVariantId,selectedItemStoreId, user_email = null, client_code = null, product_code = null, basicAuth, loading_client_price;
    private SwipeRefreshLayout refreshLayout;
    private List<Price> priceList;
    private boolean isNotStores;
    private int countVariantIsFree, selectedStoreCount, countStore, selectedItemQuantity = 0, cartCount = 0;
    private double selectedTotalPrice = 0,base_price,client_price;
    private LinearLayout optionsParentLay, countParentLay;
    private FlowLayout optionLay, countLay;
    private TextView priceBase, priceClient, quantityValue, sku, barcode, title_sku, title_barcode, title_product, percent, totalText, opt_cart, store_cart;
    private ImageView minus, plus, backButton, image;
    private List<Variant> variantList;
    private SessionManager sessionManager;
    private Toolbar toolbar;
    private DecimalFormat formatted;
    private User user;
    private TableLayout bottomLay;
    private Client client;
    private View border_botom;
    private ProgressDialog dialogProgress;
    private RetrofitBuilder retrofitBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        sessionManager = new SessionManager(this);
        db_handler = new DB_Handler(this);
        retrofitBuilder = new RetrofitBuilder(this);

        //User
        user = db_handler.getFirstUser();
        basicAuth = getBase64Auth(user.getName(), user.getPassword());

        setIds();

        formatted = new DecimalFormat("#0.00");

        // если менеджер загрузил цены для клиента нужно показывать ценны клиента без интернета
        loading_client_price = sessionManager.getSessionData(Constants.SESSION_LOADING_PRICE);

        // Set Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide Title
        TextView titleToolbar = findViewById(R.id.titleToolbar);
        titleToolbar.setText("Картка товару");

        // Back Button
        backButton.setVisibility(View.VISIBLE);

        // Get Product code
        product_code = getIntent().getStringExtra(Constants.PRODUCT_ID_KEY);
        user_email = sessionManager.getSessionData(Constants.SESSION_EMAIL);
        client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);

        // Get Product Details By Id
        product = db_handler.getProductDetailsById(product_code);
        title_product.setText(product.getName());

        client = db_handler.getClientsById(client_code);

//        // Находим наш list
//        ExpandableListView listView = (ExpandableListView)findViewById(R.id.exListView);
//        //Создаем набор данных для адаптера
//        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
//        ArrayList<String> children1 = new ArrayList<String>();
//        ArrayList<String> children2 = new ArrayList<String>();
//        children1.add("Child_1");
//        children1.add("Child_2");
//        groups.add(children1);
//        children2.add("Child_1");
//        children2.add("Child_2");
//        children2.add("Child_3");
//        groups.add(children2);
//        //Создаем адаптер и передаем context и список с данными
//        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups);
//        listView.setAdapter(adapter);


//<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        android:orientation="vertical"
//        android:layout_below="@id/toolbar"
//        android:layout_width="fill_parent"
//        android:id="@+id/ddd"
//        android:layout_height="wrap_content"
//                >
//        <ExpandableListView
//        android:id="@+id/exListView"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:indicatorLeft="250dp"
//        android:indicatorRight="300dp"
//                />
//    </LinearLayout>



        setToolbarIconsClickListeners();

        setQuantityUpdateListeners();

        setOnClickListeners();

    }

    private void setOnClickListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        refreshLayout.setOnRefreshListener(this);
    }

    public void addCartPlus(View view){
        quantityValue.setCursorVisible(false);

        //скрыть клавиатуру ввода
        hideInputKeyboard(view);

        isSuccessAddingToCart();
        setValues();
    }

    public void addCartMinus(View view){
        quantityValue.setCursorVisible(false);

        //скрыть клавиатуру ввода
        hideInputKeyboard(view);

        isSuccessRemoveToCart();
        setValues();
    }

    public void hideInputKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    private void setIds() {
        backButton = findViewById(R.id.backButton);
        optionsParentLay = findViewById(R.id.optionsParentLay);
        countParentLay = findViewById(R.id.countParentLay);
        countLay = findViewById(R.id.countLay);
        optionLay = findViewById(R.id.optionLay);
        priceBase = findViewById(R.id.priceBase);
        priceClient = findViewById(R.id.priceClient);
        quantityValue = findViewById(R.id.quantityValue);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        sku = findViewById(R.id.sku);
        barcode = findViewById(R.id.barcode);
        title_sku = findViewById(R.id.title_sku);
        title_barcode = findViewById(R.id.title_barcode);
        title_product = findViewById(R.id.title);
        percent = findViewById(R.id.percent);
        refreshLayout = findViewById(R.id.refresherDetailProduct);
        totalText = findViewById(R.id.totalText);
        image = findViewById(R.id.image);
        border_botom = findViewById(R.id.border_botom);
        bottomLay = findViewById(R.id.bottomLay);
    }

    private void updateProduct(){
        //Получаем цены и остатки по товару
        getInfoProductByClient(product_code, client_code);
    }

    private void getInfoProductByClient(String product_code, String client_code) {

        dialogProgress = showProgress(this);

        // Initialize Retrofit
        OkHttpClient httpClient = retrofitBuilder.setClient();
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.PRODUCT_INFO_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<ResponseJSON> call = retrofitInterface.getProductInfo(basicAuth,product_code,client_code);
        call.request();
        call.enqueue(new Callback<ResponseJSON>() {
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                try {
                    if (response.body() != null) {
                        refreshLayout.setRefreshing(true);
                        //скрываем заставку прокрутка
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        insertInfoProductByClient(response.body());
                        dialogProgress.hide();

                    }
                } catch (Exception e) {
                    dialogProgress.hide();
                    infoMsg(getApplicationContext(), e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                // выводим значения если проблемы с интернетом
                dialogProgress.hide();
                infoMsg(getApplicationContext(), Util.getErrorMessage(t, ProductDetails.this));
                setValues();
            }
        });
    }

    private void insertInfoProductByClient(ResponseJSON body) {
        List<ProductJSON> list = body.getJsonList();
        if (list != null){
            for (int i = 0; i < list.size(); i++) {
                String var_id = list.get(i).getVar_id();
                //обновляем цены
                List<Price> prices = list.get(i).getPriceList();
                for (int j = 0; j < prices.size(); j++) {
                    int type = prices.get(j).getType();
                    double value = prices.get(j).getValue();
                    db_handler.insertPriceVariant(var_id,type,value);
                }
                //обновляем количество
                List<Count> countList = list.get(i).getCountList();
                for (int h = 0; h < countList.size(); h++) {
                    String store = countList.get(h).getStore();
                    double count_store = countList.get(h).getCount();
                    db_handler.insertCountStore(var_id, store, count_store);
                }
            }
        }
        //выводим значения
        setValues();

    }

    private void setToolbarIconsClickListeners() {
        ImageView cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartCount > 0) {
                    startActivity(new Intent(getApplicationContext(), ShoppingCart.class));
                    updateCartCount();
                }
            }
        });
    }

    private void updateCartCount() {
        cartCount = db_handler.getCartItemCount();
        TextView count = findViewById(R.id.count);
        if (cartCount > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(cartCount));
        } else {
            count.setVisibility(View.GONE);
        }
    }

    private boolean isSuccessAddingToCart() {
        long status  = db_handler.insertIntoCart(product.getProduct_code(), selectedItemVariantId, selectedItemQuantity, client_price, selectedItemStoreId, client_code);

        updateCartCount();
        selectedTotalPrice();
        setValues();
        return true;
    }

    private boolean isSuccessRemoveToCart() {
        if (selectedItemQuantity >= 0) {
            updateCartCount();
            selectedTotalPrice();
            setValues();
        }
        return true;
    }

    private int getCountCartItemByStore(String product_code, String var_id, String store_id){
        int count = db_handler.getCountCartItemByStore(product_code, var_id, store_id);
        if (count == 0){
            db_handler.deleteCartVariantId(var_id, store_id);
            updateCartCount();
        }
        return count;
    }

    private void setValues() {
        variantList = db_handler.getProductVariant(product.getProduct_code(), client.getClient_type());
        setOptionsLayout(variantList);
    }

    private void setOptionsLayout(final List<Variant> variantList) {
        //зачистим все характеристики в блоке
        optionLay.removeAllViews();
        //чистим все остатки в блоке
        countLay.removeAllViews();

        if (variantList != null) {
            if (variantList.size() > 0) {
                for (int i = 0; i < variantList.size(); i++) {
                    String VariantId = variantList.get(i).getId();
                    List<ProductOption> productOptions = variantList.get(i).getProductOptions();

                    //опции или характеристики
                    for (int j = 0; j < productOptions.size(); j++) {

                        final TextView opt = new TextView(this);
                        opt_cart = new TextView(this);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            opt.setBackground(getResources().getDrawable(R.drawable.rounded_opt));
                        } else {
                            opt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_opt));
                        }

                        opt.setPadding(15,15,15,15);
                        opt.setTextColor(getResources().getColor(R.color.black));

                        Variant variant = db_handler.getVariantDetailsById(VariantId);

                        opt.setText(productOptions.get(j).getOpt_val());

                        List<Count> countList = variant.getCount();

                        priceList = variant.getPrice();

                        //проверяем если первый елемент и не выбра тогда выделяем рамкой виделяем его ддругой рамкой
                        if(i == 0 && selectedItemVariantId == null){

                            selectedItemVariantId = variant.getVariant_id();
                            selectedSku = variant.getSku();
                            selectedBarcode = variant.getBarcode();

                            //свойства
                            setPropertyVariant(selectedSku, selectedBarcode);

                            //остатки
                            insertCountVariant(countList);

                            //количество в корзине
                            int countCart = db_handler.getCountCartItem(product_code, variant.getVariant_id());
                            opt_cart.setText(String.valueOf(countCart));

                            //цены
                            insertPriceVariant(priceList);

                            //обновляем итоговое сумму для корзины
                            selectedTotalPrice();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                opt.setBackground(getResources().getDrawable(R.drawable.rounded_btn_grey));
                            } else {
                                opt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn_grey));
                            }

                            opt.setPadding(15,15,15,15);
                            opt.setTextColor(getResources().getColor(R.color.black));
                            //opt.setBackgroundColor(getResources().getColor(R.color.white));

                        }else{
                            try {
                                if (selectedItemVariantId.equals(productOptions.get(j).getVar_id())) {

                                    //чистим все остатки в блоке
                                    countLay.removeAllViews();

                                    //остатки
                                    insertCountVariant(countList);

                                    //количество в корзине
                                    int countCart = db_handler.getCountCartItem(product_code, variant.getVariant_id());
                                    opt_cart.setText(String.valueOf(countCart));

                                    //цены
                                    insertPriceVariant(priceList);

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        opt.setBackground(getResources().getDrawable(R.drawable.rounded_btn_grey));
                                    } else {
                                        opt.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn_grey));
                                    }

                                    opt.setPadding(10,10,10,10);
                                    opt.setTextColor(getResources().getColor(R.color.black));
                                    //opt.setBackgroundColor(getResources().getColor(R.color.white));
                                }
                            } catch (NullPointerException ignore) {
                            }
                        }

                        opt.setFocusableInTouchMode(false);
                        opt.setFocusable(true);
                        opt.setClickable(true);
                        opt.setTextSize(16);


                        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                        params1.setMargins(0,30,30,0);
                        params1.gravity = LEFT;
                        opt.setLayoutParams(params1);
                        opt.setPadding(26,26,26,26);
                        opt.setTag(VariantId);

                        //количество в корзине
                        int countCart = db_handler.getCountCartItem(product_code, variant.getVariant_id());
                        if (countCart > 0) {
                            opt_cart.setText(String.valueOf(countCart));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                opt_cart.setBackground(ContextCompat.getDrawable(this, R.drawable.yellow_circle));
                            }

                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                            params.gravity = RIGHT;
                            params.setMargins(0,0,8,0);
                            opt_cart.setLayoutParams(params);

                            opt_cart.setTextColor(getResources().getColor(R.color.white));
                            opt_cart.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            opt_cart.setTextSize(10);
                            opt_cart.setGravity(CENTER);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                opt_cart.setZ(1);
                            }
                        }else{
                            opt_cart.setVisibility(View.GONE);
                        }


                        FrameLayout fr = new FrameLayout(this);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        fr.setLayoutParams(layoutParams);

                        fr.addView(opt_cart);
                        fr.addView(opt);

                        optionLay.setPadding(8,8,8,8);
                        optionLay.setBackground(ContextCompat.getDrawable(this, R.color.white));
                        optionLay.addView(fr);

                        // Option Click Listener
                        opt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TextView textView = (TextView) view;

                                selected = textView.getText().toString();

                                Variant variant = db_handler.getVariantDetailsById(VariantId);
                                selectedItemVariantId = variant.getVariant_id();

                                selectedSku = variant.getSku();
                                selectedBarcode = variant.getBarcode();

                                List<Count> countList = variant.getCount();
                                priceList = variant.getPrice();

                                //цены
                                insertPriceVariant(priceList);

                                //остатки
                                insertCountVariant(countList);

                                //свойства
                                setPropertyVariant(selectedSku, selectedBarcode);

                                //обновляем итоговое сумму для корзины
                                selectedTotalPrice();

                                //выводим свойства
                                setOptionsLayout(variantList); // refresh to set selected

                                addPlusMinus();
                            }

                        });
                    }
                }
            }
        }
    }

    private void insertCountVariant(List<Count> countList){

        //чистим все остатки в блоке
        countLay.removeAllViews();

        if (countList != null) {
            isNotStores = false;
            for (int k = 0; k < countList.size(); k++) {
                if (countList.get(k).getCount() > 0) {

                    //если хотябы есть на одном складе
                    isNotStores = true;

                    //показываем кнопки добавления в корзину
                    bottomLay.setVisibility(View.VISIBLE);

                    final TextView count = new TextView(this);
                    store_cart = new TextView(this);

                    String text_store = countList.get(k).getName_store() + ": " + String.valueOf(countList.get(k).getCount());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        count.setBackground(getResources().getDrawable(R.drawable.rounded_opt));
                    } else {
                        count.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_opt));
                    }
                    count.setTextColor(getResources().getColor(R.color.black));

                    count.setTag(countList.get(k));

                    // виделяем его ддругой рамкой
                    if (k == 0 && selectedItemStoreId == null) {

                        selectedItemStoreId = countList.get(k).getStore();
                        final int countS = (int) countList.get(k).getCount();
                        countStore = countS;

                        selectedStoreCount = getCountCartItemByStore(product_code, selectedItemVariantId, selectedItemStoreId);

                        countVariantIsFree = countS - selectedStoreCount;

                        quantityValue.setText(String.valueOf(selectedStoreCount));

                        selectedItemQuantity = selectedStoreCount;
                        selectedTotalPrice();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            count.setBackground(getResources().getDrawable(R.drawable.rounded_btn_grey));
                        } else {
                            count.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn_grey));
                        }

                        count.setPadding(10, 10, 10, 10);
                        count.setTextColor(getResources().getColor(R.color.black));
                        count.setFocusableInTouchMode(true);
                    } else {
                        try {
                            if (selectedItemStoreId.equals(countList.get(k).getStore())) {

                                selectedItemStoreId = countList.get(k).getStore();
                                final int countS = (int) countList.get(k).getCount();
                                countStore = countS;

                                selectedStoreCount = getCountCartItemByStore(product_code, selectedItemVariantId, selectedItemStoreId);

                                countVariantIsFree = countS - selectedStoreCount;

                                quantityValue.setText(String.valueOf(selectedStoreCount));

                                selectedItemQuantity = selectedStoreCount;
                                selectedTotalPrice();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    count.setBackground(getResources().getDrawable(R.drawable.rounded_btn_grey));
                                } else {
                                    count.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn_grey));
                                }
                                count.setPadding(10, 10, 10, 10);
                                count.setTextColor(getResources().getColor(R.color.black));
                            }
                        } catch (NullPointerException ignore) {
                        }
                    }

                    FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    params1.setMargins(0, 30, 30, 0);
                    params1.gravity = LEFT;
                    count.setLayoutParams(params1);
                    count.setPadding(26, 26, 26, 26);

                    count.setFocusableInTouchMode(false);
                    count.setFocusable(true);
                    count.setClickable(true);
                    count.setTextSize(16);

                    count.setText(text_store);

                    //количество которое уже в корзине
                    final int[] countStore = {getCountCartItemByStore(product_code, selectedItemVariantId, countList.get(k).getStore())};
                    if (countStore[0] > 0) {

                        store_cart.setText(String.valueOf(countStore[0]));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            store_cart.setBackground(ContextCompat.getDrawable(this, R.drawable.yellow_circle));
                        }

                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                        params.gravity = RIGHT;
                        params.setMargins(0, 0, 8, 0);
                        store_cart.setLayoutParams(params);

                        store_cart.setTextColor(getResources().getColor(R.color.white));
                        store_cart.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        store_cart.setTextSize(10);
                        store_cart.setGravity(CENTER);


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            store_cart.setZ(1);
                        }

                    } else {
                        store_cart.setVisibility(View.GONE);
                    }


                    FrameLayout fr = new FrameLayout(this);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(30, 8, 0, 0);
                    fr.setLayoutParams(layoutParams);

                    fr.addView(store_cart);
                    fr.addView(count);

                    countLay.setPadding(30, 8, 8, 8);
                    countLay.setBackground(ContextCompat.getDrawable(this, R.color.white));
                    countLay.addView(fr);

                    //countLay.addView(count);
                    //countLay.addView(store_cart);

                    // Store Click Listener
                    count.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView textView = (TextView) view;
                            selected = textView.getText().toString();

                            final int countS = (int) ((Count) view.getTag()).getCount();
                            countStore[0] = countS;
                            String id_store = ((Count) view.getTag()).getStore();

                            selectedItemStoreId = id_store;
                            selectedStoreCount = getCountCartItemByStore(product_code, selectedItemVariantId, selectedItemStoreId);
                            //если уже в корзине больше товара чем есть на складе
                            countVariantIsFree = countS - selectedStoreCount;

                            selectedItemQuantity = 0;

                            Variant variant = db_handler.getVariantDetailsById(selectedItemVariantId);
                            insertCountVariant(variant.getCount());

                            addPlusMinus();

                        }
                    });
                }
            }
            if (!isNotStores){
                TextView not_store_cart = new TextView(this);
                countLay.setPadding(30, 8, 8, 8);
                countLay.setBackground(ContextCompat.getDrawable(this, R.color.white));
                not_store_cart.setText("Немає залишку на складах");
                countLay.addView(not_store_cart);
                bottomLay.setVisibility(View.GONE);
            }
        }
    }

    private void insertPriceVariant(List<Price> priceList){
        if (priceList != null) {
            boolean isNotClientPrice = true;

            int codePriceByClient = Integer.parseInt(client.getPrice_id());

            for (int k = 0; k < priceList.size(); k++) {
                // Price Base
                if (priceList.get(k).getType() == codePriceByClient) {
                    base_price = priceList.get(k).getValue();
                }
                // Price Client
                if (priceList.get(k).getType() == 0) {
                    isNotClientPrice = false;
                    client_price = priceList.get(k).getValue();
                }
            }
            //запишем цену базу
            priceBase.setText(formatted.format(base_price) + " ₴");
            priceBase.setPaintFlags(priceBase.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            //запишем цену клиента
            //запишем процент скидки
            if (isNetworkAvaliable(this)) {
                if (client_price > 0 && !isNotClientPrice) {
                    priceClient.setText(formatted.format(client_price) + " ₴");
                    double percentValue = (1 - (client_price / base_price)) * 100;
                    percent.setVisibility(View.VISIBLE);
                    String formattedPercent = new DecimalFormat("#0").format(percentValue);
                    percent.setText(formattedPercent + " %");

                    if (percentValue > 0){
                        priceBase.setVisibility(View.VISIBLE);
                    }else{
                        priceBase.setVisibility(View.GONE);
                    }
                } else {
                    if (isNotClientPrice) {
                        client_price = base_price;
                    }
                    priceClient.setText(formatted.format(client_price) + " ₴");
                    percent.setVisibility(View.VISIBLE);
                    String formattedPercent = new DecimalFormat("#0").format(0);
                    percent.setText(formattedPercent + " %");

                    priceBase.setVisibility(View.GONE);
                }
            }else{
                if (loading_client_price == "N"){
                    client_price = base_price;
                    priceClient.setText(formatted.format(client_price) + " ₴");
                    percent.setVisibility(View.VISIBLE);
                    String formattedPercent = new DecimalFormat("#0").format(0);
                    percent.setText(formattedPercent + " %");
                }else{
                    priceClient.setText(formatted.format(client_price) + " ₴");
                    double percentValue = (1 - (client_price / base_price)) * 100;
                    percent.setVisibility(View.VISIBLE);
                    String formattedPercent = new DecimalFormat("#0").format(percentValue);
                    percent.setText(formattedPercent + " %");

                    if (percentValue > 0){
                        priceBase.setVisibility(View.VISIBLE);
                    }else{
                        priceBase.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    private void setPropertyVariant(String sku, String barcode) {

        this.sku.setVisibility(View.VISIBLE);
        this.title_sku.setVisibility(View.VISIBLE);
        this.barcode.setVisibility(View.VISIBLE);
        this.title_barcode.setVisibility(View.VISIBLE);

        if (sku != "") {
            this.sku.setText(sku);
        }
        if (barcode != "") {
            this.barcode.setText(barcode);
            Picasso.get().load("file:///android_asset/images/" + barcode + ".jpg").fit().centerInside().placeholder(R.drawable.ic_image_grey600_36dp)
                    .error(R.drawable.ic_image_grey600_36dp).into(this.image);
            //PhotoViewAttacher mAttacher = new PhotoViewAttacher(this.image);
        }
    }

    private void addPlusMinus(){
        quantityValue.setCursorVisible(false);
        minus.setVisibility(View.VISIBLE);
        plus.setVisibility(View.VISIBLE);
    }
    // Quantity Update Listeners
    private void setQuantityUpdateListeners() {
        // Decrement Listener
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db_handler.updateItemQuantity(selectedStoreCount - 1, selectedItemVariantId, selectedItemStoreId);
                selectedItemQuantity = getCountCartItemByStore(product_code, selectedItemVariantId, selectedItemStoreId);

                if (selectedItemQuantity < 0){
                    quantityValue.setText(String.valueOf(0));
                    //если 0 тогда удаляем товар из корзины
                }else{
                    quantityValue.setText(String.valueOf(selectedItemQuantity));
                    addCartMinus(view);
                    selectedTotalPrice();
                }

            }
        });

        // Increment Listener
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (countVariantIsFree == 0) {
                    infoMsg(getApplicationContext(), "Немає залишку на складах");
                }else{
                    selectedItemQuantity = selectedStoreCount + 1;
                    addCartPlus(view);
                    selectedTotalPrice();
                }

                quantityValue.setText(String.valueOf(selectedItemQuantity));
            }
        });

        quantityValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus.setVisibility(View.GONE);
                plus.setVisibility(View.GONE);
                quantityValue.setText("");
                totalText.setVisibility(View.VISIBLE);
                totalText.setText("Введіть кількість:");
                quantityValue.setCursorVisible(true);
            }
        });
        quantityValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                minus.setVisibility(View.GONE);
                plus.setVisibility(View.GONE);
                quantityValue.setText("");
                totalText.setVisibility(View.VISIBLE);
                totalText.setText("Введіть кількість:");
                quantityValue.setCursorVisible(true);
            }
        });
        quantityValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (!quantityValue.getText().toString().equals("")) {
                        int selectedQuantity = Integer.parseInt(String.valueOf(quantityValue.getText()));
                        if (selectedQuantity < countStore) {
                            selectedItemQuantity = selectedQuantity;
                        } else {
                            selectedItemQuantity = countStore;
                            //infoMsg(getApplicationContext(), "Больше нет на складе");
                        }

                        addCartPlus(view);
                        selectedTotalPrice();

                        hideInputKeyboard(view);
                        minus.setVisibility(View.VISIBLE);
                        plus.setVisibility(View.VISIBLE);
                        return true;
                    }else{
                        //не закрываем пока не введут значение
                        return false;
                    }
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
        updateProduct();
    }

    private void selectedTotalPrice(){
        if (selectedItemQuantity > 0) {
            selectedTotalPrice = selectedItemQuantity * client_price;
            String formattedDouble = new DecimalFormat("#0.00").format(selectedTotalPrice);
            totalText.setText("У кошику на суму: " + formattedDouble + " ₴");
            totalText.setVisibility(View.VISIBLE);
            border_botom.setVisibility(View.VISIBLE);
        }else{
            totalText.setVisibility(View.GONE);
            border_botom.setVisibility(View.GONE);
        }   
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onRefresh() {
        updateProduct();
    }

}

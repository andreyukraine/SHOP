package com.preethzcodez.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import com.preethzcodez.ecommerce.MainActivity;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.interfaces.FinishActivity;
import com.preethzcodez.ecommerce.json.AccesProductsJSON;
import com.preethzcodez.ecommerce.json.DiscontJSON;
import com.preethzcodez.ecommerce.json.DiscontTypeJSON;
import com.preethzcodez.ecommerce.json.ResponseJSON;
import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.pojo.CityNP;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Count;
import com.preethzcodez.ecommerce.pojo.CountVariant;
import com.preethzcodez.ecommerce.pojo.Discont;
import com.preethzcodez.ecommerce.pojo.Offer;
import com.preethzcodez.ecommerce.pojo.Option;
import com.preethzcodez.ecommerce.pojo.PointNP;
import com.preethzcodez.ecommerce.pojo.Price;
import com.preethzcodez.ecommerce.pojo.PriceType;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.pojo.ProductAccess;
import com.preethzcodez.ecommerce.pojo.ProductOption;
import com.preethzcodez.ecommerce.pojo.RegNP;
import com.preethzcodez.ecommerce.pojo.Store;
import com.preethzcodez.ecommerce.pojo.TypeDiscont;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.Util;
import com.preethzcodez.ecommerce.webservice.RetrofitBuilder;
import com.preethzcodez.ecommerce.webservice.RetrofitInterface;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static com.preethzcodez.ecommerce.utils.Util.getBase64Auth;

public class LoadActivity extends AppCompatActivity implements FinishActivity{

    private int progress = 0;
    private TextView progressCircle, progBarTitle;
    private DB_Handler db_handler;
    private Handler handlerTitle;
    private Handler handlerBar;
    private RetrofitBuilder retrofitBuilder;
    private OkHttpClient httpClient;
    private Object obj;
    private User user;
    private String basicAuth;
    private DateFormat dateFormat;
    private Date date;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        db_handler = new DB_Handler(this);
        obj = new Object();

        // Initialize Retrofit
        retrofitBuilder = new RetrofitBuilder(this);
        httpClient = retrofitBuilder.setClient();

        //дата обновления каталога
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        date = new Date();

        //User
        user = db_handler.getFirstUser();
        basicAuth = getBase64Auth(user.getName(), user.getPassword());

        // some code
        handlerTitle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String text = (String) msg.obj;
                progBarTitle.setText(text);
            }
        };

        handlerBar = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String text = (String) msg.obj;
                progressCircle.setText( text );
            }
        };

        setId();

        //_getNewPostRegion();

        getNewPostCity();
        getNewPostPoint();
        getAccessProducts();
        getClients();
        getDiscountsType();
        getDiscounts();
        getProducts();
        getOffers();
        getCounts();
        getSubCategory();

        //нужно подтянуть картинки точек по клиентам
        getGallery();

        db_handler.insertParamsOrUpdate(Constants.DATA_UPDATE, dateFormat.format(date));

    }

    private void getGallery() {

        File dir;
        boolean success = false;
        
        if (Build.VERSION_CODES.R > Build.VERSION.SDK_INT) {
            dir = new File(Environment.getExternalStorageDirectory().getPath() + "//B2B");
        } else {
            dir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).getPath() + "//B2B");
        }

        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                String clientCode = files[i].getName();
                if (files[i].isDirectory()) {
                    File[] fotosClient = files[i].listFiles();
                    for (int e = 0; e < fotosClient.length; e++) {
                        String pics_url = fotosClient[e].getPath();
                        String fotoClient = fotosClient[e].getName();
                        String coordinats = fotosClient[e].getName().substring(0,fotosClient[e].getName().indexOf("-"));
                        String date_time = fotosClient[e].getName().substring(fotosClient[e].getName().indexOf("-")+1,fotosClient[e].getName().length()-4);

                        String lat = coordinats.substring(0, coordinats.indexOf("_"));
                        String lon = coordinats.substring(lat.length() + 1, coordinats.length());

                        String pics_date = date_time.substring(0, date_time.indexOf("_"));
                        String pics_time = date_time.substring(pics_date.length() + 1, date_time.length() );

                        db_handler.insertPics(clientCode, pics_url, lat, lon, pics_date, pics_time);
                    }
                }
                System.out.println(files[i]);
            }

        }

    }

    private void setId() {
        progressCircle = (TextView) findViewById(R.id.progress_circle);
        progBarTitle = findViewById(R.id.progress_bar_title);
    }

    private void setTextHandler(final String text, Handler handler) {
        Message msg = new Message();
        msg.obj = text;
        handler.sendMessage(msg);
    }

    private void postProgress(double progress, int all) {
        double percent = Math.round(progress / all * 100);
        String strProgress = String.valueOf((int) percent) + " %";
        setTextHandler(strProgress, handlerBar);
    }

    // Load Next Activity
    private void loadNextActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void finishActivity() {
        overridePendingTransition(0, 0);
        finish();
    }

    private void getAccessProducts() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.PRODUCTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<AccesProductsJSON> call = retrofitInterface.getAccessProducts(basicAuth);
        call.request();
        call.enqueue(new Callback<AccesProductsJSON>() {
            @Override
            public void onResponse(Call<AccesProductsJSON> call, Response<AccesProductsJSON> response) {
                try {
                    if (response.body() != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                synchronized (obj) {
                                    accessProductData(response.body());
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<AccesProductsJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    //Загружаем JSON с клиентами и сохраняем в локальную базу DB
    private void accessProductData(AccesProductsJSON responseJSON) {
        setTextHandler("Загружаем ограничение по товарам", handlerTitle);
        List<ProductAccess> accessProducts = responseJSON.getProducts();
        for (int i = 0; i < accessProducts.size(); i++) {
            db_handler.insertAccessProduct(accessProducts.get(i).getId(), accessProducts.get(i).getOpt_id(), accessProducts.get(i).getClient_type());
        }
    }

    // Получаем клиентов
    private void getClients() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.CLIENTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<ArrayList<Client>> call = retrofitInterface.getClients(basicAuth, user.getName());
        call.request();
        call.enqueue(new Callback<ArrayList<Client>>() {
            @Override
            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
                try {
                    if (response.body() != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                synchronized (obj) {
                                    clientData(response.body());
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    //Загружаем JSON с клиентами и сохраняем в локальную базу DB
    private void clientData(ArrayList<Client> responseJSON) {
        setTextHandler("Загружаем клиентов", handlerTitle);
        for (int i = 0; i < responseJSON.size(); i++) {
            String name = responseJSON.get(i).getName();
            String xml_id = responseJSON.get(i).getXml_id();
            double dept = responseJSON.get(i).getDebt();
            String price_id = responseJSON.get(i).getPrice_id();
            String type = responseJSON.get(i).getType();
            String address = responseJSON.get(i).getAddress();
            String phone = responseJSON.get(i).getPhone();


            ArrayList address_mass = responseJSON.get(i).getAddress2();
            if (address_mass != null){
                for (int t = 0; t < address_mass.size(); t++) {
                    db_handler.insertAddressClient(xml_id, address_mass.get(t));
                }
            }

            db_handler.insertClient(xml_id, name, dept, price_id, type, address,phone);

            postProgress(i,responseJSON.size());

        }
        Log.i("Clients Sync", "success");
    }

    //получаем акции
    private void getDiscounts() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.DISCONTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<DiscontJSON> call = retrofitInterface.getDiscounts(basicAuth);
        call.request();
        call.enqueue(new Callback<DiscontJSON>() {
            @Override
            public void onResponse(Call<DiscontJSON> call, Response<DiscontJSON> response) {
                try {
                    if (response.body() != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                setTextHandler("Загружаем акции", handlerTitle);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                synchronized (obj) {
                                    discountData(response.body());
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<DiscontJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    //Загружаем JSON с клиентами и сохраняем в локальную базу DB
    private void discountData(DiscontJSON responseJSON) {
        List<Discont> disconts = responseJSON.getDiscontList();
        for (int i = 0; i < disconts.size(); i++) {

            String code = disconts.get(i).getCode();
            String name = disconts.get(i).getName();
            String type = disconts.get(i).getType();
            String clientType = disconts.get(i).getClient_type();

            db_handler.insertDiscont(code, name, type, clientType);

            postProgress(i,disconts.size());

        }
        Log.i("Discounts Sync", "success");
    }

    //получаем акции
    private void getDiscountsType() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.DISCONTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<DiscontTypeJSON> call = retrofitInterface.getDiscountsType(basicAuth);
        call.request();
        call.enqueue(new Callback<DiscontTypeJSON>() {
            @Override
            public void onResponse(Call<DiscontTypeJSON> call, Response<DiscontTypeJSON> response) {
                try {
                    if (response.body() != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                setTextHandler("Загружаем виды дисконтов", handlerTitle);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                synchronized (obj) {
                                    discountTypeData(response.body());
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<DiscontTypeJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    //Загружаем JSON с клиентами и сохраняем в локальную базу DB
    private void discountTypeData(DiscontTypeJSON responseJSON) {
        List<TypeDiscont> discontsType = responseJSON.getDisconts_type();
        for (int i = 0; i < discontsType.size(); i++) {

            String code = discontsType.get(i).getId();
            String name = discontsType.get(i).getName();

            db_handler.insertDiscontType(code, name);

            postProgress(i,discontsType.size());

        }
        Log.i("Discounts Type Sync", "success");
    }

    // Получени товаров
    private void getProducts() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.PRODUCTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Call Web Service
        Call<ResponseJSON> call = retrofitInterface.getProducts(basicAuth);
        call.request();
        call.enqueue(new Callback<ResponseJSON>() {
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                try {
                    if (response.body() != null) {
                        setTextHandler("Загружаем категории", handlerTitle);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (obj) {
                                    processData(response.body());
                                }
                            }
                        }).start();

                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }

        });
    }

    // Загружаем JSON с товарами и сохраняем в локальную базу DB
    private void processData(ResponseJSON responseJSON) {
        try {
            //getListStorage
            List<Store> storageList = responseJSON.getStorage();
            for (int j = 0; j < storageList.size(); j++) {
                String id = responseJSON.getStorage().get(j).getId();
                String name = responseJSON.getStorage().get(j).getName();
                db_handler.insertStore(id, name);
            }

            //getListPrices
            List<PriceType> pricesList = responseJSON.getPrices();
            for (int q = 0; q < pricesList.size(); q++) {
                int id = responseJSON.getPrices().get(q).getId();
                String price_name = responseJSON.getPrices().get(q).getPrice_name();
                db_handler.insertPrice(id, price_name);
            }

            // Get Categories
            List<Category> categoryList = responseJSON.getCategories();
            for (int z = 0; z < categoryList.size(); z++) {

                int CategoryID = responseJSON.getCategories().get(z).getId();
                String CategoryName = responseJSON.getCategories().get(z).getName();
                int Level = responseJSON.getCategories().get(z).getLevel();
                List<Product> productList = categoryList.get(z).getProducts();

                // insert category into local DB
                db_handler.insertCategories(CategoryID, CategoryName, Level, 0);

                // Get Products
                if (productList != null) {
                    int all_count = productList.size();
                    for (int y = 0; y < all_count; y++) {
                        String ProductID = productList.get(y).getId();
                        String ProductName = productList.get(y).getName();
                        // insert products into local DB
                        db_handler.insertProducts(ProductID, CategoryID, ProductName);
                        postProgress(y,all_count);
                    }
                }

                // Get Child Categories
                List<Integer> childCategories = categoryList.get(z).getChildCategories();
                for (int k = 0; k < childCategories.size(); k++) {
                    int SubcategoryID = childCategories.get(k);

                    if(Level > 2) {
                        if (productList != null) {
                            db_handler.insertChildCategoryMapping(CategoryID, SubcategoryID, 0);
                        }
                    }else{
                        db_handler.insertChildCategoryMapping(CategoryID, SubcategoryID, 0);
                    }
                }

                Log.i("Sync products", "success " + CategoryName);
            }

        } catch (Exception e) {
            setTextHandler(e.getMessage(), handlerTitle);
        }

    }

    // Получаем опции товаров
    private void getOffers() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.PRODUCTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<ResponseJSON> call = retrofitInterface.getOffers(basicAuth);
        call.request();
        call.enqueue(new Callback<ResponseJSON>() {
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                try {
                    if (response.body() != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (obj) {
                                    setTextHandler("Загружаем опции", handlerTitle);
                                    offersData(response.body());
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    //Загружаем JSON с offers и сохраняем в локальную базу DB
    private void offersData(ResponseJSON responseJSON) {
        try {
            //get Oprions
            List<Option> optionsList = responseJSON.getOptions();
            for (int t = 0; t < optionsList.size(); t++) {
                String opt_id = responseJSON.getOptions().get(t).getXml_id();
                String name = responseJSON.getOptions().get(t).getName();
                db_handler.insertOprions(opt_id, name);
            }
            //get Offers
            List<Offer> offersList = responseJSON.getOffers();

            int all_count = offersList.size();
            for (int g = 0; g < all_count; g++) {
                String var_id = responseJSON.getOffers().get(g).getId();
                String sku = responseJSON.getOffers().get(g).getSku();
                String barcode = responseJSON.getOffers().get(g).getBarcode();
                String pdc_id = responseJSON.getOffers().get(g).getPdc_id();
                db_handler.insertVariants(var_id, pdc_id, sku, barcode);

                if (offersList.get(g).getProductOptions() != null) {
                    List<ProductOption> productOptions = offersList.get(g).getProductOptions();
                    for (int e = 0; e < productOptions.size(); e++) {
                        String opt_val = productOptions.get(e).getOpt_val();
                        String opt_id = productOptions.get(e).getOpt_id();
                        db_handler.insertVariantOptions(var_id, opt_id, opt_val);
                    }
                }
                postProgress(g, all_count);
            }
            Log.i("Offers Sync", "success");
        } catch (Exception e) {
            setTextHandler(e.getMessage(), handlerTitle);
        }
    }

    // Получаем остатки товаров
    private void getCounts() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.PRODUCTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<ResponseJSON> call = retrofitInterface.getCounts(basicAuth);
        call.request();
        call.enqueue(new Callback<ResponseJSON>() {
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                try {
                    if (response.body() != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (obj) {
                                    //getSubCategory();
                                    setTextHandler("Загружаем остатки", handlerTitle);
                                    countsData(response.body());
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    //Загружаем JSON с остатками и сохраняем в локальную базу DB
    private void countsData(ResponseJSON responseJSON) {
        try {
            //get Counts
            List<CountVariant> countVariants = responseJSON.getCountVariant();
            for (int i = 0; i < countVariants.size(); i++) {
                String var_id = countVariants.get(i).getVar_id();
                //количество по складам
                List<Count> countList = countVariants.get(i).getCountList();
                for (int j = 0; j < countList.size(); j++) {
                    String store = countList.get(j).getStore();
                    Double count = countList.get(j).getCount();
                    db_handler.insertCountStore(var_id, store, count);
                }
                //базовая цена
                List<Price> priceBase = countVariants.get(i).getPriceList();
                for (int f = 0; f < priceBase.size(); f++) {
                    int type_id = priceBase.get(f).getType();
                    Double value = priceBase.get(f).getValue();
                    db_handler.insertPriceVariant(var_id, type_id, value);
                }
                postProgress(i,countVariants.size());
            }
            Log.i("Counts Sync", "success");
            loadNextActivity();
        } catch (Exception e) {
            setTextHandler(e.getMessage(), handlerTitle);
        }
    }

    // Получени альтернативные категории
    private void getSubCategory() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.PRODUCTS_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Call Web Service
        Call<ResponseJSON> call = retrofitInterface.getSubProducts(basicAuth);
        call.request();
        call.enqueue(new Callback<ResponseJSON>() {
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                try {
                    if (response.body() != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                setTextHandler("Загружаем доп категории", handlerTitle);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                synchronized (obj) {
                                    processSubData(response.body());
                                }
                            }
                        }).start();

                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }

        });
    }

    // Загружаем альтернативные категории
    private void processSubData(ResponseJSON responseJSON) {
        try {

            // Get Categories
            List<Category> categoryList = responseJSON.getCategories();
            for (int z = 0; z < categoryList.size(); z++) {

                int CategoryID = responseJSON.getCategories().get(z).getId();
                String CategoryName = responseJSON.getCategories().get(z).getName();
                int Level = responseJSON.getCategories().get(z).getLevel();
                List<Product> productList = categoryList.get(z).getProducts();

                // insert category into local DB
                db_handler.insertCategories(CategoryID, CategoryName, Level, 1);

                // Update Sub Category Products
                if (productList != null) {
                    int all_count = productList.size();
                    for (int y = 0; y < all_count; y++) {
                        String ProductID = productList.get(y).getId();

                        // insert products into local DB
                        db_handler.updateSubCategory(ProductID, CategoryID);
                        postProgress(y, all_count);
                    }
                }

                // Get Child Categories
                List<Integer> childCategories = categoryList.get(z).getChildCategories();
                for (int k = 0; k < childCategories.size(); k++) {
                    int SubcategoryID = childCategories.get(k);

                    if(Level > 2) {
                        if (productList != null) {
                            db_handler.insertChildCategoryMapping(CategoryID, SubcategoryID, 1);
                        }
                    }else{
                        db_handler.insertChildCategoryMapping(CategoryID, SubcategoryID, 1);
                    }
                }

                Log.i("Sync dop products", "success " + CategoryName);
            }

        } catch (Exception e) {
            setTextHandler(e.getMessage(), handlerTitle);
        }

    }

    //получаем регионы
    private void getNewPostRegion() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.NEWPOST_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<ArrayList<RegNP>> call = retrofitInterface.getNPRegion(basicAuth);
        call.request();
        call.enqueue(new Callback<ArrayList<RegNP>>() {
            @Override
            public void onResponse(Call<ArrayList<RegNP>> call, Response<ArrayList<RegNP>> response) {
                try {
                    if (response.body() != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                setTextHandler("Загружаем регионы", handlerTitle);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                synchronized (obj) {
                                    RegionNPData(response.body());
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RegNP>> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    private void RegionNPData(ArrayList<RegNP> regNPS){
        for (int i = 0; i < regNPS.size(); i++) {
            String id = regNPS.get(i).getId();
            String name = regNPS.get(i).getName();
            String code = regNPS.get(i).getCode();
            db_handler.insertRegionNP(id, name, code);
        }
    }

    //получаем города
    private void getNewPostCity() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.NEWPOST_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<ArrayList<CityNP>> call = retrofitInterface.getNPCity(basicAuth);
        call.request();
        call.enqueue(new Callback<ArrayList<CityNP>>() {
            @Override
            public void onResponse(Call<ArrayList<CityNP>> call, Response<ArrayList<CityNP>> response) {
                try {
                    if (response.body() != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                setTextHandler("Загружаем города", handlerTitle);
                                synchronized (obj) {
                                    CityNPData(response.body());
                                }
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CityNP>> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    private void CityNPData(ArrayList<CityNP> CityNP){
        for (int i = 0; i < CityNP.size(); i++) {
            String id = CityNP.get(i).getId();
            String name = CityNP.get(i).getName();
            String code = CityNP.get(i).getCode_np();
            String id_region = CityNP.get(i).getId_region();
            db_handler.insertCityNP(id, name, code, id_region);
            postProgress(i,CityNP.size());
        }
    }

    //получаем отделения
    private void getNewPostPoint() {
        Retrofit retrofit = retrofitBuilder.retrofitBuilder(httpClient, Constants.NEWPOST_URL);
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        // Call Web Service
        Call<ArrayList<PointNP>> call = retrofitInterface.getNPPoint(basicAuth);
        call.request();
        call.enqueue(new Callback<ArrayList<PointNP>>() {
            @Override
            public void onResponse(Call<ArrayList<PointNP>> call, Response<ArrayList<PointNP>> response) {
                try {
                    if (response.body() != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                setTextHandler("Загружаем отделение", handlerTitle);
                                synchronized (obj) {
                                    PointNPData(response.body());
                                }
                            }
                        }).start();

                    }
                } catch (Exception e) {
                    setTextHandler(e.getMessage(), handlerTitle);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PointNP>> call, Throwable t) {
                //тут смотрим что за ошибка при парсинге json
                setTextHandler(Util.getErrorMessage(t, LoadActivity.this), handlerTitle);
            }
        });
    }

    private void PointNPData(ArrayList<PointNP> PointNP){
        for (int i = 0; i < PointNP.size(); i++) {
            String name = PointNP.get(i).getName();
            String code = PointNP.get(i).getCode_np();
            String id_city_code_nd = PointNP.get(i).getId_city_code_np();
            int weight = 0;
            db_handler.insertPointNP(name, code, weight,id_city_code_nd);
            postProgress(i,PointNP.size());
        }
    }

}
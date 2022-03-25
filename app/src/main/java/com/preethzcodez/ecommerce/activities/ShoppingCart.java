package com.preethzcodez.ecommerce.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.preethzcodez.ecommerce.MainActivity;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.AddressList;
import com.preethzcodez.ecommerce.adapters.DiscontList;
import com.preethzcodez.ecommerce.adapters.ShoppingCartListAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.interfaces.UpdateShop;
import com.preethzcodez.ecommerce.objects.Address;
import com.preethzcodez.ecommerce.pojo.Cart;
import com.preethzcodez.ecommerce.pojo.CartStore;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Price;
import com.preethzcodez.ecommerce.pojo.User;
import com.preethzcodez.ecommerce.utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.preethzcodez.ecommerce.utils.Util.getBase64Auth;
import static com.preethzcodez.ecommerce.utils.Util.hideProgress;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;
import static com.preethzcodez.ecommerce.utils.Util.showProgress;

public class ShoppingCart extends AppCompatActivity implements ShoppingCartListAdapter.MonitorListItems, UpdateShop {

    private Toolbar toolbar;
    private TextView titleToolbar, payableBase, payableClient, clientOrder, sumStore, shippingDate2, textDialog;
    private ImageView cart, backButton;
    private ListView listView;
    private Button crateOrder;
    private TextInputLayout cl_client;
    private SessionManager sessionManager;
    private DB_Handler db_handler;
    private String client_code;
    private TextInputEditText comment;
    private LinearLayout cartBlock;
    private TabLayout tabLayout, tabs;
    private List<CartStore> cartStores;
    private List<Cart> shoppingCart;
    private ListView lv;
    private Spinner addresses;
    private Address addressOrder;
    private int tabSelected, cartCountAll = 0;
    private View v, vProducts, vBlock;
    private Dialog dialog;
    private LayoutInflater vi;
    private DecimalFormat formatter;
    private double totalAmountBase = 0.0;
    private double totalAmountClient = 0.0;
    private Client client;
    private Address address_order;
    private Context thisContext;
    private Bundle args;
    private TextInputEditText editAddress, shippingDate;
    private User user;
    private ArrayList<Address> adressesList;
    private String basicAuth;
    private DatePickerDialog.OnDateSetListener dateS;
    private SimpleDateFormat format;
    private final Calendar myCalendarStart = Calendar.getInstance();
    private ProgressDialog dialogProgress;
    private static Boolean isEmptyDate = false, isEmptyAddress = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vi = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //setContentView(R.layout.shopping_cart);
        thisContext = this;
        args = new Bundle();
        db_handler = new DB_Handler(this);
        sessionManager = new SessionManager(this);
        formatter = new DecimalFormat("#,###.00");
        format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

        //Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_message_view);
        textDialog = dialog.findViewById(R.id.textDialog);

        //User
        user = db_handler.getFirstUser();
        basicAuth = getBase64Auth(user.getName(), user.getPassword());

        // Set Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);
        client = db_handler.getClientsById(client_code);

        adressesList = db_handler.getAddressesByClient(client.getXml_id());

        setUpdateProducts();

    }

    private void setUpdateProps() {


        vBlock = vi.inflate(R.layout.shopping_cart_props, null);
        setContentView(vBlock);

        setToolbarView();
        setViewTabs("Информация");

        crateOrder = findViewById(R.id.crateOrder);
        shippingDate = findViewById(R.id.shippingDate);
        comment = findViewById(R.id.comment);
        clientOrder = findViewById(R.id.clientOrder);
        cl_client = findViewById(R.id.cl_client);

        editAddress = findViewById(R.id.editAddress);
        addresses = findViewById(R.id.addresses);

        editAddress.setText("Выберите адрес доставки ...");
        editAddress.setTextColor(getResources().getColor(R.color.red));

        shippingDate.setText("Выберите дату отгрузки ...");
        shippingDate.setTextColor(getResources().getColor(R.color.red));

        if (adressesList.size() >= 3) {
            AddressList adapter = new AddressList(this, R.layout.address_list, R.id.title, adressesList);
            addresses.setAdapter(adapter);
        }else{
            if (adressesList.size() == 2) {
                address_order = adressesList.get(1);
                editAddress.setText(address_order.getName());
                editAddress.setTextColor(getResources().getColor(R.color.black));
                editAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                isEmptyAddress = true;
            }else{
                editAddress.setText("Нет адресов для доставки");
                editAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }

        //тут делаем проверку еслии заказ имняеться
        String isEditOrder = getIntent().getStringExtra(Constants.ORDER_IS_EDIT);

        if (isEditOrder != null) {
            if (isEditOrder.equals("1")) {

                String commentOrder = getIntent().getStringExtra(Constants.ORDER_COMMENT);
                String clientEditOrder = getIntent().getStringExtra(Constants.ORDER_CLIENT);
                String shippingDateOrder = getIntent().getStringExtra(Constants.ORDER_SHIPPING_DATE);
                String address_code = getIntent().getStringExtra(Constants.ORDER_ADDRESS_CODE);
                addressOrder = db_handler.getAddresOrderByClient(clientEditOrder,address_code);

                comment.setText(commentOrder);
                client = db_handler.getClientsById(clientEditOrder);
                shippingDate.setText(shippingDateOrder);
                shippingDate.setTextColor(getResources().getColor(R.color.black));
                editAddress.setText(addressOrder.getName());
                editAddress.setTextColor(getResources().getColor(R.color.black));
                isEmptyDate = true;
                isEmptyAddress = true;
            }
        }

        clientOrder.setText(client.getName());

        dateS = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String dateSelect = format.format(myCalendarStart.getTime());

                shippingDate.setText(dateSelect);
                shippingDate.setTextColor(getResources().getColor(R.color.black));

                if (!dateSelect.equals("")) {
                    isEmptyDate = true;
                }
            }
        };

        String textDolg = "Немає заборгованості";
        String dolg_text = Double.toString(client.getDebt());

        if (client.getDebt() > 0) {
            textDolg = "Заборгованість " + dolg_text;
            cl_client.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        }else{
            textDolg = "Переплата " + dolg_text;
            cl_client.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        }

        cl_client.setHelperText(textDolg);

        //обновляем итоговую сумму
        getTotalAmount();

        setOnClickListenerProps();
        setOnClickListenerAll();
    }

    private void setUpdateProducts() {
        vBlock = vi.inflate(R.layout.shopping_cart_products, null);

        vi = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setContentView(vBlock);

        setToolbarView();

        crateOrder = findViewById(R.id.crateOrder);
        tabLayout = findViewById(R.id.tabLayout);
        listView = findViewById(R.id.listview);

        //обновляем итоговую сумму
        getTotalAmount();

        final int select = tabSelected;
        tabLayout.removeAllTabs();

        View vv = vi.inflate(R.layout.shopping_layout_list_product, null);
        lv = vv.findViewById(R.id.listview_new);

        cartBlock = findViewById(R.id.cartBlock);

        if (cartStores.size() > 1) {
            for (int s = 0; s < cartStores.size(); s++) {
                String name_store = db_handler.getNameStore(cartStores.get(s).getStore());
                int count_products_store = cartStores.get(s).getCartList().size();

                TabLayout.Tab tab = tabLayout.newTab().setText(name_store + "(" + count_products_store + ")");

                if (count_products_store > 0) {
                    cartCountAll = cartCountAll + count_products_store;
                    if (s == select) {
                        //tab.setCustomView(R.layout.tab_cart_selected);

                        tabLayout.addTab(tab, select, true);
                        //tab.setCustomView(R.layout.custom_tab_view_red);
                        shoppingCart = cartStores.get(s).getCartList();
                        lv.setAdapter(new ShoppingCartListAdapter(thisContext, shoppingCart));
                        setSumStore(shoppingCart);
                    } else {
                        tabLayout.addTab(tab);
                    }
                }
            }
        } else {

            for (int s = 0; s < cartStores.size(); s++) {
                String name_store = db_handler.getNameStore(cartStores.get(s).getStore());
                int count_products_store = cartStores.get(s).getCartList().size();
                TabLayout.Tab tab = tabLayout.newTab().setText(name_store + "(" + count_products_store + ")");

                if (count_products_store > 0) {
                    cartCountAll = count_products_store;
                    tabLayout.addTab(tab);
                }

                shoppingCart = cartStores.get(s).getCartList();
                lv.setAdapter(new ShoppingCartListAdapter(thisContext, shoppingCart));
                setSumStore(shoppingCart);
            }
        }

        setViewTabs(R.string.TitleCategories + " " + "(" + cartCountAll + ")");

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        cartBlock.removeAllViews();
        cartBlock.addView(vv);

        setOnClickListenerProducts();
        setOnClickListenerAll();
    }

    private void setToolbarView() {
        titleToolbar = findViewById(R.id.titleToolbar);
        cart = findViewById(R.id.cart);
        backButton = findViewById(R.id.backButton);

        // Set Title
        titleToolbar.setText(R.string.shopping_cart);

        // Hide Cart Icon
        cart.setVisibility(View.GONE);

        // Back Button
        backButton.setVisibility(View.VISIBLE);
    }

    private void setViewTabs(String selectName) {
        if (selectName.equals(R.string.TitleCategories + " " + "(" + cartCountAll + ")")) {
            tabs = findViewById(R.id.tabs);
            tabs.addTab(tabs.newTab().setText(R.string.TitleCategories + " " + "(" + cartCountAll + ")"), true);
            tabs.addTab(tabs.newTab().setText(R.string.TitleInfo));
        } else {
            tabs = findViewById(R.id.tabs);
            tabs.addTab(tabs.newTab().setText(R.string.TitleCategories + " " + "(" + cartCountAll + ")"));
            tabs.addTab(tabs.newTab().setText(R.string.TitleInfo), true);
        }
    }

    private void setOnClickListenerProps() {
        // кнопка назад
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //дата отгрузки
        shippingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateSelect = new DatePickerDialog(ShoppingCart.this, dateS, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH));
                dateSelect.show();
            }
        });

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addresses.performClick();
            }
        });

        addresses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object selectedAddress = parent.getItemAtPosition(position);
                if (position > 0) {
                    address_order = (Address) selectedAddress;
                    editAddress.setText(((Address) selectedAddress).getName());
                    editAddress.setTextColor(getResources().getColor(R.color.black));
                    isEmptyAddress = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        comment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    comment.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    private void setOnClickListenerAll() {

        //при клике на таб
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        // Products
                        setUpdateProducts();
                        break;
                    case 1:
                        //Props
                        setUpdateProps();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        // оформить заказ
        crateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogProgress = showProgress(ShoppingCart.this);
                if (isEmptyDate) {
                    if (isEmptyAddress) {
                        //получаем товары по складам
                        for (int a = 0; a < cartStores.size(); a++) {

                            Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
                            calendar.add(Calendar.SECOND, a);

                            String orderNumber = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime()) + "-" + user.getName();
                            //String orderNumber = String.valueOf(rand.nextInt(200) + 1);

                            //дата заказа
                            String dataOrder = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());

                            String comment_order = String.valueOf(comment.getText());
                            List<Cart> cartList = cartStores.get(a).getCartList();
                            String sDate = shippingDate.getText().toString();

                            String adressCode = "";
                            if (address_order != null) {
                                adressCode = address_order.getCode();
                            }else{
                                adressCode = addressOrder.getCode();
                            }

                            //записываем сам заказ
                            db_handler.insertOrder(orderNumber, dataOrder, cartStores.get(a).getStore(), getТоtalAmountStore(cartList), client_code, comment_order, sDate, adressCode);

                            db_handler.insertOrderHistory(cartList, orderNumber, 0);
                        }

                        // delete from cart and place order
                        db_handler.deleteCartItems();

                        //delete temp value input date
                        isEmptyDate = false;
                        isEmptyAddress = false;

                        //view orders intent
                        Intent orders = new Intent(thisContext, Orders.class);
                        startActivity(orders);
                        overridePendingTransition(0, 0);
                        finish();
                    }else{
                        textDialog.setText("Виберіть адресу доставки");
                        dialog.show();
                    }
                } else {
                    textDialog.setText("Виберіть дату відвантаження");
                    setUpdateProps();
                    DatePickerDialog dateSelect = new DatePickerDialog(ShoppingCart.this, dateS, myCalendarStart
                            .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                            myCalendarStart.get(Calendar.DAY_OF_MONTH));
                    dateSelect.show();
                    dialog.show();
                }
                hideProgress(dialogProgress);
            }
        });

    }

    private void setOnClickListenerProducts() {

        // кнопка назад
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //при клике на таб
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tabSelected = tab.getPosition();
                View vv = vi.inflate(R.layout.shopping_layout_list_product, null);
                lv = vv.findViewById(R.id.listview_new);
                shoppingCart = cartStores.get(tab.getPosition()).getCartList();
                lv.setAdapter(new ShoppingCartListAdapter(thisContext, shoppingCart));

                cartBlock.removeAllViews();
                cartBlock.addView(vv);

                setSumStore(shoppingCart);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });
    }

    private void setSumStore(List<Cart> shoppingCart) {
        double sumTotalStore = 0;
        for (int i = 0; i < shoppingCart.size(); i++) {
            sumTotalStore = sumTotalStore + Double.valueOf(shoppingCart.get(i).getPrice_value()) * shoppingCart.get(i).getItemQuantity();
        }
        //sumStore.setText(formatter.format(sumTotalStore) + " ₴");
    }

    public void cancelDialog(View view) {
        dialog.cancel();
    }

    //обновляем итоговую сумму
    private double getTotalAmount() {

        double priceBase = 0.0;
        double priceClient = 0.0;

        payableClient = findViewById(R.id.payableAmtClient);

        cartStores = db_handler.getCartItems();

        for (int s = 0; s < cartStores.size(); s++) {
            for (int i = 0; i < cartStores.get(s).getCartList().size(); i++) {
                if (cartStores.get(s).getCartList().size() > 0) {
                    int itemQuantity = cartStores.get(s).getCartList().get(i).getItemQuantity();
                    priceBase = priceBase + getPrice(cartStores.get(s).getCartList().get(i).getVariant().getPrice(), 2) * itemQuantity;
                    priceClient = priceClient + cartStores.get(s).getCartList().get(i).getPrice_value() * itemQuantity;
                }
            }
        }

        totalAmountBase = priceBase;
        totalAmountClient = priceClient;

        if (totalAmountBase != totalAmountClient) {
            payableClient.setText(formatter.format(totalAmountClient) + " ₴");
        } else {
            payableClient.setText(formatter.format(totalAmountClient) + " ₴");
        }
        return totalAmountClient;
    }

    private double getТоtalAmountStore(List<Cart> cartList) {
        double total = 0.0;
        for (int i = 0; i < cartList.size(); i++) {
            total = total + (cartList.get(i).getPrice_value() * cartList.get(i).getItemQuantity());
        }
        return total;
    }

    //получаем цену из варианта по типу цены
    private Double getPrice(List<Price> priceList, int type_price) {
        double base_price = 0.0;
        if (priceList != null) {
            for (int k = 0; k < priceList.size(); k++) {
                if (priceList.get(k).getType() == type_price) {
                    base_price = priceList.get(k).getValue();
                }
            }
        }
        return base_price;
    }

    // finish activity if cart empty
    @Override
    public void finishActivity(List<Cart> shoppingCart) {
        try {
            int isEmpty = 0;
            for (int i = 0; i < this.cartStores.size(); i++) {
                int count_store = this.cartStores.get(i).getCartList().size();
                if (count_store > 0) {
                    isEmpty = isEmpty + count_store;
                }
            }
            if (isEmpty == 0) {
                overridePendingTransition(0, 0);
                finish();
            }
        } catch (Exception e) {
            overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    public void updateShop() {
        setUpdateProducts();
        //setUpdateProps();
    }
}

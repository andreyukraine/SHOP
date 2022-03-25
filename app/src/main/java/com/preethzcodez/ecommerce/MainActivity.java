package com.preethzcodez.ecommerce;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.preethzcodez.ecommerce.activities.LoadActivity;
import com.preethzcodez.ecommerce.activities.ShoppingCart;
import com.preethzcodez.ecommerce.comparators.SortByNameClient;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.fragments.Account;
import com.preethzcodez.ecommerce.fragments.Categories;
import com.preethzcodez.ecommerce.fragments.CategoriesType;
import com.preethzcodez.ecommerce.fragments.Clients;
import com.preethzcodez.ecommerce.fragments.Home;
import com.preethzcodez.ecommerce.fragments.Subcategories;
import com.preethzcodez.ecommerce.interfaces.FinishActivity;
import com.preethzcodez.ecommerce.interfaces.SaveLevelCategories;
import com.preethzcodez.ecommerce.interfaces.ShowBackButton;
import com.preethzcodez.ecommerce.interfaces.ToolbarTitle;
import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.utils.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import static com.preethzcodez.ecommerce.utils.Util.infoMsg;
import static com.preethzcodez.ecommerce.utils.Util.isNetworkAvaliable;
import static com.preethzcodez.ecommerce.utils.Util.showProgress;

public class MainActivity extends AppCompatActivity implements Subcategories.ChildCategories, ToolbarTitle, ShowBackButton, FinishActivity, SaveLevelCategories {

    private BottomNavigationView navigation;
    private DB_Handler db_handler;
    private SessionManager sessionManager;
    private Toolbar toolbar;
    private TextView titleToolbar, textDialog, count, dataUpdate;
    private int cartCount = 0, levelCategory = 0;
    private List<Category> childCategories = new ArrayList<>();
    private ImageView backButton, cart;
    private String subCategoryTitle = null, client_code;
    private Dialog dialog;
    private Button modOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        db_handler = new DB_Handler(this);
        sessionManager = new SessionManager(this);

        //Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_message_view);

        setId();

        // Set Toolbar
        setSupportActionBar(toolbar);

        // Set Title
        titleToolbar.setText(R.string.TitleHome);

        // Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backButtonClick();
            }
        });

        // initialize bottom navigation view
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setFirstClient();

        callProductsFragment();
        setToolbarIconsClickListeners();
    }


    private void setFirstClient() {
        List<Client> clientList = db_handler.getClients();
        Collections.sort(clientList, new SortByNameClient());
        for (int i = 0; i < clientList.size(); i++) {
            if (db_handler.getCartItemCount() > 0){
                client_code = db_handler.getCartClient();
                if (clientList.get(i).getXml_id().equals(client_code)) {
                    sessionManager.saveSession(Constants.SESSION_CLIENT_CODE, clientList.get(i).getXml_id());
                    sessionManager.saveSession(Constants.SESSION_CLIENT_POSITION, Integer.toString(i));
                }
            }else{
                if (sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE).equals("")){
                    if (clientList.get(i).getXml_id().equals("000000772")) {
                        sessionManager.saveSession(Constants.SESSION_CLIENT_CODE, clientList.get(i).getXml_id());
                        sessionManager.saveSession(Constants.SESSION_CLIENT_POSITION, Integer.toString(i));
                        client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);
                    }
                }
            }
        }
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

    private void setId(){
        toolbar = findViewById(R.id.toolbar);
        titleToolbar = findViewById(R.id.titleToolbar);
        backButton = findViewById(R.id.backButton);
        navigation = findViewById(R.id.navigation);
        dataUpdate = findViewById(R.id.dataUpdate);
        count = findViewById(R.id.count);
        cart = findViewById(R.id.cart);
        textDialog = dialog.findViewById(R.id.textDialog);
        modOk = dialog.findViewById(R.id.buttonOk);
    }

    /**
     * BottomNavigationView Listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            // Hide Back Button
            backButton.setVisibility(View.INVISIBLE);

            // Prevent Reload Same Fragment
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.nav_home: // Home
                    // Prevent Reload
                    try {
                        if (!fm.findFragmentByTag(Constants.FRAG_HOME).isVisible()) {
                            callProductsFragment();
                            titleToolbar.setText(R.string.TitleHome);
                        }
                    } catch (NullPointerException e) {
                        callProductsFragment();
                        titleToolbar.setText(R.string.TitleHome);
                    }
                    return true;

                case R.id.nav_categories: // Categories2
                    String typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);
                    if (typeCatalog.equals("true")) {
                        ft.replace(R.id.content, new Categories(), Constants.FRAG_PDT);
                    }else {
                        ft.replace(R.id.content, new CategoriesType(), Constants.FRAG_PDT);
                    }
                    ft.commit();
                    titleToolbar.setText(R.string.TitleCategories);
                    return true;

                case R.id.nav_loading_sku:
                    if (isNetworkAvaliable(getApplicationContext())){
                        db_handler.deleteData();
                        loadSynActivity();
                    }else{
                        infoMsg(getApplicationContext(), getString(R.string.NoInternet));
                    }
                    return true;

                case R.id.nav_clients: // Clients
                    ft.replace(R.id.content, new Clients(), Constants.FRAG_CLIENTS);
                    ft.addToBackStack(null);
                    ft.commit();
                    titleToolbar.setText(R.string.TitleClient);
                    return true;

                case R.id.nav_account: // User Account
                    ft.replace(R.id.content, new Account(), Constants.FRAG_ACCOUNT);
                    ft.commit();
                    titleToolbar.setText(R.string.TitleAccount);
                    return true;
            }
            return false;
        }
    };

    // call products fragment
    private void callProductsFragment() {
        Bundle args = new Bundle();
        args.putString(Constants.SESSION_CLIENT_CODE, sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE));
        Home home = new Home();
        home.setArguments(args);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, home, Constants.FRAG_HOME);
        ft.commit();
    }

    private void loadSynActivity() {
        Intent loadIntent = new Intent(this, LoadActivity.class);
        startActivity(loadIntent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update Cart Count
        cartCount = db_handler.getCartItemCount();
        if (cartCount > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(cartCount));
        } else {
            count.setVisibility(View.GONE);
        }
    }

    // Back Button Click
    private void backButtonClick() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        try {
            if (fragmentManager.findFragmentByTag(Constants.FRAG_PDT).isVisible()) {
                if (childCategories.size() > 0) {
                    // add bundle arguments
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.TITLE, subCategoryTitle);
                    bundle.putInt(Constants.CAT_LEVEL, levelCategory);
                    bundle.putSerializable(Constants.CAT_KEY, (Serializable) childCategories);

                    Subcategories subcategories = new Subcategories();
                    subcategories.setArguments(bundle);

                    fragmentTransaction.replace(R.id.content, subcategories, Constants.FRAG_SUBCAT);
                    fragmentTransaction.commit();
                    return;
                } else {
                    String typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);
                    if (typeCatalog.equals("true")) {
                        fragmentTransaction.replace(R.id.content, new Categories(), Constants.FRAG_PDT);
                    }else {
                        fragmentTransaction.replace(R.id.content, new CategoriesType(), Constants.FRAG_PDT);
                    }
                    fragmentTransaction.commit();
                    backButton.setVisibility(View.INVISIBLE);
                    titleToolbar.setText(R.string.TitleCategories);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (fragmentManager.findFragmentByTag(Constants.FRAG_SUBCAT).isVisible()) {

                String typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);
                if (typeCatalog.equals("true")) {
                    fragmentTransaction.replace(R.id.content, new Categories());
                }else {
                    fragmentTransaction.replace(R.id.content, new CategoriesType());
                }
                fragmentTransaction.commit();
                titleToolbar.setText(R.string.TitleCategories);
                backButton.setVisibility(View.INVISIBLE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (fragmentManager.findFragmentByTag(Constants.FRAG_SEARCH).isVisible()) {
                String typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);
                if (typeCatalog.equals("true")) {
                    fragmentTransaction.replace(R.id.content, new Categories(), Constants.FRAG_PDT);
                }else {
                    fragmentTransaction.replace(R.id.content, new CategoriesType(), Constants.FRAG_PDT);
                }
                fragmentTransaction.commit();
                titleToolbar.setText(R.string.TitleCategories);
                backButton.setVisibility(View.INVISIBLE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    /**
     * Save Child Categories From Subcategory Fragment
     * This is required by @backButtonClick method to restore state
     */
    @Override
    public void saveChildCategories(List<Category> childCategories) {
        this.childCategories = childCategories;
    }

    @Override
    public void saveLevelCategories(int level) {
        this.levelCategory = level;
    }

    @Override
    public void onBackPressed() {
        backButtonClick();
    }

    // Set Toolbar Title
    @Override
    public void setToolbarTitle(String toolbarTitle) {
        titleToolbar.setText(toolbarTitle);
    }

    // show back button
    @Override
    public void showBackButton() {
        backButton.setVisibility(View.VISIBLE);
    }

    // Save Subcategory Title - Need for backButtonClick method
    @Override
    public void saveSubcategoryTitle(String toolbaTitle) {
        subCategoryTitle = toolbaTitle;
    }

    // Finish Activity From Fragment
    @Override
    public void finishActivity() {
        overridePendingTransition(0,0);
        finish();
    }

    public void cancelDialog(View view) {
        dialog.cancel();
    }

    public void okDialog(View view) {
        dialog.cancel();
    }

}
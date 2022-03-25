package com.preethzcodez.ecommerce.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.SearchAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.interfaces.ToolbarTitle;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.utils.Constants;
import java.util.List;

public class Search extends Fragment implements SearchAdapter.UpdateSearch{

    DB_Handler db_handler;
    SessionManager sessionManager;
    GridView searchGrid;
    List<Product> productList;
    String search_text = "", client_code;
    Client client;
    LinearLayout searchNoFound;
    ToolbarTitle toolbarTitleCallback;
    TextView titleToolbar;
    Context context;
    View view;
    Bundle args;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        toolbarTitleCallback = (ToolbarTitle) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_list, container, false);
        db_handler = new DB_Handler(context);
        sessionManager = new SessionManager(context);

        client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);
        client = db_handler.getClientsById(client_code);

        setIds(view);

        //add search menu
        setHasOptionsMenu(true);

        // get search text
        args = getArguments();
        assert args != null;

        // get search text
        search_text = args.getString(Constants.SEARCH_TEXT);

        // Get Data and Fill Grid
        fillGridView();

        return view;
    }

    // Fill GridView With Data
    private void fillGridView() {

        productList = db_handler.getSearchProductsList(search_text, client.getClient_type());

        if (productList.size() > 0){
            searchGrid.setAdapter(new SearchAdapter(this.context, productList));
        }else{
            searchGrid.setVisibility(View.GONE);
            searchNoFound.setVisibility(View.VISIBLE);
        }

    }

    // Set Ids
    private void setIds(View view) {
        searchGrid = view.findViewById(R.id.searchGrid);
        searchNoFound = view.findViewById(R.id.notFound);
        titleToolbar = view.findViewById(R.id.titleToolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle args = new Bundle();
                args.putString(Constants.SEARCH_TEXT, query);
                args.putString(Constants.TITLE, getResources().getString(R.string.TitleSearch));

                Search search = new Search();
                search.setArguments(args);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, search, "SEARCH");
                ft.addToBackStack(null);
                ft.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                callProductsFragment();
                return true;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }

    // call products fragment
    private void callProductsFragment() {
    }

    @Override
    public void UpdateSearch() {
        System.out.println("update search");
    }
}
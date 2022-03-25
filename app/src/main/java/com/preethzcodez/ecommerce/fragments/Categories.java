package com.preethzcodez.ecommerce.fragments;

import static com.preethzcodez.ecommerce.utils.Util.infoMsg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.CategoryListAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.interfaces.SaveLevelCategories;
import com.preethzcodez.ecommerce.interfaces.ShowBackButton;
import com.preethzcodez.ecommerce.interfaces.ToolbarTitle;
import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Categories extends Fragment {

    ListView listView;
    DB_Handler db_handler;
    List<Category> categoryList;
    ToolbarTitle toolbarTitleCallback;
    ShowBackButton showBackButtonCallback;
    Subcategories.ChildCategories childCategoriesCallback;
    Bundle args;
    Context context;
    SaveLevelCategories saveLevelCategoriesCallback;
    View view;
    TextView notProducts;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context = context;
        childCategoriesCallback = (Subcategories.ChildCategories) context;
        toolbarTitleCallback = (ToolbarTitle) context;
        showBackButtonCallback = (ShowBackButton) context;
        saveLevelCategoriesCallback = (SaveLevelCategories) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.categorieslist, container, false);
        db_handler = new DB_Handler(getActivity());
        // get data
        args = getArguments();

        setId(view);

        // load products
        String typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);
        if (typeCatalog.equals("true")) {
            categoryList = db_handler.getCategoryList();
        }else{
            categoryList = db_handler.getCategoryTypeList();
        }
        // fill listview with data
        if (categoryList.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new CategoryListAdapter(getActivity(), categoryList));
            notProducts.setVisibility(View.GONE);
        }else{
            notProducts.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }

        // show back button
        //showBackButtonCallback.showBackButton();
        childCategoriesCallback.saveChildCategories(new ArrayList<>());

        //add top menu fragment
        setHasOptionsMenu(true);

        return view;
    }

    private void setId(View view) {
        listView= view.findViewById(R.id.listview);
        notProducts = view.findViewById(R.id.notProducts);
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

                ft.replace(R.id.content, search, Constants.FRAG_SEARCH);
                ft.addToBackStack(Constants.FRAG_SEARCH);
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
                return true;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }

}

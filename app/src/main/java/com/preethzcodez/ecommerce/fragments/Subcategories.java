package com.preethzcodez.ecommerce.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TableLayout;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.SubcategoryGridAdapter;
import com.preethzcodez.ecommerce.comparators.SortByNameCategory;
import com.preethzcodez.ecommerce.interfaces.ShowBackButton;
import com.preethzcodez.ecommerce.interfaces.ToolbarTitle;
import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.utils.Constants;
import java.util.Collections;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Subcategories extends Fragment {

    ChildCategories childCategoriesCallback;
    ToolbarTitle toolbarTitleCallback;
    ShowBackButton showBackButtonCallback;
    TableLayout sortFilter;
    GridView gv;
    List<Category> childCategories;
    Bundle args;
    View view;

    // interface save child categories state
    public interface ChildCategories {
        void saveChildCategories(List<Category> childCategories);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        childCategoriesCallback = (ChildCategories) context;
        toolbarTitleCallback = (ToolbarTitle) context;
        showBackButtonCallback = (ShowBackButton) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.product_list, container, false);

        setId(view);

        // Hide Filter Layout
        sortFilter.setVisibility(View.GONE);

        //add top menu fragment
        setHasOptionsMenu(true);

        // get data
        args = getArguments();

        assert args != null;

        childCategories = (List<Category>) args.getSerializable(Constants.CAT_KEY);
        childCategoriesCallback.saveChildCategories(childCategories);

        // show back button
        showBackButtonCallback.showBackButton();

        // set toolbar title
        toolbarTitleCallback.setToolbarTitle(args.getString(Constants.TITLE));
        toolbarTitleCallback.saveSubcategoryTitle(args.getString(Constants.TITLE));

        // fill gridview with data
        if (childCategories != null) {
            Collections.sort(childCategories, new SortByNameCategory());
        }

        gv.setAdapter(new SubcategoryGridAdapter(getActivity(), childCategories));


        return view;
    }

    private void setId(View view) {
        sortFilter = view.findViewById(R.id.sortFilter);
        gv = view.findViewById(R.id.productsGrid);
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

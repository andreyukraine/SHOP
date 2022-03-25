package com.preethzcodez.ecommerce.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.adapters.FilterItemListAdapter;
import com.preethzcodez.ecommerce.adapters.ProductsAdapter;
import com.preethzcodez.ecommerce.adapters.SortItemListAdapter;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.interfaces.SaveLevelCategories;
import com.preethzcodez.ecommerce.interfaces.ShowBackButton;
import com.preethzcodez.ecommerce.interfaces.ToolbarTitle;
import com.preethzcodez.ecommerce.objects.FilterValue;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.pojo.ProductOption;
import com.preethzcodez.ecommerce.pojo.Variant;
import com.preethzcodez.ecommerce.utils.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.preethzcodez.ecommerce.utils.Util.infoMsg;

public class Products extends Fragment{

    RelativeLayout sort, filter;
    TextView sortByText;
    String[] sortByArray = {"Most Recent","Most Viewed"};
    int cat_level,sortById = 0,cat_id = 0;
    GridView productsGrid;
    ArrayList<FilterValue> selectFilter = new ArrayList<>();
    List<String> filterOptions = new ArrayList<>();
    Bundle args;
    TableLayout sortFilterBlock;
    View view;
    Client client;
    HashMap<String, List<FilterValue>> listHashMap;
    ProductsAdapter productsAdapter;
    List<Product> productList;
    DB_Handler db_handler;
    SessionManager sessionManager;
    SaveLevelCategories saveLevelCategoriesCallback;
    ToolbarTitle toolbarTitleCallback;
    ShowBackButton showBackButtonCallback;
    Context context;
    String typeCatalog, client_code;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        toolbarTitleCallback = (ToolbarTitle) context;
        showBackButtonCallback = (ShowBackButton) context;
        saveLevelCategoriesCallback = (SaveLevelCategories) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.product_list, container, false);
        db_handler = new DB_Handler(getActivity());
        sessionManager = new SessionManager(getActivity());
        listHashMap = new HashMap<>();
        args = getArguments();
        typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);

        client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);
        client = db_handler.getClientsById(client_code);

        setIds(view);

        setSortListener();
        setFilterListener();

        assert args != null;
        // get category id
        cat_id = args.getInt(Constants.CAT_ID_KEY);
        cat_level = args.getInt(Constants.CAT_LEVEL);

        saveLevelCategoriesCallback.saveLevelCategories(cat_level);

        if (cat_id > 0) {
            // Show Back Button and Set Title
            showBackButtonCallback.showBackButton();
            toolbarTitleCallback.setToolbarTitle(args.getString(Constants.TITLE));
        }

        // Get Data and Fill Grid
        fillGridView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Update Items
        try {
            List<Product> productList = db_handler.getProductsTypeList(sortById, filterOptions, cat_id, client.getClient_type());
            if (typeCatalog.equals("true")) {
                productList = db_handler.getProductsList(sortById, filterOptions, cat_id, client.getClient_type());
            }
            this.productList.clear();
            this.productList.addAll(productList);
            productsAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Set Ids
    private void setIds(View view) {
        sort = view.findViewById(R.id.sortLay);
        filter = view.findViewById(R.id.filterLay);
        productsGrid = view.findViewById(R.id.productsGrid);
        sortFilterBlock = view.findViewById(R.id.sortFilter);
    }

    // Fill GridView With Data
    private void fillGridView() {

        if (selectFilter.size() > 0){
            productList = db_handler.getProductsListFilser(sortById, selectFilter, cat_id, client.getClient_type());
        }else {
            productList = db_handler.getProductsTypeList(sortById, filterOptions, cat_id, client.getClient_type());
            if (typeCatalog.equals("true")) {
                productList = db_handler.getProductsList(sortById, filterOptions, cat_id, client.getClient_type());
            }
        }
        if (productList.size() > 0) {
            //sortFilterBlock.setVisibility(View.VISIBLE);
            productsGrid.setVisibility(View.VISIBLE);
            productsGrid.setNumColumns(1);
            productsAdapter = new ProductsAdapter(context, productList);
            productsGrid.setAdapter(productsAdapter);
        }else{
            sortFilterBlock.setVisibility(View.GONE);
            productsGrid.setVisibility(View.GONE);
        }
    }

    // Set Sort Listener
    private void setSortListener() {
        sort.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                // Create Dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.categorieslist);

                ListView listView = dialog.findViewById(R.id.listview);
                listView.setAdapter(new SortItemListAdapter(getActivity(), sortByArray, sortById));
                listView.setDividerHeight(1);
                listView.setFocusable(true);
                listView.setClickable(true);
                listView.setFocusableInTouchMode(false);
                dialog.show();

                // ListView Click Listener
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        sortById = i;
                        sortByText.setText(sortByArray[sortById]);

                        // Reload Products List
                        fillGridView();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    // Set Filter Listener
    private void setFilterListener() {
        filter.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                // Create Dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filterlayout);

                // Get options
                List<ProductOption> options = getOtrions();
                List<String> idOtpions = new ArrayList<>();
                for (int i = 0; i < options.size(); i++) {
                    idOtpions.add(options.get(i).getOpt_id());
                }

                // Add Headers
                List<String> headers = new ArrayList<>();

                Object[] uniqueIdOpt = new HashSet<String>(idOtpions).toArray();

                //пробигаемся по выбраным свойствам фильтра
                if (selectFilter.size() > 0) {
                    for (int k = 0; k < selectFilter.size(); k++) {
                        for (int i = 0; i < uniqueIdOpt.length; i++) {
                            String tempId = (String) uniqueIdOpt[i];
                            String optName = db_handler.getNameOption(tempId);
                            List<FilterValue> tempVal = new ArrayList<>();

                            for (int j = 0; j < options.size(); j++) {
                                if (tempId.equals(options.get(j).getOpt_id())){
                                    FilterValue value = new FilterValue();
                                    value.setId(options.get(j).getOpt_id());
                                    value.setValue(options.get(j).getOpt_val());
                                    if (options.get(j).getOpt_val().equals(selectFilter.get(k).getValue())){
                                        value.setSelect(true);
                                    }else {
                                        value.setSelect(false);
                                    }
                                    tempVal.add(value);
                                }
                            }
                            headers.add(optName);
                            listHashMap.put(optName, tempVal);
                        }
                    }
                }else{
                    for (int i = 0; i < uniqueIdOpt.length; i++) {
                        String tempId = (String) uniqueIdOpt[i];
                        String optName = db_handler.getNameOption(tempId);
                        List<FilterValue> tempVal = new ArrayList<>();

                        for (int j = 0; j < options.size(); j++) {
                            if (tempId.equals(options.get(j).getOpt_id())){
                                FilterValue value = new FilterValue();
                                value.setId(options.get(j).getOpt_id());
                                value.setValue(options.get(j).getOpt_val());
                                value.setSelect(false);
                                tempVal.add(value);
                            }
                        }
                        headers.add(optName);
                        listHashMap.put(optName, tempVal);
                    }
                }


                final ExpandableListView listView = dialog.findViewById(R.id.expandableList);

                final FilterItemListAdapter filterItemListAdapter = new FilterItemListAdapter(getActivity(), headers, listHashMap, selectFilter);
                listView.setAdapter(filterItemListAdapter);
                listView.setDividerHeight(1);
                listView.setFocusable(true);
                listView.setClickable(true);
                listView.setFocusableInTouchMode(false);
                dialog.show();

                // ListView Click Listener
                listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                        String groupe = expandableListView.getExpandableListAdapter().getGroup(groupPosition).toString();

                        for(String option : listHashMap.keySet()){
                            //System.out.println(option);
                            if (groupe.equals(option)){
                                int iter = 0;
                                for (FilterValue values : listHashMap.get(option)){
                                    //System.out.println(iter);
                                    if (iter == childPosition) {
                                        //System.out.println(values);
                                        infoMsg(getContext(), values.getValue());
//                                        if (!selectFilter.contains(values)) {
//                                            values.setSelect(true);
//                                            selectFilter.add(values);
//                                        } else {
//                                            values.setSelect(false);
//                                            selectFilter.remove(values);
//                                        }
                                    }
                                    iter++;
                                }
                            }
                        }

                        filterItemListAdapter.notifyDataSetChanged();
                        return false;
                    }
                });

                // Filter Apply Button Click
                Button apply = dialog.findViewById(R.id.apply);
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Reload Products List By Filter
                        fillGridView();
                        dialog.dismiss();
                    }
                });

                // Clear All Button Click
                Button clear = dialog.findViewById(R.id.clear);
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            selectFilter.clear();
                        } catch (NullPointerException ignore) {

                        }

                        filterItemListAdapter.notifyDataSetChanged();
                    }
                });

                // Close Button
                final ImageView close = dialog.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private List<ProductOption> getOtrions() {

        List<String> variantIdList = new ArrayList<>();

        if (productList.size() > 0){
            for (int i = 0; i < productList.size(); i++) {
                String product_code = productList.get(i).getProduct_code();
                List<Variant> variantList = db_handler.getProductVariant(product_code, client.getClient_type());
                for (int j = 0; j < variantList.size(); j++) {
                    variantIdList.add(variantList.get(j).getId());
                }
            }
        }

        List<ProductOption> productOptions = db_handler.getOptionsVariants(variantIdList);

        return productOptions;
    }


}

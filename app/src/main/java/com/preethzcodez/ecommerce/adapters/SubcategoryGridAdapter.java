package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.preethzcodez.ecommerce.MainActivity;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.fragments.Products;
import com.preethzcodez.ecommerce.fragments.Subcategories;
import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.utils.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SubcategoryGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Category> subCategoryList;
    private DB_Handler db_handler;
    List<Category> childCategories;
    View rowView;
    Holder holder;
    Bundle bundle;

    public SubcategoryGridAdapter(Context context, List<Category> subCategoryList) {
        this.context = context;
        this.subCategoryList = subCategoryList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return subCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        holder = new Holder();
        db_handler = new DB_Handler(context);
        rowView = inflater.inflate(R.layout.categories_grid_item, null);
        bundle = new Bundle();

        setId(rowView);

        String name = subCategoryList.get(position).getName();
        int id = subCategoryList.get(position).getId();

        holder.category.setText(name);


        int category_id = subCategoryList.get(position).getId();

        holder.gridItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get subcategories by id
                String typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);
                if (typeCatalog.equals("true")) {
                    childCategories = db_handler.getSubcategoryList(category_id);
                }else{
                    childCategories = db_handler.getSubcategoryTypeList(category_id);
                }

                // initialize bundle and fragment manager

                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                // Check If Subcategories Are There Else Call Products List
                if (childCategories.size() > 0) {

                    // add bundle arguments
                    bundle.putString(Constants.TITLE, subCategoryList.get(position).getName());
                    bundle.putSerializable(Constants.CAT_KEY, (Serializable) childCategories);
                    bundle.putInt(Constants.CAT_LEVEL, subCategoryList.get(position).getLevel());

                    Subcategories subcategories = new Subcategories();
                    subcategories.setArguments(bundle);

                    //фрагмент с подчиненными категориями
                    ft.replace(R.id.content, subcategories, Constants.FRAG_SUBCAT);
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    // add bundle arguments
                    bundle.putInt(Constants.CAT_ID_KEY, category_id);
                    bundle.putInt(Constants.CAT_LEVEL, subCategoryList.get(position).getLevel());
                    bundle.putString(Constants.TITLE, subCategoryList.get(position).getName());

                    Products products = new Products();
                    products.setArguments(bundle);

                    //фрагмент с товарами
                    ft.replace(R.id.content, products, Constants.FRAG_PDT);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        return rowView;
    }

    private void setId(View rowView) {
        holder.gridItemLayout = rowView.findViewById(R.id.gridItemLayouut);
        holder.category = rowView.findViewById(R.id.name);
    }

    public class Holder {
        TextView category;
        RelativeLayout gridItemLayout;
    }
}

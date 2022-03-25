package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.preethzcodez.ecommerce.MainActivity;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.fragments.Products;
import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.SubCategoryGridView;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CategoryListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Category> categoryList;
    private DB_Handler db_handler;
    View rowView;
    Holder holder;
    List<Category> subCategoryList;
    Category categoryItem;


    public CategoryListAdapter(Context context, List<Category> categoryList)
    {
        this.context = context;
        this.categoryList = categoryList;
        db_handler = new DB_Handler(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        holder = new Holder();
        rowView = inflater.inflate(R.layout.categories_list_item, null);

        setId(rowView);

        categoryItem = categoryList.get(position);

        holder.category.setText(categoryItem.getName());

        String typeCatalog = db_handler.getParamByCode(Constants.TYPE_CATALOG);
        if (typeCatalog.equals("true")) {
            subCategoryList = db_handler.getSubcategoryList(categoryItem.getId());
        }else{
            subCategoryList = db_handler.getSubcategoryTypeList(categoryItem.getId());
        }

        holder.subCategoryGridView.setAdapter(new SubcategoryGridAdapter(context, subCategoryList));
        holder.subCategoryGridView.setExpanded(true);

        setOnClickListner(position);

        return rowView;
    }

    private void setId(View rowView) {
        holder.category = rowView.findViewById(R.id.category);
        holder.subCategoryGridView = rowView.findViewById(R.id.subcategories);
    }

    private void setOnClickListner(int position) {

        if (subCategoryList.size() == 0) {
            holder.category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();

                    FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    // add bundle arguments
                    bundle.putInt(Constants.CAT_ID_KEY, categoryList.get(position).getId());
                    bundle.putInt(Constants.CAT_LEVEL, categoryList.get(position).getLevel());
                    bundle.putString(Constants.TITLE, categoryList.get(position).getName());

                    Products products = new Products();
                    products.setArguments(bundle);

                    //фрагмент с товарами
                    ft.replace(R.id.content, products, Constants.FRAG_PDT);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }
    }

    public class Holder {
        TextView category;
        SubCategoryGridView subCategoryGridView;
    }
}

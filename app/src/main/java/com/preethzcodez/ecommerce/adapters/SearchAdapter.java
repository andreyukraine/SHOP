package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.activities.ProductDetails;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Product> productList;
    Holder holder;


    //update search
    public interface UpdateSearch{
        void UpdateSearch();
    }

    public SearchAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        holder = new Holder();
        View rowView = inflater.inflate(R.layout.product_grid_item,null);

        if (context instanceof UpdateSearch) {
            ((UpdateSearch) context).UpdateSearch();
        }

        setId(rowView);

        setValue(position);

        setOnClickListener(position);

        return rowView;
    }

    private void setOnClickListener(int position) {
        // Product Item Click
        holder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra(Constants.PRODUCT_ID_KEY, productList.get(position).getProduct_code());
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(0,0);
            }
        });
    }

    private void setValue(int position) {
        holder.name.setText(productList.get(position).getName());
        Picasso.get().load("file:///android_asset/images/" + productList.get(position).getVariants().get(0).getBarcode()+".jpg").fit().centerInside().placeholder(R.drawable.ic_image_grey600_36dp).error(R.drawable.ic_image_grey600_36dp).into(holder.img);

    }

    private void setId(View rowView) {
        holder.name = rowView.findViewById(R.id.name);
        holder.itemLay = rowView.findViewById(R.id.itemLay);
        holder.img = rowView.findViewById(R.id.img);
    }

    public class Holder {
        RelativeLayout itemLay;
        TextView name;
        ImageView img;
    }
}
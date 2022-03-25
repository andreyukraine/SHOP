package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.activities.ProductDetails;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.pojo.Order;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.pojo.ProductOption;
import com.preethzcodez.ecommerce.pojo.Variant;
import com.preethzcodez.ecommerce.pojo.VariantOrder;
import com.preethzcodez.ecommerce.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<VariantOrder> products;
    private VariantOrder variantItem;
    private double price_item;
    private Order order;
    private View rowView;
    private Holder holder;
    private SessionManager sessionManager;
    private Product product;
    private DB_Handler db_handler;
    private List<ProductOption> optionsList;
    private DecimalFormat formatter;

    public OrderListAdapter(Context context, List<VariantOrder> products, Order order) {
        this.context = context;
        this.products = products;
        this.order = order;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n", "InflateParams"})
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        rowView = inflater.inflate(R.layout.order_cart_item, null);
        holder = new Holder();
        sessionManager = new SessionManager(context);
        db_handler = new DB_Handler(context);
        formatter = new DecimalFormat("#,###0.00");

        variantItem = products.get(position);

        product = db_handler.getProductDetailsById(variantItem.getProduct_id());

        setId(rowView);

        //опции
        optionsList = variantItem.getProductOptions();

        Picasso.get().load("file:///android_asset/images/"+variantItem.getBarcode()+".jpg").fit().centerInside().placeholder(R.drawable.ic_image_grey600_36dp).error(R.drawable.ic_image_grey600_36dp).into(holder.img);

        if(order.getIsBase() == 1) {

            holder.priceClient.setText(formatter.format(variantItem.getPrice_value_1c()) + " ₴");

            if (variantItem.getQuantity_1c() < variantItem.getQuantity()) {

                //количество в 1с
                holder.countValue1C.setVisibility(View.VISIBLE);
                holder.countValue1C.setText(String.valueOf(variantItem.getQuantity_1c()));

                //количество хотят
                holder.countValue.setText(String.valueOf(variantItem.getQuantity()));
                holder.countValue.setTextColor(Color.parseColor("#37000000"));
                holder.countValue.setPaintFlags(holder.countValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                //итого по строке
                double total_item = variantItem.getPrice_value_1c() * variantItem.getQuantity_1c();
                holder.priceClientTotal.setText(formatter.format(total_item) + " ₴");
            } else {
                holder.countValue1C.setVisibility(View.GONE);
                holder.countValue.setText(String.valueOf(variantItem.getQuantity()));
                //итого по строке
                double total_item = variantItem.getPrice_value_1c() * variantItem.getQuantity();
                holder.priceClientTotal.setText(formatter.format(total_item) + " ₴");
            }
        }else{
            holder.priceClient.setText(formatter.format(variantItem.getPrice_value()) + " ₴");
            holder.countValue1C.setVisibility(View.GONE);
            holder.countValue.setText(String.valueOf(variantItem.getQuantity()));
            //итого по строке
            double total_item = variantItem.getPrice_value() * variantItem.getQuantity();
            holder.priceClientTotal.setText(formatter.format(total_item) + " ₴");
        }

        //процент
        if (variantItem.getAuto_discont() > 0 || variantItem.getManual_discont() > 0) {
            holder.percent.setVisibility(View.VISIBLE);
            String textPercent = Integer.toString(variantItem.getAuto_discont() + variantItem.getManual_discont()) + " %";
            holder.percent.setText(textPercent);
        }else{
            holder.percent.setVisibility(View.GONE);
        }

        holder.title.setText(product.getName());
        holder.options.setText("(" + getOptionsVariantToString(optionsList) + ")");

        setOnClickListener();

        return rowView;
    }

    private void setOnClickListener() {
        // Product Item Click
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra(Constants.PRODUCT_ID_KEY, variantItem.getProduct_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void setId(View rowView) {
        holder.title = rowView.findViewById(R.id.title);
        holder.countValue = rowView.findViewById(R.id.countValue);
        holder.options = rowView.findViewById(R.id.options);
        holder.priceClientTotal = rowView.findViewById(R.id.priceClientTotal);
        holder.priceClient = rowView.findViewById(R.id.priceClient);
        holder.itemLay = rowView.findViewById(R.id.itemLay);
        holder.countValue1C = rowView.findViewById(R.id.countValue1C);
        holder.img = rowView.findViewById(R.id.img);
        holder.percent = rowView.findViewById(R.id.percent);
    }


    private String getOptionsVariantToString(List<ProductOption> optionsList){
        String text_options = "";
        for (int i = 0; i < optionsList.size(); i++) {
            if (text_options.equals("")){
                text_options = optionsList.get(i).getOpt_val();
            }else{
                text_options = text_options + " " + optionsList.get(i).getOpt_val();
            }
        }
        return text_options;
    }

    public class Holder {
        RelativeLayout itemLay;
        TextView title, countValue, countValue1C, options, priceClient, priceClientTotal, percent;
        ImageView img;
    }

}

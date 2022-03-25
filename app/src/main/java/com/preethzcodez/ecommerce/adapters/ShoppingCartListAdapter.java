package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.activities.ProductDetails;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.interfaces.UpdateShop;
import com.preethzcodez.ecommerce.pojo.Cart;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Price;
import com.preethzcodez.ecommerce.pojo.ProductOption;
import com.preethzcodez.ecommerce.utils.Constants;
import com.preethzcodez.ecommerce.utils.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.List;

import static com.preethzcodez.ecommerce.utils.Util.detPercentPrices;
import static com.preethzcodez.ecommerce.utils.Util.is_equal_bouble;

public class ShoppingCartListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Cart> shoppingCart;
    private DB_Handler db_handler;
    private View rowView;
    private Holder holder;
    private SessionManager sessionManager;
    private int quantity;
    private String client_code;
    private Double priceBaseTemp;
    private Double priceClientTemp;
    private DecimalFormat formatter;
    private Client client;

    // inteface to finish activity if cart empty
    public interface MonitorListItems {
        void finishActivity(List<Cart> shoppingCart);
    }

    public ShoppingCartListAdapter(Context context, List<Cart> shoppingCart) {
        this.context = context;
        this.shoppingCart = shoppingCart;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shoppingCart.size();
    }

    @Override
    public Object getItem(int i) {
        return shoppingCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n", "InflateParams"})
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        holder = new Holder();
        db_handler = new DB_Handler(context);
        rowView = inflater.inflate(R.layout.shopping_cart_item, null);
        sessionManager = new SessionManager(context);
        formatter = new DecimalFormat("#,###.00");

        client_code = sessionManager.getSessionData(Constants.SESSION_CLIENT_CODE);
        client = db_handler.getClientsById(client_code);

        setId(rowView);

        Picasso.get().load("file:///android_asset/images/"+shoppingCart.get(position).getVariant().getBarcode()+".jpg").fit().centerInside().placeholder(R.drawable.ic_image_grey600_36dp).error(R.drawable.ic_image_grey600_36dp).into(holder.img);
        holder.title.setText(shoppingCart.get(position).getProduct().getName());

        //опции
        String options = "( " + getOptionsVariantToString(shoppingCart.get(position).getVariant().getProductOptions()) + " )";
        holder.options.setText(options);

        updateQuantity(position);

        updatePrices(position);

        setOnClickListener(position);

        return rowView;
    }

    private void updatePrices(int position){
        priceClientTemp = shoppingCart.get(position).getPrice_value();
        priceBaseTemp = getPrice(shoppingCart.get(position).getVariant().getPrice(), Integer.valueOf(client.getPrice_id()));

        holder.priceBase.setText(formatter.format(calculatePrice(priceBaseTemp, quantity)) + " ₴");
        holder.priceBase.setPaintFlags(holder.priceBase.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.priceClient.setText(formatter.format(calculatePrice(priceClientTemp, quantity)) + " ₴");

        if (is_equal_bouble(priceBaseTemp,priceClientTemp)){
            holder.priceBase.setVisibility(View.GONE);
            holder.percent.setVisibility(View.GONE);
        }else{
            int percent = detPercentPrices(priceBaseTemp,priceClientTemp);
            holder.percent.setText(Integer.toString(percent) + " %");
            holder.priceBase.setVisibility(View.VISIBLE);
        }
    }

    private void updateQuantity(int position){
        quantity = shoppingCart.get(position).getItemQuantity();
        holder.qty.setText(String.valueOf(quantity));
    }


    private void setOnClickListener(int position) {
        // Product Item Click
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra(Constants.PRODUCT_ID_KEY, shoppingCart.get(position).getProduct().getProduct_code());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        // Remove Item
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete Item From DB
                if (db_handler.deleteCartItemId(shoppingCart.get(position).getId(), shoppingCart.get(position).getStore_id())) {
                    shoppingCart.remove(position);
                    notifyDataSetChanged();

                    if (context instanceof UpdateShop) {
                        ((UpdateShop) context).updateShop();
                    }

                    if (context instanceof MonitorListItems) {
                        ((MonitorListItems) context).finishActivity(shoppingCart);
                    }

                } else {
                    Toast.makeText(context, "Не удалось удалить товар из корзины", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Quantity Decrement Listener
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoppingCart.get(position).getItemQuantity() != 1) {
                    final int rem = shoppingCart.get(position).getItemQuantity() - 1;
                    updateQuantity(rem,position);
                    updatePrices(position);
                }
            }
        });

        // Quantity Increment Listener
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int add = shoppingCart.get(position).getItemQuantity() + 1;
                updateQuantity(add,position);
                updatePrices(position);
            }
        });
    }

    private void setId(View rowView) {
        holder.title = rowView.findViewById(R.id.title);
        holder.qty = rowView.findViewById(R.id.quantityValue);
        holder.remove = rowView.findViewById(R.id.remove);
        holder.minus = rowView.findViewById(R.id.minus);
        holder.plus = rowView.findViewById(R.id.plus);
        holder.options = rowView.findViewById(R.id.options);
        holder.priceBase = rowView.findViewById(R.id.priceBase);
        holder.priceClient = rowView.findViewById(R.id.priceClient);
        holder.itemLay = rowView.findViewById(R.id.itemLay);
        holder.img = rowView.findViewById(R.id.img);
        holder.percent = rowView.findViewById(R.id.percent);
    }

    private void delCartItem(int position){
        if (db_handler.deleteCartItemId(shoppingCart.get(position).getId(), shoppingCart.get(position).getStore_id())) {
            shoppingCart.remove(position);
            notifyDataSetChanged();

            if (context instanceof UpdateShop) {
                ((UpdateShop) context).updateShop();
            }

            if (context instanceof MonitorListItems) {
                ((MonitorListItems) context).finishActivity(shoppingCart);
            }

        } else {
            Toast.makeText(context, "error deleting item", Toast.LENGTH_LONG).show();
        }
    }

    private double calculatePrice(double priceValue, int quantity) {
        return priceValue * quantity;
    }

    // Update Quantity In DB
    private void updateQuantity(int quantity, int position) {
        db_handler.updateItemQuantity(quantity, shoppingCart.get(position).getVariant().getVariant_id(), shoppingCart.get(position).getStore_id());
        if (context instanceof UpdateShop) {
            ((UpdateShop) context).updateShop();
        }
    }

    private String getOptionsVariantToString(List<ProductOption> optionList){
        String options = "";
        if (optionList != null) {
            for (int i = 0; i < optionList.size(); i++) {
                if (i == 0){
                    options =  optionList.get(i).getOpt_val();
                }else {
                    options = options + " - " + optionList.get(i).getOpt_val();
                }
            }
        }
        return options;
    }

    public class Holder {
        RelativeLayout itemLay;
        TextView title, qty, options, priceBase, priceClient, percent;
        ImageView remove, minus, plus, img;
    }

    private double getPrice(List<Price> priceList, int type_price){
        double price = 0.0;
        if (priceList != null) {
            for (int k = 0; k < priceList.size(); k++) {
                if (priceList.get(k).getType() == type_price) {
                    price = priceList.get(k).getValue();
                }
            }
        }
        return price;
    }
}

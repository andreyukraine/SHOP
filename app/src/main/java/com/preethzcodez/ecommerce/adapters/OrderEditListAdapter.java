package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import com.preethzcodez.ecommerce.pojo.Cart;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Order;
import com.preethzcodez.ecommerce.pojo.Price;
import com.preethzcodez.ecommerce.pojo.Product;
import com.preethzcodez.ecommerce.pojo.ProductOption;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import static com.preethzcodez.ecommerce.utils.Util.infoMsg;

public class OrderEditListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Cart> shoppingCart;
    Order order;
    View rowView;
    Holder holder;
    SessionManager sessionManager;
    Product product;
    int quantity;
    DB_Handler db_handler;
    List<ProductOption> optionsList;
    DecimalFormat formatter;
    Cart variantItem;
    Double priceBaseTemp;
    Double priceClientTemp;
    Client client;

    //update cart
    public interface UpdateOrder{
        void UpdateOrder();
    }

    //update cart
    public interface DeleteOrder{
        void DeleteOrder();
    }

    public OrderEditListAdapter(Context context, List<Cart> shoppingCart, Order order) {
        this.context = context;
        this.shoppingCart = shoppingCart;
        this.order = order;
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

        rowView = inflater.inflate(R.layout.order_edit_item, null);
        holder = new Holder();
        sessionManager = new SessionManager(context);
        db_handler = new DB_Handler(context);
        formatter = new DecimalFormat("#,###.00");
        client = db_handler.getClientsById(order.getClient_code());

        variantItem = shoppingCart.get(position);

        product = db_handler.getProductDetailsById(variantItem.getProduct().getProduct_code());

        setId(rowView);

        //опции
        optionsList = variantItem.getVariant().getProductOptions();

        //цены
        final double price_item = db_handler.getPriceOrderVariantId(variantItem.getVariant().getVariant_id(), order.getNumber());

        holder.title.setText(product.getName());
        holder.options.setText("(" + getOptionsVariantToString(optionsList) + ")");
        holder.priceClient.setText(formatter.format(price_item) + " ₴");

        Picasso.get().load("file:///android_asset/images/"+variantItem.getVariant().getBarcode()+".jpg").fit().centerInside().placeholder(R.drawable.ic_image_grey600_36dp).error(R.drawable.ic_image_grey600_36dp).into(holder.img);

        updateQuantity(position);

        setOnClickListener(position);

        return rowView;
    }

    private void updateQuantity(int position){
        quantity = shoppingCart.get(position).getItemQuantity();
        holder.qty.setText(String.valueOf(quantity));
    }

    private void setOnClickListener(int position) {

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoppingCart.get(position).getItemQuantity() > 2) {
                    int rem = shoppingCart.get(position).getItemQuantity() - 1;
                    updateQuantity(rem,position);
                    updatePrices(position);
                }else{
                    int rem = 1;
                    updateQuantity(rem,position);
                    updatePrices(position);
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int add = shoppingCart.get(position).getItemQuantity();
                updateQuantity(add + 1,position);
                updatePrices(position);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Delete Item From DB
                    if (db_handler.deleteOrderItem(variantItem.getVariant().getVariant_id(), variantItem.getStore_id(), order.getNumber(), 0)) {
                        shoppingCart.remove(position);
                        notifyDataSetChanged();
                        //проверяем если один товар тогда нужно удалить заказ
                        if (shoppingCart.size() > 0) {
                            if (context instanceof OrderEditListAdapter.UpdateOrder) {
                                ((OrderEditListAdapter.UpdateOrder) context).UpdateOrder();
                            }
                        }else{
                            db_handler.deleteOrder(order.getNumber());
                            notifyDataSetChanged();
                            if (context instanceof OrderEditListAdapter.DeleteOrder) {
                                ((OrderEditListAdapter.DeleteOrder) context).DeleteOrder();
                            }
                        }
                    } else {
                        infoMsg(context, "Не удалось удалить товар из заказа");
                    }

            }
        });
    }

    private void updateQuantity(int quantity, int position) {
        db_handler.updateItemQuantityOrder(quantity, shoppingCart.get(position).getVariant().getVariant_id(), shoppingCart.get(position).getStore_id(), order.getNumber());
        if (context instanceof OrderEditListAdapter.UpdateOrder) {
            ((OrderEditListAdapter.UpdateOrder) context).UpdateOrder();
        }
    }

    private void updatePrices(int position){
        priceBaseTemp = getPrice(shoppingCart.get(position).getVariant().getPrice(), Integer.valueOf(client.getPrice_id()));
        holder.priceBase.setText(formatter.format(calculatePrice(priceBaseTemp, quantity)) + " ₴");
        holder.priceBase.setPaintFlags(holder.priceBase.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        priceClientTemp = shoppingCart.get(position).getPrice_value();
        holder.priceClient.setText(formatter.format(calculatePrice(priceClientTemp, quantity)) + "₴");

    }

    private void setId(View rowView) {
        holder.title = rowView.findViewById(R.id.title);
        holder.options = rowView.findViewById(R.id.options);
        holder.priceClientTotal = rowView.findViewById(R.id.priceClientTotal);
        holder.priceBase = rowView.findViewById(R.id.priceBase);
        holder.priceClient = rowView.findViewById(R.id.priceClient);
        holder.itemLay = rowView.findViewById(R.id.itemLay);
        holder.img = rowView.findViewById(R.id.img);
        holder.qty = rowView.findViewById(R.id.quantityValue);
        holder.remove = rowView.findViewById(R.id.remove);
        holder.minus = rowView.findViewById(R.id.minus);
        holder.plus = rowView.findViewById(R.id.plus);
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

    private double calculatePrice(double priceValue, int quantity) {
        return priceValue * quantity;
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
        TextView title, options, priceClient, priceClientTotal, qty, priceBase;
        ImageView remove, minus, plus, img;
    }

}

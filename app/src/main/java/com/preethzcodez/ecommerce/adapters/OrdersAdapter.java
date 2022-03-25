package com.preethzcodez.ecommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.activities.OrderDetails;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.pojo.Client;
import com.preethzcodez.ecommerce.pojo.Order;
import com.preethzcodez.ecommerce.utils.Constants;

import java.text.DecimalFormat;
import java.util.List;

public class OrdersAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Order> orderList;
    DB_Handler db_handler;
    Holder holder;
    View rowView;
    DecimalFormat formatted;

    public OrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        rowView = inflater.inflate(R.layout.order_item, null);
        holder = new Holder();
        db_handler = new DB_Handler(context);

        setId(rowView);

        formatted = new DecimalFormat("#0.00");

        Order order = orderList.get(position);

        final double total = order.getTotal();
        final double total1C = order.getTotal_1c();
        final String number = order.getNumber();

        //убираем менеджера из номера заказа
        String[] numberSplit = number.split("-");

        final String number_1c = order.getNumber_1c();
        final String store = db_handler.getNameStore(order.getStore_id());
        final Client client = db_handler.getClientsById(order.getClient_code());
        final String comment = order.getComment();

        if (number_1c != null) {
            holder.numberOrder.setText("№" + numberSplit[0] + " / " + number_1c);
        }else{
            holder.numberOrder.setText("№" + numberSplit[0]);
        }

        if (comment.equals("")) {
            holder.orderComment.setVisibility(View.GONE);
            holder.textComment.setVisibility(View.GONE);
        }else {
            holder.textComment.setVisibility(View.VISIBLE);
            holder.orderComment.setText(comment);
        }

        holder.orderStore.setText(store);
        holder.clientOrder.setText(client.getName());
        holder.totalOrder.setText(formatted.format(total) + "  ₴");
        holder.totalOrder.setTypeface(Typeface.DEFAULT_BOLD);
        holder.orderStore.setText(store);

        //статус заказа
        String status_ord;
        switch (order.getStatus()) {
            case 2:  status_ord = "В работе склада";
                break;
            case 3:  status_ord = "Готов к отгрузке";
                break;
            case 4:  status_ord = "Отгружен";
                break;
            default: status_ord = "В работе менеджера";
                break;
        }
        holder.orderStatus.setText(status_ord);

        if (order.getBase() == 1){
            if (total1C < total){
                holder.totalOrder1C.setVisibility(View.VISIBLE);
                holder.totalOrder1C.setText(formatted.format(total1C) + "  ₴");
                holder.totalOrder1C.setTypeface(Typeface.DEFAULT_BOLD);
                holder.totalOrder.setTypeface(Typeface.DEFAULT);
                holder.totalOrder.setTextSize(12);
                holder.totalOrder.setPaintFlags(holder.totalOrder.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.totalOrder.setTextColor(context.getResources().getColor(R.color.sort_bar_divider));
            }
        }

        setOnClickListener(order);

        return rowView;
    }

    private void setOnClickListener(Order order) {
        // Product Item Click
        holder.itemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetails.class);
                intent.putExtra(Constants.ORDER_ID, order.getNumber());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    private void setId(View rowView) {
        holder.numberOrder = rowView.findViewById(R.id.numberOrder);
        holder.itemOrder = rowView.findViewById(R.id.itemOrder);
        holder.totalOrder = rowView.findViewById(R.id.totalOrder);
        holder.totalOrder1C = rowView.findViewById(R.id.totalOrder1C);
        holder.clientOrder = rowView.findViewById(R.id.clientOrder);
        holder.orderStore = rowView.findViewById(R.id.orderStore);
        holder.orderStatus = rowView.findViewById(R.id.orderStatus);
        holder.orderComment = rowView.findViewById(R.id.Comment);
        holder.textComment = rowView.findViewById(R.id.textComment);
    }

    public class Holder {
        RelativeLayout itemOrder;
        TextView numberOrder, totalOrder, totalOrder1C, clientOrder, isBase, orderStore, orderStatus, orderComment, textComment;
    }

}

package com.preethzcodez.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.objects.OrdersDate;
import com.preethzcodez.ecommerce.utils.OrdersGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersDataAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<OrdersDate> orderList;
    DB_Handler db_handler;
    Holder holder;
    View rowView;

    public OrdersDataAdapter(Context context, List<OrdersDate> orderList) {
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

        rowView = inflater.inflate(R.layout.order_data_item, null);
        holder = new Holder();
        db_handler = new DB_Handler(context);

        setId(rowView);

        String oldDateString = orderList.get(position).getDate();
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date date = null;
        try {
            date = oldDateFormat.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = newDateFormat.format(date);

        holder.date_order.setText(result);

        holder.OrdersGridView.setAdapter(new OrdersAdapter(context, orderList.get(position).getOrderList()));
        holder.OrdersGridView.setExpanded(true);

        return rowView;
    }

    private void setId(View rowView) {
        holder.date_order = rowView.findViewById(R.id.date_order);
        holder.OrdersGridView = rowView.findViewById(R.id.orders);
    }

    public class Holder {
        com.preethzcodez.ecommerce.utils.OrdersGridView OrdersGridView;
        TextView date_order;
    }

}

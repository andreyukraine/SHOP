package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.pojo.TypeDiscont;
import java.util.List;

public class DiscontGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<TypeDiscont> TypeDiscontList;
    private DB_Handler db_handler;
    View rowView;
    Holder holder;
    Bundle bundle;

    public DiscontGridAdapter(Context context, List<TypeDiscont> TypeDiscontList) {
        this.context = context;
        this.TypeDiscontList = TypeDiscontList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface IselectDiscont {
        void selectDiscont(String code, String name);
    }

    @Override
    public int getCount() {
        return TypeDiscontList.size();
    }

    @Override
    public Object getItem(int i) {
        return TypeDiscontList.get(i);
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
        rowView = inflater.inflate(R.layout.discont_list, null);
        bundle = new Bundle();

        setId(rowView);

        String name = TypeDiscontList.get(position).getName();
        String code = TypeDiscontList.get(position).getId();

        holder.title.setText(name);

        setOnClickListener(position);

        return rowView;
    }

    private void setOnClickListener(int position) {
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context instanceof DiscontGridAdapter.IselectDiscont) {
                    ((DiscontGridAdapter.IselectDiscont) context).selectDiscont(TypeDiscontList.get(position).getId(), TypeDiscontList.get(position).getName());
                }
            }
        });
    }

    private void setId(View rowView) {
        holder.title = rowView.findViewById(R.id.title);
    }

    public class Holder {
        TextView title;
    }
}

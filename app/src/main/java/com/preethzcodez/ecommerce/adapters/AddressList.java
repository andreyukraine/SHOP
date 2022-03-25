package com.preethzcodez.ecommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.objects.Address;
import com.preethzcodez.ecommerce.pojo.Discont;

import java.util.List;

public class AddressList extends ArrayAdapter<Address> {

    LayoutInflater flater;
    private boolean initialTextWasShown = false;

    public AddressList(Activity context, int resource, int textViewResourceId, List<Address> address) {
        super(context, resource, textViewResourceId, address);
        flater = context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        Address rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        holder = new viewHolder();
        flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowview = flater.inflate(R.layout.address_list, null, false);
        holder.title = (TextView) rowview.findViewById(R.id.title);
        holder.vid = (TextView) rowview.findViewById(R.id.vid);


//        if (rowview==null) {
//            holder = new viewHolder();
//            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            rowview = flater.inflate(R.layout.address_list, null, false);
//            holder.title = (TextView) rowview.findViewById(R.id.title);
//            holder.vid = (TextView) rowview.findViewById(R.id.vid);
//            if (position != 0) {
//                rowview.setTag(holder);
//            }else{
//                holder.title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                holder.vid.setVisibility(View.GONE);
//                rowview.setTag(holder);
//            }
//        }else{
//            holder = (viewHolder) rowview.getTag();
//        }

        holder.title.setText(rowItem.getName());
        holder.vid.setText(rowItem.getVid());

        return rowview;
    }

    private class viewHolder{
        TextView title;
        TextView vid;
    }
}

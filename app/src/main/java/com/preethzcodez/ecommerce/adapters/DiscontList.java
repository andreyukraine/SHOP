package com.preethzcodez.ecommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.objects.DiscontType;
import com.preethzcodez.ecommerce.utils.DiscontGridView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DiscontList extends ArrayAdapter<DiscontType> {

    private LayoutInflater flater;
    private DB_Handler db_handler;
    private boolean initialTextWasShown = false;

    public DiscontList(Activity context, int resource, int textViewResourceId, List<DiscontType> disconts) {
        super(context, resource, textViewResourceId, disconts);
        flater = context.getLayoutInflater();
        db_handler = new DB_Handler(context);
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

        DiscontType rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {
            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.discont_type_client, null, false);

            holder.type_discont = rowview.findViewById(R.id.type_discont);
            holder.discontGridView = rowview.findViewById(R.id.disconts);
            rowview.setTag(holder);

        }else{
            holder = (viewHolder) rowview.getTag();
        }

        String discount_type_name = db_handler.getTypeDiscountById(rowItem.getType());
        //убираем "- вид карты"
        discount_type_name = discount_type_name.replace(" - вид карты", "");
        holder.type_discont.setText(discount_type_name);


        holder.discontGridView.setAdapter(new DiscontGridAdapter(getContext(), rowItem.getTypeDiscont()));
        holder.discontGridView.setExpanded(true);

        return rowview;
    }

    private class viewHolder{
        TextView type_discont;
        DiscontGridView discontGridView;
    }
}

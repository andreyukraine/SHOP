package com.preethzcodez.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.objects.PicsDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PicsDataAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<PicsDate> picsList;
    DB_Handler db_handler;
    Holder holder;
    View rowView;

    public PicsDataAdapter(Context context, List<PicsDate> picsList) {
        this.context = context;
        this.picsList = picsList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return picsList.size();
    }

    @Override
    public Object getItem(int i) {
        return picsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        rowView = inflater.inflate(R.layout.pics_data_item, null);
        holder = new Holder();
        db_handler = new DB_Handler(context);

        setId(rowView);

        String oldDateString = picsList.get(position).getDate();
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date date = null;
        try {
            date = oldDateFormat.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = newDateFormat.format(date);

        holder.date_pics.setText(result);

        holder.PicsGridView.setAdapter(new GallaryListAdapter(context, picsList.get(position).getPicsList()));
        holder.PicsGridView.setExpanded(true);

        return rowView;
    }

    private void setId(View rowView) {
        holder.date_pics = rowView.findViewById(R.id.date_pics);
        holder.PicsGridView = rowView.findViewById(R.id.pics);
    }

    public class Holder {
        com.preethzcodez.ecommerce.utils.PicsGridView PicsGridView;
        TextView date_pics;
    }

}

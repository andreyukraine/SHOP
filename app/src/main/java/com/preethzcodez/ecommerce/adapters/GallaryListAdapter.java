package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.pojo.Pics;
import com.squareup.picasso.Picasso;
import java.util.List;

public class GallaryListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Pics> picsList;
    View rowView;
    Holder holder;

    public GallaryListAdapter(Context context, List<Pics> picsList) {
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        rowView = inflater.inflate(R.layout.gallery_item, null);
        holder = new Holder();

        setId(rowView);
        Picasso.get().load("file://" + picsList.get(position).getPics_name()).fit().centerInside().placeholder(R.drawable.ic_image_grey600_36dp).error(R.drawable.ic_image_grey600_36dp).into(holder.img);


        holder.img_time.setText("lat: " + picsList.get(position).getGps_lat() + "\n" + "lon: " + picsList.get(position).getGps_lon());
        holder.gpsData.setText(picsList.get(position).getTime());

        return rowView;
    }

    private void setId(View rowView) {
        holder.img = rowView.findViewById(R.id.img);
        holder.img_time = rowView.findViewById(R.id.time);
        holder.gpsData = rowView.findViewById(R.id.gpsData);
    }

    public class Holder {
        ImageView img;
        TextView gpsData;
        TextView img_time;
    }

}

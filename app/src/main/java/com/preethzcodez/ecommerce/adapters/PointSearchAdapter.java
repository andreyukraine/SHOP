package com.preethzcodez.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.preethzcodez.ecommerce.pojo.PointNP;

import java.util.ArrayList;

public class PointSearchAdapter extends ArrayAdapter<PointNP> {
    private ArrayList<PointNP> items;
    private ArrayList<PointNP> itemsAll;
    private ArrayList<PointNP> suggestions;
    private int viewResourceId;

    @SuppressWarnings("unchecked")
    public PointSearchAdapter(Context context, int viewResourceId, ArrayList<PointNP> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<PointNP>) items.clone();
        this.suggestions = new ArrayList<PointNP>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        PointNP product = items.get(position);
        if (product != null) {
            TextView productLabel = (TextView)  v.findViewById(android.R.id.text1);
            if (productLabel != null) {
                productLabel.setText(product.getName());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            String str = ((PointNP) (resultValue)).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (PointNP product : itemsAll) {
                    if (product.getName().toLowerCase()
                            .contains(constraint.toString().toLowerCase())) {
                        suggestions.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<PointNP> filteredList = (ArrayList<PointNP>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (PointNP c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}
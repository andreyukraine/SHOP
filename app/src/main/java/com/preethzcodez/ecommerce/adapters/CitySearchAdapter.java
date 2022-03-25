package com.preethzcodez.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.preethzcodez.ecommerce.objects.SelectGift;
import com.preethzcodez.ecommerce.pojo.CityNP;
import java.util.ArrayList;
import java.util.List;

public class CitySearchAdapter extends ArrayAdapter<CityNP> {
    private ArrayList<CityNP> items;
    private ArrayList<CityNP> itemsAll;
    private ArrayList<CityNP> suggestions;
    private int viewResourceId;

    @SuppressWarnings("unchecked")
    public CitySearchAdapter(Context context, int viewResourceId, ArrayList<CityNP> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<CityNP>) items.clone();
        this.suggestions = new ArrayList<CityNP>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        CityNP product = items.get(position);
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
            String str = ((CityNP) (resultValue)).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (CityNP product : itemsAll) {
                    if (product.getName().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
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
            ArrayList<CityNP> filteredList = (ArrayList<CityNP>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (CityNP c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }else{
                clear();
                CityNP notCity = new CityNP();
                notCity.setName("Не найден город, обновите список городов");
                add(notCity);
            }
        }
    };



}
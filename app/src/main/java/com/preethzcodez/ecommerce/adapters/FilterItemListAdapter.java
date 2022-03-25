package com.preethzcodez.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.objects.FilterValue;
import com.preethzcodez.ecommerce.pojo.ProductOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.preethzcodez.ecommerce.utils.Util.infoMsg;

public class FilterItemListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<FilterValue>> _listDataChild;
    private List<FilterValue> selectFilter;
    private List<ProductOption> productOptions;

    public FilterItemListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<FilterValue>> listChildData, List<FilterValue> selectFilter) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.selectFilter = selectFilter;
    }

    @Override
    public FilterValue getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    public List<FilterValue> getCildsGroupe(String group){
        List<FilterValue> values = new ArrayList<>();
        for(String option : this._listDataChild.keySet()){
            //System.out.println(option);
            if (group.equals(option)){
                int iter = 0;
                for (FilterValue val : Objects.requireNonNull(this._listDataChild.get(option))) {
                    values.add(val);
                }
            }
        }
        return values;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getValue();

        final FilterValue value = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert infalInflater != null;
            convertView = infalInflater.inflate(R.layout.sort_filter_listitem, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.name);

        // Set Tick Visibility
        ImageView img = convertView.findViewById(R.id.tick);

        String groupeSelect = ((ExpandableListView) parent).getExpandableListAdapter().getGroup(groupPosition).toString();

        if (value.isSelect()){
            img.setVisibility(View.VISIBLE);
        }else{
            img.setVisibility(View.GONE);
        }

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert infalInflater != null;
            convertView = infalInflater.inflate(R.layout.sort_filter_listitem, null);
        }

        // Set Count
        TextView count = convertView.findViewById(R.id.count);

        // Set Arrow
        ImageView arrow = convertView.findViewById(R.id.arrow);
        arrow.setVisibility(View.VISIBLE);

        if (isExpanded) {
            arrow.setImageResource(R.drawable.ic_chevron_down_grey600_24dp);
        } else {
            arrow.setImageResource(R.drawable.ic_chevron_right_grey600_24dp);
        }

        //вычисляем сколько отмеченных елентов
        int selectCount = 0;
        for (int i = 0; i < getCildsGroupe(headerTitle).size(); i++) {
            if (getCildsGroupe(headerTitle).get(i).isSelect()){
                selectCount++;
            }
        }
        try {
            if (selectCount > 0) {
                count.setVisibility(View.VISIBLE);
                count.setText(String.valueOf(selectCount));
            } else {
                count.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            count.setVisibility(View.GONE);
        }

        // Set Header
        TextView lblListHeader = convertView.findViewById(R.id.name);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setTextColor(Color.BLACK);
        lblListHeader.setTextSize(16);
        lblListHeader.setText(headerTitle);

        ImageView tick = convertView.findViewById(R.id.tick);
        tick.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
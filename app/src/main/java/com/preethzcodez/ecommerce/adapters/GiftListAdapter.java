package com.preethzcodez.ecommerce.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.preethzcodez.ecommerce.R;
import com.preethzcodez.ecommerce.objects.SelectGift;
import com.preethzcodez.ecommerce.pojo.ProductDiscont;

import java.util.ArrayList;
import java.util.List;

import static com.preethzcodez.ecommerce.utils.Util.infoMsg;


public class GiftListAdapter extends BaseAdapter {

    private final Context context;
    private final List<ProductDiscont> productList;
    private LayoutInflater inflater;
    private final int countGift;
    private List<SelectGift> selectGift = new ArrayList<>();
    private String idDiscont;
    Holder holder;

    public GiftListAdapter(Context context, List<ProductDiscont> productList, int countGift, String idDiscont) {
        this.productList = productList;
        this.context = context;
        this.countGift = countGift;
        this.idDiscont = idDiscont;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface IupdateGifts {
        void updateGift(List<SelectGift> selectGift, int countGift, String idDiscont, int position);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.item_gift_list, parent, false);
        holder = new Holder();

        ProductDiscont gift = productList.get(position);

        setId(rowView);
        if (gift.getOstatok() > 0) {
            holder.tv_user_name.setText(gift.getName());
            String ostatok = Integer.toString(gift.getOstatok()) + " шт.";
            holder.countStoreGift.setText(ostatok);
            holder.tv_user_count.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (countSelectGifts() <= countGift) {
                        infoMsg(context, "before - " + s.toString());
                    } else {
                        holder.tv_user_count.setText("");
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        int enterCount = Integer.parseInt(s.toString());

                        if (countSelectGifts() > countGift){

                        }else if (enterCount > countGift){

                        }else{
                            SelectGift select = new SelectGift(gift, enterCount);
                            selectGift.add(select);
                            infoMsg(context, "on text - " + s.toString());
                        }

                    }

                    if (context instanceof GiftListAdapter.IupdateGifts) {
                        ((GiftListAdapter.IupdateGifts) context).updateGift(selectGift, countGift, idDiscont, position);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.equals("")) {
                        int enterCount = Integer.parseInt(s.toString());
                        if (enterCount <= countGift) {
                            infoMsg(context, "after - " + s.toString());
                        } else {
                            s.append("");
                        }
                    }
                }
            });
        }
        return rowView;
    }

    public boolean isEnableButton(List<SelectGift> selectGift, int countGift) {
        boolean result = false;
        int countAllGift = 0;
        for (int i = 0; i < selectGift.size(); i++) {
            countAllGift = countAllGift + selectGift.get(i).getCountGift();
        }
        if (countAllGift <= countGift) {
            result = true;
        }
        if (countAllGift > countGift) {
            result = false;
        }
        return result;
    }

    private void setId(View rowView) {
        holder.tv_user_name = rowView.findViewById(R.id.tv_user_name);
        holder.tv_user_count = rowView.findViewById(R.id.tv_user_count);
        holder.countStoreGift = rowView.findViewById(R.id.countStoreGift);
    }

    private int countSelectGifts(){
        int countAllGift = 0;
        for (int i = 0; i < selectGift.size(); i++) {
            countAllGift = countAllGift + selectGift.get(i).getCountGift();
        }
        return countAllGift;
    }

    public class Holder {
        TextView tv_user_name, countStoreGift;
        EditText tv_user_count;
    }
}
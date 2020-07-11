package com.example.medicplus.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.example.medicplus.R;
import com.example.medicplus.activity.MainActivity;
import com.example.medicplus.activity.OutOfStockMedicines;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ListAdapterMedicines extends BaseAdapter {

    Context context;
    private final ArrayList<String> name;
    private final ArrayList<String> category;
    private final ArrayList<String> list_price;
    private final ArrayList<String> qty;
    private final ArrayList<String> expired_date;
    private final ArrayList<Bitmap> images;


    public ListAdapterMedicines(Context context, ArrayList<String> name, ArrayList<String> category, ArrayList<String> expired_date, ArrayList<String> list_price, ArrayList<String> qty, ArrayList<Bitmap> images) {
        this.context = context;
        this.name = name;
        this.category = category;
        this.expired_date = expired_date;
        this.list_price = list_price;
        this.qty = qty;
        this.images = images;
    }

    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_invoice_listview, parent, false);

            viewHolder.med_name = (TextView) convertView.findViewById(R.id.invoice_desc);
            viewHolder.med_price = (TextView) convertView.findViewById(R.id.invoice_price);
            viewHolder.med_qty = (TextView) convertView.findViewById(R.id.invoice_qty);
            viewHolder.med_type = (TextView) convertView.findViewById(R.id.invoice_payment_type);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.med_name.setText(name.get(position));
        viewHolder.med_qty.setText("Qty: " + qty.get(position));
        viewHolder.med_price.setText("₹ " + list_price.get(position));
        viewHolder.med_type.setText(category.get(position));
        viewHolder.icon.setImageBitmap(images.get(position));

        return convertView;
    }

    private static class ViewHolder {

        TextView med_name;
        TextView med_qty;
        TextView med_price;
        TextView med_type;
        ImageView icon;

    }






}
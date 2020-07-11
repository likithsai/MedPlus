package com.example.medicplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medicplus.R;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ListAdapterInvoiceList extends BaseAdapter {
    private Context mContext;

    private ArrayList<String> invoice_desc;
    private ArrayList<String> invoice_date;
    private ArrayList<String> invoice_price;
    private ArrayList<String> invoice_qty;
    private ArrayList<String> invoice_payment_type;


    public ListAdapterInvoiceList(Context context, ArrayList<String> desc, ArrayList<String> date, ArrayList<String> price, ArrayList<String> qty, ArrayList<String> payment_type) {
        this.mContext = context;
        this.invoice_desc = desc;
        this.invoice_date = date;
        this.invoice_price = price;
        this.invoice_qty = qty;
        this.invoice_payment_type = payment_type;
    }


    @Override
    public int getCount() {
        return invoice_desc.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.custom_invoice_listview, parent, false);
            
            viewHolder.invoice_desc = (TextView) convertView.findViewById(R.id.invoice_desc);
            viewHolder.invoice_price = (TextView) convertView.findViewById(R.id.invoice_price);
            viewHolder.invoice_qty = (TextView) convertView.findViewById(R.id.invoice_qty);
            viewHolder.invoice_payment_type = (TextView) convertView.findViewById(R.id.invoice_payment_type);
            viewHolder.invoice_image = (ImageView) convertView.findViewById(R.id.appIconIV);
            viewHolder.invoice_price_per_unit = (TextView) convertView.findViewById(R.id.invoice_price_per_unit);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.invoice_desc.setText(invoice_desc.get(position));
        viewHolder.invoice_price.setText("₹ " + invoice_price.get(position));
        viewHolder.invoice_qty.setText("Invoice ID: " + invoice_date.get(position));
        viewHolder.invoice_payment_type.setText(invoice_payment_type.get(position));
        viewHolder.invoice_image.setPadding(20, 20, 20, 20);
        viewHolder.invoice_image.setImageResource(R.drawable.ic_manufacturer);
        viewHolder.invoice_price_per_unit.setText("Price Per Unit: ₹ " + Float.parseFloat(invoice_price.get(position)) /Integer.parseInt(invoice_qty.get(position)));

        return convertView;
    }

    private static class ViewHolder {
        TextView invoice_desc;
        TextView invoice_price;
        TextView invoice_payment_type;
        TextView invoice_qty;
        TextView invoice_price_per_unit;
        ImageView invoice_image;
    }

}
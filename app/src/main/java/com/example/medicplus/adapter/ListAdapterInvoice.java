package com.example.medicplus.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ListAdapterInvoice extends BaseAdapter {
    private Context mContext;

    private ArrayList<String> med_name;
    private ArrayList<String> med_expiry;
    private ArrayList<String> med_category;
    private ArrayList<String> med_price;
    private ArrayList<String> med_quantity;
    private ArrayList<Bitmap> med_image;
    private ArrayList<String> med_sel_quantity;


    public ListAdapterInvoice(Context context, ArrayList<String> med_name, ArrayList<String> med_expiry, ArrayList<String> med_category, ArrayList<String> med_price, ArrayList<String> med_qty, ArrayList<Bitmap> med_img, ArrayList<String> sel_qty) {
        this.mContext = context;
        this.med_name = med_name;
        this.med_expiry = med_expiry;
        this.med_category = med_category;
        this.med_price = med_price;
        this.med_quantity = med_qty;
        this.med_image = med_img;
        this.med_sel_quantity = sel_qty;
    }

    public ListAdapterInvoice(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        return (med_name != null && !med_name.isEmpty()) ? med_name.size() : 0;
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
            convertView = inflater.inflate(R.layout.invoice_listview, parent, false);
            
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.list_price = (TextView) convertView.findViewById(R.id.list_price);
            viewHolder.price_per_unit = (TextView) convertView.findViewById(R.id.price_per_unit);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);
            viewHolder.expired_date = (TextView) convertView.findViewById(R.id.expiry_date);
            viewHolder.lst = (CardView) convertView.findViewById(R.id.list);
//            viewHolder.numPicker = (ArrowNumberPicker) convertView.findViewById(R.id.inv_qty_picker);
            viewHolder.numPicker = (com.travijuu.numberpicker.library.NumberPicker) convertView.findViewById(R.id.inv_qty_picker);
            viewHolder.but_delete = (TextView) convertView.findViewById(R.id.but_delete);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.name.setText(med_name.get(position));
        viewHolder.expired_date.setText("Exp: " + med_expiry.get(position));
        viewHolder.price_per_unit.setText("Price Per Unit: ₹ " + med_price.get(position));
        viewHolder.icon.setImageBitmap(med_image.get(position));
        viewHolder.numPicker.setValue(Integer.parseInt(med_sel_quantity.get(position)));
        viewHolder.numPicker.setMax(Integer.parseInt(med_quantity.get(position)));
        viewHolder.list_price.setText("₹ " + Float.parseFloat(med_price.get(position)) * Integer.parseInt(med_sel_quantity.get(position)));

        viewHolder.numPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                med_sel_quantity.add(position, String.valueOf(viewHolder.numPicker.getValue()));
                viewHolder.list_price.setText("₹ " + Float.parseFloat(med_price.get(position)) * Integer.parseInt(med_sel_quantity.get(position)));
            }
        });

//        viewHolder.numPicker.setPickerChangedListener(new ArrowNumberPicker.PickerChangeListener() {
//            @Override
//            public void onPickerChanged(int newValue) {
//                med_sel_quantity.add(position, String.valueOf(viewHolder.numPicker.getValue()));
//                viewHolder.list_price.setText("₹ " + Float.parseFloat(med_price.get(position)) * Integer.parseInt(med_sel_quantity.get(position)));
//            }
//        });


        viewHolder.but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(mContext)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete medicine " + med_name.get(position) + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int a) {

                                med_name.remove(position);
                                med_expiry.remove(position);
                                med_category.remove(position);
                                med_price.remove(position);
                                med_quantity.remove(position);
                                med_image.remove(position);
                                med_sel_quantity.remove(position);

                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }
        });

        return convertView;
    }


    public float getTotal() {
        float result = 0;
        for(int i=0; i<med_name.size(); i++) {
            result += Float.parseFloat(med_price.get(i))  * Integer.parseInt(med_sel_quantity.get(i));
        }

        return result;
    }

    public ArrayList<String> getInvoiceName() {
        return med_name;
    }


    public ArrayList<String> getInvoicePrice() {
        return med_price;
    }

    public ArrayList<String> getInvoiceQty() {
        return med_sel_quantity;
    }

    public ArrayList<Bitmap> getInvoiceImage() { return med_image; }


    private static class ViewHolder {

        TextView name;
        TextView list_price;
        TextView price_per_unit;
        //TextView qty;
        ImageView icon;
        TextView expired_date;
        TextView but_delete;
        CardView lst;
//        ArrowNumberPicker numPicker;
        com.travijuu.numberpicker.library.NumberPicker numPicker;
    }

}
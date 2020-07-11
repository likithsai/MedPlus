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
import com.example.medicplus.R;
import com.example.medicplus.activity.MainActivity;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ListAdapterCustomer extends BaseAdapter {

    Context context;
//    private final String [] name;
//    private final String [] category;
//    private final Bitmap[] images;

    private ArrayList<String> name;
    private ArrayList<String> category;
    private ArrayList<Bitmap> images;

//    public ListAdapterCustomer(Context context, String[] name, String[] category, Bitmap[] images){
//        //super(context, R.layout.single_list_app_item, utilsArrayList);
//        this.context = context;
//        this.name = name;
//        this.category = category;
//        this.images = images;
//    }

    public ListAdapterCustomer(MainActivity context, ArrayList<String> name, ArrayList<String> category, ArrayList<Bitmap> images) {
        this.context = context;
        this.name = name;
        this.category = category;
        this.images = images;
    }

//    @Override
//    public int getCount() {
//        return name.length;
//    }

    @Override
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
            convertView = inflater.inflate(R.layout.custom_listview_customer, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.category = (TextView) convertView.findViewById(R.id.category);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        viewHolder.name.setText(name[position]);
//        viewHolder.category.setText(category[position]);
//        viewHolder.icon.setImageBitmap(images[position]);
        viewHolder.name.setText(name.get(position));
        viewHolder.category.setText(category.get(position));
        viewHolder.icon.setImageBitmap(images.get(position));
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView category;
        ImageView icon;
    }

}
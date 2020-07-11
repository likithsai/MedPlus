package com.example.medicplus.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.medicplus.R;
import com.example.medicplus.activity.AddMedicines;
import com.example.medicplus.activity.AddStore;
import com.example.medicplus.activity.MainActivity;
import com.example.medicplus.database.DatabaseHandler;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ListAdapterStores extends BaseAdapter {

    Context context;
//    private final String[] store_name;
//    private final String[] store_city;
//    private final Bitmap[] store_images;
//    private final String[] store_id;

    private ArrayList<String> store_name;
    private ArrayList<String> store_city;
    private ArrayList<String> store_id;
    private ArrayList<Bitmap> store_images;

    public ListAdapterStores(Context context, String[] name, String[] city, Bitmap[] store_images, String[] store_id){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
//        this.context = context;
//        this.store_name = name;
//        this.store_city = city;
//        this.store_images = store_images;
//        this.store_id = store_id;

    }

    public ListAdapterStores(MainActivity context, ArrayList<String> name, ArrayList<String> city, ArrayList<Bitmap> images, ArrayList<String> store_id) {
        this.context = context;
        this.store_name = name;
        this.store_city = city;
        this.store_images = images;
        this.store_id = store_id;
    }

    public ListAdapterStores(AddMedicines addMedicines, ArrayList<String> category_name) {
    }

//    @Override
//    public int getCount() {
//        return store_name.length;
//    }

    @Override
    public int getCount() {
        return store_name.size();
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
            convertView = inflater.inflate(R.layout.custom_store_listview, parent, false);
            viewHolder.img_store = (ImageView) convertView.findViewById(R.id.img_store);
            viewHolder.name = (TextView) convertView.findViewById(R.id.store_name);
            //viewHolder.address = (TextView) convertView.findViewById(R.id.store_address);
            viewHolder.city = (TextView) convertView.findViewById(R.id.store_city);
            viewHolder.but_delete = (ImageView) convertView.findViewById(R.id.but_delete);
            viewHolder.but_edit = (ImageView) convertView.findViewById(R.id.but_edit);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.img_store.setImageBitmap(store_images.get(position));
        viewHolder.name.setText(store_name.get(position));
        viewHolder.city.setText(store_city.get(position));

        viewHolder.but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                    .setTitle("Delete Store")
                    .setMessage("Do you want to delete Store " + store_name.get(position) + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int a) {
                            Toast.makeText(context, "Store " + store_name.get(position) + " deleted!", Toast.LENGTH_LONG).show();
                            DatabaseHandler db = new DatabaseHandler(context);
                            db.deleteStore(store_id.get(position));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
            }
        });


        viewHolder.but_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addStore = new Intent(context, AddStore.class);
                addStore.putExtra("STORE_EDIT", store_id.get(position));
                context.startActivity(addStore);
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView img_store;
        TextView name;
        //TextView address;
        TextView city;
        ImageView but_delete, but_edit;
    }

}
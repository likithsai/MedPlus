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
import com.example.medicplus.activity.AddCategory;
import com.example.medicplus.database.DatabaseHandler;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ListAdapterCategory extends BaseAdapter {

    Context context;
    private ArrayList<String> category_id;
    private ArrayList<String> category;
    private ArrayList<Bitmap> category_image;

    public ListAdapterCategory(Context context, ArrayList<String> id, ArrayList<String> category, ArrayList<Bitmap> category_image){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.category_id = id;
        this.category = category;
        this.category_image = category_image;
    }

    @Override
    public int getCount() {
        return category.size();
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
            convertView = inflater.inflate(R.layout.custom_category_row, parent, false);
            viewHolder.category_name = (TextView) convertView.findViewById(R.id.category_name);
            viewHolder.but_edit = (ImageView) convertView.findViewById(R.id.but_edit);
            viewHolder.but_delete = (ImageView) convertView.findViewById(R.id.but_delete);
            viewHolder.appIconIV = (ImageView) convertView.findViewById(R.id.appIconIV);

            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.but_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, AddCategory.class);
                in.putExtra("MED_CAT_EDIT", category_id.get(position));
                context.startActivity(in);
            }
        });

        viewHolder.but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Store")
                        .setMessage("Do you want to delete Store " + category.get(position) + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int a) {
                                Toast.makeText(context, "Store " + category.get(position) + " deleted!", Toast.LENGTH_LONG).show();
                                DatabaseHandler db = new DatabaseHandler(context);
                                db.deleteCategory(category.get(position));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

        viewHolder.category_name.setText(category.get(position));
        viewHolder.appIconIV.setImageBitmap(category_image.get(position));
        return convertView;
    }

    private static class ViewHolder {
        TextView category_name;
        ImageView but_edit;
        ImageView but_delete;
        ImageView appIconIV;
    }

}
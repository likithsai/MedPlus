package com.example.medicplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.medicplus.R;
import com.example.medicplus.database.medicines;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class CustomSearchTextview extends ArrayAdapter<medicines> {

    Context context;
    int resource, textViewResourceId;
    List<medicines> items, tempItems, suggestions;

    public CustomSearchTextview(Context context, int resource, int textViewResourceId, List<medicines> items) {
        super(context, resource, textViewResourceId, items);

        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<medicines>(items); // this makes the difference.
        suggestions = new ArrayList<medicines>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, parent, false);
        }
        medicines med = items.get(position);
        if (med != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null) lblName.setText(med.getMedicineName());

            TextView lblExpiry = (TextView) view.findViewById(R.id.lbl_expiry);
            if (lblExpiry != null) lblExpiry.setText("Expiry: " + med.getMedicineExpiry());

            TextView lblprice = (TextView) view.findViewById(R.id.lbl_price);
            if (lblprice != null) lblprice.setText("₹ " + med.getMedicineSellingPrice());

            TextView lblQty = (TextView) view.findViewById(R.id.lbl_qty);
            if (lblQty != null) lblQty.setText("Qty: " + med.getMedicineQuantity());

        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }






    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((medicines) resultValue).getMedicineName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (medicines med : tempItems) {
                    if (med.getMedicineName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(med);
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
            List<medicines> filterList = (ArrayList<medicines>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (medicines med : filterList) {
                    add(med);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
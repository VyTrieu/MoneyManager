package com.example.acer.quanlychitieu.MyAdapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.acer.quanlychitieu.R;
import com.example.acer.quanlychitieu.Values.ThuChiValues;

import java.util.ArrayList;

/**
 * Created by Acer on 22/05/2017.
 */

public class ThuChi_Adapter extends ArrayAdapter<ThuChiValues> {
    private Activity context;
    private int resource;
    private ArrayList<ThuChiValues> objects;

    public ThuChi_Adapter(Activity context, int resource, ArrayList<ThuChiValues> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.chi_tiet, null);

        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        TextView txtCategory = (TextView) convertView.findViewById(R.id.txtCategory);
        TextView txtMoney = (TextView) convertView.findViewById(R.id.txtMoney);

        ThuChiValues dept = getItem(position);
        txtDate.setText(dept.getDate());
        txtCategory.setText(dept.getcategory() + ": " + dept.getThuchi());
        txtMoney.setText(dept.getMoney());
        return convertView;
    }
}

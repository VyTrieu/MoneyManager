package com.example.acer.quanlychitieu.MyAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.acer.quanlychitieu.R;
import com.example.acer.quanlychitieu.Values.KeHoachValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by Acer on 24/05/2017.
 */

public class KeHoach_Adapter extends ArrayAdapter<KeHoachValues> {
    private Activity context;
    private int resource;
    private ArrayList<KeHoachValues> objects;

    public KeHoach_Adapter(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<KeHoachValues> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.kehoach_listdetails, null);

        TextView txtTen = (TextView) convertView.findViewById(R.id.txtTen);
        TextView txtSoTien = (TextView) convertView.findViewById(R.id.txtSoTien);
        TextView txtMucTieu = (TextView) convertView.findViewById(R.id.txtMucTieu);
        TextView txtNgayConLai = (TextView) convertView.findViewById(R.id.txtNgayConLai);

        KeHoachValues values = getItem(position);
        txtTen.setText(values.getTen());
        txtMucTieu.setText(values.getMuctieu());
        txtSoTien.setText(values.getSotien());
        txtNgayConLai.setText(values.autoupdate_date());
        return convertView;
    }
}

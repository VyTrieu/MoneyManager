package com.example.acer.quanlychitieu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.acer.quanlychitieu.Database.ThaoTacDatabase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Locale;

public class ThongKeActivity extends AppCompatActivity {

    private static String TAG = "ThongKeActivity";
    TabHost tabHost;
    private PieChart pieChart_ThuNhap, pieChart_ChiTieu;
    ThaoTacDatabase data;

    ArrayList<String> ydata_ChiTieu = new ArrayList<>();
    ArrayList<String> ydata_ThuNhap = new ArrayList<>();

    String [] details_chitieu = {"Ăn uống", "Hóa đơn & Tiện ích", "Di chuyển", "Mua sắm",
            "Bạn bè & Người yêu", "Giải trí", "Du lịch", "Sức khỏe",
            "Quà tặng & Từ thiện", "Gia dình", "Giáo dục", "Cho vay", "Trả nợ", "Khác"};
    String [] details_thunhap = {"Lương", "Thưởng", "Được tặng", "Tiền lãi", "Thu nợ", "Đi vay", "Khác"};

    ArrayList<String> xdata_ChiTieu = new ArrayList<>();
    ArrayList<String> xdata_ThuNhap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        data = new ThaoTacDatabase(getApplicationContext());
        ydata_ChiTieu = data.thongke_list_money("Chi Tiêu");
        xdata_ChiTieu = data.thongke_list_detail("Chi Tiêu");

        ydata_ThuNhap = data.thongke_list_money("Thu Nhập");
        xdata_ThuNhap = data.thongke_list_detail("Thu Nhập");

        setTab();
        getWidgetTab();
    }
    private void setTab() {
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();;
        TabHost.TabSpec spec;
        //Tab thu nhập
        spec = tabHost.newTabSpec("Thu Nhập");
        spec.setContent(R.id.tab_thunhap);
        spec.setIndicator("Thu Nhập");
        tabHost.addTab(spec);
        //Tab chi tieu
        spec = tabHost.newTabSpec("Chi Tiêu");
        spec.setContent(R.id.tab_chitieu);
        spec.setIndicator("Chi Tiêu");
        tabHost.addTab(spec);
        //Tab mặc định được chọn ban đầu là 0
        tabHost.setCurrentTab(0);
    }
    private void getWidgetTab() {
        pieChart_ChiTieu = (PieChart) findViewById(R.id.pieChart_ChiTieu);
        pieChart_ThuNhap = (PieChart) findViewById(R.id.pieChart_ThuNhap);
        chart_chitieu();
        chart_ThuNhap();
    }

    private void addPieData_ChiTieu() {
        ArrayList<PieEntry> yEntry = new ArrayList<>();
        ArrayList<String> xEntry = new ArrayList<>();

        for (int i = 0; i < ydata_ChiTieu.size(); i++) {
            yEntry.add(new PieEntry(Float.parseFloat(ydata_ChiTieu.get(i)), i));
        }
        for(int i = 0; i < xdata_ChiTieu.size(); i++) {
            xEntry.add(xdata_ChiTieu.get(i));
        }
        //Tạo data cho chart
        PieDataSet pieDataSet = new PieDataSet(yEntry, "Số tiền chi tiêu của tháng");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setSelectionShift(5);

        //Thêm màu
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Integer> loaichitieu_color = new ArrayList<>();
        loaichitieu_color.add(Color.YELLOW); //ăn uống
        loaichitieu_color.add(Color.BLUE); //hóa đơn
        loaichitieu_color.add(Color.GREEN); //di chuyển
        loaichitieu_color.add(Color.CYAN); //shopping
        loaichitieu_color.add(Color.RED); //Bạn bè
        loaichitieu_color.add(Color.LTGRAY); //Giải trí
        loaichitieu_color.add(Color.MAGENTA); //Du lịch
        loaichitieu_color.add(Color.rgb(241, 175, 0)); //Sức khỏe
        loaichitieu_color.add(Color.rgb(0, 174, 114)); //Gia đình
        loaichitieu_color.add(Color.rgb(241, 147, 115)); //Giáo dục
        loaichitieu_color.add(Color.rgb(255, 250, 205)); //Cho vay
        loaichitieu_color.add(Color.rgb(255, 228, 225)); //Trả nợ
        loaichitieu_color.add(Color.rgb(127, 255, 212)); //Khác

        for(int i = 0; i < xdata_ChiTieu.size(); i++) {
            for (int j = 0; j < details_chitieu.length; j ++) {
                if (xdata_ChiTieu.get(i).equals(details_chitieu[j])) {
                    colors.add(loaichitieu_color.get(j));
                }
            }
        }
        pieDataSet.setColors(colors);

        //Thêm chú thích (legend)
        Legend legend = pieChart_ChiTieu.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

        //Khởi tạo Pie Data
        PieData pieData = new PieData(pieDataSet);

        pieChart_ChiTieu.setData(pieData);
        pieChart_ChiTieu.highlightValue(null);
        pieChart_ChiTieu.invalidate();
    }
    private void chart_chitieu() {
        pieChart_ChiTieu.setDrawHoleEnabled(true);
        pieChart_ChiTieu.setHoleRadius(2);
        pieChart_ChiTieu.setTransparentCircleRadius(10);
        pieChart_ChiTieu.setEntryLabelTextSize(10);

        addPieData_ChiTieu();

        pieChart_ChiTieu.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e(TAG, "onValueSelected");
                Log.e(TAG, "onValueSelected " + e.getY());
                Log.e(TAG, "onValueSelected " + h.toString());

                int post = 0;
                if(h.getY() == e.getY()) {
                    post = (int) h.getX();
                }
                Toast.makeText(getApplicationContext(), xdata_ChiTieu.get(post) + ": " + ydata_ChiTieu.get(post) + "đ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addPieData_ThuNhap() {
        ArrayList<PieEntry> yEntry = new ArrayList<>();
        ArrayList<String> xEntry = new ArrayList<>();

        for (int i = 0; i < ydata_ThuNhap.size(); i++) {
            yEntry.add(new PieEntry(Float.parseFloat(ydata_ThuNhap.get(i)), i));
        }
        for(int i = 0; i < ydata_ThuNhap.size(); i++) {
            xEntry.add(ydata_ThuNhap.get(i));
        }
        //Tạo data cho chart
        PieDataSet pieDataSet = new PieDataSet(yEntry, "Số tiền thu nhập của tháng");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setSelectionShift(5);

        //Thêm màu
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Integer> loaichitieu_color = new ArrayList<>();
        loaichitieu_color.add(Color.YELLOW); //Thu nợ
        loaichitieu_color.add(Color.BLUE); //Đi vay
        loaichitieu_color.add(Color.GREEN); //Thưởng
        loaichitieu_color.add(Color.CYAN); //Được tặng
        loaichitieu_color.add(Color.RED); //Lương
        loaichitieu_color.add(Color.rgb(255, 228, 225)); //Tiền lãi
        loaichitieu_color.add(Color.rgb(127, 255, 212)); //Khác

        for(int i = 0; i < xdata_ThuNhap.size(); i++) {
            for (int j = 0; j < details_thunhap.length; j ++) {
                if (xdata_ThuNhap.get(i).equals(details_thunhap[j])) {
                    colors.add(loaichitieu_color.get(j));
                }
            }
        }
        pieDataSet.setColors(colors);

        //Thêm chú thích (legend)
        Legend legend = pieChart_ThuNhap.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

        //Khởi tạo Pie Data
        PieData pieData = new PieData(pieDataSet);

        pieChart_ThuNhap.setData(pieData);
        pieChart_ThuNhap.highlightValue(null);
        pieChart_ThuNhap.invalidate();
    }
    private void chart_ThuNhap() {
        pieChart_ThuNhap.setDrawHoleEnabled(true);
        pieChart_ThuNhap.setHoleRadius(2);
        pieChart_ThuNhap.setTransparentCircleRadius(10);
        pieChart_ThuNhap.setEntryLabelTextSize(10);

        addPieData_ThuNhap();
        pieChart_ThuNhap.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e(TAG, "onValueSelected");
                Log.e(TAG, "onValueSelected " + e.getY());
                Log.e(TAG, "onValueSelected " + h.toString());

                int post = 0;
                if(h.getY() == e.getY()) {
                    post = (int) h.getX();
                }
                Toast.makeText(getApplicationContext(), xdata_ThuNhap.get(post) + ": " + ydata_ThuNhap.get(post) + "đ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

}

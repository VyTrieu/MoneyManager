package com.example.acer.quanlychitieu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import static android.R.attr.id;

public class NhomThuChiActivity extends AppCompatActivity {

    String txtMsg;
    TabHost tabHost;
    ListView listThuNhap, listChiTieu;
    FloatingActionButton btnThemChiTieu, btnThemThuNhap;

    ArrayAdapter<String> adapter_chitieu = null;
    ArrayAdapter<String> adapter_thunhap = null;

    ArrayList<String> chitieu = new ArrayList<>();
    ArrayList<String> thunhap = new ArrayList<>();

    String [] chitieu_bd = {"Ăn uống", "Hóa đơn & Tiện ích", "Di chuyển", "Mua sắm",
            "Bạn bè & Người yêu", "Giải trí", "Du lịch", "Sức khỏe",
            "Quà tặng & Từ thiện", "Gia dình", "Giáo dục", "Cho vay", "Trả nợ", "Khác"};

    String [] thunhap_bd = {"Lương", "Thưởng", "Được tặng", "Tiền lãi", "Thu nợ", "Đi vay", "Khác"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhom_thu_chi);

        listThuNhap = (ListView) findViewById(R.id.list_tab_thunhap);
        listChiTieu = (ListView) findViewById(R.id.list_tab_chitieu);
        btnThemChiTieu = (FloatingActionButton) findViewById(R.id.btnThemChiTieu);
        btnThemThuNhap = (FloatingActionButton) findViewById(R.id.btnThemThuNhap);

        setTab();
        getWidget();
    }
    //Khởi tạo tabHost
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
    //Cầu hình tab
    private void getWidget(){
        for (int i = 0; i < chitieu_bd.length; i++) {
            chitieu.add(chitieu_bd[i]);
        }
        for(int i = 0; i < thunhap_bd.length; i++) {
            thunhap.add(thunhap_bd[i]);
        }
        adapter_chitieu = new ArrayAdapter<String>(NhomThuChiActivity.this, android.R.layout.simple_list_item_1, chitieu);
        adapter_thunhap = new ArrayAdapter<String>(NhomThuChiActivity.this, android.R.layout.simple_list_item_1, thunhap);

        listThuNhap.setAdapter(adapter_thunhap);
        listChiTieu.setAdapter(adapter_chitieu);

        btnThemThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(NhomThuChiActivity.this);
                dialog.setTitle("Thêm Nhóm");
                dialog.setContentView(R.layout.them_nhom_thuchi);
                final EditText editText = (EditText) dialog.findViewById(R.id.edtTenNhom);
                Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
                final RadioButton radThuNhap = (RadioButton) dialog.findViewById(R.id.radioThuNhap);
                final RadioButton radChiTieu = (RadioButton) dialog.findViewById(R.id.radioChiTieu);
                dialog.show();
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (radThuNhap.isChecked()){
                            thunhap.add(editText.getText().toString());
                            dialog.dismiss();
                            adapter_thunhap.notifyDataSetChanged();
                            listThuNhap.setAdapter(adapter_thunhap);
                    }
                    else if (radChiTieu.isChecked()) {
                            chitieu.add(editText.getText().toString());
                            dialog.dismiss();
                            adapter_chitieu.notifyDataSetChanged();
                            listChiTieu.setAdapter(adapter_chitieu);
                        }
                    }
                });
            }
        });

        final Intent categoty_intent = getIntent();
        final Bundle category_bundle = categoty_intent.getExtras();
        listThuNhap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtMsg = "Thu Nhập: " + thunhap.get(position);
                category_bundle.putString("txtMsg", txtMsg);
                categoty_intent.putExtras(category_bundle);
                setResult(Activity.RESULT_OK, categoty_intent);
                finish();
            }
        });
        listChiTieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtMsg = "Chi Tiêu: " + chitieu.get(position);
                category_bundle.putString("txtMsg", txtMsg);
                categoty_intent.putExtras(category_bundle);
                setResult(Activity.RESULT_OK, categoty_intent);
                finish();
            }
        });
    }
}

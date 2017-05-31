package com.example.acer.quanlychitieu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ThemDuLieuActivity extends AppCompatActivity {

    EditText edtSoTien, edtNote;
    TextView txtDate, txtCategory;
    Spinner spinnerWallet;
    Calendar calendar;
    Button btnDone;
    ImageView imgView;
    String spinner;

    String[] itemsWallet = {"Tiền mặt", "Tài khoản ngân hàng"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_du_lieu);

        Init();

        //Lấy ngày hôm nay
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(calendar.getTime());
        txtDate.setText(strDate);

        txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent category = new Intent(ThemDuLieuActivity.this, NhomThuChiActivity.class);
                Bundle bundle = new Bundle();
                category.putExtras(bundle);
                startActivityForResult(category,200);
            }
        });

        //Spnner cho phần Wallet
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, itemsWallet);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerWallet.setAdapter(adapter1);
        spinnerWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner = itemsWallet[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        //Show Calendar
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });
        //Xử lý sự kiện btnDone
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnDone();
            }
        });
    }

    private void Init(){
        //Khai báo Edit Text
        edtSoTien = (EditText) findViewById(R.id.edtSoTien);
        edtNote = (EditText) findViewById(R.id.edtNote);
        //Khai báo Text Chọn Nhóm
        txtCategory = (TextView) findViewById(R.id.Category);
        //Khai báo Spinner Wallet
        spinnerWallet = (Spinner) findViewById(R.id.spinnerWallet);
        //Khai báo Button Xong
        btnDone = (Button) findViewById(R.id.btnDone);
        //Khai báo Date, định dạng khi lần đầu chạy
        txtDate = (TextView) findViewById(R.id.txtDate);
        calendar = Calendar.getInstance();
    };
    private void chonNgay() {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(ThemDuLieuActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year,month,dayOfMonth);
                        txtDate.setText(dateFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private void setBtnDone () {
        if(edtSoTien.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
        }
        else if (txtCategory.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Bạn chưa chọn nhóm", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putString("sotien", edtSoTien.getText().toString());
            bundle.putString("category", txtCategory.getText().toString());
            bundle.putString("date", txtDate.getText().toString());
            bundle.putString("note", edtNote.getText().toString());
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgView.setImageBitmap(bitmap);
                }
                break;
            case 200:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String s = bundle.getString("txtMsg");
                    txtCategory.setText(s);
                }
                break;
        }
    }
}

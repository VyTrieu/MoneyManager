package com.example.acer.quanlychitieu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThemTietKiemActivity extends AppCompatActivity {

    EditText edtTen, edtMucTieu, edtSoTienBanDau;
    TextView txtDate;
    Button btnDone;
    Calendar calendar;
    String strDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tiet_kiem);

        Init();

        //Lấy ngày hôm nay
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
        strDate = dft.format(calendar.getTime());

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonDone();
            }
        });
    }

    private void setButtonDone() {
        if(edtTen.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Chưa nhập tên!", Toast.LENGTH_SHORT).show();
        else if(edtMucTieu.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Chưa nhập mục tiêu!", Toast.LENGTH_SHORT).show();
        else if(txtDate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Chưa chọn ngày kết thúc!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putString("ten", edtTen.getText().toString());
            bundle.putString("muctieu", edtMucTieu.getText().toString());
            bundle.putString("sotien", edtSoTienBanDau.getText().toString());
            bundle.putString("ngaybatdau", strDate);
            bundle.putString("ngayketthuc", txtDate.getText().toString());
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    private void chonNgay() {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(ThemTietKiemActivity.this,
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

    private void Init() {
        edtTen = (EditText) findViewById(R.id.edtTieuChi);
        edtMucTieu = (EditText) findViewById(R.id.edtMucTieu);
        edtSoTienBanDau = (EditText) findViewById(R.id.edtTienBanDau);
        txtDate = (TextView) findViewById(R.id.txtNgayKetThuc);
        btnDone = (Button) findViewById(R.id.btnDone);
        calendar = Calendar.getInstance();
    }
}

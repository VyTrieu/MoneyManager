package com.example.acer.quanlychitieu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NguoiDungActivity extends AppCompatActivity {

    EditText edtEmail, edtMatKhau;
    TextView txtDangKi;
    Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        Init();
    }

    private void Init() {
        edtEmail = (EditText) findViewById(R.id.edtNguoiDung);
        edtMatKhau = (EditText) findViewById(R.id.edtPassword);
        txtDangKi = (TextView) findViewById(R.id.txtDangKi);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
    }
}

package com.example.acer.quanlychitieu;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.quanlychitieu.Database.ThaoTacDatabase;
import com.example.acer.quanlychitieu.Values.ThuChiValues;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChinhSuaActivity extends AppCompatActivity {

    ThaoTacDatabase sql;
    EditText edtSoTien, edtNote;
    TextView txtDate, txtCategory;
    Calendar calendar;
    Button btnSave, btnDel;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua);

        sql = new ThaoTacDatabase(getApplicationContext());

        edtSoTien = (EditText) findViewById(R.id.edtSoTien);
        edtNote = (EditText) findViewById(R.id.edtNote);
        txtCategory = (TextView) findViewById(R.id.Category);
        txtDate = (TextView) findViewById(R.id.txtDate);
        calendar = Calendar.getInstance();
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDelete);

        init();

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });
        txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent category = new Intent(ChinhSuaActivity.this, NhomThuChiActivity.class);
                Bundle bundle = new Bundle();
                category.putExtras(bundle);
                startActivityForResult(category,400);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnSave();
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnDel();
            }
        });
    }

    private void init() {
        if(getIntent().getExtras() != null ) {
            ThuChiValues get = (ThuChiValues) getIntent().getSerializableExtra("data");
            if (get != null) {
                edtSoTien.setText(get.getMoney());
                edtNote.setText(get.getNote());
                txtCategory.setText(get.getcategory() + ": " + get.getThuchi());
                txtDate.setText(get.getDate());
                id = (String.valueOf(get.getId()));
            }
        }
    }

    private void chonNgay() {
        String date = txtDate.getText().toString();
        int year = Integer.parseInt(date.substring(6));
        int month = Integer.parseInt(date.substring(3,5));
        int dayOfMonth = Integer.parseInt(date.substring(0,2));
        final DatePickerDialog datePickerDialog = new DatePickerDialog(ChinhSuaActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year,month,dayOfMonth);
                        txtDate.setText(dateFormat.format(calendar.getTime()));
                    }
                },
                year,
                month - 1,
                dayOfMonth
        );
        datePickerDialog.show();
    }

    private void setBtnSave() {
        if(edtSoTien.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
        }
        else {
            String thuchi = txtCategory.getText().toString().substring(0, 8);
            String loaithuchi = txtCategory.getText().toString().substring(10);
            ThuChiValues edit = new ThuChiValues(thuchi, loaithuchi,
                    edtSoTien.getText().toString(),
                    txtDate.getText().toString(),
                    edtNote.getText().toString());
            edit.setId(Integer.parseInt(id));
            boolean result = sql.thuchi_update(edit);
            if (result == true) {
                Toast.makeText(getApplicationContext(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
                Intent callback = getIntent();
                setResult(RESULT_OK, callback);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Lưu thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBtnDel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Question");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String thuchi = txtCategory.getText().toString().substring(0, 8);
                String loaithuchi = txtCategory.getText().toString().substring(10);
                ThuChiValues del = new ThuChiValues(thuchi, loaithuchi,
                        edtSoTien.getText().toString(),
                        txtDate.getText().toString(),
                        edtNote.getText().toString());
                del.setId(Integer.parseInt(id));
                boolean result = sql.thuchi_delete(del);
                if(result == true) {
                    Toast.makeText(getApplicationContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    Intent callback = getIntent();
                    setResult(RESULT_OK, callback);
                    finish();
                }
                else Toast.makeText(getApplicationContext(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getApplicationContext();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String s = bundle.getString("txtMsg");
                txtCategory.setText(s);
            }
        }
    }
}

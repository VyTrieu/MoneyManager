package com.example.acer.quanlychitieu;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.quanlychitieu.Database.ThaoTacDatabase;
import com.example.acer.quanlychitieu.Values.KeHoachValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.acer.quanlychitieu.R.id.txtCategory;
import static com.example.acer.quanlychitieu.R.id.txtTen;

public class ChinhSuaTietKiemActivity extends AppCompatActivity {

    EditText edtTen, edtMucTieu, edtSoTienBanDau;
    TextView txtDate, txtNgayConLai, txtLoaiTien, txtSoTienConLai;
    Button btnLuu, btnXoa;
    Calendar calendar;
    int conlai, id;
    String [] loaitien = new String[]{"Gửi vào", "Rút ra"};
    ThaoTacDatabase sql;
    KeHoachValues get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_tiet_kiem);

        sql = new ThaoTacDatabase(getApplicationContext());
        Init();
        getData();
        txtLoaiTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonloaitien();
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_picker();
            }
        });
        edtSoTienBanDau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                conlai = Integer.parseInt(get.getSotien());
                if (txtLoaiTien.getText().equals("Gửi vào")) {
                    conlai = conlai - Integer.parseInt(edtSoTienBanDau.getText().toString());
                } else if (txtLoaiTien.getText().equals("Rút ra")) {
                    conlai = conlai + Integer.parseInt(edtSoTienBanDau.getText().toString());
                } else if (txtLoaiTien.getText().toString().equals("Chọn loại tiền")) {
                    Toast.makeText(getApplicationContext(), "Chưa chọn loại tiền!", Toast.LENGTH_SHORT).show();
                }
                    txtSoTienConLai.setText(String.valueOf(conlai));
                }

            @Override
            public void afterTextChanged(Editable s) {
            }
            });
        btnLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setbtn_Luu();
                }
            });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setbtn_Xoa();
            }
        });
        }

    private void setbtn_Xoa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Question");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                KeHoachValues del = new KeHoachValues(id, edtTen.getText().toString(),
                        edtMucTieu.getText().toString(),
                        txtDate.getText().toString(),
                        edtSoTienBanDau.getText().toString());
                boolean result = sql.kehoach_delete(del);
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

    private void chonloaitien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChinhSuaTietKiemActivity.this);
        builder.setTitle("Chọn loại tiền");
        builder.setItems(loaitien, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtLoaiTien.setText(loaitien[which]);
            }
        });
        builder.show();
    }

    private void setbtn_Luu() {
        if (edtTen.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Chưa nhập tên kế hoạch!", Toast.LENGTH_SHORT).show();
        else if (edtMucTieu.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Chưa nhập số tiền cần đạt được!", Toast.LENGTH_SHORT).show();
        else {
            KeHoachValues edit = new KeHoachValues(id, edtTen.getText().toString(),
                    edtMucTieu.getText().toString(), txtDate.getText().toString(), txtSoTienConLai.getText().toString());
            boolean result = sql.kehoach_update(edit);
            if (result == true) {
                Toast.makeText(getApplicationContext(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
                Intent callback = getIntent();
                setResult(RESULT_OK, callback);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Lưu thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void date_picker() {
        String date = txtDate.getText().toString();
        int year = Integer.parseInt(date.substring(6));
        int month = Integer.parseInt(date.substring(3,5));
        int dayOfMonth = Integer.parseInt(date.substring(0,2));
        final DatePickerDialog datePickerDialog = new DatePickerDialog(ChinhSuaTietKiemActivity.this,
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

    private void getData() {
        Intent getIntent = getIntent();
        Bundle callbundle = getIntent.getExtras();
        get = (KeHoachValues) getIntent().getSerializableExtra("data");
        if (get != null) {
            edtTen.setText(get.getTen());
            edtMucTieu.setText(get.getMuctieu());
            txtDate.setText(get.getNgayketthuc());
            txtNgayConLai.setText(get.autoupdate_date());
            txtSoTienConLai.setText(get.getSotien());
            id = callbundle.getInt("id");
        }
    }

    private void Init() {
        edtTen = (EditText) findViewById(R.id.edtTieuChi);
        edtMucTieu = (EditText) findViewById(R.id.edtMucTieu);
        edtSoTienBanDau = (EditText) findViewById(R.id.edtTienBanDau);
        txtDate = (TextView) findViewById(R.id.txtNgayKetThuc);
        txtSoTienConLai = (TextView) findViewById(R.id.txtsotienconlai);
        txtNgayConLai = (TextView) findViewById(R.id.txtNgayConLai);
        txtLoaiTien = (TextView) findViewById(R.id.txtLoaiTien);
        btnLuu = (Button) findViewById(R.id.btnSave);
        btnXoa = (Button) findViewById(R.id.btnDelete);
        calendar = Calendar.getInstance();
    }

}

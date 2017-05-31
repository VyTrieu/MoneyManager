package com.example.acer.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.quanlychitieu.Database.ThaoTacDatabase;
import com.example.acer.quanlychitieu.MyAdapter.ThuChi_Adapter;
import com.example.acer.quanlychitieu.Values.KeHoachValues;
import com.example.acer.quanlychitieu.Values.ThuChiValues;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtThuNhap, txtChiTieu, txtConLai;
    ListView listView;
    Intent intent;
    ThaoTacDatabase database;
    ThuChiValues values = null;
    KeHoachValues kehoach = null;
    ArrayList<ThuChiValues> arrayList = new ArrayList<>();
    ThuChi_Adapter adapter = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new ThaoTacDatabase(getApplicationContext());
        arrayList = database.thuchi_list();

        Init();

        adapter = new ThuChi_Adapter(this, R.layout.chi_tiet, arrayList);
        listView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent themduieu = new Intent(MainActivity.this, ThemDuLieuActivity.class);
                startActivityForResult(themduieu, 123);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                values = adapter.getItem(position);
                values.setId(position + 1);
                Intent detail = new Intent(MainActivity.this, ChinhSuaActivity.class);
                detail.putExtra("data", values);
                startActivityForResult(detail, 321);
            }
        });
    }

    private void Init() {
        txtThuNhap = (TextView) findViewById(R.id.txtThuNhap);
        txtChiTieu = (TextView) findViewById(R.id.txtChiTieu);
        txtConLai = (TextView) findViewById(R.id.txtConLai);
        listView = (ListView) findViewById(R.id.list_tongquan);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            getApplicationContext();
        } else if (id == R.id.nav_gallery) {
            intent = new Intent(MainActivity.this, ThongKeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            intent = new Intent(MainActivity.this, TietKiemActivity.class);
            startActivityForResult(intent,213);
        } else if (id == R.id.nav_manage) {
            Toast.makeText(getApplicationContext(), "Comming soon....", Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 123:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String sotien = bundle.getString("sotien");
                    String kq = bundle.getString("category");
                    String cut = kq.substring(0, 8);
                    String details = kq.substring(10);
                    String date = bundle.getString("date");
                    String note = bundle.getString("note");
                    values = new ThuChiValues(cut, details , sotien, date, note);
                    if (cut.equals("Thu Nhập")) {
                        txtThuNhap.setText(String.valueOf(convert(values.getMoney())
                                + convert(txtThuNhap.getText().toString())));
                    }
                    if (cut.equals("Chi Tiêu")) {
                        txtChiTieu.setText(String.valueOf(convert(values.getMoney())
                                + convert(txtChiTieu.getText().toString())));
                    }
                    int i = convert(txtThuNhap.getText().toString())
                            - convert(txtChiTieu.getText().toString());
                    txtConLai.setText(String.valueOf(i));
                    database.thuchi_add(values);
                    loadding_list();
                    break;
                }
            case 321: {
                if (resultCode == RESULT_OK) {
                    loadding_list();
                    break;
                }
            }
            case 213: {
                if(resultCode == RESULT_OK) {
                    kehoach = (KeHoachValues) data.getSerializableExtra("dulieu");
                    if (kehoach.getTen().toString().isEmpty()) {
                        break;
                    } else {
                        String category = "Khác: gửi vào tiết kiệm " + kehoach.getTen().toString();
                        String money = kehoach.getSotien();
                        String date = kehoach.getNgaybatdau();
                        values = new ThuChiValues("Chi Tiêu", category, money, date, kehoach.getTen().toString());
                        database.thuchi_add(values);
                        loadding_list();
                        break;
                    }
                }
            }
        }
    }

    private void loadding_list() {
        arrayList = database.thuchi_list();
        adapter = new ThuChi_Adapter(this, R.layout.chi_tiet, arrayList);
        listView.setAdapter(adapter);
    }

    private int convert(String number) {
        if(number .equals("")) {
            return 0;
        }
        else {return Integer.parseInt(number);}
    }
    
}

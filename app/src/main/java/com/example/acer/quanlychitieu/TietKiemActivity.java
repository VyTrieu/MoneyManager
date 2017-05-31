package com.example.acer.quanlychitieu;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.acer.quanlychitieu.Database.ThaoTacDatabase;
import com.example.acer.quanlychitieu.MyAdapter.KeHoach_Adapter;
import com.example.acer.quanlychitieu.Values.KeHoachValues;
import com.example.acer.quanlychitieu.Values.ThuChiValues;

import java.util.ArrayList;
import java.util.Date;

public class TietKiemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ThaoTacDatabase database;
    KeHoachValues values = null;
    ArrayList<KeHoachValues> list = new ArrayList<>();
    KeHoach_Adapter adapter = null;
    int s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiet_kiem);

        database = new ThaoTacDatabase(getApplicationContext());
        list = database.plan_list();

        listView = (ListView) findViewById(R.id.listTietKiem);
        adapter = new KeHoach_Adapter(TietKiemActivity.this, R.layout.kehoach_listdetails, list);
        listView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TietKiemActivity.this, ThemTietKiemActivity.class);
                startActivityForResult(intent, 123);
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
                Intent chinhsua = new Intent(TietKiemActivity.this, ChinhSuaTietKiemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", position + 1);
                chinhsua.putExtra("data", values);
                chinhsua.putExtras(bundle);
                startActivityForResult(chinhsua,321);
            }
        });
    }

    private void sendback_MainAct() {
        Intent sendback = getIntent();
        sendback.putExtra("dulieu", values);
        setResult(Activity.RESULT_OK, sendback);
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
        getMenuInflater().inflate(R.menu.tiet_kiem, menu);
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
            Intent inte = getIntent();
            finish();

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(TietKiemActivity.this, ThongKeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            getApplicationContext();

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
                s = convert(bundle.getString("muctieu")) - convert(bundle.getString("sotien"));
                values = new KeHoachValues(bundle.getString("ten"),
                        bundle.getString("muctieu"),
                        bundle.getString("ngaybatdau"),
                        bundle.getString("ngayketthuc"),
                        String.valueOf(s));
                database.kehoach_add(values);
                loading_list();
                sendback_MainAct();
                break;
            }
            case 321:
                if (resultCode == RESULT_OK) {
                    loading_list();
                    sendback_MainAct();
                }
                break;
            }
    }
    private int convert(String number) {
        if(number.equals("")) {
            return 0;
        }
        else {return Integer.parseInt(number);}
    }
    private void loading_list() {
        list = database.plan_list();
        adapter = new KeHoach_Adapter(TietKiemActivity.this, R.layout.kehoach_listdetails, list);
        listView.setAdapter(adapter);
    }
}

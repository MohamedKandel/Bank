package com.mkandeel.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Customers extends AppCompatActivity {

    private Adapter adapter;
    private ListView lv;
    private ArrayList<Users_Module> list;
    private DBConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        connection = new DBConnection(this);
        lv = findViewById(R.id.lv);

        list = connection.GetUsersData();
        adapter = new Adapter(this,list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Customers.this,View_one.class);
                intent.putExtra("ID",list.get(position).getID());
                startActivity(intent);
                finish();
            }
        });
    }
}
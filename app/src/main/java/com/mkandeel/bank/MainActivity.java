package com.mkandeel.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ArrayList<Users_Module> list;
    private DBConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = new DBConnection(this);
        btn = findViewById(R.id.btn);
        list = connection.GetUsersData();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    CreateUsers();
                }
                Intent intent = new Intent(MainActivity.this, Customers.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void CreateUsers() {
        connection.InsertUsers(0, "customer 1", "customer1@gmail.com", 1500);
        connection.InsertUsers(1, "customer 2", "customer2@gmail.com", 2000);
        connection.InsertUsers(2, "customer 3", "customer3@gmail.com", 3700);
        connection.InsertUsers(3, "customer 4", "customer4@gmail.com", 7000);
        connection.InsertUsers(4, "customer 5", "customer5@gmail.com", 2500);
        connection.InsertUsers(5, "customer 6", "customer6@gmail.com", 1000);
        connection.InsertUsers(6, "customer 7", "customer7@gmail.com", 4200);
        connection.InsertUsers(7, "customer 8", "customer8@gmail.com", 3300);
        connection.InsertUsers(8, "customer 9", "customer9@gmail.com", 7800);
        connection.InsertUsers(9, "customer 10", "customer10@gmail.com", 5200);
    }
}
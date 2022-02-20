package com.mkandeel.bank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class View_one extends AppCompatActivity {

    private int ID;
    private int ID2;
    private ArrayList<Users_Module> list_data;
    private ArrayList<Users_Module> list;
    private DBConnection connection;
    private Adapter adapter;
    private Button btn;
    private ListView lv;
    private String mamount;
    private TextView txt_name, txt_mail, txt_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_one);

        lv = findViewById(R.id.lv);
        txt_name = findViewById(R.id.txt_name);
        txt_balance = findViewById(R.id.txt_balance);
        txt_mail = findViewById(R.id.txt_mail);
        btn = findViewById(R.id.button);
        list_data = new ArrayList<>();
        connection = new DBConnection(this);

        ID = getIntent().getIntExtra("ID", -1);
        if (ID > -1) {
            list_data = connection.GetUserDataByID(ID);
            txt_name.setText(list_data.get(0).getName());
            txt_mail.setText(list_data.get(0).getMail());
            txt_balance.setText(String.valueOf(list_data.get(0).getBalance()));

            list = new ArrayList<>();

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateDialog();
                }
            });

        } else {
            Toast.makeText(this, "Invalid Customer ID", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void CreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText txt = new EditText(this);
        txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //for decimal numbers
        builder.setTitle("Transfer");
        builder.setMessage("Enter amount to transfer");
        builder.setView(txt);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mamount = txt.getText().toString();
                dialog.dismiss();

                list = connection.GetUsersData();
                adapter = new Adapter(View_one.this, list);
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ID2 = list.get(position).getID();
                        connection.InsertTransfer(connection.GetMaxID(), ID, ID2, Double.parseDouble(mamount));
                        connection.UpdateUsers(ID, ID2, Double.parseDouble(mamount));

                        Intent intent = new Intent(View_one.this, Customers.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
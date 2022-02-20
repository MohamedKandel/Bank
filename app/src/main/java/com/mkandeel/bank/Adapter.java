package com.mkandeel.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Users_Module> {

    ArrayList<Users_Module> list;
    Context context;

    public Adapter(Context context, ArrayList<Users_Module> list) {
        super(context,R.layout.lv_row,R.id.txt_name,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lv_row, parent, false);
        TextView txt_name = view.findViewById(R.id.txt_name);
        TextView txt_balance = view.findViewById(R.id.txt_balance);

        txt_name.setText(list.get(position).getName());
        txt_balance.setText(String.valueOf(list.get(position).getBalance()));
        return view;
    }
}

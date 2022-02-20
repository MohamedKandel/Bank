package com.mkandeel.bank;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBConnection extends SQLiteOpenHelper {

    static final String DBname = "bank.db";
    static final int Version = 1;

    public DBConnection(Context context) {
        super(context, DBname, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table if not exists users (ID int primary key," +
                "name varchar(30)," +
                "email varchar(30)," +
                "current_balance double);");
        sqLiteDatabase.execSQL("Create table if not exists transfers (ID int primary key," +
                "user_one_ID int," +
                "user_two_ID int," +
                "money double," +
                "Foreign Key (\"user_one_ID\") references \"users\" (\"ID\")," +
                "Foreign Key (\"user_two_ID\") references \"users\" (\"ID\"));");

        /*sqLiteDatabase.execSQL("Create view trans " +
                "as " +
                "select users.name as 'Name'," +
                "users.email as 'E-mail'," +
                "transfers.money as 'Money' " +
                "from users,transfers " +
                "where users.ID = transfers.");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop Table if exists users;");
        sqLiteDatabase.execSQL("Drop Table if exists transfers;");
        onCreate(sqLiteDatabase);
    }

    public void InsertUsers(int ID, String name, String mail, double current_balance) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", ID);
        values.put("name", name);
        values.put("email", mail);
        values.put("current_balance", current_balance);
        database.insert("users", null, values);
    }

    public void InsertTransfer(int ID, int user_one, int user_two, double money) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", ID);
        values.put("user_one_ID", user_one);
        values.put("user_two_ID", user_two);
        values.put("money", money);
        database.insert("transfers", null, values);
    }

    public ArrayList<Users_Module> GetUsersData() {
        ArrayList<Users_Module> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from users", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Users_Module module = new Users_Module(cursor.getInt(cursor.getColumnIndex("ID")),
                    cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getDouble(cursor.getColumnIndex("current_balance")));

            list.add(module);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<Users_Module> GetUserDataByID(int ID) {
        ArrayList<Users_Module> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from users where ID = " + ID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Users_Module module = new Users_Module(ID, cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getDouble(cursor.getColumnIndex("current_balance")));
            list.add(module);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<Trans_Module> GetTransData() {
        ArrayList<Trans_Module> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from transfers", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Trans_Module module = new Trans_Module(cursor.getInt(cursor.getColumnIndex("ID")),
                    cursor.getInt(cursor.getColumnIndex("user_one_ID")),
                    cursor.getInt(cursor.getColumnIndex("user_two_ID")),
                    cursor.getDouble(cursor.getColumnIndex("money")));
            list.add(module);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int GetMaxID() {
        int ID = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select MAX(ID) from transfers", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ID = cursor.getInt(0);
            cursor.moveToNext();
        }
        ID += 1;
        cursor.close();
        return ID;
    }

    private double GetBalance(int user_ID) {
        double balance = 0.0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select current_balance from users where ID = ?",
                new String[]{String.valueOf(user_ID)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            balance = cursor.getDouble(cursor.getColumnIndex("current_balance"));
            cursor.moveToNext();
        }
        cursor.close();
        return balance;
    }

    public void UpdateUsers(int user_one, int user_two, double balance) {
        double balance_one = GetBalance(user_one);
        double balance_two = GetBalance(user_two);
        String sql_update1 = "update users set current_balance = " + (balance_one - balance) + "" +
                " where ID = " + user_one;
        String sql_update2 = "update users set current_balance = " + (balance_two + balance) + "" +
                " where ID = " + user_two;

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(sql_update1);
        database.execSQL(sql_update2);
    }
}
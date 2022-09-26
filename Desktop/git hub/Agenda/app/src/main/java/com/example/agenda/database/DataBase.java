package com.example.agenda.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agenda.database.ScriptSQL;

public class DataBase extends SQLiteOpenHelper {


    public DataBase(Context context){
        super(context,"AGENDA",null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateContato());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

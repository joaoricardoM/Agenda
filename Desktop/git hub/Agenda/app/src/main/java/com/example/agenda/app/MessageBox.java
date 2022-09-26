package com.example.agenda.app;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class MessageBox {

    public static void show(Context context,String title,String msg){
        show(context,title,msg,0);
    }
    public static void show(Context context,String title,String msg,int icon){
        AlertDialog.Builder dlg= new AlertDialog.Builder(context);
        dlg.setIcon(icon);
        dlg.setTitle(title);
        dlg.setMessage(msg);
        dlg.setNeutralButton("ok",null);
        dlg.show();
    }
}

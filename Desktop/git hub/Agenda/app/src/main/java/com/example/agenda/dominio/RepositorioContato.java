package com.example.agenda.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.agenda.CustomListContato;
import com.example.agenda.R;
import com.example.agenda.dominio.entidades.Contato;

public class RepositorioContato {

    private SQLiteDatabase con;
    public RepositorioContato(SQLiteDatabase con){
        this.con=con;
    }

    private ContentValues preencherContent(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("numero", contato.getTelefone());
        values.put("apelido", contato.getApelido());
        return values;
    }

    public  void excluir(Long id){
        con.delete("CONTATO"," _id = ? ", new String[]{String.valueOf(id)});
    }
    public void alterarContato (Contato contato){
        ContentValues values=preencherContent(contato);
        con.update("CONTATO",values ," _id = ? ", new String[]{String.valueOf(contato.getId())});
    }

    public void inserirContato(Contato contato){
        ContentValues values = preencherContent(contato);
        con.insertOrThrow("CONTATO", null, values);
    }

    public CustomListContato buscaContatos(Context context){

        CustomListContato adpContatos=new CustomListContato(context, R.layout.linha_contatos);

        Cursor cursor= con.query("CONTATO",null,null,null,null,null,null);
        if(cursor.getCount()>0){
             cursor.moveToFirst();
            do {
                Contato contato=new Contato();
                contato.setId(cursor.getLong(0));
                contato.setNome(cursor.getString(1));
                contato.setTelefone(cursor.getString(2));
                contato.setApelido(cursor.getString(3));

                adpContatos.add(contato);
            }while (cursor.moveToNext());
        }
        return adpContatos;
    }
}

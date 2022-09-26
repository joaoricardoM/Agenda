package com.example.agenda;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.agenda.app.MessageBox;
import com.example.agenda.database.DataBase;
import com.example.agenda.dominio.RepositorioContato;
import com.example.agenda.dominio.entidades.Contato;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton btnAdc;
    private ImageButton btnTel;
    private EditText editPesquisa;
    private ListView listContact;
    private ArrayAdapter<Contato> adpContatos;

    public DataBase dataBase;
    private SQLiteDatabase con;
    private RepositorioContato repositorioContato;
    public static final String PAR_CONTATO = "CONTATO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdc = (ImageButton) findViewById(R.id.btnAdc);
        editPesquisa = (EditText) findViewById(R.id.editPesquisa);
        listContact = (ListView) findViewById(R.id.listContact);
        btnTel = (ImageButton) findViewById(R.id.telefone_abre);

        btnAdc.setOnClickListener(this);
        listContact.setOnItemClickListener(this);


        try {
            dataBase = new DataBase(this);
            con = dataBase.getWritableDatabase();

            repositorioContato = new RepositorioContato(con);

            adpContatos = repositorioContato.buscaContatos(this);

            listContact.setAdapter(adpContatos);

            FiltraDados filtraDados = new FiltraDados(adpContatos);
            editPesquisa.addTextChangedListener(filtraDados);

        } catch (SQLException e) {
            MessageBox.show(this, "Erro", "Conexão não foi criada: " + e.getMessage());

        }

    }

    @Override
    public void onClick(View v) {

        Intent it= new Intent(this,ActContato.class);

        startActivityForResult(it,0);//atualiza listview após criarou alterar
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        adpContatos=repositorioContato.buscaContatos(this);

        listContact.setAdapter(adpContatos);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contato contato=adpContatos.getItem(position);
        Intent it= new Intent(this,ActContato.class);

        it.putExtra(PAR_CONTATO,contato);
        startActivityForResult(it,0);//atualiza listview após criar ou alterar

    }

    private class FiltraDados implements TextWatcher{

        private ArrayAdapter<Contato> arrayAdapter;
        private FiltraDados(ArrayAdapter<Contato> arrayAdapter){
            this.arrayAdapter=arrayAdapter;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            arrayAdapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}

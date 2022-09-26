package com.example.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.agenda.app.MessageBox;
import com.example.agenda.database.DataBase;
import com.example.agenda.dominio.RepositorioContato;
import com.example.agenda.dominio.entidades.Contato;

import static com.example.agenda.MainActivity.PAR_CONTATO;

public class ActContato extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;

    private EditText editNome;
    private EditText editNum;
    private EditText editApelido;
    private ImageButton entra_dial;
    private ImageView img_contato_act;

    public DataBase dataBase;
    private SQLiteDatabase con;
    private RepositorioContato repositorioContato;
    private Contato contato;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_contato);

        editNome=(EditText) findViewById(R.id.editNome);
        editNum=(EditText) findViewById(R.id.editNum);
        editApelido=(EditText) findViewById(R.id.editApelido);
        entra_dial=(ImageButton)findViewById(R.id.entra_dial);
        img_contato_act=(ImageView) findViewById(R.id.img_contato_act);
        contato = new Contato();

        entra_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
            }
        });

        img_contato_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });
        Bundle bundle= getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(PAR_CONTATO)) {
            contato = (Contato) bundle.getSerializable(PAR_CONTATO);
            preencheDados();
        }else
            contato=new Contato();
        try{
            dataBase=new DataBase(this);
            con=dataBase.getWritableDatabase();

            repositorioContato=new RepositorioContato(con);

        }catch (SQLException e){
            MessageBox.show(this,"Erro","Conexão não foi criada: "+e.getMessage());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.img_contato_act);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            // String picturePath contains the path of selected Image
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.act_contato,menu);

        if(contato.getId()!=0) {
            menu.getItem(2).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.acao1:
                salvar();
                finish();
                break;

            case R.id.acao2:
                cancelar();
                break;
            case R.id.acao3:
                excluir();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void preencheDados(){
        editNome.setText(contato.getNome());
        editNum.setText(contato.getTelefone());
        editApelido.setText(contato.getApelido());

    }

    private void cancelar(){
        Intent it= new Intent(this,MainActivity.class);

        startActivityForResult(it,0);
    }
    private void excluir(){
        try{
            repositorioContato.excluir(contato.getId());
        }

        catch (Exception e){
            MessageBox.show(this,"Erro","Erro ao excluir os dados: "+e.getMessage());

        }
    }

    private void salvar(){
        try {
             contato.setNome(editNome.getText().toString());
            contato.setTelefone(editNum.getText().toString());
            contato.setApelido(editApelido.getText().toString());
            if (editNome.getText().toString().trim().equals("") ||
                    editNum.getText().toString().trim().equals("")){

                Toast.makeText(this, "Valores incorretos",Toast.LENGTH_LONG).show();

            }else {

                if (contato.getId() == 0) {
                    repositorioContato.inserirContato(contato);

                } else
                    repositorioContato.alterarContato(contato);
            }
        }catch (Exception e) {
            MessageBox.show(this, "Erro", "Erro ao salvar dados: " + e.getMessage());
        }
    }
}
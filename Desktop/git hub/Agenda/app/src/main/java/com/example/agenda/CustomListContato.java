package com.example.agenda;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.agenda.dominio.entidades.Contato;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;


public class CustomListContato extends ArrayAdapter<Contato> {
    private Context mContext;

    private int resource = 0;
    private LayoutInflater inflater;

    public CustomListContato(@NonNull Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.mContext = context;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(resource, parent, false);

            viewHolder.txtnome = (TextView) view.findViewById(R.id.txt_nome);
            viewHolder.img_contato = (ImageView) view.findViewById(R.id.img_contato);
            viewHolder.telefone_abre = (ImageButton) view.findViewById(R.id.telefone_abre);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        final Contato contato = getItem(position);

        viewHolder.img_contato.setImageResource( R.drawable.avatar);
        viewHolder.txtnome.setText(contato.getNome());


        viewHolder.telefone_abre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 123);

                    return ;
                }else {
                    intent.setData(Uri.parse("tel:" + contato.getTelefone()));
                    mContext.startActivity(intent);
                }
            }
        });
        return view;

    }

    static class ViewHolder {
        TextView txtnome;
        ImageView img_contato;
        ImageButton telefone_abre;
    }
}


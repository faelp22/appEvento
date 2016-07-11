package com.example.droid02.appevento.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.droid02.appevento.R;
import com.example.droid02.appevento.modelo.Participante;

import java.io.File;
import java.util.List;

/**
 * Created by droid02 on 25/06/16.
 */
public class ParticipanteAdapter extends BaseAdapter {

    private Context context;
    private List<Participante> participantes;

    public ParticipanteAdapter(Context context, List<Participante> participantes) {
        this.context = context;
        this.participantes = participantes;
    }

    @Override
    public int getCount() {
        return participantes.size();
    }

    @Override
    public Object getItem(int position) {
        return participantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return participantes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Participante participante = participantes.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.lista_item, parent, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(participante.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(participante.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = participante.getCaminhoFoto();

        TextView campoEmail = (TextView) view.findViewById(R.id.item_email);
        TextView campoEndereco = (TextView) view.findViewById(R.id.item_endereco);


        if (campoEmail != null) {
            campoEmail.setText(participante.getEmail());
            campoEndereco.setText(participante.getEndereco());
        }

        if (caminhoFoto != null && !caminhoFoto.isEmpty()) {

            File file = new File(caminhoFoto);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                campoFoto.setImageBitmap(bitmapReduzido);
                campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }

        return view;
    }

}

package br.com.a4systemlab.appevento.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import br.com.a4systemlab.appevento.R;
import br.com.a4systemlab.appevento.helpers.ParticipanteViewHolder;

import br.com.a4systemlab.appevento.modelo.Participante;

import java.io.File;
import java.util.List;

/**
 * Created by droid02 on 25/06/16.
 */
public class ParticipanteAdapter extends BaseAdapter {

    private Context context;
    private List<Participante> participantes;
    private LoadImage loadImage;

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
        ParticipanteViewHolder holder;
        String caminhoFoto = participante.getCaminhoFoto();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lista_item, parent, false);
            holder = new ParticipanteViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParticipanteViewHolder) convertView.getTag();
        }

        if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
            File file = new File(caminhoFoto);
            if (file.exists()) {
                loadImage = new LoadImage(convertView, holder, caminhoFoto, context);
                loadImage.execute();
            }
        } else {
            holder.campoFoto.setImageResource(R.drawable.participante);
        }

        holder.campoNome.setText(participante.getNome());
        holder.campoTelefone.setText(participante.getTelefone());

        if (holder.campoEmail != null) {
            holder.campoEmail.setText(participante.getEmail());
            holder.campoEndereco.setText(participante.getEndereco());
        }

        return convertView;
    }

}

package br.com.a4systemlab.appevento.helpers;

import br.com.a4systemlab.appevento.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by isael on 19/04/17.
 */

public class ParticipanteViewHolder {

    public TextView campoNome;
    public TextView campoTelefone;
    public ImageView campoFoto;
    public TextView campoEmail;
    public TextView campoEndereco;

    public ParticipanteViewHolder(View view) {
        campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        campoEmail = (TextView) view.findViewById(R.id.item_email);
        campoEndereco = (TextView) view.findViewById(R.id.item_endereco);
    }
}

package com.example.droid02.appevento.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.droid02.appevento.FormularioActivity;
import com.example.droid02.appevento.R;
import com.example.droid02.appevento.modelo.Participante;

/**
 * Created by droid02 on 11/06/16.
 */
public class FormularioHelper {

    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private ImageView campoFoto;

    private Participante participante;

    public FormularioHelper(FormularioActivity activity) {
        participante = new Participante();
        campoNome = (EditText) activity.findViewById(R.id.edt_nome);
        campoEmail = (EditText) activity.findViewById(R.id.edt_email);
        campoTelefone = (EditText) activity.findViewById(R.id.edt_telefone);
        campoEndereco = (EditText) activity.findViewById(R.id.edt_endereco);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
    }

    public Participante getParticipanteDoFormulario() {

        participante.setNome(campoNome.getText().toString());
        participante.setEmail(campoEmail.getText().toString());
        participante.setTelefone(campoTelefone.getText().toString());
        participante.setEndereco(campoEndereco.getText().toString());
        String caminhoFoto = (String) campoFoto.getTag();
        participante.setCaminhoFoto(caminhoFoto);

        return participante;
    }

    public void carregarParticipanteNoFormulario(Participante participante) {
        this.campoNome.setText(participante.getNome());
        this.campoEmail.setText(participante.getEmail());
        this.campoTelefone.setText(participante.getTelefone());
        this.campoEndereco.setText(participante.getEndereco());
        carregaImagem(participante.getCaminhoFoto());
    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);

            campoFoto.setTag(caminhoFoto);
        }
    }
}

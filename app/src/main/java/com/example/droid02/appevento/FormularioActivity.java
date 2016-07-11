package com.example.droid02.appevento;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.droid02.appevento.dao.ParticipanteDAO;
import com.example.droid02.appevento.helpers.FormularioHelper;
import com.example.droid02.appevento.modelo.Participante;

import java.io.File;

public class FormularioActivity extends AppCompatActivity {
    private static final int CODIGO_CAMERA = 3478;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Button butao = (Button) findViewById(R.id.btn_inserir);
        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);

        helper = new FormularioHelper(this);

        final Participante participanteSelecionado = (Participante) getIntent().getSerializableExtra("participanteSelecionado");
        if (participanteSelecionado != null) {
            helper.carregarParticipanteNoFormulario(participanteSelecionado);
            butao.setText("Alterar");
        }

        butao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participante participanteDoFormulario = helper.getParticipanteDoFormulario();
                ParticipanteDAO dao = new ParticipanteDAO(FormularioActivity.this);

                if (participanteSelecionado != null) {
                    participanteDoFormulario.setId(participanteSelecionado.getId());

                    if (participanteSelecionado.getCaminhoFoto() != null && !participanteSelecionado.getCaminhoFoto().isEmpty()) {
                        File file = new File(participanteSelecionado.getCaminhoFoto());
                        if (file.exists() && participanteDoFormulario.getCaminhoFoto() != participanteSelecionado.getCaminhoFoto()) {
                            file.delete();
                        }
                    }

                    dao.alterar(participanteDoFormulario);
                    Toast.makeText(FormularioActivity.this, "Participante Alterado!", Toast.LENGTH_SHORT).show();
                } else {
                    dao.inserir(participanteDoFormulario);
                    Toast.makeText(FormularioActivity.this, "Participante Salvo!", Toast.LENGTH_SHORT).show();
                }

                dao.close();
                finish();
            }
        });

        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                helper.carregaImagem(caminhoFoto);
            }
        }
    }
}

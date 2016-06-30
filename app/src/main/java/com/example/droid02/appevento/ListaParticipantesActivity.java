package com.example.droid02.appevento;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.droid02.appevento.adapters.ParticipanteAdapter;
import com.example.droid02.appevento.dao.ParticipanteDAO;
import com.example.droid02.appevento.modelo.Participante;

import java.io.File;
import java.util.List;

public class ListaParticipantesActivity extends AppCompatActivity {

    private ListView listaParticipantes;
    private ParticipanteAdapter adapter;
    private Participante participante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_participantes);
        this.listaParticipantes = (ListView) findViewById(R.id.lista_participantes);
        registerForContextMenu(listaParticipantes);

        this.listaParticipantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Participante participanteSelecionado = (Participante) parent.getItemAtPosition(position);
                Intent irParaFormulario = new Intent(ListaParticipantesActivity.this, FormularioActivity.class);
                irParaFormulario.putExtra("participanteSelecionado", participanteSelecionado);
                startActivity(irParaFormulario);
            }
        });

        this.listaParticipantes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                participante = (Participante) parent.getItemAtPosition(position);
                return false;
            }
        });

        Button botaoAdicionar = (Button) findViewById(R.id.bt_flutuante_lista_participantes);
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaParticipantesActivity.this, FormularioActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaParticipantes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_infalte, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem menuLigar = menu.add("Ligar...");
        MenuItem menuMapa = menu.add("Ver no Mapa");
        MenuItem menuSMS = menu.add("Enviar SMS");
        MenuItem deletar = menu.add("Deletar");

        menuLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (ActivityCompat.checkSelfPermission(ListaParticipantesActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaParticipantesActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + participante.getTelefone()));
                    startActivity(intentLigar);
                }

                return false;
            }
        });

        Intent itemMapa = new Intent(Intent.ACTION_VIEW);
        itemMapa.setData(Uri.parse("geo:0,0?q="+Uri.encode(participante.getEndereco())));
        menuMapa.setIntent(itemMapa);

        Intent itemSMS = new Intent(Intent.ACTION_VIEW);
        itemSMS.setData(Uri.parse("sms:"+participante.getTelefone()));
        menuSMS.setIntent(itemSMS);

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ParticipanteDAO dao = new ParticipanteDAO(ListaParticipantesActivity.this);

                if(participante.getCaminhoFoto() != null && !participante.getCaminhoFoto().isEmpty()) {
                    File file = new File(participante.getCaminhoFoto());
                    file.delete();
                }

                dao.deletar(participante);
                dao.close();
                carregarListaParticipantes();
                Snackbar.make(listaParticipantes, participante.getNome()+" Deletar com sucesso!", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.novo:
                Intent intent = new Intent(ListaParticipantesActivity.this, FormularioActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregarListaParticipantes(){
        ParticipanteDAO dao = new ParticipanteDAO(ListaParticipantesActivity.this);
        List<Participante> participantes = dao.getLista();
        adapter = new ParticipanteAdapter(this, participantes);
        this.listaParticipantes.setAdapter(adapter);
    }
}

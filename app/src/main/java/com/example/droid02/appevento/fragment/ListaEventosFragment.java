package com.example.droid02.appevento.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.droid02.appevento.EventosActivity;
import com.example.droid02.appevento.R;
import com.example.droid02.appevento.modelo.ProximosEventos;

import java.util.Arrays;
import java.util.List;

/**
 * Created by droid02 on 09/07/16.
 */
public class ListaEventosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragments_lista_eventos, container, false);

        ListView listViewProvas = (ListView) layout.findViewById(R.id.lista_eventos);

        listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProximosEventos eventoSelecionado = (ProximosEventos) parent.getItemAtPosition(position);
                EventosActivity activity = (EventosActivity) getActivity();
                activity.selecionarEvento(eventoSelecionado);
            }
        });

        ProximosEventos evento01 = new ProximosEventos("JAVOU 8", "10/01/2017", "Faculdade X");
        evento01.setPalestras(Arrays.asList("Palestra01", "Palestra02", "Palestra03"));

        ProximosEventos evento02 = new ProximosEventos("JAVOU 9", "10/02/2017", "Faculdade X");
        evento02.setPalestras(Arrays.asList("Palestra01", "Palestra02", "Palestra03"));

        ProximosEventos evento03 = new ProximosEventos("JAVOU 10", "10/04/2017", "Faculdade X");
        evento03.setPalestras(Arrays.asList("Palestra01", "Palestra02", "Palestra03"));

        List<ProximosEventos> eventosList = Arrays.asList(evento01, evento02, evento03);

        ArrayAdapter<ProximosEventos> adapter = new ArrayAdapter<ProximosEventos>(getActivity(), R.layout.support_simple_spinner_dropdown_item, eventosList);

        listViewProvas.setAdapter(adapter);

        return layout;
    }
}

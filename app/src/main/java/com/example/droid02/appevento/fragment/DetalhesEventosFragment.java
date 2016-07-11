package com.example.droid02.appevento.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.droid02.appevento.R;
import com.example.droid02.appevento.modelo.ProximosEventos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by droid02 on 09/07/16.
 */
public class DetalhesEventosFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragments_eventos_detalhes, container, false);

        if (getArguments() != null) {
            ProximosEventos evento = (ProximosEventos) getArguments().getSerializable("evento");
            Date data = null;
            try {
                data = new SimpleDateFormat("dd/MM/yyyy").parse(evento.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            CalendarView calendario = (CalendarView) layout.findViewById(R.id.calendarView);
            calendario.setDate(data.getTime(), true, true);
        }

        return layout;
    }
}

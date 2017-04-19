package br.com.a4systemlab.appevento;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import br.com.a4systemlab.appevento.fragment.DetalhesEventosFragment;
import br.com.a4systemlab.appevento.fragment.ListaEventosFragment;
import br.com.a4systemlab.appevento.modelo.ProximosEventos;

public class EventosActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventos);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (ehUmTablet()) {
            ft.replace(R.id.eventos_view, new ListaEventosFragment());
            ft.replace(R.id.eventos_detalhe, new DetalhesEventosFragment());
        } else {
            ft.replace(R.id.eventos_view, new ListaEventosFragment());
        }

        ft.commit();
    }

    public void selecionarEvento(ProximosEventos evento) {
        Bundle pendurado = new Bundle();
        pendurado.putSerializable("evento", evento);

        DetalhesEventosFragment detalhes = new DetalhesEventosFragment();
        detalhes.setArguments(pendurado);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (ehUmTablet()) {
            ft.replace(R.id.eventos_detalhe, detalhes);
        } else {
            ft.replace(R.id.eventos_view, detalhes);
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private boolean ehUmTablet() {
        return getResources().getBoolean(R.bool.ehTablet);
    }
}

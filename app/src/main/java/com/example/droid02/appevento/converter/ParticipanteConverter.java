package com.example.droid02.appevento.converter;

import com.example.droid02.appevento.modelo.Participante;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by droid02 on 09/07/16.
 */
public class ParticipanteConverter {

    public String converterParaJSON(List<Participante> participantes) {
        JSONStringer js = new JSONStringer();

        try {
            js.object()
                    .key("lista").array()
                    .object()
                    .key("participantes").array();
            for (Participante p : participantes) {
                js.object();
                js.key("id").value(p.getId());
                js.key("nome").value(p.getNome());
                js.key("email").value(p.getEmail());

                js.endObject();
            }
            js.endArray()
                    .endObject()
                    .endArray()
                    .endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}

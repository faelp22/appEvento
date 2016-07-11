package com.example.droid02.appevento.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.droid02.appevento.modelo.Participante;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by droid02 on 11/06/16.
 */
public class ParticipanteDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "appEventos";
    private static final int VERSAO = 3;
    private final String TABELA = "participantes";

    public ParticipanteDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " ("
                + "id INTEGER PRIMARY KEY, "
                + "nome TEXT UNIQUE NOT NULL, "
                + "email TEXT, "
                + "telefone TEXT, "
                + "endereco TEXT, "
                + "caminhoFoto TEXT"
                + "); ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";

        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE " + TABELA + " ADD COLUMN endereco TEXT;";
                db.execSQL(sql);
            case 2:
                sql = "ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT;";
                db.execSQL(sql);
        }
    }

    public void inserir(Participante participante) {
        ContentValues values = new ContentValues();
        values.put("nome", participante.getNome());
        values.put("email", participante.getEmail());
        values.put("telefone", participante.getTelefone());
        values.put("endereco", participante.getEndereco());
        values.put("caminhoFoto", participante.getCaminhoFoto());
        getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Participante> getLista() {
        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA + ";";
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        Participante participante = null;

        while (c.moveToNext()) {
            participante = new Participante();
            participante.setId(c.getLong(c.getColumnIndex("id")));
            participante.setNome(c.getString(c.getColumnIndex("nome")));
            participante.setEmail(c.getString(c.getColumnIndex("email")));
            participante.setTelefone(c.getString(c.getColumnIndex("telefone")));
            participante.setEndereco(c.getString(c.getColumnIndex("endereco")));
            participante.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            lista.add(participante);
        }

        return lista;
    }

    public void alterar(Participante participante) {
        ContentValues values = new ContentValues();
        values.put("nome", participante.getNome());
        values.put("email", participante.getEmail());
        values.put("telefone", participante.getTelefone());
        values.put("endereco", participante.getEndereco());
        values.put("caminhoFoto", participante.getCaminhoFoto());

        String[] argumentos = {participante.getId().toString()};

        getWritableDatabase().update(TABELA, values, "id=?", argumentos);
    }

    public void deletar(Participante participante) {
        String[] argumentos = {participante.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", argumentos);
    }

    public boolean ehParticipante(String telefone) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABELA
                + " WHERE telefone = ?", new String[]{telefone});
        int resultado = c.getCount();
        c.close();
        return resultado > 0;
    }

}

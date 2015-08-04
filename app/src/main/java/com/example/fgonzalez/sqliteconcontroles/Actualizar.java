package com.example.fgonzalez.sqliteconcontroles;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fgonzalez on 19/06/2015.
 */
public class Actualizar {

    public int actualizarInspeccion (String inspeccionNo, String cliente, long tipoSiembra, SQLiteDatabase bd)
    {
        ContentValues registro = new ContentValues();

        registro.put("InspeccionNo", inspeccionNo);
        registro.put("cliente", cliente);
        registro.put("tipoSiembra", tipoSiembra);

        int cant = bd.update("inspeccion", registro, "inspeccionNo=" + inspeccionNo, null);

        return cant;

    }
}

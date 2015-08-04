package com.example.fgonzalez.sqliteconcontroles;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fgonzalez on 19/06/2015.
 */
public class Baja {


    public   int elimnarInspeccion(String inspeccionNo, SQLiteDatabase bd)
    {
        int cant = bd.delete("inspeccion", "InspeccionNo=" + inspeccionNo, null); // (votantes es la nombre de la tabla, condicion)

        return cant;
    }
}

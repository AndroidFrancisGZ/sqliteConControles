package com.example.fgonzalez.sqliteconcontroles;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fgonzalez on 19/06/2015.
 */
public class Consulta {


   

    public Cursor getInspeccion (String inspeccionNo, SQLiteDatabase bd)
    {
        //Se crea el cursor
        Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                "select inspeccion.cliente, TipoSiembra.TipoSiembraDescripcion  from inspeccion inner join TipoSiembra on (inspeccion.TipoSiembra=TipoSiembra.TipoSiembraId) where inspeccionNo=" + inspeccionNo, null);


        return  fila;
    }

    public Cursor getTipoSiembras (SQLiteDatabase bd)
    {

        //Creamos el cursor
        Cursor cursorTipoSiembra = bd.rawQuery("select TipoSiembraId AS _id, TipoSiembraDescripcion from TipoSiembra", null);



        return cursorTipoSiembra;
    }
}

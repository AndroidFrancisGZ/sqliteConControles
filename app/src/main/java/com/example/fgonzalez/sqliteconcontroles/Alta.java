package com.example.fgonzalez.sqliteconcontroles;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by fgonzalez on 19/06/2015.
 */
public class Alta  {


        public void InsertarInspeccion (String inspeccionNo, String cliente, long tipoSiembra, SQLiteDatabase bd)
        {
            ContentValues registro = new ContentValues();  //es una clase para guardar datos

            registro.put("InspeccionNo", inspeccionNo);
            registro.put("Cliente", cliente);
            registro.put("TipoSiembra", tipoSiembra);

            bd.insert("Inspeccion", null, registro);


        }

}

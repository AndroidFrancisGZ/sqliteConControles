package com.example.fgonzalez.sqliteconcontroles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import junit.runner.Version;

/**
 * Created by fgonzalez on 18/06/2015.
 */
public class InspeccionDeCampoSQLiteOpenHelper extends SQLiteOpenHelper {




    public InspeccionDeCampoSQLiteOpenHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, nombre, factory, 14);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table inspeccion(InspeccionNo integer primary key, Cliente text, TipoSiembra text )");

        db.execSQL("create table TipoSiembra(TipoSiembraId integer primary key, TipoSiembraDescripcion text )");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists inspeccion");
        db.execSQL("drop table if exists TipoSiembra");
        db.execSQL("create table inspeccion(InspeccionNo integer primary key, Cliente text, TipoSiembra int )");
        db.execSQL("create table TipoSiembra(TipoSiembraId integer primary key, TipoSiembraDescripcion text )");
        db.execSQL("insert into TipoSiembra(TipoSiembraId, TipoSiembraDescripcion) values (1,'Agricultura tradicional')");
        db.execSQL("insert into TipoSiembra(TipoSiembraId, TipoSiembraDescripcion) values (2,'Agricultura de conservacion')");
        db.execSQL("insert into TipoSiembra(TipoSiembraId, TipoSiembraDescripcion) values (5,'Agricultura de prueba')");

    }

    public  SQLiteDatabase AbrirCrearConexion ()
    {
         SQLiteDatabase bd = getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.
        return  bd;
    }
}

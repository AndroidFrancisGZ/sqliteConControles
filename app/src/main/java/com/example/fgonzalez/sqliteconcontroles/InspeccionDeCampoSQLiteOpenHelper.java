package com.example.fgonzalez.sqliteconcontroles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fgonzalez on 18/06/2015.
 */
public class InspeccionDeCampoSQLiteOpenHelper extends SQLiteOpenHelper {

    public InspeccionDeCampoSQLiteOpenHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table inspeccion(inspeccionNo integer primary key, cliente text )");

        db.execSQL("create table votantes(dni integer primary key, nombre text, colegio text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists inspeccion");
        db.execSQL("create table inspeccion(InspeccionNo integer primary key, cliente text )");
    }
}

package com.example.fgonzalez.sqliteconcontroles;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText etInspeccionNo, etCliente;
    private String array_spinner[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etInspeccionNo = (EditText) findViewById(R.id.et_InspeccionNo);
        etCliente = (EditText) findViewById(R.id.et_Cliente);


        array_spinner = new String[3];
        array_spinner[0] = "opcion 1";
        array_spinner[1] = "opcion 2";
        array_spinner[2] = "opcion 3";

        Spinner s = (Spinner) findViewById(R.id.Spinner01);
        ArrayAdapter adapter = new ArrayAdapter(this,  android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new MyOnItemSelectedListener());


        Spinner sp =	(Spinner)findViewById(R.id.Spinner01);
        String spinnerString = null;
        spinnerString = sp.getSelectedItem().toString();
        int nPos = sp.getSelectedItemPosition();


        Toast.makeText(getApplicationContext(), "getSelectedItem=" + spinnerString,
                Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "getSelectedItemPosition=" + nPos,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void alta(View v) {
        InspeccionDeCampoSQLiteOpenHelper inspeccion = new InspeccionDeCampoSQLiteOpenHelper(this,"Inspeccion", null, 1);
        SQLiteDatabase bd = inspeccion.getWritableDatabase();

        String inspeccionNo = etInspeccionNo.getText().toString();
        String cliente = etCliente.getText().toString();

        ContentValues registro = new ContentValues();  //es una clase para guardar datos

        registro.put("InspeccionNo", inspeccionNo);
        registro.put("cliente", cliente);

       /* registro.put("manejoSiembra", "x");
        registro.put("riego", "si");*/

        bd.insert("inspeccion", null, registro);
        bd.close();

        etInspeccionNo.setText("");
        etCliente.setText("");


        Toast.makeText(this, "Se cargaron los datos de la persona",
                Toast.LENGTH_SHORT).show();
    }



    public void consulta(View v) {
        InspeccionDeCampoSQLiteOpenHelper inspeccion = new InspeccionDeCampoSQLiteOpenHelper(this,
                "Inspeccion", null, 1);
        SQLiteDatabase bd = inspeccion.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.


        String inspeccionNo = etInspeccionNo.getText().toString();

        Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                "select cliente  from inspeccion where inspeccionNo=" + inspeccionNo, null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            etCliente.setText(fila.getString(0));

        } else
            Toast.makeText(this, "No existe una persona con dicho dni" ,
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}

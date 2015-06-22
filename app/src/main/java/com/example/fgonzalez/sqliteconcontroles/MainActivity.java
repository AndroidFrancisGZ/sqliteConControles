package com.example.fgonzalez.sqliteconcontroles;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private EditText etInspeccionNo, etCliente;
    public long TipoSiembra;
    private Spinner spTipoSiembra,spTipoSiembraCodigo;
    private String array_spinnerTipoSiembra[]; // declaracion del arreglo para llenar el spinner con codigo

    InspeccionDeCampoSQLiteOpenHelper inspeccionSQLiteOpenHelper = new InspeccionDeCampoSQLiteOpenHelper(this, "Inspeccion", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etInspeccionNo = (EditText) findViewById(R.id.et_InspeccionNo);
        etCliente = (EditText) findViewById(R.id.et_Cliente);
        spTipoSiembra = (Spinner) findViewById(R.id.sp_TipoSiembra);


        /*llenar un spinner desde codigo*/
        /*
        spTipoSiembraCodigo = (Spinner) findViewById(R.id.sp_TipoSiembraCodigo);
        spTipoSiembraCodigo.setEnabled(false);
        spTipoSiembraCodigo.setVisibility(View.INVISIBLE);

        String PrimerValor = "Tipo Siembra";
        llenar_SpinnerTipoSiembraCodigo(PrimerValor);*/





        /*llenar spinner por xml*/

        /*
        Spinner sp = (Spinner) findViewById(R.id.sp_TipoSiembra); //Se recupera el control spinner del archivo xml

        ArrayAdapter adapterSpinnerVersiones = ArrayAdapter.createFromResource(//Se crea el adaptador- metodo createFromResource :
                this, R.array.TiposSiembra, android.R.layout.simple_spinner_item); // sirve para crear ArrayAdapters utilizando recursos externos en este caso arrays.xml

        adapterSpinnerVersiones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //  setDropDownViewResource()  sirve para
                                                                                                    //definir la forma en la que se desplegara toda la lista de opciones

        sp.setAdapter(adapterSpinnerVersiones);//Se asigna el adaptador a la variable sp

            */

        /*
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Se agrega el listener al spinner

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) { //Se dispara cuando el usuario selecciona una opcion del spinner

                //Toast.makeText(parentView.getContext(),"Has seleccionado " + parentView.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show(); Muesta mensaje con el valor seleccionado




                 TipoSiembra = parentView.getItemAtPosition(position).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */



        //llenar spinner desde bd


        String PrimerValor = "Tipo Siembra";

        llenarDesdeBD_SpinnerTipoSiembra(PrimerValor);




    }

    private void llenarDesdeBD_SpinnerTipoSiembra(String primerValor) {

        SQLiteDatabase bd = inspeccionSQLiteOpenHelper.AbrirCrearConexion(); //Abre la conexion a la bd


        Spinner spinnerTipoSiembra = (Spinner) findViewById(R.id.sp_TipoSiembra);

        Consulta consulta = new Consulta();

        Cursor cursorTipoSiembra = consulta.getTipoSiembras(bd); //Se crea el cursor

        //Creamos el adaptador
        SimpleCursorAdapter adapterSiembra = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,  cursorTipoSiembra, new String[]{"TipoSiembraDescripcion"}, new int[]{android.R.id.text1});

        //Anadimos el layout para el menu
        adapterSiembra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Le indicamos al spinner el adaptador a usar
        spinnerTipoSiembra.setAdapter(adapterSiembra);

        bd.close();

        spinnerTipoSiembra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // TipoSiembra = parent.getItemAtPosition(position).toString();

                TipoSiembra = id;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    public void alta(View v) {

        SQLiteDatabase bd = inspeccionSQLiteOpenHelper.AbrirCrearConexion(); //Abre la conexion a la bd

        String inspeccionNo = etInspeccionNo.getText().toString();
        String cliente = etCliente.getText().toString();


        Alta alta = new Alta();

        alta.InsertarInspeccion(inspeccionNo,cliente,TipoSiembra, bd);

        ContentValues registro = new ContentValues();  //es una clase para guardar datos

        /*registro.put("InspeccionNo", inspeccionNo);
        registro.put("Cliente", cliente);
        registro.put("TipoSiembra", TipoSiembra);



        bd.insert("Inspeccion", null, registro);*/

        bd.close();

        etInspeccionNo.setText("");
        etCliente.setText("");


        Toast.makeText(this, "Se cargaron los datos de la persona",
                Toast.LENGTH_SHORT).show();

    }



    public void consulta(View v) {


        SQLiteDatabase bd = inspeccionSQLiteOpenHelper.AbrirCrearConexion(); //Abre la conexion a la bd

        String inspeccionNo = etInspeccionNo.getText().toString();//Se obtiene el valor del numero de inspeccion

        Consulta consulta = new Consulta();//Se instancia la clase consulta

       Cursor  fila = consulta.getInspeccion(inspeccionNo,bd); //Se obtiene la inspeccion


        if (fila.moveToFirst()) {  //si ha devuelto 1 fila,

            etCliente.setText(fila.getString(0));

            llenarDesdeBD_SpinnerTipoSiembra(fila.getString(1));

           // Toast.makeText(this,"El tipo de siembra es:  " + fila.getString(1),Toast.LENGTH_LONG).show(); //Imprime un mensaje con el tipo de siembra seleccionado * para verificar

        } else //Si no devuelve una fila
            Toast.makeText(this, "No existe el cliente" ,
                    Toast.LENGTH_SHORT).show();


        bd.close();//Se cierra la conexion



    }




    public void llenar_SpinnerTipoSiembraCodigo (String PrimerValor)
    {

        array_spinnerTipoSiembra = new String[3];
        array_spinnerTipoSiembra[0] = PrimerValor;
        array_spinnerTipoSiembra[1] = "Agricultura de conservacion";
        array_spinnerTipoSiembra[2] = "Agricultura tradicional";

        Spinner sp = (Spinner) findViewById(R.id.sp_TipoSiembra);
        ArrayAdapter adapter = new ArrayAdapter(this,  android.R.layout.simple_spinner_item, array_spinnerTipoSiembra);
        sp.setAdapter(adapter);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Se agrega el listener al spinner

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) { //Se dispara cuando el usuario selecciona una opcion del spinner


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}

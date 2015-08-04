package com.example.fgonzalez.sqliteconcontroles;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;




public class MainActivity extends ActionBarActivity {

    private EditText etInspeccionNo, etCliente;
    public long TipoSiembra;
    private RadioGroup radioGrupoProfundidadDeSiembra;
    private RadioButton radioButtonProfundidadDeSiembra;
    private Button btnAlta;

    InspeccionDeCampoSQLiteOpenHelper inspeccionSQLiteOpenHelper = new InspeccionDeCampoSQLiteOpenHelper(this, "Inspeccion", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etInspeccionNo = (EditText) findViewById(R.id.et_InspeccionNo);
        etCliente = (EditText) findViewById(R.id.et_Cliente);


        radioGrupoProfundidadDeSiembra  = (RadioGroup) findViewById(R.id.radio);


        addListenerOnButton(); //Agregar listener para obtener el valor de checkbox

        String PrimerValor = "Tipo Siembra";

        llenarDesdeBD_SpinnerTipoSiembra(PrimerValor);



    }

    private void addListenerOnButton() {

        radioGrupoProfundidadDeSiembra = (RadioGroup) findViewById(R.id.rdProfundidadDeSiembra);
        btnAlta = (Button) findViewById(R.id.btn_alta);

        btnAlta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGrupoProfundidadDeSiembra.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButtonProfundidadDeSiembra = (RadioButton) findViewById(selectedId);

                Toast.makeText(MainActivity.this,
                        radioButtonProfundidadDeSiembra.getText(), Toast.LENGTH_SHORT).show();

            }

        });

    }


    private void llenarDesdeBD_SpinnerTipoSiembra(String primerValor) {

        SQLiteDatabase bd = inspeccionSQLiteOpenHelper.AbrirCrearConexion(); //Abre la conexion a la bd


        final Spinner spinnerTipoSiembra = (Spinner) findViewById(R.id.sp_TipoSiembra);



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

       Cursor  fila = consulta.getInspeccion(inspeccionNo, bd); //Se obtiene la inspeccion


        if (fila.moveToFirst()) {  //si ha devuelto 1 fila,

            etCliente.setText(fila.getString(0));


            //Spinner spinnerTipoSiembra = (Spinner) findViewById(R.id.sp_TipoSiembra);




            llenarDesdeBD_SpinnerTipoSiembra(fila.getString(1));



            Toast.makeText(this,"El tipo de siembra es:  " + fila.getString(1),Toast.LENGTH_LONG).show(); //Imprime un mensaje con el tipo de siembra seleccionado * para verificar

        } else //Si no devuelve una fila
            Toast.makeText(this, "No existe el cliente" ,
                    Toast.LENGTH_SHORT).show();


        bd.close();//Se cierra la conexion



    }



    public void baja(View v) {
        SQLiteDatabase bd = inspeccionSQLiteOpenHelper.AbrirCrearConexion(); //Abre la conexion a la bd



        String inspeccionNo = etInspeccionNo.getText().toString();//Se obtiene el valor del numero de inspeccion

        Baja baja = new Baja();

        int cant = baja.elimnarInspeccion(inspeccionNo, bd);


        bd.close();

        etInspeccionNo.setText("");
        etCliente.setText("");


        if (cant == 1)
            Toast.makeText(this, "Se borro la persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe una persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }




    public void modificacion(View v) {

        SQLiteDatabase bd = inspeccionSQLiteOpenHelper.AbrirCrearConexion(); //Abre la conexion a la bd

        String inspeccionNo = etInspeccionNo.getText().toString();
        String cliente = etCliente.getText().toString();
        Long tipoSiembra = TipoSiembra;

        Actualizar actualizar = new Actualizar();

        int cant = actualizar.actualizarInspeccion(inspeccionNo, cliente, tipoSiembra, bd);


        bd.close();

        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe una persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }



    /*public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdProfundidadSi:
                if (checked)
                    Toast.makeText(this, "Si",
                            Toast.LENGTH_SHORT).show();
                    break;
            case R.id.rdProfundidadNO:
                if (checked)
                    Toast.makeText(this, "No" +
                                    "",
                            Toast.LENGTH_SHORT).show();
                    break;
        }


    }*/








}

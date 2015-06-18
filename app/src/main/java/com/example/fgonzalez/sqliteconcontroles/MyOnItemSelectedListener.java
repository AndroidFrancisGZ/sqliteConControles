package com.example.fgonzalez.sqliteconcontroles;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by fgonzalez on 18/06/2015.
 */
public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(parent.getContext(), "El Item es " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

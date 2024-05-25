package com.example.lab5_20206442.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_20206442.Adapter.TareaAdapter;
import com.example.lab5_20206442.Entity.TareaData;
import com.example.lab5_20206442.MainActivity;
import com.example.lab5_20206442.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SegundaVistaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TareaAdapter adapter;
    private ConstraintLayout guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segunda_vista);

        // OBTENCIÓN DEL INTENT:
        Intent intent = getIntent();
        String codigo = intent.getStringExtra("codigo");
        Log.d("msg-SEGUNDA", codigo);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<TareaData> tareaData = listarArchivos();
        /*
        tareaData.add(new TareaData("Creación de equipo", "Joselin", "29/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Edición de equipo", "Joselin", "29/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Fin en sitio x", "Massiel", "28/04/2024", "29/04/2024",codigo));
        tareaData.add(new TareaData("Borrado de equipo", "Joselin", "28/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Creación de equipo", "Massiel", "27/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Edición de equipo", "Joselin", "27/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Edición de equipo", "Massiel", "27/04/2024","29/04/2024", codigo)); */

        adapter = new TareaAdapter(tareaData);
        recyclerView.setAdapter(adapter);

        guardar =  findViewById(R.id.Guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SegundaVistaActivity.this, GuardarActivity.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);

            }
        });


    }

    private List<TareaData>   listarArchivos() {
        String fileName = "Tareas.json";
        List<TareaData> listaTareas = null;

        try (FileInputStream fileInputStream = this.openFileInput(fileName)) {
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            String jsonString = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type type = new TypeToken<List<TareaData>>() {}.getType();
            listaTareas = gson.fromJson(jsonString, type);


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mg-RECEPCION_DESPUESDE_GUARDAR", "error");
        }
        return listaTareas;

    }

}

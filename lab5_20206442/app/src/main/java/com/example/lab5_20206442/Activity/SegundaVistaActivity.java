package com.example.lab5_20206442.Activity;

import android.app.NotificationManager;
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
import com.example.lab5_20206442.Notificaciones;
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


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<TareaData> tareaData = listarArchivos(codigo);

        Notificaciones.createNotificationChannel(NotificationManager.IMPORTANCE_DEFAULT,SegundaVistaActivity.this);
        Notificaciones.lanzarNotificacion("Recordatorio","Alumno " + codigo + "tienes " + tareaData.size() + " tareas" , NotificationManager.IMPORTANCE_DEFAULT,SegundaVistaActivity.this);

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

    private List<TareaData>   listarArchivos(String codigo) {
        String fileName = "Tareas.json";
        List<TareaData> listaTareasFiltradas = new ArrayList<>();
        List<TareaData> listaTareas = null;

        try (FileInputStream fileInputStream = this.openFileInput(fileName)) {
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            String jsonString = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type type = new TypeToken<List<TareaData>>() {}.getType();

            listaTareas = gson.fromJson(jsonString, type);

            for (TareaData tarea : listaTareas) {
                if (tarea.getCodigo().equals(codigo)) {
                    listaTareasFiltradas.add(tarea);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mg-RECEPCION_DESPUESDE_GUARDAR", "error");
        }
        return listaTareasFiltradas;

    }

}

package com.example.lab5_20206442.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab5_20206442.Adapter.TareaAdapter;
import com.example.lab5_20206442.Entity.TareaData;
import com.example.lab5_20206442.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GuardarActivity extends AppCompatActivity {

    private ConstraintLayout atras;
    private ConstraintLayout Guardar;
    private EditText titulo;
    private EditText fecha;
    private EditText hora;
    private EditText descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardar);

        // OBTENCIÓN DEL INTENT:
        Intent intent = getIntent();
        String codigo = intent.getStringExtra("codigo");
        Log.d("msg-GUARDAR", codigo);

        atras =  findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuardarActivity.this,SegundaVistaActivity.class));
            }
        });

        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmacionPopup(codigo);
            }
        });

    }

    private void ConfirmacionPopup(String codigo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de crear la tarea?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarTarea(codigo);
                dialog.dismiss();

                //NotificationHelper.createNotificationChannel(NuevoReporteActivity.this);
                //NotificationHelper.sendNotification(NuevoReporteActivity.this, "Reporte", "Nuevo reporte creado");
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    /*

    private void guardarTarea(String codigo) {
        titulo = findViewById(R.id.titulo);
        String tituloString = titulo.getText().toString().trim();

        descripcion = findViewById(R.id.descripcion);
        String descripcionString = descripcion.getText().toString().trim();

        fecha = findViewById(R.id.fecha);
        String fechaString = fecha.getText().toString().trim();

        hora = findViewById(R.id.hora);
        String horaString = hora.getText().toString().trim();

        TareaData tarea = new TareaData(tituloString, descripcionString, fechaString, horaString, codigo);
        List<TareaData> tareas = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("tasks_" + codigo + ".json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            isr.close();
            fis.close();

            Gson gson = new Gson();
            Type taskListType = new TypeToken<ArrayList<TareaData>>() {
            }.getType();
            tareas = gson.fromJson(sb.toString(), taskListType);
        } catch (IOException e) {
            tareas = new ArrayList<>();
        }

        tareas.add(tarea);
    } */

    private void guardarTarea(String codigo) {

        titulo = findViewById(R.id.titulo);
        String tituloString = titulo.getText().toString().trim();

        descripcion = findViewById(R.id.descripcion);
        String descripcionString = descripcion.getText().toString().trim();

        fecha = findViewById(R.id.fecha);
        String fechaString = fecha.getText().toString().trim();

        hora = findViewById(R.id.hora);
        String horaString = hora.getText().toString().trim();

        TareaData tarea = new TareaData(tituloString,descripcionString,fechaString,horaString,codigo);

        // Obtener la lista actual de tareas
        List<TareaData> listaTareas = obtenerListaTareas();
        if (listaTareas == null) {
            listaTareas = new ArrayList<>();
        }
        listaTareas.add(tarea);

        Gson gson = new Gson();
        String jsonString = gson.toJson(listaTareas);

        String fileName = "Tareas.json";

        try (FileOutputStream fileOutputStream = this.openFileOutput(fileName, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());) {

            fileWriter.write(jsonString);

            // Correcto
            Toast.makeText(GuardarActivity.this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
            Log.d("mg-ARCHIVOrecibido", "exito");

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mg-ARCHIVOrecibido", "error");
        }

        Intent intent = new Intent(GuardarActivity.this, SegundaVistaActivity.class);
        intent.putExtra("codigo", codigo);
        startActivity(intent);


    }

    private List<TareaData> obtenerListaTareas() {
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

            for (TareaData tarea : listaTareas) {
                Log.d("mg-RECEPCION_ANTESDE_GUARDAR", tarea.getTitulo());
            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("mg-RECEPCION_ANTESDE_GUARDAR", "error");
        }

        return listaTareas;
    }


}

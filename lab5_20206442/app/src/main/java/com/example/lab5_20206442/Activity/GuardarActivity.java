package com.example.lab5_20206442.Activity;

import android.app.NotificationManager;
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
import com.example.lab5_20206442.MainActivity;
import com.example.lab5_20206442.Notificaciones;
import com.example.lab5_20206442.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuardarActivity extends AppCompatActivity {

    private ConstraintLayout Guardar;
    private EditText titulo;
    private EditText fecha;
    private EditText hora;
    private EditText descripcion;
    private EditText recordatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardar);

        // OBTENCIÓN DEL INTENT:
        Intent intent = getIntent();
        String codigo = intent.getStringExtra("codigo");
        Log.d("msg-GUARDAR", codigo);


        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = findViewById(R.id.titulo);
                String tituloString = titulo.getText().toString().trim();

                descripcion = findViewById(R.id.descripcion);
                String descripcionString = descripcion.getText().toString().trim();

                fecha = findViewById(R.id.fecha);
                String fechaString = fecha.getText().toString().trim();

                hora = findViewById(R.id.hora);
                String horaString = hora.getText().toString().trim();

                recordatorio = findViewById(R.id.recordatoria);
                String recordatorioString = recordatorio.getText().toString().trim();

                if (tituloString.isEmpty() || descripcionString.isEmpty() || fechaString.isEmpty() || horaString.isEmpty() || recordatorioString.isEmpty()) {
                    Toast.makeText(GuardarActivity.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                } else {
                    int horaInt = Integer.parseInt(horaString);
                    if (horaInt >= 0 && horaInt <= 24) {
                        ConfirmacionPopup(codigo);
                    } else {
                        Toast.makeText(GuardarActivity.this, "Hora inválida. Por favor, ingrese una hora en el formato HH:mm", Toast.LENGTH_SHORT).show();
                    }
                }

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

    private void guardarTarea(String codigo) {

        titulo = findViewById(R.id.titulo);
        String tituloString = titulo.getText().toString().trim();

        descripcion = findViewById(R.id.descripcion);
        String descripcionString = descripcion.getText().toString().trim();

        fecha = findViewById(R.id.fecha);
        String fechaString = fecha.getText().toString().trim();

        hora = findViewById(R.id.hora);
        String horaString = hora.getText().toString().trim();

        recordatorio = findViewById(R.id.recordatoria);
        String recordatorioString = recordatorio.getText().toString().trim();

        TareaData tarea = new TareaData(tituloString,descripcionString,fechaString,horaString,codigo, recordatorioString);

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

            Toast.makeText(GuardarActivity.this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
            Notificaciones.createNotificationChannel(NotificationManager.IMPORTANCE_HIGH, GuardarActivity.this);
            Notificaciones.lanzarNotificacion("Tarea creada","Se ha agregado una nueva tarea llamada " + tarea.getTitulo(), NotificationManager.IMPORTANCE_HIGH,GuardarActivity.this);


        } catch (IOException e) {
            e.printStackTrace();

        }

        Intent intent = new Intent(GuardarActivity.this, SegundaVistaActivity.class);
        //validarVencimiento(fechaString , horaString, tituloString);
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



        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaTareas;
    }

    //PARA LAS VALIDACIONES DE USO AYUDA DE CHATGPT



    public void validarVencimiento(String fechaString, String horaString, String titulo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String hora = horaString +":00";

        LocalDateTime fecha = LocalDateTime.parse(fechaString + " " + hora, formatter);
        LocalDateTime fechaActual = LocalDateTime.now();

        Duration diferencia = Duration.between(fechaActual, fecha);

        long diferenciaEnHoras = diferencia.toHours();

        if (diferenciaEnHoras < 3) {
            Notificaciones.createNotificationChannel(Notificaciones.prioridad(String.valueOf(diferenciaEnHoras)),GuardarActivity.this);
            Notificaciones.lanzarNotificacion(titulo,"Te quedan menos de 3 horas",Notificaciones.prioridad(String.valueOf(diferenciaEnHoras)),GuardarActivity.this);
        }

    }

}

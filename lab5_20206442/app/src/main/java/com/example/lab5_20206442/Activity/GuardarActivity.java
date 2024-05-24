package com.example.lab5_20206442.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab5_20206442.Adapter.TareaAdapter;
import com.example.lab5_20206442.Entity.TareaData;
import com.example.lab5_20206442.R;

import java.io.FileOutputStream;
import java.io.IOException;
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

        String fileName = "Tarea";

        try(FileOutputStream fileOutputStream = this.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream) ){

            objectOutputStream.writeObject(tarea);

            // Correcto
            Toast.makeText(GuardarActivity.this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();

        }catch (IOException e){
            e.printStackTrace();
        }


        Intent intent = new Intent(GuardarActivity.this, SegundaVistaActivity.class);
        intent.putExtra("codigo", codigo);
        startActivity(intent);


    }


}

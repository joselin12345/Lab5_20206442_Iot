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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
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

        List<TareaData> tareaData = new ArrayList<>();
        tareaData.add(new TareaData("Creación de equipo", "Joselin", "29/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Edición de equipo", "Joselin", "29/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Fin en sitio x", "Massiel", "28/04/2024", "29/04/2024",codigo));
        tareaData.add(new TareaData("Borrado de equipo", "Joselin", "28/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Creación de equipo", "Massiel", "27/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Edición de equipo", "Joselin", "27/04/2024","29/04/2024", codigo));
        tareaData.add(new TareaData("Edición de equipo", "Massiel", "27/04/2024","29/04/2024", codigo));

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

        listarArchivos();

    }

    private void  listarArchivos(){
        String fileName = "Tarea";
        try(FileInputStream fileInputStream = openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){

            TareaData[] listaTareas = ( TareaData[]) objectInputStream.readObject();

            for (TareaData tarea : listaTareas){
                Log.d("msg-LECTURA", tarea.getCodigo());
            }

        }catch (FileNotFoundException | ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

}

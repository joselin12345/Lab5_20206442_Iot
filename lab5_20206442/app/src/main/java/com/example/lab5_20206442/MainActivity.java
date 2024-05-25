package com.example.lab5_20206442;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab5_20206442.Activity.SegundaVistaActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCodigo;
    private ConstraintLayout atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //notificaciones
        Notificaciones.createNotificationChannel(NotificationManager.IMPORTANCE_DEFAULT,MainActivity.this);
        Notificaciones.lanzarNotificacion("Bienvenido","Ingresa tu código", NotificationManager.IMPORTANCE_DEFAULT,MainActivity.this);
        askPermission();

        editTextCodigo = findViewById(R.id.editTextText);

        atras =  findViewById(R.id.ingresar);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editTextCodigo.getText().toString();

                if (codigo.length() == 8 && codigo.matches("\\d+")) {
                    Intent intent = new Intent(MainActivity.this, SegundaVistaActivity.class);
                    intent.putExtra("codigo", codigo);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Ingrese un código de 8 dígitos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void askPermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }


}
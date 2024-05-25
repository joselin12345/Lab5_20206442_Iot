package com.example.lab5_20206442;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notificaciones {
    private static NotificationManager notificationManager;

    public static void createNotificationChannel(int importancia, Context context) {
        String channelId = "channelDefaultPri";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Canal notificaciones ",
                    importancia);
            channel.setDescription("Canal para notificaciones con prioridad default");
            getNotificationManager(context).createNotificationChannel(channel);
        }
    }
    private static NotificationManager getNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
    public static void lanzarNotificacion(String titulo, String contenido, int importancia, Context context) {
        String channelId = "channelDefaultPri";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.tarea)
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setPriority(importancia)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }

    public static int prioridad(String hora){
        int horaInt = Integer.parseInt(hora);
        int importancia;

        if (horaInt < 3){
            importancia= NotificationManager.IMPORTANCE_HIGH;
        } else if (horaInt > 3 && horaInt < 5) {
            importancia= NotificationManager.IMPORTANCE_DEFAULT;
        } else {
            importancia= NotificationManager.IMPORTANCE_LOW;
        }

        return importancia;

    }



}

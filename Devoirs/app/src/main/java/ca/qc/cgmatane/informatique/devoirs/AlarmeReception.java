package ca.qc.cgmatane.informatique.devoirs;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmeReception extends BroadcastReceiver {

    protected Context context;
    protected Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;

        Bundle parametres = intent.getExtras();
        String matiere = parametres.get("matiere").toString();
        String tache = parametres.get("tache").toString();

        NotificationCompat.Builder constructeurNotification = new NotificationCompat.Builder(context);
        constructeurNotification.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(matiere)
                .setContentText(tache)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        NotificationManager gestionnaireNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        gestionnaireNotification.notify(1, constructeurNotification.build());

        Log.d("HELLO", "Alarm...");
    }
}

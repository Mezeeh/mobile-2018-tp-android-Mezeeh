package ca.qc.cgmatane.informatique.devoirs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Alarme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_alarme);

        Log.d("HELLO", "ICI CEST ALARME");
    }

    public void ajouterAlarme(long tempsAlarmeMsec){
        Intent intent = new Intent(this, AlarmeReception.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);
        Toast.makeText(this, "Alarm set in " + 3 + " seconds",Toast.LENGTH_LONG).show();
        Log.d("HELLO", "" + System.currentTimeMillis());
    }
}

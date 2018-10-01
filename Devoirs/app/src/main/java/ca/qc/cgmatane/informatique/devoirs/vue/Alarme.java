package ca.qc.cgmatane.informatique.devoirs.vue;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import ca.qc.cgmatane.informatique.devoirs.modele.AlarmeReception;
import ca.qc.cgmatane.informatique.devoirs.R;

public class Alarme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_alarme);

        Bundle parametres = this.getIntent().getExtras();
        String matiere = parametres.getString("matiere");
        String tache = parametres.getString("tache");
        long tempsAlarme = parametres.getLong("tempsAlarme");
        tempsAlarme = System.currentTimeMillis() + 3000;

        Intent intententionLancerAlarme = new Intent(this, AlarmeReception.class);
        intententionLancerAlarme.putExtra("matiere", matiere);
        intententionLancerAlarme.putExtra("tache", tache);

        PendingIntent attenteIntentionLancerAlarme = PendingIntent.getBroadcast(this, 0, intententionLancerAlarme, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, tempsAlarme, attenteIntentionLancerAlarme);

        Toast.makeText(this, "Alarme dans " + ((tempsAlarme - System.currentTimeMillis()) / 1000) + " secondes",Toast.LENGTH_LONG).show();
        this.finish();
    }
}

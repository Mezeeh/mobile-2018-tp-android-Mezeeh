package ca.qc.cgmatane.informatique.devoirs.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ca.qc.cgmatane.informatique.devoirs.Devoirs;
import ca.qc.cgmatane.informatique.devoirs.R;
import ca.qc.cgmatane.informatique.devoirs.accesseur.DevoirsDAO;
import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;
import org.w3c.dom.Text;

import java.util.Calendar;

public class AjouterDevoir extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    protected DevoirsDAO accesseurDevoirs;
    protected Devoir devoir;

    protected EditText champMatiere,
                        champTache;

    protected TextView champAlarme;

    protected Button actionAjouterDevoir,
                        actionAjouterAlarme;

    protected Calendar calendrier;

    protected DatePickerDialog dialogueChoixDate;

    protected TimePickerDialog dialogueChoixHeure;

    protected int annee,
                    mois,
                    jour,
                    heure,
                    minute;

    protected int anneeAlarme,
                    moisAlarme,
                    jourAlarme,
                    heureAlarme,
                    minuteAlarme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_devoir);

        this.accesseurDevoirs = DevoirsDAO.getInstance();

        champMatiere = findViewById(R.id.vue_ajouter_devoir_matiere);
        champTache = findViewById(R.id.vue_ajouter_devoir_tache);

        champAlarme = findViewById(R.id.vue_temps_alarme);

        actionAjouterDevoir = findViewById(R.id.appliquer_action_confirmer_ajouter);
        actionAjouterDevoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterDevoir();
            }
        });

        actionAjouterAlarme = findViewById(R.id.action_ajouter_alarme);
        actionAjouterAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hello", "HEY");
                afficherDialogueChoixDate();
            }
        });
    }

    private void afficherDialogueChoixDate() {
        calendrier = Calendar.getInstance();

        annee = calendrier.get(Calendar.YEAR);
        mois = calendrier.get(Calendar.MONTH);
        jour = calendrier.get(Calendar.DAY_OF_MONTH);

        dialogueChoixDate = new DatePickerDialog(AjouterDevoir.this, AjouterDevoir.this, annee, mois, jour);
        dialogueChoixDate.show();
    }

    private void ajouterDevoir(){
        devoir = new Devoir(champMatiere.getText().toString(), champTache.getText().toString(), 0);

        accesseurDevoirs.ajouterDevoir(devoir);
        naviguerRetourALaBibliotheque();
    }

    public void naviguerRetourALaBibliotheque(){
        this.finish();
    }

    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jourDuMois) {
        this.anneeAlarme = annee;
        this.moisAlarme = mois;
        this.jourAlarme = jourDuMois;

        Log.d("HELLO", "" + annee + " " + mois + " " + jour);

        afficherDialogueChoixHeure();
    }

    private void afficherDialogueChoixHeure() {
        heure = calendrier.get(Calendar.HOUR_OF_DAY);
        minute = calendrier.get(Calendar.MINUTE);

        dialogueChoixHeure = new TimePickerDialog(AjouterDevoir.this, AjouterDevoir.this, heure, minute, DateFormat.is24HourFormat(this));
        dialogueChoixHeure.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int heureDuJour, int minute) {
        this.heureAlarme = heureDuJour;
        this.minuteAlarme = minute;

        Log.d("HELLO", "" + heureAlarme + " " + minuteAlarme);

        remplissageChampAlarme();
    }

    private void remplissageChampAlarme() {
        champAlarme.setText("Alarme : " + heureAlarme + ":" + minuteAlarme + " " + jourAlarme + "/" + moisAlarme + "/" + anneeAlarme);
    }
}

package ca.qc.cgmatane.informatique.devoirs.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ca.qc.cgmatane.informatique.devoirs.R;
import ca.qc.cgmatane.informatique.devoirs.accesseur.DevoirsDAO;
import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    protected int anneeAlarme,
                    moisAlarme,
                    jourAlarme,
                    heureAlarme,
                    minuteAlarme;

    protected boolean aAlarme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_devoir);

        this.accesseurDevoirs = DevoirsDAO.getInstance();

        aAlarme = false;

        champMatiere = findViewById(R.id.vue_ajouter_devoir_matiere);
        champTache = findViewById(R.id.vue_ajouter_devoir_tache);

        champAlarme = findViewById(R.id.vue_ajouter_devoir_temps_alarme);

        actionAjouterDevoir = findViewById(R.id.appliquer_action_confirmer_ajouter);
        actionAjouterDevoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterDevoir();
            }
        });

        actionAjouterAlarme = findViewById(R.id.action_ajouter_devoir_ajouter_alarme);
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

        int annee = calendrier.get(Calendar.YEAR);
        int mois = calendrier.get(Calendar.MONTH);
        int jour = calendrier.get(Calendar.DAY_OF_MONTH);

        dialogueChoixDate = new DatePickerDialog(AjouterDevoir.this, AjouterDevoir.this, annee, mois, jour);
        dialogueChoixDate.show();
    }

    private void ajouterDevoir(){
        devoir = new Devoir(champMatiere.getText().toString(), champTache.getText().toString(), 0);

        devoir.setaAlarme(aAlarme);
        if(aAlarme){
            try {
                long tempsAlarme = new SimpleDateFormat("hh:mm dd/MM/yyyy").parse(this.heureAlarme + ":" + this.minuteAlarme + " " + this.jourAlarme + "/" + this.moisAlarme + "/" + this.anneeAlarme).getTime();
                devoir.setTempsAlarme(tempsAlarme);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

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

        Log.d("HELLO", "" + annee + " " + mois + " " + jourDuMois);

        afficherDialogueChoixHeure();
    }

    private void afficherDialogueChoixHeure() {
        int heure = calendrier.get(Calendar.HOUR_OF_DAY);
        int minute = calendrier.get(Calendar.MINUTE);

        dialogueChoixHeure = new TimePickerDialog(AjouterDevoir.this, AjouterDevoir.this, heure, minute, DateFormat.is24HourFormat(this));
        dialogueChoixHeure.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int heureDuJour, int minute) {
        this.heureAlarme = heureDuJour;
        this.minuteAlarme = minute;

        Log.d("HELLO", "" + heureDuJour + " " + minute);

        remplissageChampAlarme();
    }

    private void remplissageChampAlarme() {
        aAlarme = true;
        actionAjouterAlarme.setText("Modifier l'alarme");
        champAlarme.setText("Alarme : " + heureAlarme + ":" + minuteAlarme + " " + jourAlarme + "/" + moisAlarme + "/" + anneeAlarme);
    }
}

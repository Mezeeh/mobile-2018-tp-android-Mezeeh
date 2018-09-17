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
import java.util.Date;

public class ModifierDevoir extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    protected DevoirsDAO accesseurDevoirs;
    protected Devoir devoir;

    protected EditText champMatiere,
                        champTache,
                        champAlarme;

    protected Button actionModifierDevoir,
                        actionSupprimerAlarme;

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
        setContentView(R.layout.vue_modifier_devoir);

        this.accesseurDevoirs = DevoirsDAO.getInstance();

        Bundle parametres = this.getIntent().getExtras();
        String parametre_id_devoir = parametres.get("id_devoir").toString();
        int id_livre = Integer.parseInt(parametre_id_devoir);

        devoir = accesseurDevoirs.trouverDevoir(id_livre);

        this.aAlarme = devoir.isaAlarme();

        champMatiere = findViewById(R.id.vue_modifier_devoir_champ_matiere);
        champTache = findViewById(R.id.vue_modifier_devoir_champ_tache);

        champMatiere.setText(devoir.getMatiere());
        champTache.setText(devoir.getTache());

        champAlarme = findViewById(R.id.vue_modifier_devoir_temps_alarme);
        champAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherDialogueChoixDate();
            }
        });

        aAlarme = devoir.isaAlarme();
        if(aAlarme) {
            calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(devoir.getTempsAlarme());
            this.heureAlarme = calendrier.get(Calendar.HOUR_OF_DAY);
            this.minuteAlarme = calendrier.get(Calendar.MINUTE);
            this.jourAlarme = calendrier.get(Calendar.DAY_OF_MONTH);
            this.moisAlarme = calendrier.get(Calendar.MONTH) + 1;
            this.anneeAlarme = calendrier.get(Calendar.YEAR);

            champAlarme.setText(String.format("%02d", this.heureAlarme) + ":" + String.format("%02d", this.minuteAlarme) + " " + String.format("%02d", this.jourAlarme) + "/" + String.format("%02d", this.moisAlarme) + "/" + this.anneeAlarme);
        }

        actionModifierDevoir = findViewById(R.id.action_modifier_devoir);
        actionModifierDevoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierDevoir();
            }
        });

        actionSupprimerAlarme = findViewById(R.id.action_modifier_devoir_supprimer_alarme);
        actionSupprimerAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerAlarme();
            }
        });
        if(aAlarme)
            actionSupprimerAlarme.setVisibility(View.VISIBLE);
    }

    private void supprimerAlarme() {
        aAlarme = false;
        devoir.setaAlarme(aAlarme);
        devoir.setTempsAlarme(0);
        champAlarme.setText("");
        actionSupprimerAlarme.setVisibility(View.GONE);
    }

    private void modifierDevoir(){
        devoir = new Devoir(champMatiere.getText().toString(), champTache.getText().toString(), this.devoir.getId_devoir());
        devoir.setaAlarme(aAlarme);
        if(aAlarme){
            long tempsAlarmeMsec = 0;
            try {
                tempsAlarmeMsec = new SimpleDateFormat("hh:mm dd/MM/yyyy").parse(this.heureAlarme + ":" + this.minuteAlarme + " " + this.jourAlarme + "/" + this.moisAlarme + "/" + this.anneeAlarme).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("HELLO", "alarmeEnMsec " + tempsAlarmeMsec);
            devoir.setTempsAlarme(tempsAlarmeMsec);
        }

        accesseurDevoirs.modifierDevoir(devoir);
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

        afficherDialogueChoixHeure();
    }

    private void afficherDialogueChoixDate() {
        calendrier = Calendar.getInstance();

        int annee = calendrier.get(Calendar.YEAR);
        int mois = calendrier.get(Calendar.MONTH) + 1; // +1 parce que larray des mois commence a 0 donc decembre = 11
        int jour = calendrier.get(Calendar.DAY_OF_MONTH);

        dialogueChoixDate = new DatePickerDialog(ModifierDevoir.this, ModifierDevoir.this, annee, mois, jour);
        dialogueChoixDate.show();
    }

    private void afficherDialogueChoixHeure() {
        int heure = calendrier.get(Calendar.HOUR_OF_DAY);
        int minute = calendrier.get(Calendar.MINUTE);

        dialogueChoixHeure = new TimePickerDialog(ModifierDevoir.this, ModifierDevoir.this, heure, minute, DateFormat.is24HourFormat(this));
        dialogueChoixHeure.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int heureDuJour, int minute) {
        this.heureAlarme = heureDuJour;
        this.minuteAlarme = minute;

        remplissageChampAlarme();
    }

    private void remplissageChampAlarme() {
        aAlarme = true;
        actionSupprimerAlarme.setVisibility(View.VISIBLE);
        champAlarme.setText(String.format("%02d", this.heureAlarme) + ":" + String.format("%02d", this.minuteAlarme) + " " + String.format("%02d", this.jourAlarme) + "/" + String.format("%02d", this.moisAlarme) + "/" + this.anneeAlarme);
    }
}

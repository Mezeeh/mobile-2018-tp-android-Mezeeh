package ca.qc.cgmatane.informatique.devoirs.vue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import ca.qc.cgmatane.informatique.devoirs.R;
import ca.qc.cgmatane.informatique.devoirs.accesseur.DevoirsDAO;
import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModifierDevoir extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    protected DevoirsDAO accesseurDevoirs;
    protected Devoir devoir;

    protected EditText champMatiere,
            champTache,
            champAlarme;

    protected Button actionModifierDevoir,
            actionSupprimerAlarme,
            actionTerminerAlarme,
            actionSupprimerDevoir;

    protected Calendar calendrier;

    protected DatePickerDialog dialogueChoixDate;

    protected TimePickerDialog dialogueChoixHeure;

    protected int anneeAlarme,
            moisAlarme,
            jourAlarme,
            heureAlarme,
            minuteAlarme;

    protected boolean aAlarme,
            devoirTermine,
            tempsAlarmeDevoirEstPasse;

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
        this.tempsAlarmeDevoirEstPasse = ((devoir.getTempsAlarme() - System.currentTimeMillis()) < 0);

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

        if (aAlarme) {
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

        actionSupprimerDevoir = findViewById(R.id.action_supprimer_devoir);
        actionSupprimerDevoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerDevoir();
            }
        });

        actionSupprimerAlarme = findViewById(R.id.action_modifier_devoir_supprimer_alarme);
        actionSupprimerAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerAlarme();
            }
        });

        actionTerminerAlarme = findViewById(R.id.action_modifier_devoir_terminer_devoir);
        actionTerminerAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminerDevoir();
            }
        });

        if (!tempsAlarmeDevoirEstPasse && aAlarme)
            actionSupprimerAlarme.setVisibility(View.VISIBLE);
        else if (tempsAlarmeDevoirEstPasse && aAlarme) {
            actionSupprimerAlarme.setVisibility(View.VISIBLE);
            actionTerminerAlarme.setVisibility(View.VISIBLE);
        }
    }

    private void supprimerAlarme() {
        devoir.setaAlarme(false);
        devoir.setTempsAlarme(0);
        champAlarme.setText("");
        actionSupprimerAlarme.setVisibility(View.GONE);
    }

    private void terminerDevoir() {
        tempsAlarmeDevoirEstPasse = true;
        devoir.setDevoirEstTermine(true);
        devoirTermine = devoir.isDevoirEstTermine();
        actionTerminerAlarme.setVisibility(View.GONE);
    }

    private void modifierDevoir() {
        devoir = new Devoir(champMatiere.getText().toString(), champTache.getText().toString(), this.devoir.getId_devoir());
        devoir.setaAlarme(aAlarme);
        devoir.setDevoirEstTermine(devoirTermine);
        if (!devoir.isDevoirEstTermine() && aAlarme) {
            long tempsAlarmeMsec = 0;
            try {
                tempsAlarmeMsec = new SimpleDateFormat("hh:mm dd/MM/yyyy").parse(this.heureAlarme + ":" + this.minuteAlarme + " " + this.jourAlarme + "/" + this.moisAlarme + "/" + this.anneeAlarme).getTime();
                tempsAlarmeDevoirEstPasse = ((tempsAlarmeMsec - System.currentTimeMillis()) < 0);
                devoir.setTempsAlarme(tempsAlarmeMsec);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            devoir.ajouterAlarme(this);
        }

        accesseurDevoirs.modifierDevoir(devoir);
        naviguerRetourALaBibliotheque();
    }

    private void supprimerDevoir() {
        Log.d("Click", "" + devoir.getId_devoir());
    }

    public void naviguerRetourALaBibliotheque() {
        this.finish();
    }

    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jourDuMois) {
        this.anneeAlarme = annee;
        this.moisAlarme = mois + 1; // +1 parce que larray des mois commence a 0 donc decembre = 11
        this.jourAlarme = jourDuMois;

        afficherDialogueChoixHeure();
    }

    private void afficherDialogueChoixDate() {
        calendrier = Calendar.getInstance();

        int annee = calendrier.get(Calendar.YEAR);
        int mois = calendrier.get(Calendar.MONTH);
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

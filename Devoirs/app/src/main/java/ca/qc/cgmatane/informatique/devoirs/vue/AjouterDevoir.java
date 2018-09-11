package ca.qc.cgmatane.informatique.devoirs.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.qc.cgmatane.informatique.devoirs.Devoirs;
import ca.qc.cgmatane.informatique.devoirs.R;
import ca.qc.cgmatane.informatique.devoirs.accesseur.DevoirsDAO;
import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;
import org.w3c.dom.Text;

public class AjouterDevoir extends AppCompatActivity {
    protected DevoirsDAO accesseurDevoirs;
    protected Devoir devoir;

    protected EditText champMatiere,
                        champTache;

    protected TextView champAlarme;

    protected Button actionAjouterDevoir,
                        actionAjouterAlarme;

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
            }
        });
    }

    private void ajouterDevoir(){
        devoir = new Devoir(champMatiere.getText().toString(), champTache.getText().toString(), 0);

        accesseurDevoirs.ajouterDevoir(devoir);
        naviguerRetourALaBibliotheque();
    }

    public void naviguerRetourALaBibliotheque(){
        this.finish();
    }
}

package ca.qc.cgmatane.informatique.devoirs.vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ca.qc.cgmatane.informatique.devoirs.R;
import ca.qc.cgmatane.informatique.devoirs.accesseur.DevoirsDAO;
import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;

public class AjouterDevoir extends AppCompatActivity {
    protected DevoirsDAO accesseurDevoirs;
    protected Devoir devoir;

    protected EditText champMatiere,
                        champTache;

    protected Button actionAjouterDevoir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_devoir);

        this.accesseurDevoirs = DevoirsDAO.getInstance();

        champMatiere = findViewById(R.id.vue_ajouter_devoir_matiere);
        champTache = findViewById(R.id.vue_ajouter_devoir_tache);

        actionAjouterDevoir = findViewById(R.id.appliquer_action_confirmer_ajouter);
        actionAjouterDevoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterDevoir();
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

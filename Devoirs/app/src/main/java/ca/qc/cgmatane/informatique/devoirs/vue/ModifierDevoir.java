package ca.qc.cgmatane.informatique.devoirs.vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ca.qc.cgmatane.informatique.devoirs.R;
import ca.qc.cgmatane.informatique.devoirs.accesseur.DevoirsDAO;
import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;

public class ModifierDevoir extends AppCompatActivity {
    protected DevoirsDAO accesseurDevoirs;
    protected Devoir devoir;

    protected EditText champMatiere,
                        champTache;

    protected Button actionModifierDevoir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_devoir);

        this.accesseurDevoirs = DevoirsDAO.getInstance();

        Bundle parametres = this.getIntent().getExtras();
        String parametre_id_devoir = parametres.get("id_devoir").toString();
        int id_livre = Integer.parseInt(parametre_id_devoir);

        devoir = accesseurDevoirs.trouverDevoir(id_livre);

        champMatiere = findViewById(R.id.vue_modifier_devoir_champ_matiere);
        champTache = findViewById(R.id.vue_modifier_devoir_champ_tache);

        champMatiere.setText(devoir.getMatiere());
        champTache.setText(devoir.getTache());

        actionModifierDevoir = findViewById(R.id.action_modifier_devoir);
        actionModifierDevoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierDevoir();
            }
        });
    }

    private void modifierDevoir(){
        devoir = new Devoir(champMatiere.getText().toString(), champTache.getText().toString(), this.devoir.getId_devoir());

        accesseurDevoirs.modifierDevoir(devoir);
        naviguerRetourALaBibliotheque();
    }

    public void naviguerRetourALaBibliotheque(){
        this.finish();
    }
}

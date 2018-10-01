package ca.qc.cgmatane.informatique.devoirs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ca.qc.cgmatane.informatique.devoirs.accesseur.BaseDeDonnees;
import ca.qc.cgmatane.informatique.devoirs.accesseur.DevoirsDAO;
import ca.qc.cgmatane.informatique.devoirs.vue.AjouterDevoir;
import ca.qc.cgmatane.informatique.devoirs.vue.ModifierDevoir;

import java.util.HashMap;
import java.util.List;

public class Devoirs extends AppCompatActivity {
    protected static final int ACTIVITE_MODIFIER_DEVOIR = 1;
    protected static final int ACTIVITE_AJOUTER_DEVOIR = 2;

    protected ListView vueListeDevoir;
    protected List<HashMap<String, String>> listeDevoir;

    protected Intent intentionNaviguerModifierDevoir;
    protected Intent intentionNaviguerAjouterDevoir;

    protected Button actionNaviguerAjouterDevoir;

    protected DevoirsDAO devoirsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_devoirs);

        BaseDeDonnees.getInstance(getApplicationContext());

        devoirsDAO = DevoirsDAO.getInstance();

        vueListeDevoir = (ListView) findViewById(R.id.vue_liste_devoir);

        afficherTousLesDevoirs();

        vueListeDevoir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View vue, int positionDansAdapteur, long positionItem) {
                ListView vueListeDevoir = (ListView) vue.getParent();

                HashMap<String, String> devoir = (HashMap<String, String>) vueListeDevoir.getItemAtPosition((int) positionItem);

                intentionNaviguerModifierDevoir = new Intent(Devoirs.this, ModifierDevoir.class);
                intentionNaviguerModifierDevoir.putExtra("id_devoir", devoir.get("id_devoir"));
                startActivityForResult(intentionNaviguerModifierDevoir, ACTIVITE_MODIFIER_DEVOIR);
            }
        });

            intentionNaviguerAjouterDevoir = new Intent(Devoirs.this, AjouterDevoir.class);

            actionNaviguerAjouterDevoir = (Button)findViewById(R.id.action_naviguer_ajouter_devoir);
            actionNaviguerAjouterDevoir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(intentionNaviguerAjouterDevoir, ACTIVITE_AJOUTER_DEVOIR);
                }
            });
    }

    protected void afficherTousLesDevoirs(){
        listeDevoir = devoirsDAO.recupererListeDevoirPourAdapteur();

        SimpleAdapter adapteur = new SimpleAdapter(
                this,
                listeDevoir,
                android.R.layout.two_line_list_item,
                new String[] {"matiere","tache"},
                new int[] {android.R.id.text1, android.R.id.text2});

        vueListeDevoir.setAdapter(adapteur);
    }

    protected void onActivityResult(int activite, int resultat, Intent donnees){
        switch (activite){
            case ACTIVITE_MODIFIER_DEVOIR:
            case ACTIVITE_AJOUTER_DEVOIR:
                afficherTousLesDevoirs();
                break;
        }
    }
}

package ca.qc.cgmatane.informatique.devoirs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ca.qc.cgmatane.informatique.devoirs.accesseur.DevoirsDAO;
import ca.qc.cgmatane.informatique.devoirs.vue.AjouterDevoir;
import ca.qc.cgmatane.informatique.devoirs.vue.ModifierDevoir;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Devoirs extends AppCompatActivity {

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

        devoirsDAO = new DevoirsDAO();

        vueListeDevoir = (ListView) findViewById(R.id.vue_liste_devoir);
        listeDevoir = devoirsDAO.preparerDevoirs();

        SimpleAdapter adapteur = new SimpleAdapter(
                this,
                listeDevoir,
                android.R.layout.two_line_list_item,
                new String[] {"matiere","tache"},
                new int[] {android.R.id.text1, android.R.id.text2});

        vueListeDevoir.setAdapter(adapteur);

        vueListeDevoir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View vue, int positionDansAdapteur, long positionItem) {
                ListView vueListeDevoir = (ListView) vue.getParent();

                Log.d("Journalisation", "positionItem : " + positionItem);

                HashMap<String, String> devoir = (HashMap<String, String>) vueListeDevoir.getItemAtPosition((int) positionItem);

                Toast message = Toast.makeText(getApplicationContext(),
                        "Position : " + positionItem +
                                " Matière : " + devoir.get("matiere") +
                                " Tâche : " + devoir.get("tache"),
                        Toast.LENGTH_SHORT);

                message.show();

                intentionNaviguerModifierDevoir = new Intent(Devoirs.this, ModifierDevoir.class);
                startActivity(intentionNaviguerModifierDevoir);
            }
        });

            intentionNaviguerAjouterDevoir = new Intent(Devoirs.this, AjouterDevoir.class);

            actionNaviguerAjouterDevoir = (Button)findViewById(R.id.action_naviguer_ajouter_devoir);
            actionNaviguerAjouterDevoir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intentionNaviguerAjouterDevoir);
                }
            });
    }

//    private List<HashMap<String, String>> preparerDevoirs() {
//        List<HashMap<String, String>> listeDevoir = new ArrayList<HashMap<String, String>>();
//
////        Log.d("Test", "preparerDevoirs()");
//
//        HashMap<String, String> devoir = new HashMap<String, String>();
//
//        devoir.put("matiere", "Programmation Mobile");
//        devoir.put("tache", "Echafaud du travail pratique Android Java");
//        listeDevoir.add(devoir);
//
//        devoir = new HashMap<String, String>();
//        devoir.put("matiere", "Espagnol");
//        devoir.put("tache", "Faire page 14 et 18 dans le cahier");
//        listeDevoir.add(devoir);
//
//        devoir = new HashMap<String, String>();
//        devoir.put("matiere", "Ethique");
//        devoir.put("tache", "Lire page 4 a 19 des notes de cours");
//        listeDevoir.add(devoir);
//
//        return listeDevoir;
//    }
}

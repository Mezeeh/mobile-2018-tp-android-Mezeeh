package ca.qc.cgmatane.informatique.devoirs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Devoirs extends AppCompatActivity {

    protected ListView vueListeDevoir;
    protected List<HashMap<String, String>> listeDevoir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_devoirs);

        vueListeDevoir = (ListView) findViewById(R.id.vue_liste_devoir);
        listeDevoir = preparerDevoirs();
    }

    private List<HashMap<String, String>> preparerDevoirs() {
        List<HashMap<String, String>> listeDevoir = new ArrayList<HashMap<String, String>>();


        Log.d("Test", "preparerDevoirs()");

        return listeDevoir;
    }
}

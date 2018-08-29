package ca.qc.cgmatane.informatique.devoirs.accesseur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DevoirsDAO {
    private  static DevoirsDAO instance;

    protected List<HashMap<String, String>> listeDevoir;

    public DevoirsDAO() {
        instance = null;
        listeDevoir = new ArrayList<HashMap<String, String>>();

        preparerDevoirs();
    }

    public List<HashMap<String, String>> recupererListeDevoir() {
        return listeDevoir;
    }

    public static DevoirsDAO getInstance(){
        if(null == instance)
            instance = new DevoirsDAO();

        return instance;
    }

    private List<HashMap<String, String>> preparerDevoirs() {
//        Log.d("Test", "preparerDevoirs()");

        HashMap<String, String> devoir = new HashMap<String, String>();

        devoir.put("matiere", "Programmation Mobile");
        devoir.put("tache", "Echafaud du travail pratique Android Java");
        listeDevoir.add(devoir);

        devoir = new HashMap<String, String>();
        devoir.put("matiere", "Espagnol");
        devoir.put("tache", "Faire page 14 et 18 dans le cahier");
        listeDevoir.add(devoir);

        devoir = new HashMap<String, String>();
        devoir.put("matiere", "Ethique");
        devoir.put("tache", "Lire page 4 a 19 des notes de cours");
        listeDevoir.add(devoir);

        return listeDevoir;
    }
}

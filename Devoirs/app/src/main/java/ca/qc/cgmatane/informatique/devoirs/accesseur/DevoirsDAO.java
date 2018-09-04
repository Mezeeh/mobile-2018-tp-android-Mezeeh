package ca.qc.cgmatane.informatique.devoirs.accesseur;

import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DevoirsDAO {
    private  static DevoirsDAO instance;

    protected List<Devoir> listeDevoir;
    protected List<HashMap<String, String>> listeDevoirPourAdapteur;

    public DevoirsDAO() {
        instance = null;
        listeDevoir = new ArrayList<Devoir>();

        preparerDevoirs();
    }

    public static DevoirsDAO getInstance(){
        if(null == instance)
            instance = new DevoirsDAO();

        return instance;
    }

    public List<HashMap<String, String>> recupererListeDevoirPourAdapteur(){
        listeDevoirPourAdapteur = new ArrayList<HashMap<String, String>>();

        for(Devoir devoir : listeDevoir)
            listeDevoirPourAdapteur.add(devoir.obtenirDevoirPourAdapteur());

        return listeDevoirPourAdapteur;
    }

    private void preparerDevoirs() {
//        Log.d("Test", "preparerDevoirs()");

        listeDevoir.add(new Devoir("Programmation Mobile", "Release initial du travail pratique Android Java", 1));
        listeDevoir.add(new Devoir("Espagnol", "Faire page 14 et 18 dans le cahier", 2));
        listeDevoir.add(new Devoir("Ethique", "Lire page 4 a 19 des notes de cours", 3));

        /*HashMap<String, String> devoir = new HashMap<String, String>();

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

        return listeDevoir;*/
    }

    public Devoir trouverDevoir(int id_devoir){
        for(Devoir devoir : this.listeDevoir)
            if(devoir.getId_devoir() == id_devoir)
                return devoir;

        return null;
    }

    public void modifierDevoir(Devoir devoirAModifier){
        for(Devoir devoir : this.listeDevoir){
            if(devoir.getId_devoir() == devoirAModifier.getId_devoir()){
                devoir.setMatiere(devoirAModifier.getMatiere());
                devoir.setTache(devoirAModifier.getTache());
                break;
            }
        }
    }

    public void ajouterDevoir(Devoir devoir){
        listeDevoir.add(devoir);
    }
}

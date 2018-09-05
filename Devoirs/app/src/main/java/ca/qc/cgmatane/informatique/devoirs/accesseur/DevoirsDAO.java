package ca.qc.cgmatane.informatique.devoirs.accesseur;

import android.database.Cursor;
import android.util.Log;
import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DevoirsDAO {
    private  static DevoirsDAO instance;

    private BaseDeDonnees baseDeDonnees;

    protected List<Devoir> listeDevoir;
    protected List<HashMap<String, String>> listeDevoirPourAdapteur;

    public DevoirsDAO() {
        instance = null;
        baseDeDonnees = BaseDeDonnees.getInstance();
        listeDevoir = new ArrayList<Devoir>();

//        preparerDevoirs();
    }

    public static DevoirsDAO getInstance(){
        if(null == instance)
            instance = new DevoirsDAO();

        return instance;
    }

    public void listerDevoir(){
        String LISTER_DEVOIRS = "SELECT * FROM devoir";

        Cursor curseur = baseDeDonnees.getReadableDatabase().rawQuery(LISTER_DEVOIRS, null);

        this.listeDevoir.clear();

        Devoir devoir;

        int indexId_devoir = curseur.getColumnIndex("id_devoir");
        int indexMatiere = curseur.getColumnIndex("matiere");
        int indexTache = curseur.getColumnIndex("tache");

        for(curseur.moveToFirst() ; !curseur.isAfterLast() ; curseur.moveToNext()){
            int id_devoir = curseur.getInt(indexId_devoir);
            String matiere = curseur.getString(indexMatiere);
            String tache = curseur.getString(indexTache);

            devoir = new Devoir(matiere, tache, id_devoir);
            this.listeDevoir.add(devoir);
        }
    }

    public List<HashMap<String, String>> recupererListeDevoirPourAdapteur(){
        listeDevoirPourAdapteur = new ArrayList<HashMap<String, String>>();

        listerDevoir();

        for(Devoir devoir : listeDevoir)
            listeDevoirPourAdapteur.add(devoir.obtenirDevoirPourAdapteur());

        return listeDevoirPourAdapteur;
    }

    private void preparerDevoirs() {
//        Log.d("Test", "preparerDevoirs()");

        listeDevoir.add(new Devoir("Programmation Mobile", "Release initial du travail pratique Android Java", 1));
        listeDevoir.add(new Devoir("Espagnol", "Faire page 14 et 18 dans le cahier", 2));
        listeDevoir.add(new Devoir("Ethique", "Lire page 4 a 19 des notes de cours", 3));
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
                String MODIFIER_DEVOIR = "UPDATE devoir SET matiere = '" + devoirAModifier.getMatiere() + "', tache = '" + devoirAModifier.getTache() + "' WHERE id_devoir =" + devoirAModifier.getId_devoir();
                baseDeDonnees.getWritableDatabase().execSQL(MODIFIER_DEVOIR);
                break;
            }
        }
    }

    public void ajouterDevoir(Devoir devoir){
        String AJOUTER_DEVOIR = "insert into devoir(matiere, tache) VALUES('" + devoir.getMatiere() + "', '" + devoir.getTache() + "')";
        baseDeDonnees.getWritableDatabase().execSQL(AJOUTER_DEVOIR);
    }
}

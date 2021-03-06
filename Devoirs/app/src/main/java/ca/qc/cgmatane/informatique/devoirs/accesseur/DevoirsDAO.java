package ca.qc.cgmatane.informatique.devoirs.accesseur;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import ca.qc.cgmatane.informatique.devoirs.modele.Devoir;

import java.sql.PreparedStatement;
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
        int indexTempsAlarme = curseur.getColumnIndex("temps_alarme");
        int indexEstTermine = curseur.getColumnIndex("est_termine");

        for(curseur.moveToFirst() ; !curseur.isAfterLast() ; curseur.moveToNext()){
            int id_devoir = curseur.getInt(indexId_devoir);
            String matiere = curseur.getString(indexMatiere);
            String tache = curseur.getString(indexTache);

            devoir = new Devoir(matiere, tache, id_devoir);

            if(curseur.getInt(indexEstTermine) == 0 && 0 != curseur.getDouble(indexTempsAlarme)) {
                long tempsAlarme = (long) curseur.getDouble(indexTempsAlarme);
                devoir.setaAlarme(true);
                devoir.setTempsAlarme(tempsAlarme);
            }

            if(curseur.getInt(indexEstTermine) == 0){
                devoir.setDevoirEstTermine(false);
                this.listeDevoir.add(devoir);
            }
        }
    }

    public List<HashMap<String, String>> recupererListeDevoirPourAdapteur(){
        listeDevoirPourAdapteur = new ArrayList<HashMap<String, String>>();

        listerDevoir();

        for(Devoir devoir : listeDevoir)
            listeDevoirPourAdapteur.add(devoir.obtenirDevoirPourAdapteur());

        return listeDevoirPourAdapteur;
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
                String MODIFIER_DEVOIR = "UPDATE devoir SET matiere = '" + devoirAModifier.getMatiere() + "', tache = '" + devoirAModifier.getTache() + "' , temps_alarme = '" + (devoirAModifier.getTempsAlarme() == 0 ? null : devoirAModifier.getTempsAlarme()) + "', est_termine = '" + (devoirAModifier.isDevoirEstTermine() ? 1 : 0) + "' WHERE id_devoir =" + devoirAModifier.getId_devoir();

                baseDeDonnees.getWritableDatabase().execSQL(MODIFIER_DEVOIR);
                break;
            }
        }
    }

    public void ajouterDevoir(Devoir devoir){
        String AJOUTER_DEVOIR;

        if(devoir.isaAlarme()) {
            AJOUTER_DEVOIR = "insert into devoir(matiere, tache, temps_alarme, est_termine) VALUES('" + devoir.getMatiere() + "', '" + devoir.getTache() + "', '" + devoir.getTempsAlarme() + "', '" + (devoir.isDevoirEstTermine() ? 1 : 0) + "')";
        } else {
            AJOUTER_DEVOIR = "insert into devoir(matiere, tache) VALUES('" + devoir.getMatiere() + "', '" + devoir.getTache() + "')";
        }

        baseDeDonnees.getWritableDatabase().execSQL(AJOUTER_DEVOIR);
    }

    public void supprimerDevoir(int id){
        String SUPPRIMER_DEVOIR = "DELETE FROM devoir WHERE id_devoir = ?;";

        SQLiteStatement requete = baseDeDonnees.getWritableDatabase().compileStatement(SUPPRIMER_DEVOIR);
        requete.bindLong(1, id);
        requete.executeUpdateDelete();
    }
}

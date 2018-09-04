package ca.qc.cgmatane.informatique.devoirs.modele;

import java.util.HashMap;

public class Devoir {
    protected String matiere,
                    tache;

    protected int id_devoir;

    protected HashMap<String, String> devoirPourAdapteur;

    public Devoir(String matiere, String tache) {
        this.matiere = matiere;
        this.tache = tache;
    }

    public Devoir(String matiere, String tache, int id_devoir) {
        this.matiere = matiere;
        this.tache = tache;
        this.id_devoir = id_devoir;
    }

    public HashMap<String, String> obtenirDevoirPourAdapteur(){
        devoirPourAdapteur = new HashMap<String, String>();

        devoirPourAdapteur.put("matiere", this.matiere);
        devoirPourAdapteur.put("tache", this.tache);
        devoirPourAdapteur.put("id_devoir", "" + this.id_devoir);

        return devoirPourAdapteur;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public int getId_devoir() {
        return id_devoir;
    }

    public void setId_devoir(int id_devoir) {
        this.id_devoir = id_devoir;
    }
}

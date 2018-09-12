package ca.qc.cgmatane.informatique.devoirs.modele;

import java.util.HashMap;

public class Devoir {
    protected String matiere,
                    tache;

    protected int id_devoir;

    protected boolean aAlarme;

    protected int anneeAlarme,
                    moisAlarme,
                    jourAlarme,
                    heureAlarme,
                    minuteAlarme;

    protected HashMap<String, String> devoirPourAdapteur;

    public Devoir(String matiere, String tache) {
        this.matiere = matiere;
        this.tache = tache;

        this.aAlarme = false;
    }

    public Devoir(String matiere, String tache, int id_devoir) {
        this.matiere = matiere;
        this.tache = tache;
        this.id_devoir = id_devoir;

        this.aAlarme = false;
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

    public boolean isaAlarme() {
        return aAlarme;
    }

    public void setaAlarme(boolean aAlarme) {
        this.aAlarme = aAlarme;
    }

    public int getAnneeAlarme() {
        return anneeAlarme;
    }

    public void setAnneeAlarme(int anneeAlarme) {
        this.anneeAlarme = anneeAlarme;
    }

    public int getMoisAlarme() {
        return moisAlarme;
    }

    public void setMoisAlarme(int moisAlarme) {
        this.moisAlarme = moisAlarme;
    }

    public int getJourAlarme() {
        return jourAlarme;
    }

    public void setJourAlarme(int jourAlarme) {
        this.jourAlarme = jourAlarme;
    }

    public int getHeureAlarme() {
        return heureAlarme;
    }

    public void setHeureAlarme(int heureAlarme) {
        this.heureAlarme = heureAlarme;
    }

    public int getMinuteAlarme() {
        return minuteAlarme;
    }

    public void setMinuteAlarme(int minuteAlarme) {
        this.minuteAlarme = minuteAlarme;
    }
}

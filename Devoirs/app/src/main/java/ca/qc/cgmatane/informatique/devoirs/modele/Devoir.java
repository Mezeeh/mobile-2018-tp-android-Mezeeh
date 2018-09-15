package ca.qc.cgmatane.informatique.devoirs.modele;

import java.util.HashMap;

public class Devoir {
    protected String matiere,
                    tache;

    protected int id_devoir;

    protected boolean aAlarme;

    protected long tempsAlarme;

    protected boolean devoirEstTermine;

    protected HashMap<String, String> devoirPourAdapteur;

    public Devoir(String matiere, String tache) {
        this.matiere = matiere;
        this.tache = tache;

        this.aAlarme = false;
        this.devoirEstTermine = false;
    }

    public Devoir(String matiere, String tache, int id_devoir) {
        this.matiere = matiere;
        this.tache = tache;
        this.id_devoir = id_devoir;

        this.aAlarme = false;
        this.devoirEstTermine = false;
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

    public long getTempsAlarme() {
        return tempsAlarme;
    }

    public void setTempsAlarme(long tempsAlarme) {
        this.tempsAlarme = tempsAlarme;
    }

    public boolean isDevoirEstTermine() {
        return devoirEstTermine;
    }

    public void setDevoirEstTermine(boolean devoirEstTermine) {
        this.devoirEstTermine = devoirEstTermine;
    }
}

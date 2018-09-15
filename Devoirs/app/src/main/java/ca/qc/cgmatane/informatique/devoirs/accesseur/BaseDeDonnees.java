package ca.qc.cgmatane.informatique.devoirs.accesseur;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnees extends SQLiteOpenHelper {

    private static BaseDeDonnees instance = null;

    public static BaseDeDonnees getInstance(Context contexte) {
        if(null == instance)
            instance = new BaseDeDonnees(contexte);
        return instance;
    }

    public static BaseDeDonnees getInstance() {
        return instance;
    }

    public BaseDeDonnees(Context context) {
        super(context, "devoirs", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table devoir(id_devoir INTEGER PRIMARY KEY, matiere TEXT, tache TEXT, temps_alarme INTEGER, est_termine INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String DELETE = "delete from devoir where 1 = 1";
        db.execSQL(DELETE);

        String INSERT_1 = "insert into devoir(id_devoir, matiere, tache, temps_alarme, est_termine) VALUES('1', 'Programmation Mobile', 'Release initial du travail pratique Android Java', null, 0)";
        String INSERT_2 = "insert into devoir(id_devoir, matiere, tache, temps_alarme, est_termine) VALUES('2', 'Espagnol', 'Faire page 14 et 18 dans le cahier', null, 0)";
        String INSERT_3 = "insert into devoir(id_devoir, matiere, tache, temps_alarme, est_termine) VALUES('3', 'Ethique', 'Lire page 4 a 19 des notes de cours', null, 0)";

        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);
        db.execSQL(INSERT_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String DETRUIRE_TABLE = "drop table devoir";
//        db.execSQL(DETRUIRE_TABLE);
        String CREER_TABLE = "CREATE TABLE devoir(id_devoir INTEGER PRIMARY KEY, matiere TEXT, tache TEXT, temps_alarme INTEGER, est_termine INTEGER)";
        db.execSQL(CREER_TABLE);
    }
}

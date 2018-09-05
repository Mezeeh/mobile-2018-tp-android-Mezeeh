package ca.qc.cgmatane.informatique.devoirs.accesseur;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnees extends SQLiteOpenHelper {

    public BaseDeDonnees(Context context) {
        super(context, "devoirs", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table devoir(id_devoir INTEGER PRIMARY KEY, matiere TEXT, tache TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        String DELETE = "delete from devoir where 1 = 1";
        db.execSQL(DELETE);

        String INSERT_1 = "insert into livre(id_devoir, matiere, tache) VALUES('1', 'Programmation Mobile', 'Release initial du travail pratique Android Java')";
        String INSERT_2 = "insert into livre(id_devoir, matiere, tache) VALUES('2', 'Espagnol', 'Faire page 14 et 18 dans le cahier')";
        String INSERT_3 = "insert into livre(id_devoir, matiere, tache) VALUES('3', 'Ethique', 'Lire page 4 a 19 des notes de cours')";

        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);
        db.execSQL(INSERT_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String DETRUIRE_TABLE = "drop table devoir";
//        db.execSQL(DETRUIRE_TABLE);
        String CREER_TABLE = "CREATE TABLE devoir(id_devoir INTEGER PRIMARY KEY, matiere TEXT, tache TEXT)";
        db.execSQL(CREER_TABLE);
    }
}

package com.eric.appsql;


import static android.icu.text.MessagePattern.ArgType.SELECT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;
import java.util.Vector;


public class GestionBD extends SQLiteOpenHelper {

    //instance unique de la classe Singleton GestionBD
    private static GestionBD instance;

    private SQLiteDatabase database;


    // méthode de base pour un Singleton
    public static GestionBD getInstance(Context contexte) {
        if (instance == null)
            instance = new GestionBD(contexte);
        return instance;
    }

    // 4 paramètre
    // - le contexte de l'application
    // - le nom de la base de données
    // - factory sert a modifier le cursor qui sert a faire des requete
    // - la version de SqLite utilisé
    GestionBD(Context context) {
        super(context, "db", null, 1);
    }

    // ne pas appeler plusieurs fois OnCreate sinon c'est qu'il y a plusieurs fois la meme base de donnée
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creation
        db.execSQL("CREATE TABLE inventeurs (_id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, origine TEXT, invention TEXT, annee INTEGER)");

        // Version hardCodée
        db.execSQL("INSERT INTO inventeurs (nom, origine, invention, annee) VALUES ('Laszlo Biro', 'Hongrie', 'Stylo à bille', 1938)");
        db.execSQL("INSERT INTO inventeurs (nom, origine, invention, annee) VALUES ('Benjamin Franklin', 'Etats-Unis', 'Paratonnerre', 1752)");
        db.execSQL("INSERT INTO inventeurs (nom, origine, invention, annee) VALUES ('Mary Anderson', 'Etats-Unis', 'Essuie-glace', 1903)");
        db.execSQL("INSERT INTO inventeurs (nom, origine, invention, annee) VALUES ('Grace Hopper', 'Etats-Unis', 'Compilateur', 1952)");
        db.execSQL("INSERT INTO inventeurs (nom, origine, invention, annee) VALUES ('Benoit Rouquayrol', 'France', 'Scaphandre', 1864)");



    }

    // Autre facon de faire ici en utilisant insert a la place
    // permet d'utiliser des données relative a l'inventeur qui est donnée

    public void ajoutInventeur(SQLiteDatabase db, Inventeur i){
        // besoin de faire un ContentValues (ce comporte comme un hashTable
        ContentValues values = new ContentValues();

        // Prepare the data to insert
        values.put("nom", i.getNom());
        values.put("origine", i.getOrigine());
        values.put("invention", i.getInvention());
        values.put("annee", i.getAnnee());

        db.insert("inventeurs", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS inventeurs");
        onCreate(db);
    }

    public void ouvrirConnexion(){
        database = this.getWritableDatabase();
    }

    public void fermerConnexion(){
        database.close();
    }

    public Vector<String> retournerInvention(){
        Cursor cursor = database.rawQuery("SELECT invention FROM inventeurs",null);
        Vector<String> vecteur = new Vector<String>();

        while (cursor.moveToNext()) {
            // la méthode getcolumnIndexOrThrow permet de ne pas créer d'erreur si le nom de colonmne n'existe pas
            // getcolumnIndex permet d'utiliser un String (nom de la colonne a la place de son incrementation de colonne)
            vecteur.add(cursor.getString(cursor.getColumnIndexOrThrow("invention")));
            // setup avec une int qui réfère au numéro de la colonne
            //vecteur.add(cursor.getString(0));
        }

        cursor.close();
        return vecteur;
    }

    public boolean retournerMatch(String inventionChoisi, String nomChoisi){
        Cursor cursor = database.rawQuery("SELECT * FROM inventeurs",null);

        // solution meilleur a Eric
        String [] tab = {inventionChoisi, nomChoisi};
        Cursor c = database.rawQuery("SELECT * FROM inventeurs WHERE invention = ? AND nom = ?", tab);
        return c.moveToNext();

        // ma solution moins efficace
        /*while (cursor.moveToNext()) {
            String invention = cursor.getString(cursor.getColumnIndexOrThrow("invention"));
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            if(inventionChoisi.equals(invention) && nomChoisi.equals(nom)){
                return true;
            }
        }
        return false;*/
    }


}

package com.eric.appsql;

import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.Vector;


public class MainActivity extends AppCompatActivity {


    Modele modele = new Modele();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GestionBD instance = GestionBD.getInstance(getApplicationContext());


        instance.ouvrirConnexion();
        Vector<String> st = new Vector<String>(instance.retournerInvention());
        System.out.println(st);

        String testnom = "Grace Hopper";
        String testinvetion = "Compilateur";
        instance.retournerMatch(testinvetion,testnom);

        System.out.println(instance.retournerMatch(testinvetion,testnom));

        instance.fermerConnexion();


        // Premiere version avec chat
        // Creation du curseur
        // (le curseur ne correspond pas a la souris mais l'endroit ou on situe dans la base de donnée)
        //
        //Cursor cursor = GestionBD.getInstance(getApplicationContext()).retournerInvention();

        // On loop  le curseur sur la base de donnée afin d,aller chercher les valeurs case par case
        // Rangée par rangée
        // Cette méthode n'est pas très efficace
        /*while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            String origine = cursor.getString(cursor.getColumnIndexOrThrow("origine"));
            String invention = cursor.getString(cursor.getColumnIndexOrThrow("invention"));
            int annee = cursor.getInt(cursor.getColumnIndexOrThrow("annee"));

            // Print the values
            System.out.println("ID: " + itemId + ", Nom: " + nom + ", Origine: " + origine + ", Invention: " + invention + ", Année: " + annee);
        }
        // Close the cursor to release its resources
        // Que ce passe t-il si on oublie de fermer le curseur??

        cursor.close();
    }*/
    }

}






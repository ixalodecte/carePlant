package plantProject.fr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class databasePlant extends SQLiteOpenHelper {

    public static final String NOM = "nom";
    public static final String COL_1 = "humiditeMoins";
    public static final String COL_2 = "humiditePlus";
    public static final String COL_3 = "temperatureMoins";
    public static final String COL_4 = "temperaturePlus";
    public static final String COL_5 = "lminositeMoins";
    public static final String COL_6 = "luminositePlus";


    public static final String DATABASE_NAME = "plantDB.db";
    public static final String TABLE_NAME = "PLANTE";
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    NOM + " TEXT PRIMARY KEY, " +
                    COL_1 + " REAL, " +
                    COL_2 + " REAL, " +
                    COL_3 + " REAL, " +
                    COL_4 + " REAL, " +
                    COL_5 + " REAL, " +
                    COL_6 + " REAL);";

    public databasePlant(@Nullable Context context) {
        super(context, DATABASE_NAME , null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('epicea', 2.0, 50.0, 20, 30, 3000, 7000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('coucou', 3.0, 50.0, 25, 30, 1000, 2000 )");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('test', 7.0, 20.0, 10, 15, 1000, 2000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('cactus', 2.0, 50.0, 20, 30, 3000, 7000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('bonzai', 3.0, 50.0, 25, 30, 1000, 2000 )");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('arbuste', 7.0, 20.0, 10, 15, 1000, 2000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('tardis', 2.0, 50.0, 20, 30, 3000, 7000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('acacia', 3.0, 50.0, 25, 30, 1000, 2000 )");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('rose', 7.0, 20.0, 10, 15, 1000, 2000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('tulipe', 2.0, 50.0, 20, 30, 3000, 7000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('bleuet', 3.0, 50.0, 25, 30, 1000, 2000 )");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('muguet', 7.0, 20.0, 10, 15, 1000, 2000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('fougere', 2.0, 50.0, 20, 30, 3000, 7000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('mousse', 3.0, 50.0, 25, 30, 1000, 2000 )");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('roseaux', 7.0, 20.0, 10, 15, 1000, 2000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('pissenlit', 2.0, 50.0, 20, 30, 3000, 7000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('trefle', 3.0, 50.0, 25, 30, 1000, 2000 )");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('zozo', 7.0, 20.0, 10, 15, 1000, 2000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('cactus du sud', 3.0, 50.0, 25, 30, 1000, 2000 )");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('cactus du nord', 7.0, 20.0, 10, 15, 1000, 2000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('cactus lunaire', 2.0, 50.0, 20, 30, 3000, 7000)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values('cactus carnivore', 3.0, 50.0, 25, 30, 1000, 2000 )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    public void query(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public boolean insertData(String nom, double humiditeMoins, double humiditePlus, double temperatureMoins, double temperaturePlus, double luminositeMoins, double luminositePlus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(NOM, nom);
        c.put(COL_1, humiditeMoins);
        c.put(COL_2, humiditePlus);
        c.put(COL_3, temperatureMoins);
        c.put(COL_4, temperaturePlus);
        c.put(COL_5, luminositeMoins);
        c.put(COL_6, luminositePlus);
        long resultat = db.insert(TABLE_NAME, null, c);
        db.close();
        return resultat != -1;


    }

    public Plante cursorToPlant(Cursor c){
        Plante plante = new Plante();
        plante.nom = c.getString(c.getColumnIndex(NOM));
        plante.humiditeMoins = c.getDouble(c.getColumnIndex(COL_1));
        plante.humiditePlus = c.getDouble(c.getColumnIndex(COL_2));
        plante.temperatureMoins = c.getDouble(c.getColumnIndex(COL_3));
        plante.temperaturePlus = c.getDouble(c.getColumnIndex(COL_4));
        plante.luminositeMoins = c.getDouble(c.getColumnIndex(COL_5));
        plante.luminositePlus = c.getDouble(c.getColumnIndex(COL_6));
        return plante;
    }
    public Plante findPlanteByName(String nom){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * From " + TABLE_NAME + " Where nom = " + "'" + nom  + "'",null);

        //Normallement 1 seul resultat doit être renvoyé.
        if (c.getCount() !=1){
            return null;
        }

        c.moveToFirst();

        Plante plante = cursorToPlant(c);

        db.close();
        return plante;
    }

    //auteur (inspiré de) : https://hacksmile.com/android-sqlite-search-searching-sqlite-database-in-android/
    public LinkedList<Plante> search(String chaine){
        LinkedList<Plante> liste = new LinkedList<Plante>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql;
        if(chaine.equals("")){
            sql = "SELECT * FROM " + TABLE_NAME;
        }
        else {
            sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + NOM + " LIKE '%" + chaine + "%'";
        }
        sql += " ORDER BY nom";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            // la recherche a trouve des lignes
            if (cursor.moveToFirst()) {
                do {
                    liste.add(cursorToPlant(cursor));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return liste;
    }
}

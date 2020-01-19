package plantProject.fr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends NavDrawerActivity {


    private ImageView temp;
    private ImageView lum;
    private ImageView hum;
    private ImageView panneauProbleme;
    private Button param;
    private ImageButton recherche;
    private TextView reservoir;
    private TextView TV_nomPlante;
    private TextView textErrorReservoir;
    public static double humidite;
    public static double temperature;
    public static double luminosite;
    public static int niveau;
    public static String nom;
    public static Plante plante;




    private DatabaseReference capteurs;

    //Constante pour shared preferences. On n'enregistre que le nom de la plante (chaine de caractères)
    public static final String SHARED_PREFS = "sharedPref";
    public static final String NOM = "nom";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_main);
        this.temp = findViewById(R.id.logo_temp);
        this.lum = findViewById(R.id.logo_lum);
        this.hum = findViewById(R.id.logo_hum);
        this.panneauProbleme = findViewById(R.id.panneauProbleme);
        this.textErrorReservoir = findViewById(R.id.textErrorReservoir);
        this.param = findViewById(R.id.paramButton);
        this.recherche = findViewById(R.id.rechercheButton);
        reservoir = findViewById(R.id.ReservoirDesc);
        this.TV_nomPlante = findViewById(R.id.nomPlante);
        humidite = 0.0;
        luminosite = 0.0;
        temperature = 0.0;
        niveau = 0;

        //Si on a deja un nom de plante, on cherche ses propriete dans la base,
        //Sinon on ouvre la page de recherche
        nom = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).getString(NOM,"");
        Log.d("nom pref partage:", nom + "voila");


        if (nom.equals("")){
            Intent openRech = new Intent(getApplicationContext(), recherchePlante.class);
            startActivity(openRech);


            finish();
        }



        else {
            databasePlant database = new databasePlant(getApplicationContext());
            plante = database.findPlanteByName(nom);







            //Si la plante n'est pas dans la base de donnée:
            if (plante == null){
                Intent openRech = new Intent(getApplicationContext(), recherchePlante.class);
                startActivity(openRech);
                finish();
            }
            else{
                //Enregistrement sur firebase
                FirebaseDatabase databaseE = FirebaseDatabase.getInstance();
                DatabaseReference myRef = databaseE.getReference("info");

                myRef.child("humiditeMoins").setValue(plante.humiditeMoins);
                myRef.child("humiditePlus").setValue(plante.humiditePlus);
                myRef.child("temperatureMoins").setValue(plante.temperatureMoins);
                myRef.child("temperaturePlus").setValue(plante.temperaturePlus);


                //affichage nom de la plante
                this.TV_nomPlante.setText(nom);

                capteurs = FirebaseDatabase.getInstance().getReference().child("capteurs");
                capteurs.child("timestamp").setValue("coolool");


                //Definition des évenement de l'activité
                this.temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openTemp = new Intent(getApplicationContext(), temperatureDetail.class);
                        startActivity(openTemp);
                    }
                });

                this.lum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openLum = new Intent(getApplicationContext(), LuminositeDetail.class);
                        startActivity(openLum);
                    }
                });

                this.hum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openHum = new Intent(getApplicationContext(), humiditeDetail.class);
                        startActivity(openHum);
                    }
                });

                this.param.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openParam = new Intent(getApplicationContext(), NavDrawerActivity.class);
                        startActivity(openParam);
                    }
                });

                this.recherche.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openRech = new Intent(getApplicationContext(), recherchePlante.class);
                        startActivity(openRech);
                        finish();
                    }
                });

                //Si la valeur d'un des champs capteurs change dans la base de donnée, on actualise les infos
                capteurs.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                            String location = locationSnapshot.getKey();
                            Log.d("nom:", location);
                            if (location.equals("humidite")){
                                MainActivity.humidite = locationSnapshot.getValue(double.class);
                            }
                            if (location.equals("temperature")){
                                MainActivity.temperature = locationSnapshot.getValue(double.class);
                            }
                            if (location.equals("luminosite")){
                                MainActivity.luminosite = locationSnapshot.getValue(double.class);
                            }
                            if (location.equals("niveau")){
                                MainActivity.niveau = locationSnapshot.getValue(int.class);
                            }

                        }
                        changeImage();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
            });}
        }



    }




    /*
     * Fonction qui définit les images de l'activité principale
     * Elle change la couleur des pictogrammes luminosité et humidité, et affiche une alerte
     * si le reservoir est vide
     */
    protected void changeImage(){
        if (humidite >= plante.humiditeMoins && humidite <= plante.humiditePlus){
            hum.setImageResource(R.drawable.gouttes_vert);
        }
        else{
            hum.setImageResource(R.drawable.gouttes_rouge);
        }

        if (temperature >= plante.temperatureMoins && temperature <= plante.temperaturePlus){
            temp.setImageResource(R.drawable.termo_vert);
        }
        else{
            temp.setImageResource(R.drawable.termo_rouge);
        }

        if (luminosite >= plante.luminositeMoins && luminosite <= plante.luminositePlus){
            lum.setImageResource(R.drawable.lumiere_vert);
        }
        else{
            lum.setImageResource(R.drawable.lumiere_rouge);
        }

        if (niveau < 5){
            panneauProbleme.setVisibility(View.VISIBLE);
            textErrorReservoir.setText("Reservoir vide");
        }
        else if (niveau <30){
            panneauProbleme.setVisibility(View.VISIBLE);
            textErrorReservoir.setText("Reservoir presque vide");
        }
        else {
            panneauProbleme.setVisibility(View.INVISIBLE);
            textErrorReservoir.setText("");
        }

    }

    //Non utilisé
    private boolean enregistrementFichier(String nomFichier){
        databasePlant database = new databasePlant(getApplicationContext());
        FileInputStream file = null;
        try {
            file = new FileInputStream(nomFichier);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        String line;
        while (true) {
            try {
                if (((line = br.readLine()) == null)) break;
                database.query(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}

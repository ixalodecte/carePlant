package plantProject.fr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    private ImageView temp;
    private ImageView lum;
    private ImageView hum;
    private Button param;
    private ImageButton recherche;
    TextView reservoir;
    private double marge;
    private TextView TV_nomPlante;
    public static double humidite;
    public static double temperature;
    public static double luminosite;
    public static int niveau;
    public static boolean probleme;
    public static String nom;
    public static Plante plante;




    private DatabaseReference capteurs;

    //Constante pour shared preferences
    public static final String SHARED_PREFS = "sharedPref";
    public static final String NOM = "nom";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.temp = findViewById(R.id.logo_temp);
        this.lum = findViewById(R.id.logo_lum);
        this.hum = findViewById(R.id.logo_hum);
        this.param = findViewById(R.id.paramButton);
        this.recherche = findViewById(R.id.rechercheButton);
        reservoir = findViewById(R.id.ReservoirDesc);
        this.TV_nomPlante = findViewById(R.id.nomPlante);
        this.humidite = 0.0;
        this.luminosite = 0.0;
        this.temperature = 0.0;
        this.niveau = 0;

        //Si on a deja un nom de plante, on cherche ses propriete dans la base,
        //Sinon on ouvre la page de recherche
        this.nom = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).getString(NOM,"");
        Log.d("nom pref partage:", this.nom + "voila");


        if (this.nom == ""){
            Intent openRech = new Intent(getApplicationContext(), recherchePlante.class);
            startActivity(openRech);
            finish();
        }

        else {
            databasePlant database = new databasePlant(getApplicationContext());
            plante = database.findPlanteByName(this.nom);
            //Si la plante n'est pas dans la base de donnée:
            if (plante == null || this.plante == null){
                Intent openRech = new Intent(getApplicationContext(), recherchePlante.class);
                startActivity(openRech);
                finish();
            }
        }

        //affichage nom de la plante
        this.TV_nomPlante.setText(this.plante.nom);


        capteurs = FirebaseDatabase.getInstance().getReference().child("capteurs");
        capteurs.child("timestamp").setValue("coolool");


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
                        reservoir.setText(String.format(getString(R.string.infoReservoir), locationSnapshot.getValue(int.class) ));
                    }

                }
                changeImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }





    protected void changeImage(){
        if (humidite >= plante.humiditeMoins && humidite <= plante.humiditePlus){
            hum.setImageResource(R.drawable.gouttes_vert);
        }
        /*else if (humidite < humiditeMoins-1 || humidite > humiditePlus){
            hum.setImageResource(R.drawable.gouttes_rouge);
        }*/
        else{
            hum.setImageResource(R.drawable.gouttes_rouge);
        }

        if (temperature >= plante.temperatureMoins && temperature <= plante.temperaturePlus){
            temp.setImageResource(R.drawable.termo_vert);
        }
        /*else if (temperature < temperatureMoins-1 || temperature > temperaturePlus+1){
            temp.setImageResource(R.drawable.termo_jaune);
        }*/
        else{
            temp.setImageResource(R.drawable.termo_rouge);
        }

        if (luminosite >= plante.luminositeMoins && luminosite <= plante.luminositePlus){
            lum.setImageResource(R.drawable.lumiere_vert);
        }
        /*else if (luminosite < luminositeMoins-1 || luminosite > luminositePlus+1){
            lum.setImageResource(R.drawable.lumiere_rouge);
        }*/
        else{
            lum.setImageResource(R.drawable.lumiere_rouge);
        }
    }
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

package plantProject.fr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class recherchePlante extends NavDrawerActivity {
    private Button choixValide;
    private EditText recherche;
    private ListView resultatRecherche;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> elemList;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_recherche_plante);
        choixValide = findViewById(R.id.buttonOk);
        recherche = findViewById(R.id.barreRecherche);
        resultatRecherche = findViewById(R.id.listResultat);

        elemList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, elemList);
        resultatRecherche.setAdapter(arrayAdapter);

        databasePlant database = new databasePlant(getApplicationContext());
        affichage(database.search(""));

        this.recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                databasePlant database = new databasePlant(getApplicationContext());
                affichage(database.search(String.valueOf(s)));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.resultatRecherche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recherche.setText(elemList.get(position));
            }
        });


        this.choixValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = recherche.getText().toString();

                //Enregistrement du nom sur l'appareil
                SharedPreferences s = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editeur = s.edit();
                editeur.putString(MainActivity.NOM, nom);
                editeur.commit();


                //On ouvre une nouvelle activité
                Intent openMainAct = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(openMainAct);
                finish();
            }
        });


    }
    public void affichage(LinkedList<Plante> plante){
        Log.d("taille", String.valueOf(plante.size()));
        elemList.clear();
        for (int i =0; i<20; i++){
            if (!plante.isEmpty()){
                Log.d("nomPlante", plante.getFirst().nom);
                elemList.add(plante.pop().nom);
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }

}

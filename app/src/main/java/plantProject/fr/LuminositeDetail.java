package plantProject.fr;

import android.os.Bundle;
import android.widget.TextView;

public class LuminositeDetail extends NavDrawerActivity {

    private TextView texte;
    private TextView message;
    private TextView valeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_luminosite_detail);


        texte = findViewById(R.id.bodyLum);

        texte.setText(String.format(getString(R.string.lumInfo1), MainActivity.nom, MainActivity.plante.luminositeMoins, MainActivity.plante.luminositePlus, MainActivity.luminosite));


        message = findViewById(R.id.messageLum);
        valeur = findViewById(R.id.valeurLum);

        if (MainActivity.luminosite < MainActivity.plante.luminositeMoins){
            message.setText("Luminosité trop faible");
        }

        else if (MainActivity.luminosite > MainActivity.plante.luminositePlus){
            message.setText("Luminosité trop forte");
        }

        else{
            message.setText("Tout est OK");
        }

        valeur.setText(String.valueOf(MainActivity.luminosite) + " lux");
    }
}

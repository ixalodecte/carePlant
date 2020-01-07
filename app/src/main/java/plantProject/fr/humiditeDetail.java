package plantProject.fr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class humiditeDetail extends AppCompatActivity {

    private TextView texte;
    private TextView message;
    private TextView valeur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidite_detail);


        texte = findViewById(R.id.bodyHum);

        texte.setText(String.format(getString(R.string.humInfo1), MainActivity.nom, MainActivity.plante.humiditeMoins, MainActivity.plante.humiditePlus, MainActivity.humidite));

        message = findViewById(R.id.messageHum);
        valeur = findViewById(R.id.valeurHum);

        if (MainActivity.humidite < MainActivity.plante.humiditeMoins){
            message.setText("Humidité trop faible");
        }

        else if (MainActivity.humidite > MainActivity.plante.humiditePlus){
            message.setText("Humidité trop forte");
        }

        else{
            message.setText("Tout est OK");
        }

        valeur.setText(String.valueOf(MainActivity.humidite) + " %");
    }
}

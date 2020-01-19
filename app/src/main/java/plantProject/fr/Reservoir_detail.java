package plantProject.fr;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Reservoir_detail extends NavDrawerActivity {

    private ImageView image;
    private TextView message;
    private TextView valeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_reservoir_detail);

        message = findViewById(R.id.messageNiveau);
        valeur = findViewById(R.id.valeurNiveau);
        image = findViewById(R.id.imageReservoir);

        valeur.setText(MainActivity.niveau + " %");

        if(MainActivity.niveau > 50){
            image.setImageResource(R.drawable.reservoir_100);
            message.setText("Le réservoir est plein");
        }
        else if (MainActivity.niveau < 5){
            image.setImageResource(R.drawable.reservoir_0);
            message.setText("Le réservoir est vide");
        }
        else if (MainActivity.niveau < 25){
            image.setImageResource(R.drawable.reservoir_25);
            message.setText("Le réservoir est presque vide");
        }
        else {
            image.setImageResource(R.drawable.reservoir_50);
            message.setText("Le réservoir est à moitié rempli");}
    }
}

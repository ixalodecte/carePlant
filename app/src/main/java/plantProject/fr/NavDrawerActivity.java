package plantProject.fr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    public String[] layers;
    public DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View nav;
        Button temp;
        Button hum;
        Button search;






        setContentView(R.layout.nav_drawer);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);


        fab.hide(); // in place of visible
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        this.drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setNavigationViewListener();


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setContent(int r){
        RelativeLayout placeHolder = findViewById(R.id.lecontenu);
        getLayoutInflater().inflate(r, placeHolder);

    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_search: {
                Intent openSearch = new Intent(getApplicationContext(), recherchePlante.class);
                startActivity(openSearch);
                break;
            }
            case R.id.nav_hum:{
                Intent openHum = new Intent(getApplicationContext(), humiditeDetail.class);
                startActivity(openHum);
                break;
            }

            case R.id.nav_temp:{
                Intent openTemp = new Intent(getApplicationContext(), temperatureDetail.class);
                startActivity(openTemp);
                break;
            }
            case R.id.nav_reservoir:{
                Intent openreservoir = new Intent(getApplicationContext(), Reservoir_detail.class);
                startActivity(openreservoir);
                break;
            }
            case R.id.nav_home:{
                Intent openMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(openMain);
                break;
            }

        }
        drawer.closeDrawer(GravityCompat.START);

        //close navigation drawer

        return true;
    }
}

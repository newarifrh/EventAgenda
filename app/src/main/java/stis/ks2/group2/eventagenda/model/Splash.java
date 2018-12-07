package stis.ks2.group2.eventagenda.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import stis.ks2.group2.eventagenda.activities.BerandaActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, BerandaActivity.class);
        startActivity(intent);
        finish();
    }
}
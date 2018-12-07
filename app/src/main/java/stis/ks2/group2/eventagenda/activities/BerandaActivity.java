package stis.ks2.group2.eventagenda.activities;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import stis.ks2.group2.eventagenda.Fragment.LoginFragment;
import stis.ks2.group2.eventagenda.Fragment.EditProfilFragment;
import stis.ks2.group2.eventagenda.Fragment.EventkuFragment;
import stis.ks2.group2.eventagenda.Fragment.BerandaFragment;
import stis.ks2.group2.eventagenda.Fragment.ManageEventFragment;
import stis.ks2.group2.eventagenda.Fragment.SetupFragment;
import stis.ks2.group2.eventagenda.Fragment.UserGuideFragment;
import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.model.SessionManager;


public class BerandaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SessionManager sessionManager;
    public static NavigationView navigationView;
    public ImageView imgFoto;
    public TextView textNama, textEmail;
    public String stringNama, stringEmail, stringKode;
    public Toolbar toolbar;
    public static MenuItem add;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        stringNama = user.get(sessionManager.NAME);
        stringEmail = user.get(sessionManager.NIM);
        stringKode = user.get(sessionManager.KODE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        textNama = navigationView.getHeaderView(0).findViewById(R.id.nav_Nama);
        textEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_email);
        imgFoto = navigationView.getHeaderView(0).findViewById(R.id.nav_img);


        if (stringEmail == null) {
            textEmail.setText(null);
        } else {
            stringEmail = stringEmail + "@stis.ac.id";
            textEmail.setText(stringEmail);
        }
        textNama.setText(stringNama);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new BerandaFragment()).commit();
        hideitem();

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void hideitem() {
        Menu nav_Menu = navigationView.getMenu();

        if (!sessionManager.isLoggin()) {
            //Tidak ada session
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_eventku).setVisible(false);
            nav_Menu.findItem(R.id.nav_profil).setVisible(false);
            nav_Menu.findItem(R.id.nav_addEvent).setVisible(false);
            nav_Menu.findItem(R.id.nav_manageEvent).setVisible(false);
            imgFoto.setVisibility(View.INVISIBLE);
        } else {
            //Ada session
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_eventku).setVisible(true);
            nav_Menu.findItem(R.id.nav_profil).setVisible(true);
            imgFoto.setVisibility(View.VISIBLE);
            if (stringKode.equals("1")) {
                nav_Menu.findItem(R.id.nav_addEvent).setVisible(true);
                nav_Menu.findItem(R.id.nav_manageEvent).setVisible(true);
            } else {
                nav_Menu.findItem(R.id.nav_addEvent).setVisible(false);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.beranda, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
     /*   int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_beranda) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new BerandaFragment()).commit();
        } else if (id == R.id.nav_login) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new LoginFragment()).commit();
        } else if (id == R.id.nav_profil) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new EditProfilFragment()).commit();
        } else if (id == R.id.nav_addEvent) {
            Intent i = new Intent(this, AddEventActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_setup) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SetupFragment()).commit();
        } else if (id == R.id.nav_manageEvent) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ManageEventFragment()).commit();
        }else if (id == R.id.nav_eventku) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new EventkuFragment()).commit();
        } else if (id == R.id.nav_guide) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new UserGuideFragment()).commit();
        }else if (id == R.id.nav_logout) {
            SessionManager sessionManager;
            sessionManager = new SessionManager(this);
            sessionManager.logout();

            Menu nav_Menu = navigationView.getMenu();

            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_eventku).setVisible(false);
            nav_Menu.findItem(R.id.nav_profil).setVisible(false);
            nav_Menu.findItem(R.id.nav_addEvent).setVisible(false);
            nav_Menu.findItem(R.id.nav_manageEvent).setVisible(false);

            textNama = navigationView.getHeaderView(0).findViewById(R.id.nav_Nama);
            textEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_email);
            imgFoto = navigationView.getHeaderView(0).findViewById(R.id.nav_img);


            textEmail.setText(null);
            textNama.setText(null);

            imgFoto.setVisibility(View.INVISIBLE);
            nav_Menu.findItem(R.id.nav_addEvent).setVisible(false);

            fragmentManager.beginTransaction().replace(R.id.content_frame, new BerandaFragment()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

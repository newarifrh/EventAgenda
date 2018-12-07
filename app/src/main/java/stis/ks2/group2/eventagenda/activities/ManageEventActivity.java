package stis.ks2.group2.eventagenda.activities;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.model.SessionManager;


public class ManageEventActivity extends AppCompatActivity {

    private String id, WEB_URL;
    private TextView textNama;
    private ImageView imgGambar;
    private TextView textDeskripsi;
    SessionManager sessionManager;
    private RequestOptions opsi;

    private String imgUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_event);
        opsi = new RequestOptions().centerCrop().placeholder(R.drawable.bnw).error(R.drawable.bnw);
        id = getIntent().getExtras().getString("event_id");
        sessionManager = new SessionManager(ManageEventActivity.this);
        WEB_URL = new SessionManager(ManageEventActivity.this).getUserDetail().get(SessionManager.SERVER);
        textNama = findViewById(R.id.textNama);
        imgGambar = findViewById(R.id.imgEvent);
        textDeskripsi = findViewById(R.id.textDeskripsi);
        jsonRequest();
    }

    private void jsonRequest() {
        JsonArrayRequest request = new JsonArrayRequest(WEB_URL + "/ppk/index.php/Agenda/event/id/" + id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try {
                    jsonObject = response.getJSONObject(0);
                    textNama.setText(jsonObject.getString("nama"));
                    Glide.with(ManageEventActivity.this).load(WEB_URL+jsonObject.getString("gambar")).apply(opsi).into(imgGambar);
                    textDeskripsi.setText(jsonObject.getString("deskripsi"));

                    imgUrl = WEB_URL+jsonObject.getString("gambar");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ManageEventActivity.this);
        requestQueue.add(request);
    }

    public void buttonListUser(View v) {
        Intent i = new Intent(ManageEventActivity.this, ListerUserActivity.class);
        i.putExtra("event_id", id);
        startActivity(i);

    }

    public void clickZoom(View v) {
        CustomDialog();
    }

    public void CustomDialog() {
        final Dialog myDialog = new Dialog(ManageEventActivity.this, R.style.Theme_AppCompat_DayNight_DarkActionBar);

        RequestOptions opsi = new RequestOptions().placeholder(R.drawable.bnw).error(R.drawable.bnw);
        myDialog.setContentView(R.layout.custom_dialog);

        ImageView imgFullscreen = myDialog.findViewById(R.id.imgFullscren);

        Glide.with(ManageEventActivity.this).load(imgUrl).apply(opsi).into(imgFullscreen);


        myDialog.show();




    }


}

package stis.ks2.group2.eventagenda.activities;


import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.AppCompatButton;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stis.ks2.group2.eventagenda.R;

import stis.ks2.group2.eventagenda.model.Pertanyaan;
import stis.ks2.group2.eventagenda.model.SessionManager;

public class EventActivity extends AppCompatActivity {

    private String id;
    private TextView textNama;
    private ImageView imgGambar;
    private TextView textDeskripsi;
    private List<Pertanyaan> listPertanyaan;
    public String cekPertanyaan;
    private Button buttonNext;
    SessionManager sessionManager;
    private String nim;
    private RequestOptions opsi;
    private String cek, WEB_URL;
    private List<String> eventKu;
    private Dialog myDialog;
    private ImageView imgFullscreen;
    private String imgUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        opsi = new RequestOptions().centerCrop().placeholder(R.drawable.bnw).error(R.drawable.bnw);
        setContentView(R.layout.activity_event);
        buttonNext = findViewById(R.id.buttonNext);
        id = getIntent().getExtras().getString("event_id");
        sessionManager = new SessionManager(EventActivity.this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        listPertanyaan = new ArrayList<>();
        nim = user.get(sessionManager.NIM);
        WEB_URL = new SessionManager(EventActivity.this).getUserDetail().get(SessionManager.SERVER);

        cek = "";


        if (!(nim == null)) {
            String json = user.get(sessionManager.EVENTKU);
            Type type = new TypeToken<List<String>>() {
            }.getType();
            eventKu = gson.fromJson(json, type);
            for (int i = 0; i < eventKu.size(); i++) {
                if (eventKu.get(i).equals(id)) {
                    cek = "ada";
                    break;
                }
            }
        }
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
                    for (int i = 0; i < response.length(); i++) {

                        jsonObject = response.getJSONObject(i);
                        Pertanyaan pertanyaan = new Pertanyaan();
                        if (jsonObject.getString("pertanyaan").equals("null")) {
                            cekPertanyaan = "kosong";
                            buttonNext.setText("Daftar");
                        } else {
                            pertanyaan.setPertanyaan(jsonObject.getString("pertanyaan"));
                            pertanyaan.setIdPertanyaan(jsonObject.getString("idPertanyaan"));
                            listPertanyaan.add(pertanyaan);
                            cekPertanyaan = "ada";
                            buttonNext.setText("Next");
                        }
                        textNama.setText(jsonObject.getString("nama"));
                        Glide.with(EventActivity.this).load(WEB_URL+jsonObject.getString("gambar")).apply(opsi).into(imgGambar);
                        imgUrl = WEB_URL+jsonObject.getString("gambar");
                        textDeskripsi.setText(jsonObject.getString("deskripsi"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(EventActivity.this);
        requestQueue.add(request);
    }

    public void buttonNext(View view) {
        if (nim == null) {
            Toast.makeText(EventActivity.this, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            if (cek.equals("ada")) {
                Toast.makeText(EventActivity.this, "Anda sudah pernah mendaftar event ini", Toast.LENGTH_SHORT).show();
            } else {
                if (cekPertanyaan.equals("kosong")) {
                    DaftarEvent(nim, id);
                    Intent intent = new Intent(EventActivity.this, BerandaActivity.class);
                    startActivity(intent);
                } else {
                    Intent i = new Intent(this, PertanyaanActivity.class);
                    i.putExtra("id", id);
                    startActivity(i);
                }
            }
        }
    }

    private void DaftarEvent(final String nim, final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL + "/ppk/index.php/Agenda/register",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        eventKu.add(id);
                        sessionManager.editEventKuSession(eventKu);
                        Toast.makeText(EventActivity.this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EventActivity.this, "Gagal mendaftar", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("nim", nim);
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void clickZoom(View v) {
        CustomDialog();
    }
    public void CustomDialog() {
        myDialog = new Dialog(EventActivity.this, R.style.Theme_AppCompat_DayNight_DarkActionBar);

        RequestOptions opsi = new RequestOptions().placeholder(R.drawable.bnw).error(R.drawable.bnw);
        myDialog.setContentView(R.layout.custom_dialog);

        imgFullscreen = myDialog.findViewById(R.id.imgFullscren);

        Glide.with(EventActivity.this).load(imgUrl).apply(opsi).into(imgFullscreen);

        myDialog.show();



    }

}

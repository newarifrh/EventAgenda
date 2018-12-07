package stis.ks2.group2.eventagenda.activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import stis.ks2.group2.eventagenda.adapters.RecyclerViewAdapterPertanyaan;
import stis.ks2.group2.eventagenda.model.Pertanyaan;
import stis.ks2.group2.eventagenda.model.SessionManager;


public class PertanyaanActivity extends AppCompatActivity {


    SessionManager sessionManager;

    private String id, nim, WEB_URL;
    private RecyclerView recyclerView;
    private List<Pertanyaan> lstPertanyaan;

    private RecyclerViewAdapterPertanyaan.MyViewHolder holder;

    private List<String> eventKu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertanyaan);
        id = getIntent().getExtras().getString("id");
        lstPertanyaan = new ArrayList<>();
        getSupportActionBar().setTitle("Jawab Pertanyaan Tambahan");


        Gson gson = new Gson();
        WEB_URL = new SessionManager(PertanyaanActivity.this).getUserDetail().get(SessionManager.SERVER);

        sessionManager = new SessionManager(PertanyaanActivity.this);
        sessionManager.checkLogin();

        Button buttonDaftar = findViewById(R.id.buttonDaftar);
        HashMap<String, String> user = sessionManager.getUserDetail();

        nim = user.get(sessionManager.NIM);
        String json = user.get(sessionManager.EVENTKU);
        Type type = new TypeToken<List<String>>() {
        }.getType();
        eventKu = gson.fromJson(json, type);

        recyclerView = findViewById(R.id.recyclerPertanyaan);
        jsonRequest();

        buttonDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jawaban, idPertanyaan;
                Thread[] pertanyaan = new Thread[recyclerView.getChildCount() + 1];

                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    holder = (RecyclerViewAdapterPertanyaan.MyViewHolder) recyclerView.findViewHolderForLayoutPosition(i);
                    idPertanyaan = lstPertanyaan.get(i).getIdPertanyaan();
                    jawaban = holder.inputText.getText().toString();
                    pertanyaan[i] = new Thread(new MyThread(nim, id, idPertanyaan, jawaban, String.valueOf(i + 1)));
                    pertanyaan[i].start();


                }
                pertanyaan[recyclerView.getChildCount()] = new Thread(new MyThread(nim, id, "null", "null", "1412"));
                pertanyaan[recyclerView.getChildCount()].start();
                Intent intent;
                intent = new Intent(PertanyaanActivity.this, BerandaActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }


    private class MyThread extends Thread {

        private String nim, id, idPertanyaan, jawaban, ic;

        MyThread(String nim, String id, String idPertanyaan, String jawaban, String ic) {
            this.nim = nim;
            this.id = id;
            this.idPertanyaan = idPertanyaan;
            this.jawaban = jawaban;
            this.ic = ic;
        }

        @Override
        public void run() {
            if (jawaban == "null") {
                DaftarEvent(nim, id);
            } else {
                TambahJawaban(idPertanyaan, nim, jawaban, ic);
            }
        }

    }

    private void setupRecyclerView(List<Pertanyaan> lstPertanyaan) {
        RecyclerViewAdapterPertanyaan myadapter = new RecyclerViewAdapterPertanyaan(this, lstPertanyaan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
    }

    private void DaftarEvent(final String nim, final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL + "/ppk/index.php/Agenda/register",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        eventKu.add(id);
                        sessionManager.editEventKuSession(eventKu);
                        Toast.makeText(PertanyaanActivity.this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PertanyaanActivity.this, "Gagal mendaftar", Toast.LENGTH_SHORT).show();
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

    private void TambahJawaban(final String idPertanyaan, final String nim, final String jawaban, final String ic) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL + "/ppk/index.php/Agenda/addJawaban",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*try {
                            JSONArray jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PertanyaanActivity.this, "Jawaban ke-" + ic + " gagal terkirim", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idPertanyaan", idPertanyaan);
                params.put("nim", nim);
                params.put("jawaban", jawaban);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void jsonRequest() {


        JsonArrayRequest request = new JsonArrayRequest(WEB_URL + "/ppk/index.php/Agenda/event/id/" + id, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Pertanyaan pertanyaan = new Pertanyaan();

                        if (!(jsonObject.getString("pertanyaan") == "null")) {

                            pertanyaan.setIdPertanyaan(jsonObject.getString("idPertanyaan"));

                            pertanyaan.setPertanyaan(jsonObject.getString("pertanyaan"));

                            lstPertanyaan.add(pertanyaan);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    setupRecyclerView(lstPertanyaan);
                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(PertanyaanActivity.this);
        requestQueue.add(request);
    }

}

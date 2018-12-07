package stis.ks2.group2.eventagenda.activities;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import stis.ks2.group2.eventagenda.model.Events;
import stis.ks2.group2.eventagenda.model.Pertanyaan;
import stis.ks2.group2.eventagenda.model.SessionManager;

public class InformasiPendaftarActivity extends AppCompatActivity {

    private String nama, kelas, nim, id, WEB_URL;
    SessionManager sessionManager;
    TextView tvNama, tvNim, tvKelas, tvQnA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_informasi_activity);


        nama = getIntent().getExtras().getString("nama");
        nim = getIntent().getExtras().getString("nim");
        kelas = getIntent().getExtras().getString("kelas");
        id = getIntent().getExtras().getString("id");


        tvNama = findViewById(R.id.nimUser);
        tvNim = findViewById(R.id.namaUser);
        tvKelas = findViewById(R.id.kelasUser);
        tvQnA = findViewById(R.id.listQnA);

        sessionManager = new SessionManager(InformasiPendaftarActivity.this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        WEB_URL = user.get(sessionManager.SERVER);

        tvNama.setText(nama);
        tvNim.setText(nim);
        tvKelas.setText(kelas);

        jsonRequest(id, nim);


    }

    private void jsonRequest(final String data, final String nim) {
        JsonArrayRequest request = new JsonArrayRequest(WEB_URL + "/ppk/index.php/Agenda/QNA/id/" + data + "/nim/" + nim, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                String QnA = "";


                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        QnA = QnA + String.valueOf(i+1) + ". "+ jsonObject.getString("pertanyaan") + "\n";
                        QnA = QnA + "Jawab : " + jsonObject.getString("jawaban") + "\n\n";

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tvQnA.setText(QnA);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InformasiPendaftarActivity.this, "Tidak bisa menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(InformasiPendaftarActivity.this);
        requestQueue.add(request);
    }


}

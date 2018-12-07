package stis.ks2.group2.eventagenda.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.model.SessionManager;

public class PertanyaanTambahanActivity extends AppCompatActivity {
    private int margin;
    private LinearLayout layoutTambahPertanyaan;
    private List<TextInputEditText> allEdit;
    private String[] dataPertanyaan;
    private String idEvent, WEB_URL;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertanyaan_tambahan);
        getSupportActionBar().setTitle("Buat Pertanyaan Tambahan");

        allEdit = new ArrayList<>();
        sessionManager = new SessionManager(PertanyaanTambahanActivity.this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        WEB_URL = user.get(sessionManager.SERVER);
        idEvent = getIntent().getExtras().getString("idEvent");
        Button buttonTambah = findViewById(R.id.tambahPertanyaan);
        margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        layoutTambahPertanyaan = findViewById(R.id.layoutTambahPertanyaan);


        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout inputLayout = new TextInputLayout(PertanyaanTambahanActivity.this);
                TextInputEditText inputEditText = new TextInputEditText(PertanyaanTambahanActivity.this);
                allEdit.add(inputEditText);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(margin, margin, margin, margin);
                layoutTambahPertanyaan.addView(inputLayout, params);
                inputLayout.setHint("Masukkan Pertanyaan");
                inputLayout.addView(inputEditText);

            }
        });
    }


    public void kirimPertanyaan(View view) {
        dataPertanyaan = new String[(allEdit.size())];
        Thread[] pertanyaan = new Thread[allEdit.size()];

        for (int i = 0; i < allEdit.size(); i++) {
            dataPertanyaan[i] = allEdit.get(i).getText().toString();
        }

        for (int i = 0; i < dataPertanyaan.length; i++) {
            pertanyaan[i] = new Thread(new MyThread(idEvent, dataPertanyaan[i]));
            pertanyaan[i].start();
        }

    }

    private class MyThread extends Thread {

        private String idEvent, pertanyaan;

        MyThread(String idEvent, String pertanyaan) {
            this.idEvent = idEvent;
            this.pertanyaan = pertanyaan;
        }

        @Override
        public void run() {
            KirimPertanyaan(idEvent, pertanyaan);
        }

    }

    private void KirimPertanyaan(final String idEvent, final String pertanyaan) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL + "/ppk/index.php/Agenda/addPertanyaan",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PertanyaanTambahanActivity.this, "Berhasil menambahkan pertanyaan", Toast.LENGTH_SHORT).show();
                        Intent intent = new  Intent(PertanyaanTambahanActivity.this, BerandaActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PertanyaanTambahanActivity.this, "Gagal menambahkan pertanyaan", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idEvent", idEvent);
                params.put("pertanyaan", pertanyaan);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}

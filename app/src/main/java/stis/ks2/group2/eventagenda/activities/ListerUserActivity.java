package stis.ks2.group2.eventagenda.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.adapters.RecyclerViewAdapterEventKu;
import stis.ks2.group2.eventagenda.adapters.RecyclerViewAdapterUser;
import stis.ks2.group2.eventagenda.model.Events;
import stis.ks2.group2.eventagenda.model.SessionManager;
import stis.ks2.group2.eventagenda.model.Users;

public class ListerUserActivity extends AppCompatActivity {

    private String id, WEB_URL;
    SessionManager sessionManager;
    private RequestOptions opsi;
    private RecyclerView recyclerView;
    private List<Users> lstUsers;

    public ListerUserActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_user);
        id = getIntent().getExtras().getString("event_id");
        getSupportActionBar().setTitle("List User");

        sessionManager = new SessionManager(ListerUserActivity.this);
        WEB_URL = new SessionManager(ListerUserActivity.this).getUserDetail().get(SessionManager.SERVER);
        lstUsers = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewListUser);

        jsonRequest(id);

    }

    public void jsonRequest(final String idEvent) {
            JsonArrayRequest request = new JsonArrayRequest(WEB_URL + "/ppk/index.php/Agenda/listUserJoinEvent/idEvent/" + idEvent, new Response.Listener<JSONArray>() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Users users = new Users();
                        users.setNim(jsonObject.getString("nim"));
                        users.setNama(jsonObject.getString("nama"));
                        users.setKelas(jsonObject.getString("kelas"));
                        users.setId(idEvent);

                        lstUsers.add(users);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(lstUsers);
                setupRecyclerView(lstUsers);
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(ListerUserActivity.this);
        requestQueue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupRecyclerView(List<Users> lstUsers) {
        RecyclerViewAdapterUser myadapter = new RecyclerViewAdapterUser(ListerUserActivity.this, lstUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListerUserActivity.this));
        recyclerView.setAdapter(myadapter);
    }


}

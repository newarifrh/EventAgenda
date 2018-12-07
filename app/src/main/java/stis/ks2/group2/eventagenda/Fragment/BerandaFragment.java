package stis.ks2.group2.eventagenda.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.activities.BerandaActivity;
import stis.ks2.group2.eventagenda.adapters.RecyclerViewAdapterBeranda;
import stis.ks2.group2.eventagenda.model.Events;
import stis.ks2.group2.eventagenda.model.SessionManager;

public class BerandaFragment extends Fragment {
    private Handler mHandler = new Handler();
    private Editable key;
    SessionManager sessionManager;


    private List<Events> lstEvents;
    private RecyclerView recyclerView;

    public String WEB_URL;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.beranda_layout, container, false);
        EditText search = myView.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void afterTextChanged(Editable s) {

                mHandler.removeCallbacks(mFilterTask);
                mHandler.postDelayed(mFilterTask, 200);
                key = s;


            }
        });
        recyclerView = myView.findViewById(R.id.recyclerViewid);
        lstEvents = new ArrayList<>();
        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        String mNim = user.get(sessionManager.NIM);
        WEB_URL = user.get(sessionManager.SERVER);

        ((BerandaActivity)getActivity()).setActionBarTitle("Beranda");
        if (mNim == null) {
            Toast.makeText(getActivity(), "Sedang tidak login", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Login " + mNim, Toast.LENGTH_SHORT).show();
        }

        return myView;
    }

    Runnable mFilterTask = new Runnable() {

        @Override
        public void run() {
            recyclerView.setAdapter(null);
            lstEvents.clear();

            jsonRequest(key.toString());
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstEvents.clear();
        jsonRequest("");
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void jsonRequest(String data) {
        JsonArrayRequest request = new JsonArrayRequest(WEB_URL + "/ppk/index.php/Agenda/search/key/" + data, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Events events = new Events();
                        events.setId(jsonObject.getString("id"));
                        events.setNama(jsonObject.getString("nama"));
                        events.setGambar(WEB_URL+jsonObject.getString("gambar"));
                        lstEvents.add(events);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setupRecyclerView(lstEvents);
            }

        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Tidak bisa menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setupRecyclerView(List<Events> lstEvents) {
        RecyclerViewAdapterBeranda myadapter = new RecyclerViewAdapterBeranda(getContext(), lstEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myadapter);
    }
}

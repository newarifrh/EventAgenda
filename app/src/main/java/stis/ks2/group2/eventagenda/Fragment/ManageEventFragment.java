package stis.ks2.group2.eventagenda.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.activities.BerandaActivity;
import stis.ks2.group2.eventagenda.adapters.RecyclerViewAdapterBeranda;
import stis.ks2.group2.eventagenda.adapters.RecyclerViewAdapterManage;
import stis.ks2.group2.eventagenda.model.Events;
import stis.ks2.group2.eventagenda.model.SessionManager;

public class ManageEventFragment extends Fragment {
    SessionManager sessionManager;
    private List<Events> lstEvents;
    private RecyclerView recyclerView;

    public String WEB_URL, mNim;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.manage_event_layout, container, false);
        recyclerView = myView.findViewById(R.id.recyclerViewManage);
        lstEvents = new ArrayList<>();
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();
        mNim = user.get(sessionManager.NIM);
        WEB_URL = user.get(sessionManager.SERVER);
        ((BerandaActivity)getActivity()).setActionBarTitle("Kelola Event");


        return myView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstEvents.clear();
        jsonRequest();
    }

    private void jsonRequest() {
        JsonArrayRequest request = new JsonArrayRequest(WEB_URL + "/ppk/index.php/Agenda/manageEvent/nim/" + mNim, new Response.Listener<JSONArray>() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setupRecyclerView(List<Events> lstEvents) {
        RecyclerViewAdapterManage myadapter = new RecyclerViewAdapterManage(getContext(), lstEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myadapter);
    }
}

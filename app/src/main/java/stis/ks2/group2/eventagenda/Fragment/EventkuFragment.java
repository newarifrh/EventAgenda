package stis.ks2.group2.eventagenda.Fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import stis.ks2.group2.eventagenda.adapters.RecyclerViewAdapterEventKu;
import stis.ks2.group2.eventagenda.model.Events;
import stis.ks2.group2.eventagenda.model.SessionManager;

public class EventkuFragment extends Fragment {
    SessionManager sessionManager;
    public String mNim, WEB_URL;
    private List<Events> lstEvents;
    private RecyclerView recyclerView;

    View myView;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.eventku_layout, container, false);
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();
        ((BerandaActivity)getActivity()).setActionBarTitle("EventKu");

        mNim = user.get(sessionManager.NIM);
        WEB_URL = user.get(sessionManager.SERVER);


        if (!(mNim == null)) {
            lstEvents = new ArrayList<>();
            lstEvents.clear();
            jsonRequestEventku(mNim);
        }

        recyclerView = myView.findViewById(R.id.recyclerViewEventKu);

        return myView;

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void jsonRequestEventku(String inputNim) {


        JsonArrayRequest request = new JsonArrayRequest(WEB_URL + "/ppk/index.php/Agenda/eventku/nim/" + inputNim, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {


                JSONObject jsonObject;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Events events = new Events();
                        events.setId(jsonObject.getString("id"));
                        events.setNama(jsonObject.getString("nama"));
                        events.setGambar(WEB_URL + jsonObject.getString("gambar"));

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


            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupRecyclerView(List<Events> lstEvents) {
        RecyclerViewAdapterEventKu myadapter = new RecyclerViewAdapterEventKu(getContext(), lstEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myadapter);
    }
}

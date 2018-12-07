package stis.ks2.group2.eventagenda.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.activities.BerandaActivity;
import stis.ks2.group2.eventagenda.model.SessionManager;


public class EditProfilFragment extends Fragment {
    SessionManager sessionManager;

    public String mNim, mNama, mNoHP1, mNoHP2, mNoHP3;
    public TextInputEditText nama, noHP1, noHP2, noHP3;
    public TextView textNama;
    public Button btnSave, btnResetPass;
    public String newNo1, newNo2, newNo3, WEB_URL;


    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_profil_layout, container, false);

        WEB_URL = new SessionManager(getActivity()).getUserDetail().get(SessionManager.SERVER);
        sessionManager = new SessionManager(getActivity());
        ((BerandaActivity)getActivity()).setActionBarTitle("Edit Profil");
        HashMap<String, String> user = sessionManager.getUserDetail();

        mNim = user.get(sessionManager.NIM);

        mNama = user.get(sessionManager.NAME);
        mNoHP1 = user.get(sessionManager.NOHP1);
        mNoHP2 = user.get(sessionManager.NOHP2);
        mNoHP3 = user.get(sessionManager.NOHP3);

        if (mNoHP1.equalsIgnoreCase("null")) {
            mNoHP1 = "";
        }
        if (mNoHP2.equalsIgnoreCase("null")) {
            mNoHP2 = "";
        }
        if (mNoHP3.equalsIgnoreCase("null")) {
            mNoHP3 = "";
        }



        nama = myView.findViewById(R.id.inputNama);
        noHP1 = myView.findViewById(R.id.inputNoHP1);
        noHP2 = myView.findViewById(R.id.inputNoHP2);
        noHP3 = myView.findViewById(R.id.inputNoHP3);
        textNama = myView.findViewById(R.id.textNama);

        textNama.setText(mNama);
        nama.setText(mNama);

        noHP1.setText(mNoHP1);
        noHP3.setText(mNoHP3);

        noHP2.setText(mNoHP2);

        btnSave = myView.findViewById(R.id.buttonSave);
        btnResetPass = myView.findViewById(R.id.buttonResetPass);

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.content_frame, new ResetPassFragment()).commit();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNo1 = noHP1.getText().toString();
                newNo2 = noHP2.getText().toString();
                newNo3 = noHP3.getText().toString();

                UbahInformasi(mNim, newNo1, newNo2, newNo3);
            }
        });


        return myView;
    }


    private void UbahInformasi(final String nim, final String nohp1, final String nohp2, final String nohp3) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL + "/ppk/index.php/Agenda/changeinformasi",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
                            String status = jsonObject.getString("status");

                            if (status.equals("true")) {

                                sessionManager.editNoHPSession(nohp1, nohp2, nohp3);
                                Toast.makeText(getActivity(), "Berhasil memperbaharui informasi", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Gagal memperbaharui informasi", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Tidak bisa menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                params.put("nohp1", nohp1);
                params.put("nohp2", nohp2);
                params.put("nohp3", nohp3);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}

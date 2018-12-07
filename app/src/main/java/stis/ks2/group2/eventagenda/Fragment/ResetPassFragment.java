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


public class ResetPassFragment extends Fragment {
    SessionManager sessionManager;
    public String WEB_URL;
    public String mNim, mNama;
    public TextInputEditText nama, pwl, pwb1, pwb2;
    public TextView textNama;
    public Button btnResetPass;
    public String stringPwl, stringPwb1, stringPwb2;


    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.reset_pass_layout, container, false);


        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();

        mNim = user.get(sessionManager.NIM);

        mNama = user.get(sessionManager.NAME);
        WEB_URL = user.get(sessionManager.SERVER);
        ((BerandaActivity) getActivity()).setActionBarTitle("Ganti Password");

        textNama = myView.findViewById(R.id.textNama);
        pwl = myView.findViewById(R.id.inputPwl);
        pwb1 = myView.findViewById(R.id.inputPwb1);
        pwb2 = myView.findViewById(R.id.inputPwb2);
        textNama.setText(mNama);


        btnResetPass = myView.findViewById(R.id.buttonResetPass);

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringPwl = pwl.getText().toString();
                stringPwb1 = pwb1.getText().toString();
                stringPwb2 = pwb2.getText().toString();

                if (stringPwb1.equals(stringPwb2)) {
                    ResetPass(mNim, stringPwl, stringPwb1);
                } else {
                    Toast.makeText(getActivity(), "Password dan konfirmasi password tidak sama", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return myView;
    }


    private void ResetPass(final String nim, final String pwl, final String pwb) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL + "/ppk/index.php/Agenda/changepass",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
                            String status = jsonObject.getString("status");

                            if (status.equals("true")) {
                                Toast.makeText(getActivity(), "Berhasil mengganti password", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getFragmentManager();

                                fragmentManager.beginTransaction().replace(R.id.content_frame, new BerandaFragment()).commit();
                            } else {
                                Toast.makeText(getActivity(), "Password lama salah", Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                params.put("oldpw", pwl);
                params.put("newpw", pwb);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}

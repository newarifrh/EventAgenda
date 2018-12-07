package stis.ks2.group2.eventagenda.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.activities.BerandaActivity;
import stis.ks2.group2.eventagenda.model.SessionManager;

import static stis.ks2.group2.eventagenda.activities.BerandaActivity.navigationView;


public class LoginFragment extends Fragment {

    public TextInputEditText inputNim, inputPassword;
    public Button buttonLogin;
    public String mNim, mPassword, WEB_URL;

    public ImageView imgFoto;
    public TextView textNama, textEmail;


    SessionManager sessionManager;

    View myView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.login_layout, container, false);

        inputNim = myView.findViewById(R.id.inputNim);
        inputPassword = myView.findViewById(R.id.inputPassword);

        buttonLogin = myView.findViewById(R.id.buttonLogin);
        ((BerandaActivity)getActivity()).setActionBarTitle("Login");

        sessionManager = new SessionManager(getActivity());
        WEB_URL = new SessionManager(getActivity()).getUserDetail().get(SessionManager.SERVER);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNim = inputNim.getText().toString();
                mPassword = inputPassword.getText().toString();
                Login(mNim, mPassword);


            }
        });


        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void Login(final String nim, final String password) {

        String URL_LOGIN = WEB_URL + "/ppk/index.php/Agenda/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONArray jsonArray = new JSONArray(response);

                            JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
                            String status = jsonObject.getString("status");


                            if (status.equals("FALSE")) {
                                Toast.makeText(getActivity(), "password salah", Toast.LENGTH_SHORT).show();

                            } else {
                                List<String> eventKu = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject all = new JSONObject(jsonArray.get(i).toString());
                                    eventKu.add(all.getString("idEvent"));
                                }

                                String nim = jsonObject.getString("nim");
                                String nama = jsonObject.getString("nama");
                                String kelas = jsonObject.getString("kelas");
                                String kode = jsonObject.getString("kode");
                                String noHP1 = jsonObject.getString("noHP1");
                                String noHP2 = jsonObject.getString("noHP2");
                                String noHP3 = jsonObject.getString("noHP3");
                                String stringKode = jsonObject.getString("kode");
                                sessionManager.createSession(nim, nama, kelas, kode, noHP1, noHP2, noHP3, eventKu);
                                textNama = navigationView.getHeaderView(0).findViewById(R.id.nav_Nama);
                                textEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_email);
                                imgFoto = navigationView.getHeaderView(0).findViewById(R.id.nav_img);

                                if (nim == null) {
                                    textEmail.setText(null);
                                } else {
                                    nim = nim + "@stis.ac.id";
                                    textEmail.setText(nim);
                                }


                                textNama.setText(nama);

                                imgFoto.setVisibility(View.VISIBLE);


                                FragmentManager fragmentManager = getFragmentManager();

                                fragmentManager.beginTransaction().replace(R.id.content_frame, new BerandaFragment()).commit();


                                Menu nav_Menu = navigationView.getMenu();

                                nav_Menu.findItem(R.id.nav_eventku).setVisible(true);
                                nav_Menu.findItem(R.id.nav_profil).setVisible(true);
                                nav_Menu.findItem(R.id.nav_logout).setVisible(true);
                                nav_Menu.findItem(R.id.nav_login).setVisible(false);

                                if (stringKode.equals("1")) {
                                    nav_Menu.findItem(R.id.nav_addEvent).setVisible(true);
                                    nav_Menu.findItem(R.id.nav_manageEvent).setVisible(true);
                                } else {
                                    nav_Menu.findItem(R.id.nav_addEvent).setVisible(false);
                                }
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
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


}

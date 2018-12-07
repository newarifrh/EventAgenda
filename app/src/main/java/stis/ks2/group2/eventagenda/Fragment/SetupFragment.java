package stis.ks2.group2.eventagenda.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.activities.BerandaActivity;
import stis.ks2.group2.eventagenda.model.SessionManager;

public class SetupFragment extends Fragment {

    public AppCompatButton buttonSetup, buttonDefault;
    public TextInputEditText editTextSetup;
    private FragmentManager fragmentManager;
    SessionManager sessionManager;

    View myView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.setup_layout, container, false);
        buttonSetup = myView.findViewById(R.id.buttonSetup);
        buttonDefault = myView.findViewById(R.id.buttonDefault);
        editTextSetup = myView.findViewById(R.id.editTextSetup);
        sessionManager = new SessionManager(getActivity());
        fragmentManager = getFragmentManager();
        ((BerandaActivity)getActivity()).setActionBarTitle("Setup Server");

        buttonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSetup.setText("http://192.168.43.61");
            }
        });

        buttonSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.editServerSession(editTextSetup.getText().toString());
                Toast.makeText(getActivity(), "Setup telah dibuat", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new BerandaFragment()).commit();

            }
        });


        return myView;
    }


}

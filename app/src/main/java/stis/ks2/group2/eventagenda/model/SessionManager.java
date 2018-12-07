package stis.ks2.group2.eventagenda.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;
    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String NIM = "NIM";
    public static final String NAME = "NAME";
    public static final String KELAS = "KELAS";
    public static final String KODE = "KODE";
    public static final String NOHP1 = "NOHP1";
    public static final String NOHP2 = "NOHP2";
    public static final String NOHP3 = "NOHP3";
    public static final String EVENTKU = "EVENTKU";
    public static final String SERVER = "SERVER";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }


    public void createSession(String nim, String nama, String kelas, String kode, String noHP1,
                              String noHP2, String noHP3, List<String> eventKu) {
        Gson gson = new Gson();
        String json = gson.toJson(eventKu);

        editor.putBoolean(LOGIN, true);
        editor.putString(NIM, nim);
        editor.putString(NAME, nama);
        editor.putString(KELAS, kelas);
        editor.putString(KODE, kode);
        editor.putString(NOHP1, noHP1);
        editor.putString(NOHP2, noHP2);
        editor.putString(NOHP3, noHP3);
        editor.putString(EVENTKU, json);
        editor.apply();
    }

    public void editServerSession(String server){
        editor.remove(SERVER);
        editor.putString(SERVER, server);
        editor.apply();
    }

    public void editNoHPSession(String noHP1, String noHP2, String noHP3) {
        editor.remove(NOHP1);
        editor.remove(NOHP2);
        editor.remove(NOHP3);
        editor.putString(NOHP1, noHP1);
        editor.putString(NOHP2, noHP2);
        editor.putString(NOHP3, noHP3);
        editor.apply();
    }

    public void editEventKuSession(List<String> eventKu) {
        Gson gson = new Gson();
        String json = gson.toJson(eventKu);
        editor.remove(EVENTKU);
        editor.putString(EVENTKU, json);
        editor.apply();
    }

    public boolean isLoggin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLoggin()) {
        }
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NIM, sharedPreferences.getString(NIM, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(KELAS, sharedPreferences.getString(KELAS, null));
        user.put(KODE, sharedPreferences.getString(KODE, null));
        user.put(NOHP1, sharedPreferences.getString(NOHP1, null));
        user.put(NOHP2, sharedPreferences.getString(NOHP2, null));
        user.put(NOHP3, sharedPreferences.getString(NOHP3, null));
        user.put(EVENTKU, sharedPreferences.getString(EVENTKU, null));
        user.put(SERVER, sharedPreferences.getString(SERVER, null));

        return user;
    }

    public void logout() {
        editor.remove(LOGIN);
        editor.remove(NIM);
        editor.remove(NAME);
        editor.remove(KELAS);
        editor.remove(KODE);
        editor.remove(NOHP1);
        editor.remove(NOHP2);
        editor.remove(NOHP3);
        editor.remove(EVENTKU);
        editor.commit();
    }

}

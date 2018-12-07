package stis.ks2.group2.eventagenda.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import stis.ks2.group2.eventagenda.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import stis.ks2.group2.eventagenda.model.RequestHandlerUpload;
import stis.ks2.group2.eventagenda.model.SessionManager;


public class AddEventActivity extends AppCompatActivity {


    public static final String UPLOAD_KEY = "img";
    private String id, nim, idEvent;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateStart, dateEnd;
    private TimePickerDialog.OnTimeSetListener timeStart;
    private TextInputEditText inputDateStart, inputDateEnd, inputTimeStart, inputEvent, inputLokasi, inputDeskripsi;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView imgPosterEvent;
    SessionManager sessionManager;
    private String WEB_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setTitle("Buat Event");
        imgPosterEvent = findViewById(R.id.imgPosterEvent);
        myCalendar = Calendar.getInstance();
        sessionManager = new SessionManager(AddEventActivity.this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        nim = user.get(sessionManager.NIM);
        WEB_URL = new SessionManager(AddEventActivity.this).getUserDetail().get(SessionManager.SERVER);


        dateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateStart();

            }
        };

        dateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateEnd();

            }
        };

        timeStart = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTimeStart();
            }
        };

        inputDateStart = findViewById(R.id.inputDateStart);
        inputDateEnd = findViewById(R.id.inputDateEnd);
        inputTimeStart = findViewById(R.id.inputTimeStart);
        inputEvent = findViewById(R.id.inputEvent);
        inputLokasi = findViewById(R.id.inputLokasi);
        inputDeskripsi = findViewById(R.id.inputDeskripsi);

        inputDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEventActivity.this, R.style.DialogTheme, dateStart, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        inputDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEventActivity.this, R.style.DialogTheme, dateEnd, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        inputTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddEventActivity.this, R.style.DialogTheme, timeStart, myCalendar
                        .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
            }
        });


    }

    public void klikTambahEvent(View v) {
        String nama, cp, tanggalMulai, jamMulai, tanggalSelesai, lokasi, deskripsi, gambar;
        nama = inputEvent.getText().toString();
        cp = nim;
        tanggalMulai = inputDateStart.getText().toString();
        jamMulai = inputTimeStart.getText().toString();
        tanggalSelesai = inputDateEnd.getText().toString();
        lokasi = inputLokasi.getText().toString();
        deskripsi = inputDeskripsi.getText().toString();
        gambar = id;

        TambahEvent(nama, cp, tanggalMulai, jamMulai, tanggalSelesai, lokasi, deskripsi, gambar);

        DialogPertanyaan();
    }

    private void DialogPertanyaan() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Ingin menambahkan \npertanyaan?");
        alertDialogBuilder
                .setMessage("Pilih Ya untuk menambahkan pertanyaan")
                .setIcon(R.drawable.ic_info)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AddEventActivity.this, PertanyaanTambahanActivity.class);
                        intent.putExtra("idEvent", idEvent);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddEventActivity.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void buttonPilihImg(View v) {
        showFileChooser();
    }

    public void buttonUpload(View v) {
        uploadImage();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgPosterEvent.setImageBitmap(bitmap);
                imgPosterEvent.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(AddEventActivity.this, "gagal", Toast.LENGTH_SHORT).show();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {
            private String data;
            private ProgressDialog loading;
            private RequestHandlerUpload rh = new RequestHandlerUpload();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddEventActivity.this, "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (data == "kosong") {
                    Toast.makeText(getApplicationContext(), "Silahkan pilih foto dahulu", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject jsonObject;
                    JSONArray response;
                    try {
                        response = new JSONArray(s);
                        jsonObject = response.getJSONObject(0);
                        id = jsonObject.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Berhasil mengunggah foto", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];

                if (bitmap == null) {
                    data = "kosong";
                    return data;
                } else {

                    String uploadImage = getStringImage(bitmap);

                    HashMap<String, String> data = new HashMap<>();

                    data.put(UPLOAD_KEY, uploadImage);

                    return rh.sendPostRequest(WEB_URL + "/ppk/index.php/Agenda/sendImg", data);
                }
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void updateDateStart() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        inputDateStart.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateDateEnd() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        inputDateEnd.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeStart() {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        inputTimeStart.setText(sdf.format(myCalendar.getTime()));
    }

    private void TambahEvent(final String nama, final String cp, final String tanggalMulai,
                             final String jamMulai, final String tanggalSelesai, final String lokasi,
                             final String deskripsi, final String gambar) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL + "/ppk/index.php/Agenda/event",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        idEvent = response.replace("\"", "");
                        Toast.makeText(AddEventActivity.this, "Berhasil membuat Event", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddEventActivity.this, "Gagal mendaftar", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("cp", cp);
                params.put("tanggalMulai", tanggalMulai);
                params.put("jamMulai", jamMulai);
                params.put("tanggalSelesai", tanggalSelesai);
                params.put("lokasi", lokasi);
                params.put("deskripsi", deskripsi);
                params.put("gambar", gambar);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddEventActivity.this);
        requestQueue.add(stringRequest);
    }


}

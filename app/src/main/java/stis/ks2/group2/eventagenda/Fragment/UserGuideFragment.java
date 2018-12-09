package stis.ks2.group2.eventagenda.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.activities.BerandaActivity;
import stis.ks2.group2.eventagenda.adapters.ExpandableRecyclerViewAdapterGuide;
import stis.ks2.group2.eventagenda.model.BodyGuide;
import stis.ks2.group2.eventagenda.model.GuideCreator;
import stis.ks2.group2.eventagenda.model.HeadGuide;
import stis.ks2.group2.eventagenda.model.SessionManager;

public class UserGuideFragment extends Fragment {


    RecyclerView recyclerView;
    View myView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((ExpandableRecyclerViewAdapterGuide)recyclerView.getAdapter()).onSaveInstanceState(outState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.user_guide_layout, container, false);

        recyclerView = myView.findViewById(R.id.userguideRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ExpandableRecyclerViewAdapterGuide adapter = new ExpandableRecyclerViewAdapterGuide(getActivity(),initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(adapter);


        return myView;
    }

    private List<ParentObject> initData(){
        GuideCreator guideCreator = GuideCreator.get(getActivity());
        List<HeadGuide> head = guideCreator.getAll();
        List<ParentObject> parentObjects = new ArrayList<>();
        List<BodyGuide> isi = new ArrayList<>();


        BodyGuide a = new BodyGuide("1. Pilih menu yang berada di pojok kiri atas  \n" +
                "2. Pilih menu login\n"+
                "3. Masukan username dan password yang telah disediakan, setelah mengisi tekan enter/submit\n" +
                "4. Akun anda telah login sebagai member\n", R.mipmap.login);
        List<Object> childList = new ArrayList<>();
        childList.add(a);
        head.get(0).setChildObjectList(childList);
        parentObjects.add(head.get(0));


        BodyGuide b = new BodyGuide("1. Tekan box pencarian yang berada di beranda\n" +
                "2. Masukan nama event yang akan dicari\n"+ "3. Hasil akan ditampilkan di beranda aplikasi", R.mipmap.search);
        List<Object> childList2 = new ArrayList<>();
        childList2.add(b);
        head.get(1).setChildObjectList(childList2);
        parentObjects.add(head.get(1));


        BodyGuide c = new BodyGuide("1. Login terlebih dahulu\n" +
                "2. Pilih event yang ingin diikuti\n" +
                "3. Pada laman event pilih daftar/next\n" + "4. Jika tombol bertuliskan next maka anda harus menjawab pertanyaan yang diajukan pada laman selanjutnya terlebih dahulu untuk mendaftar\n" + "5. Akun anda telah terdaftar di event", R.mipmap.daftar);
        List<Object> childList3 = new ArrayList<>();
        childList3.add(c);
        head.get(2).setChildObjectList(childList3);
        parentObjects.add(head.get(2));


        BodyGuide d = new BodyGuide("1. Login terlebih dahulu\n" +
                "2. Pilih menu\n" +
                "3. Pilih eventku\n" +
                "4. Laman akan menampilkan event yang telah terdaftar\n", R.mipmap.eventku);
        List<Object> childList4 = new ArrayList<>();
        childList4.add(d);
        head.get(3).setChildObjectList(childList4);
        parentObjects.add(head.get(3));


        BodyGuide e = new BodyGuide("1. Login terlebih dahulu\n" +
                "2. Pilih menu yang berada di pojok kiri atas\n" +
                "3. Pilih edit profil\n" +
                "4. Tekah ubah password\n" +
                "5. Masukan password lama dan password baru, setelah selesai tekan submit\n" +
                "6. Password akun anda telah berubah", R.mipmap.password);
        List<Object> childList5 = new ArrayList<>();
        childList5.add(e);
        head.get(4).setChildObjectList(childList5);
        parentObjects.add(head.get(4));


        BodyGuide f = new BodyGuide("1. Login terlebih dahulu\n" +
                "2. Pilih menu yang berada di pojok kiri atas\n" +
                "3. Pilih edit profil\n" +
                "4. Ubah nomor yang ingin diganti, setelah selesai tekan submit\n" +
                "5. Nomor telepon akun anda telah berganti", R.mipmap.nomortelepon);
        List<Object> childList6 = new ArrayList<>();
        childList6.add(f);
        head.get(5).setChildObjectList(childList6);
        parentObjects.add(head.get(5));


        BodyGuide g = new BodyGuide("1. Login sebagai event handler\n" +
                "2. Buka buat event yang berada pada menu\n" +
                "3. Isi informasi event\n" +
                "4. Upload poto poster dari event\n" +
                "5. Pilih tambah event\n" +
                "6. Pilih ya jika ingin mengajukan pertanyaan atau pilih tidak jika tidak ingin mengajukan pertanyaan", R.mipmap.tambahevent);
        List<Object> childList7 = new ArrayList<>();
        childList7.add(g);
        head.get(6).setChildObjectList(childList7);
        parentObjects.add(head.get(6));


        BodyGuide h = new BodyGuide("1. Login sebagai event handler\n" +
                "2. Pilih kelola event\n" +
                "3. Pilih event yang ingin dilihat\n" +
                "4. Pada laman event tekan list user\n"+
                "5. Laman akan menampilkan list user dari event", R.mipmap.pendaftar);
        List<Object> childList8 = new ArrayList<>();
        childList8.add(h);
        head.get(7).setChildObjectList(childList8);
        parentObjects.add(head.get(7));


        BodyGuide i = new BodyGuide("1. Login sebagai event handler\n" +
                "2. Pilih kelola event\n" +
                "3. Pilih event yang ingin dilihat\n" +
                "4. Pada laman event tekan list user\n"+
                "5. Pilih salah satu pendaftar untuk melihat jawabannya" +
                "6. Laman akan menampilkan jawaban pendaftar tersebut", R.mipmap.jawaban);
        List<Object> childList9 = new ArrayList<>();
        childList9.add(i);
        head.get(8).setChildObjectList(childList9);
        parentObjects.add(head.get(8));

        return parentObjects;
    }


}

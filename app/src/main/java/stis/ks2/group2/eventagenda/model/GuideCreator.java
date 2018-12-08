package stis.ks2.group2.eventagenda.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class GuideCreator {

    static GuideCreator guide;
    List<HeadGuide> headlist;
    ArrayList<HeadGuide> judul;

    private GuideCreator(Context context) {
        headlist = new ArrayList<>();
        HeadGuide a = new HeadGuide("1. Login");
        headlist.add(a);
        HeadGuide b = new HeadGuide("2. Mencari event");
        headlist.add(b);
        HeadGuide c = new HeadGuide("3. Mendaftar event");
        headlist.add(c);
        HeadGuide d = new HeadGuide("4. Melihat event yang terdaftar");
        headlist.add(d);
        HeadGuide e = new HeadGuide("5. Mengubah password");
        headlist.add(e);
        HeadGuide f = new HeadGuide("6. Mengubah nomor telepon");
        headlist.add(f);
        HeadGuide g = new HeadGuide("7. Menambahkan event");
        headlist.add(g);
        HeadGuide h = new HeadGuide("8. Melihat pendaftar dalam event yang anda tambahkan");
        headlist.add(h);
        HeadGuide i = new HeadGuide("9. Melihat jawaban pendaftar dalam event yang anda tambahkan");
        headlist.add(i);


    }

    public static GuideCreator get(Context context) {
        if (guide == null) {
            guide = new GuideCreator(context);
        }
        return guide;
    }

    public List<HeadGuide> getAll() {
        return headlist;
    }
}

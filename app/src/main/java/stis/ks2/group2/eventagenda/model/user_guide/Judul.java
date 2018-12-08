package stis.ks2.group2.eventagenda.model.user_guide;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Judul extends ExpandableGroup<Isi> {
    public Judul(String title, List<Isi> items) {
        super(title, items);
    }

}

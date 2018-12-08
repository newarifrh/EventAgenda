package stis.ks2.group2.eventagenda.model.user_guide;

import android.os.Parcel;
import android.os.Parcelable;

public class Isi implements Parcelable {
    public final String nama;

    public Isi(String nama) {
        this.nama = nama;
    }

    protected Isi(Parcel in) {
        nama = in.readString();
    }

    public static final Creator<Isi> CREATOR = new Creator<Isi>() {
        @Override
        public Isi createFromParcel(Parcel in) {
            return new Isi(in);
        }

        @Override
        public Isi[] newArray(int size) {
            return new Isi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);

    }
}

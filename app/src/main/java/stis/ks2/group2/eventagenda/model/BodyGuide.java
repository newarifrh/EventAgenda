package stis.ks2.group2.eventagenda.model;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;

public class BodyGuide {

    private String text;
    private int gambar;

    public BodyGuide(String text, int gambar) {
        this.text = text;
        this.gambar = gambar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }
}

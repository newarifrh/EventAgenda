package stis.ks2.group2.eventagenda.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import stis.ks2.group2.eventagenda.R;

public class BodyGuideViewHolder extends ChildViewHolder {
    public TextView urutan;
    public ImageView gambar;

    public BodyGuideViewHolder(View itemView) {
        super(itemView);
        urutan = itemView.findViewById(R.id.urutanGuide);
        gambar = itemView.findViewById(R.id.gambarGuide);
    }
}

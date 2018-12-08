package stis.ks2.group2.eventagenda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.ViewHolder.BodyGuideViewHolder;
import stis.ks2.group2.eventagenda.ViewHolder.HeadGuideViewHolder;
import stis.ks2.group2.eventagenda.model.BodyGuide;
import stis.ks2.group2.eventagenda.model.HeadGuide;

public class ExpandableRecyclerViewAdapterGuide extends ExpandableRecyclerAdapter<HeadGuideViewHolder, BodyGuideViewHolder> {

    LayoutInflater inflater;
    private Context context;
    private RequestOptions opsi;

    public ExpandableRecyclerViewAdapterGuide(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
        this.context = context;
        opsi = new RequestOptions().centerCrop().placeholder(R.drawable.bnw).error(R.drawable.bnw);
    }

    @Override
    public HeadGuideViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.userguide_row_item, viewGroup, false);
        return new HeadGuideViewHolder(view);
    }

    @Override
    public BodyGuideViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.userguideisi_row_item, viewGroup, false);
        return new BodyGuideViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(HeadGuideViewHolder headGuideViewHolder, int i, Object o) {
        HeadGuide head = (HeadGuide)o;
        headGuideViewHolder.headTitle.setText(head.getTitle());
    }

    @Override
    public void onBindChildViewHolder(BodyGuideViewHolder bodyGuideViewHolder, int i, Object o) {
        BodyGuide body = (BodyGuide)o;
        bodyGuideViewHolder.urutan.setText(body.getText());
        bodyGuideViewHolder.gambar.setImageResource(body.getGambar());
    }
}

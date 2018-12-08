package stis.ks2.group2.eventagenda.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import stis.ks2.group2.eventagenda.R;

public class HeadGuideViewHolder extends ParentViewHolder {

    public TextView headTitle;

    public HeadGuideViewHolder(View itemView) {
        super(itemView);
        headTitle = itemView.findViewById(R.id.headTitle);
    }
}

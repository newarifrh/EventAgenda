package stis.ks2.group2.eventagenda.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import stis.ks2.group2.eventagenda.activities.EventActivity;
import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.model.Events;

public class RecyclerViewAdapterBeranda extends RecyclerView.Adapter<RecyclerViewAdapterBeranda.MyViewHolder> {
    private RequestOptions opsi;
    private Context mContext;
    private List<Events> mData;

    public RecyclerViewAdapterBeranda(Context mContext, List<Events> mData) {
        this.mContext = mContext;
        this.mData = mData;
        opsi = new RequestOptions().centerCrop().placeholder(R.drawable.bnw).error(R.drawable.bnw);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.events_row_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);


        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, EventActivity.class);
                i.putExtra("event_id", mData.get(viewHolder.getAdapterPosition()).getId());
                i.putExtra("event_nama", mData.get(viewHolder.getAdapterPosition()).getNama());
                i.putExtra("event_gambar", mData.get(viewHolder.getAdapterPosition()).getGambar());
                mContext.startActivity(i);
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_nama.setText(mData.get(position).getNama());
        Glide.with(mContext).load(mData.get(position).getGambar()).apply(opsi).into(holder.tv_img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama;
        ImageView tv_img;
        LinearLayout view_container;

        MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            tv_nama = itemView.findViewById(R.id.textNama);
            tv_img = itemView.findViewById(R.id.imgEvent);
        }
    }

}


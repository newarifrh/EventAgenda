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

import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.activities.EventActivity;
import stis.ks2.group2.eventagenda.activities.InformasiPendaftarActivity;
import stis.ks2.group2.eventagenda.model.Events;
import stis.ks2.group2.eventagenda.model.Users;

public class RecyclerViewAdapterUser extends RecyclerView.Adapter<RecyclerViewAdapterUser.MyViewHolder> {
    private Context mContext;
    private List<Users> mData;

    public RecyclerViewAdapterUser(Context mContext, List<Users> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.users_row_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, InformasiPendaftarActivity.class);
                i.putExtra("nama", mData.get(viewHolder.getAdapterPosition()).getNama());
                i.putExtra("nim", mData.get(viewHolder.getAdapterPosition()).getNim());
                i.putExtra("kelas", mData.get(viewHolder.getAdapterPosition()).getKelas());
                i.putExtra("id", mData.get(viewHolder.getAdapterPosition()).getId());
                mContext.startActivity(i);
            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_nama.setText(mData.get(position).getNama());
        holder.tv_nim.setText(mData.get(position).getNim());
        holder.tv_kelas.setText(mData.get(position).getKelas());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama, tv_nim, tv_kelas;
        LinearLayout view_container;

        MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            tv_nama = itemView.findViewById(R.id.namaUser);
            tv_nim = itemView.findViewById(R.id.nimUser);
            tv_kelas = itemView.findViewById(R.id.kelasUser);

        }
    }

}


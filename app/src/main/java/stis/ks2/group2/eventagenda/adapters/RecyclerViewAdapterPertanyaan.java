package stis.ks2.group2.eventagenda.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.List;
import stis.ks2.group2.eventagenda.R;
import stis.ks2.group2.eventagenda.model.Pertanyaan;

public class RecyclerViewAdapterPertanyaan extends RecyclerView.Adapter<RecyclerViewAdapterPertanyaan.MyViewHolder> {

    private Context mContext;
    private List<Pertanyaan> mData;

    public RecyclerViewAdapterPertanyaan(Context mContext, List<Pertanyaan> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.pertanyaan_row_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pertanyaan.setHint(mData.get(position).getPertanyaan());
        holder.inputText.setText(mData.get(position).getJawaban());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextInputLayout pertanyaan;
        public EditText inputText;
        LinearLayout view_container;

        MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.containerPertanyaan);
            pertanyaan = itemView.findViewById(R.id.pertanyaan);
            inputText = itemView.findViewById(R.id.inputText);
        }
    }
}
package ahafidz.com.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.bean.Record;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Record> records;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView id;
        private TextView kode;
        private TextView nama;
        private TextView keterangan;
        private TextView dibuatOleh;
        private TextView dirubahOleh;
        private TextView tglDibuat;
        private TextView tglDirubah;

        public MyViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.R1);
            kode = view.findViewById(R.id.R2);
            nama = view.findViewById(R.id.R3);
            keterangan = view.findViewById(R.id.R4);
            dibuatOleh = view.findViewById(R.id.R5);
            dirubahOleh = view.findViewById(R.id.R6);
            tglDibuat = view.findViewById(R.id.R7);
            tglDirubah = view.findViewById(R.id.R8);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public HomeAdapter(Context mContext, List<Record> records) {
        this.mContext = mContext;
        this.records = records;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Record record = records.get(position);

        holder.id.setText(record.getId());
        holder.kode.setText(record.getKode());
        holder.nama.setText(record.getNama());
        holder.keterangan.setText(record.getKeterangan());
        holder.dibuatOleh.setText(record.getDibuatOleh());
        holder.dirubahOleh.setText(record.getDirubahOleh());
        holder.tglDibuat.setText(record.getTglDibuat());
        holder.tglDirubah.setText(record.getTglDirubah());

    }

    @Override
    public int getItemCount() {
        return records.size();

    }
}
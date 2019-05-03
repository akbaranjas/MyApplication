package ahafidz.com.myapplication.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.bean.Record;
import ahafidz.com.myapplication.listener.OnSelectListListener;
import ahafidz.com.myapplication.util.Constant;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    public List<Record> records;
    public List<Record> selected_records;
    private OnSelectListListener onSelectListListener;
    Integer type;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView kode;
        private TextView nama;
        private TextView keterangan;
        private TextView dibuatOleh;
        private TextView dirubahOleh;
        private TextView tglDibuat;
        private TextView tglDirubah;
        private TextView textViewOption;
        private CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            textViewOption = view.findViewById(R.id.textViewOptions);
            id = view.findViewById(R.id.R1);
            kode = view.findViewById(R.id.R2);
            nama = view.findViewById(R.id.R3);
            keterangan = view.findViewById(R.id.R4);
            dibuatOleh = view.findViewById(R.id.R5);
            dirubahOleh = view.findViewById(R.id.R6);
            tglDibuat = view.findViewById(R.id.R7);
            tglDirubah = view.findViewById(R.id.R8);
            cardView = view.findViewById(R.id.card_view);
        }

    }


    public HomeAdapter(Integer type, Context mContext, List<Record> records, List<Record> selected_records, OnSelectListListener listener) {
        this.type = type;
        this.mContext = mContext;
        this.records = records;
        this.selected_records = selected_records;
        this.onSelectListListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Record record = records.get(position);

        holder.id.setText(String.valueOf(record.getId()));
        holder.kode.setText(record.getKode());
        holder.nama.setText(record.getNama());
        holder.keterangan.setText(record.getKeterangan());
        holder.dibuatOleh.setText(record.getDibuatOleh());
        holder.dirubahOleh.setText(record.getDirubahOleh());
        holder.tglDibuat.setText(record.getTglDibuat());
        holder.tglDirubah.setText(record.getTglDirubah());

        if(type == 1){
            holder.textViewOption.setVisibility(View.INVISIBLE);
        }else{
            holder.textViewOption.setVisibility(View.VISIBLE);
        }

        if(selected_records.contains(record))
            holder.cardView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
        else
            holder.cardView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_normal_state));

        holder.textViewOption.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, holder.textViewOption);
                popup.inflate(R.menu.list_option_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_menu:
                                onSelectListListener.onSelect(Constant.TITLE_UPDATE, record);
                                break;
                            case R.id.delete_menu:
                                onSelectListListener.onSelect(Constant.TITLE_DELETE, record);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return records.size();

    }
}
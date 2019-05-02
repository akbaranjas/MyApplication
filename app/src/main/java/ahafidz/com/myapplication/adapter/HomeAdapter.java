package ahafidz.com.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private List<Record> records;
    private OnSelectListListener onSelectListListener;

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
        }

    }


    public HomeAdapter(Context mContext, List<Record> records, OnSelectListListener listener) {
        this.mContext = mContext;
        this.records = records;
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
                            case R.id.print_menu:
                                onSelectListListener.onSelect(Constant.TITLE_PRINT, record);
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
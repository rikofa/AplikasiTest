package com.aplikasi.test;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aplikasi.test.Model.Barang;
import com.aplikasi.test.View.homeFragment;
import com.aplikasi.test.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.MyViewHolder> {
    String nama, kode, satuan;
    int harga, jumlah, status;

    String nama1, kode1, satuan1;
    int harga1, jumlah1, status1;

    private Context context;
    private List<Barang> dataList;
    AlertDialog dialog;
    private IProcessFilter mCallback;

    public AdapterBarang(Context context, List<Barang> dataList, IProcessFilter mCallback) {
        this.context = context;
        this.dataList = dataList;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_barang, parent, false);
        return new AdapterBarang.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvSatuan.setText(""+dataList.get(position).getSatuan_barang());
        holder.tvKode.setText(dataList.get(position).getKode_barang());
        holder.tvHarga.setText(""+dataList.get(position).getHarga_barang());
        holder.tvJumlah.setText(""+dataList.get(position).getJumlah_barang());
        holder.tvNama.setText(""+dataList.get(position).getNama_barang());

        kode = dataList.get(position).getKode_barang();

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delete(v, kode);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = dataList.get(position).getSatuan_barang();
                kode = dataList.get(position).getKode_barang();
                harga = dataList.get(position).getHarga_barang();
                jumlah = dataList.get(position).getJumlah_barang();
                nama = dataList.get(position).getNama_barang();
                status = dataList.get(position).getStatus_barang();
                satuan = dataList.get(position).getSatuan_barang();
                showCreate(v, kode, nama, harga, jumlah, satuan, status);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface IProcessFilter {
        void onProcessFilter();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSatuan, tvKode, tvHarga, tvJumlah, tvNama;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvSatuan = itemView.findViewById(R.id.tvSatuan);
            tvKode = itemView.findViewById(R.id.tvKode);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
        }
    }

    public void delete(View v, String kode){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        MainViewModel mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(activity);
        dialog1.setCancelable(true);
        dialog1.setTitle("Delete Barang ?");

        dialog1.setCancelable(true);

        dialog1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainViewModel.delete(kode).observe(activity, barang->{
                    mCallback.onProcessFilter();
                });
                dialog.dismiss();
            }
        });

        dialog1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = dialog1.create();
        dialog.show();

    }
    public void showCreate(View v, String kode, String nama, int harga, int jumlah, String satuan, int status){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        EditText edtKode, edtNama, edtJumlah, edtHarga, edtSatuan, edtStatus;
        Button btnCreate;
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add, null);
        dialog1.setView(view);
        dialog1.setCancelable(true);
        dialog1.setTitle("Edit Barang");

        dialog1.setCancelable(true);

        edtKode = view.findViewById(R.id.edtKode);
        edtNama = view.findViewById(R.id.edtNama);
        edtJumlah = view.findViewById(R.id.edtJumlah);
        edtHarga = view.findViewById(R.id.edtHarga);
        edtSatuan = view.findViewById(R.id.edtSatuan);
        edtStatus = view.findViewById(R.id.edtStatus);
        btnCreate = view.findViewById(R.id.btnCreate);

        edtKode.setText(kode);
        edtNama.setText(nama);
        edtJumlah.setText(""+jumlah);
        edtHarga.setText(""+harga);
        edtSatuan.setText(satuan);
        edtStatus.setText(""+status);

        btnCreate.setText("Change");

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainViewModel mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
                nama1 = edtNama.getText().toString();
                kode1 = edtKode.getText().toString();
                satuan1 = edtSatuan.getText().toString();
                harga1 = Integer.parseInt(edtHarga.getText().toString());
                jumlah1= Integer.parseInt(edtJumlah.getText().toString());
                status1 = Integer.parseInt(edtStatus.getText().toString());
                mainViewModel.update(kode1, nama1, harga1, jumlah1, satuan1, status1).observe(activity, barang->{
                    Log.d("TEST"," test = " + barang.getNama_barang());
                    mCallback.onProcessFilter();
                    dialog.dismiss();
                });
            }
        });

        dialog = dialog1.create();
        dialog.show();

    }
}

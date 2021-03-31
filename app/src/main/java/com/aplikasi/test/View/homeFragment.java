package com.aplikasi.test.View;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aplikasi.test.AdapterBarang;
import com.aplikasi.test.MainActivity;
import com.aplikasi.test.Model.Barang;
import com.aplikasi.test.R;
import com.aplikasi.test.Utils.Utils;
import com.aplikasi.test.ViewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment implements View.OnClickListener, AdapterBarang.IProcessFilter {
    Button btnLogin, btnDaftar;
    MainActivity mainActivity;
    LinearLayout l1, l3;
    RelativeLayout l2;
    RecyclerView recyclerView;
    AdapterBarang adapterBarang;
    ExtendedFloatingActionButton fabCreate;
    EditText edtSearch;
    Toolbar toolbar;
    private boolean isSearchOpened = false;
    AlertDialog dialog;
    MainViewModel mainViewModel;
    List<Barang> barangList;
    Barang barang;
    LinearLayout llProgressBar;
    SwipeRefreshLayout swipe;

    String nama, kode, satuan;
    int harga, jumlah, status;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        initUI(v);

        if (mainViewModel.getIsLogin().equals("true")){
            l2.setVisibility(View.VISIBLE);
            l1.setVisibility(View.GONE);
            l3.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            fabCreate.show();

            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    showList();
                }
            });

            showList();

            fabCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCreate();
                }
            });

        }

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 0) {
                    String kodeBarang = "empty";
                    llProgressBar.setVisibility(View.VISIBLE);
                    showSearch(s.toString());

//                    Observable.just(barangList).filter(barangs -> )

//                        for (int i = 0; i < barangList.size(); i++){
//                            if(barangList.get(i).getNama_barang().matches(s.toString())) {
//                                kodeBarang = barangList.get(i).getKode_barang();
//                                Log.d("tst", barangList.get(i).getNama_barang());
//                                showSearch(kodeBarang);
//                                break;
//                            }else{
//                                barangList.clear();
//                                adapterBarang.notifyDataSetChanged();
//                                llProgressBar.setVisibility(View.GONE);
//                            }
//                        }

                }else{
                    showList();
                }
            }
        });

        return v;
    }

    private void showSearch(String kode){
        Log.d("tst", kode);
        barangList.clear();
        mainViewModel.search(kode) .observe(requireActivity(), barang1 -> {
            barang = new Barang();
            if (barang1 != null) {
//                            if (barang1.getSuccess()){
                Log.d("test", ""+barang1.getStatus_barang());
                Log.d("test", ""+barang1.getSatuan_barang());
                Log.d("test", ""+barang1.getNama_barang());
                Log.d("test", ""+barang1.getKode_barang());
                Log.d("test", ""+barang1.getJumlah_barang());
                Log.d("test", ""+barang1.getHarga_barang());

                if (barang1.getSatuan_barang() != null){
                    barang.setStatus_barang(barang1.getStatus_barang());
                    barang.setSatuan_barang(barang1.getSatuan_barang());
                    barang.setNama_barang(barang1.getNama_barang());
                    barang.setKode_barang(barang1.getKode_barang());
                    barang.setJumlah_barang(barang1.getJumlah_barang());
                    barang.setHarga_barang(barang1.getHarga_barang());
                    barangList.add(barang);
                }

                adapterBarang.notifyDataSetChanged();

//                            }
                llProgressBar.setVisibility(View.GONE);
            }

        });
    }

    public void initUI(View v){
        btnLogin = v.findViewById(R.id.btnLogin);
        btnDaftar = v.findViewById(R.id.btnDaftar);
        swipe = v.findViewById(R.id.swipe);
        l1 = v.findViewById(R.id.l1);
        l2 = v.findViewById(R.id.l2);
        l3 = v.findViewById(R.id.l3);
        edtSearch = v.findViewById(R.id.edtSearch);
        fabCreate = v.findViewById(R.id.fabCreate);
        recyclerView = v.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        llProgressBar = v.findViewById(R.id.llProgressBar);

        toolbar = v.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        btnLogin.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                toFrag(new loginFragment());
                Toast.makeText(requireActivity(), "OK LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnDaftar:
                toFrag(new registerFragment());
                Toast.makeText(requireActivity(), "OK DAFTAR", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void toFrag(Fragment fragment){
        getFragmentManager().beginTransaction().
                replace(R.id.frame, fragment).
                commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
    }

    public void showCreate(){
        EditText edtKode, edtNama, edtJumlah, edtHarga, edtSatuan, edtStatus;
        Button btnCreate;
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add, null);
        dialog1.setView(view);
        dialog1.setCancelable(true);
        dialog1.setTitle("Create Barang");

        dialog1.setCancelable(true);

        edtKode = view.findViewById(R.id.edtKode);
        edtNama = view.findViewById(R.id.edtNama);
        edtJumlah = view.findViewById(R.id.edtJumlah);
        edtHarga = view.findViewById(R.id.edtHarga);
        edtSatuan = view.findViewById(R.id.edtSatuan);
        edtStatus = view.findViewById(R.id.edtStatus);
        btnCreate = view.findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llProgressBar.setVisibility(View.VISIBLE);

                if (edtKode.getText().toString().isEmpty()){
                    edtKode.setError("Input Kode Barang !");
                    return;
                }else if (edtNama.getText().toString().isEmpty()){
                    edtNama.setError("Input Nama Barang !");
                    return;
                }else if (edtJumlah.getText().toString().isEmpty()){
                    edtJumlah.setError("Input Jumlah Barang !");
                    return;
                }else if (edtHarga.getText().toString().isEmpty()){
                    edtHarga.setError("Input Harga Barang !");
                    return;
                }else if (edtSatuan.getText().toString().isEmpty()){
                    edtSatuan.setError("Input Satuan Barang !");
                    return;
                }else if (edtStatus.getText().toString().isEmpty()){
                    edtStatus.setError("Input Status Barang !");
                    return;
                }

                nama = edtNama.getText().toString();
                kode = edtKode.getText().toString();
                satuan = edtSatuan.getText().toString();
                harga = Integer.parseInt(edtHarga.getText().toString());
                jumlah = Integer.parseInt(edtJumlah.getText().toString());
                status = Integer.parseInt(edtStatus.getText().toString());
                mainViewModel.create(kode, nama, harga, jumlah, satuan, status).observe(requireActivity(), barang->{
                    Log.d("TEST"," test = " + barang.getNama_barang());
                    showList();
                });
            }
        });

        dialog = dialog1.show();

    }

    private void showList(){
        llProgressBar.setVisibility(View.VISIBLE);
        if (barangList != null)
            barangList.clear();
        mainViewModel.listBarang().observe(requireActivity(), barang ->{
            if(barang != null){
                barangList = barang;
                adapterBarang = new AdapterBarang(requireContext(), barangList, this);
                recyclerView.setAdapter(adapterBarang);
                if (dialog != null)
                    dialog.dismiss();
                if (swipe.isRefreshing())
                    swipe.setRefreshing(false);
            }
            llProgressBar.setVisibility(View.GONE);
        });
    }

    @Override
    public void onProcessFilter() {
        showList();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_logout:
                logOut();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut(){
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(getActivity());
        dialog1.setCancelable(true);
        dialog1.setTitle("Anda Ingin Log Out ?");

        dialog1.setCancelable(true);

        dialog1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.setKey(requireContext(), Utils.token,"");
                Utils.setKey(requireContext(), Utils.isLogin, "false");
                mainViewModel.setLogin("false");
                mainViewModel.setToken("");
                toFrag(new homeFragment());
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


}
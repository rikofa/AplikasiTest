package com.aplikasi.test.View;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aplikasi.test.R;
import com.aplikasi.test.ViewModel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registerFragment extends Fragment {
    EditText edtEmail, edtPassword;
    Button btnDaftar;
    Activity activity;
    LinearLayout llProgressBar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public registerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static registerFragment newInstance(String param1, String param2) {
        registerFragment fragment = new registerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        MainViewModel mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        initUI(v);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llProgressBar.setVisibility(View.VISIBLE);
                if(edtEmail.getText().toString().isEmpty() || !mainViewModel.isEmailValid(edtEmail.getText().toString())) {
                    edtEmail.setError("Masukan Email !");
                    llProgressBar.setVisibility(View.GONE);
                    return;
                }else if(edtPassword.getText().toString().isEmpty()) {
                    edtPassword.setError("Masukan Password !");
                    llProgressBar.setVisibility(View.GONE);
                    return;
                }

                mainViewModel.daftar(edtEmail.getText().toString(), edtPassword.getText().toString()).observe(requireActivity(), user ->{
                    if(user != null){
                        if (user.getSucces() != null){
                            if (user.getSucces()){
                                toFrag(new loginFragment());
                                Toast.makeText(requireActivity(), "" + user.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else if (user.getEmail() != null){
                            Log.d("YA", " ok");
                            Toast.makeText(requireActivity(),"The email has already been taken.",Toast.LENGTH_SHORT).show();
                        }

                        llProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void initUI(View v){
        edtEmail = v.findViewById(R.id.edtEmail);
        edtPassword = v.findViewById(R.id.edtPassword);
        llProgressBar = v.findViewById(R.id.llProgressBar);


        btnDaftar = v.findViewById(R.id.btnDaftar);
    }

    public void toFrag(Fragment fragment){
        getFragmentManager().beginTransaction().
                replace(R.id.frame, fragment).
                commit();
    }
}
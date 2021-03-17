package com.aplikasi.test.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.aplikasi.test.Utils.Utils;
import com.aplikasi.test.ViewModel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link loginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class loginFragment extends Fragment {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    Utils utils;
    LinearLayout llProgressBar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public loginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static loginFragment newInstance(String param1, String param2) {
        loginFragment fragment = new loginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainViewModel mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        initUI(v);

        btnLogin.setOnClickListener(new View.OnClickListener() {
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

                mainViewModel.login(edtEmail.getText().toString(), edtPassword.getText().toString()).observe(requireActivity(), user ->{

                    if(user != null){
                        if (user.getToken() != null){
                            Utils.setKey(requireContext(), Utils.token, user.getToken());
                            Utils.setKey(requireContext(), Utils.isLogin, "true");
                            mainViewModel.setLogin("true");
                            mainViewModel.setToken(user.getToken());
                            toFrag(new homeFragment());
                        }else if (user.getError() != null){
                            Toast.makeText(getActivity(), ""+user.getError(), Toast.LENGTH_SHORT).show();
                        }

                        llProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        return v;
    }

    private void initUI(View v){
        btnLogin = v.findViewById(R.id.btnLogin);
        edtEmail = v.findViewById(R.id.edtEmail);
        edtPassword = v.findViewById(R.id.edtPassword);
        llProgressBar = v.findViewById(R.id.llProgressBar);

        utils = new Utils();
    }

    public void toFrag(Fragment fragment){
        getFragmentManager().beginTransaction().
                replace(R.id.frame, fragment).
                commit();
    }

}
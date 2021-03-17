package com.aplikasi.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aplikasi.test.Api.ApiService;
import com.aplikasi.test.Api.ApiSetting;
import com.aplikasi.test.Utils.Utils;
import com.aplikasi.test.View.homeFragment;
import com.aplikasi.test.View.loginFragment;
import com.aplikasi.test.View.registerFragment;
import com.aplikasi.test.ViewModel.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin, btnDaftar;
    Boolean backPress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setToken(Utils.getKey(this, Utils.token));
        mainViewModel.setLogin(Utils.getKey(this, Utils.isLogin));
        setContentView(R.layout.activity_main);
        toFrag(new homeFragment());


    }

    public void toFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frame, fragment).
                commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                toFrag(new loginFragment());
                Toast.makeText(this, "OK LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnDaftar:
                toFrag(new registerFragment());
                Toast.makeText(this, "OK DAFTAR", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (backPress){
            backPress = false;
            toFrag(new homeFragment());
        }else
            super.onBackPressed();
        
        new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        backPress = true;
                    }
                }, 2000);
    }
}
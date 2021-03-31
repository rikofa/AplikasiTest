package com.aplikasi.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import okhttp3.internal.Util;

import android.content.DialogInterface;
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
    MainViewModel mainViewModel;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
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

    private void show(){
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
        dialog1.setCancelable(true);
        dialog1.setTitle("Anda Ingin Log Out Atau Keluar ?");

        dialog1.setCancelable(true);

        dialog1.setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialog1.setNegativeButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.setKey(getApplicationContext(), Utils.token,"");
                Utils.setKey(getApplication(), Utils.isLogin, "false");
                mainViewModel.setLogin("false");
                mainViewModel.setToken("");
                toFrag(new homeFragment());
                dialog.dismiss();
            }
        });

        dialog = dialog1.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (backPress){
            backPress = false;
            if (Utils.getKey(this, Utils.isLogin).equals("true")){
                show();
            }
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
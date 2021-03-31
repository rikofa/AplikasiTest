package com.aplikasi.test.ViewModel;

import android.content.Context;
import android.util.Log;

import com.aplikasi.test.Api.ApiService;
import com.aplikasi.test.Api.ApiSetting;
import com.aplikasi.test.Model.Barang;
import com.aplikasi.test.Model.User;
import com.aplikasi.test.R;
import com.aplikasi.test.Repository.MainRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
public String message ="";
public String token = "";
public String error = "";
public String email = "";
public String isLogin = "";
public Boolean success = false;
private MainRepository mainRepository;
LiveData<User> user;
LiveData<Barang> barang;
LiveData<List<Barang>> barangList;

    public LiveData<User> login(String email, String password){

        mainRepository = new MainRepository();
        this.user = mainRepository.login(email, password);

        return user;
    }

    public LiveData<User> daftar(String email, String password){
        mainRepository = new MainRepository();
        this.user = mainRepository.daftar(email, password);
        return user;
    }

    public LiveData<List<Barang>> listBarang(){
        mainRepository = new MainRepository();
        this.barangList = mainRepository.getList(token);
        return this.barangList;
    }
    public String getMessage() {
        Log.d("message", "message = " +message);
        return message;
    }

    public LiveData<Barang> create(String kode, String nama, int harga, int jumlah, String satuan, int status){

        mainRepository = new MainRepository();
        this.barang = mainRepository.create(token, kode, nama, harga, jumlah, satuan, status);

        return this.barang;
    }

    public LiveData<Barang> update(String kode, String nama, int harga, int jumlah, String satuan, int status){

        mainRepository = new MainRepository();
        this.barang = mainRepository.update(token, kode, nama, harga, jumlah, satuan, status);

        return this.barang;
    }

    public LiveData<Barang> delete(String kode){

        mainRepository = new MainRepository();
        this.barang = mainRepository.delete(token, kode);

        return this.barang;
    }

    public LiveData<Barang> search(String kode){
        mainRepository = new MainRepository();
        this.barang = mainRepository.search(token, kode);

        return this.barang;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        Log.d("match", "APA = " + matcher.matches());
        return matcher.matches();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }

    public void setLogin(String login) {
        isLogin = login;
    }

    public String getIsLogin() {
        return isLogin;
    }
}

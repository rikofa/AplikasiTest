package com.aplikasi.test.Repository;

import android.util.Log;

import com.aplikasi.test.Api.ApiService;
import com.aplikasi.test.Api.ApiSetting;
import com.aplikasi.test.Model.Barang;
import com.aplikasi.test.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    public LiveData<User> daftar(String email, String password){
        final MutableLiveData<User> data = new MutableLiveData<>();

        Map<String, Object> m = new HashMap<>();
        m.put("email", email);
        m.put("password", password);

        ApiService apiService = ApiSetting.getClient().create(ApiService.class);
        Call<User> call = apiService.register(m);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.toString();
            }
        });

        return data;
    }

    public LiveData<User> login(String email, String password){

        final MutableLiveData<User> data = new MutableLiveData<>();

        Map<String, Object> m = new HashMap<>();
        m.put("email", email);
        m.put("password", password);

        ApiService apiService = ApiSetting.getClient().create(ApiService.class);
        Call<User> call = apiService.login(m);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });


        return data;
    }

    public MutableLiveData<List<Barang>> getList(String token){

        final MutableLiveData<List<Barang>> data = new MutableLiveData<>();


        ApiService apiService = ApiSetting.getClient().create(ApiService.class);
        Call<List<Barang>> call = apiService.listBarang("Bearer " + token);
        call.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }

    public LiveData<Barang> create(String token, String kode, String nama, int harga, int jumlah, String satuan, int status){
        final MutableLiveData<Barang> data = new MutableLiveData<>();

        Map<String, Object> m = new HashMap<>();
        m.put("kode_barang", kode);
        m.put("nama_barang", nama);
        m.put("jumlah_barang", jumlah);
        m.put("satuan_barang", satuan);
        m.put("status_barang", status);
        m.put("harga_barang", harga);

        ApiService apiService = ApiSetting.getClient().create(ApiService.class);
        Call<Barang> call = apiService.addBarang("Bearer " + token, m);
        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {

                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<Barang> update(String token, String kode, String nama, int harga, int jumlah, String satuan, int status){
        final MutableLiveData<Barang> data = new MutableLiveData<>();

        Map<String, Object> m = new HashMap<>();
        m.put("kode_barang", kode);
        m.put("nama_barang", nama);
        m.put("jumlah_barang", jumlah);
        m.put("satuan_barang", satuan);
        m.put("status_barang", status);
        m.put("harga_barang", harga);

        ApiService apiService = ApiSetting.getClient().create(ApiService.class);
        Call<Barang> call = apiService.updateBarang("Bearer " + token, m);
        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {

                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<Barang> delete(String token, String kode){
        final MutableLiveData<Barang> data = new MutableLiveData<>();

        Map<String, Object> m = new HashMap<>();
        m.put("kode_barang", kode);

        ApiService apiService = ApiSetting.getClient().create(ApiService.class);
        Call<Barang> call = apiService.delete("Bearer " + token, m);
        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {

                t.printStackTrace();
            }
        });

        return data;
    }

    public MutableLiveData<Barang> search(String token, String kode){

        final MutableLiveData<Barang> data = new MutableLiveData<>();

        Map<String, Object> m = new HashMap<>();
        m.put("kode_barang", kode);

        ApiService apiService = ApiSetting.getClient().create(ApiService.class);
        Call<Barang> call = apiService.cariBarang("Bearer " + token, m);
        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }

}

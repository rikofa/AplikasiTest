package com.aplikasi.test.Api;

import com.aplikasi.test.Model.Barang;
import com.aplikasi.test.Model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("register")
    Call<User> register(
            @FieldMap Map<String, Object> m
    );

    @FormUrlEncoded
    @POST("auth/login")
    Call<User> login(
            @FieldMap Map<String, Object> m
    );

    @FormUrlEncoded
    @POST("item/add")
    Call<Barang> addBarang(
            @Header("Authorization") String authorization,
            @FieldMap Map<String, Object> m
    );

    @FormUrlEncoded
    @POST("item/update")
    Call<Barang> updateBarang(
            @Header("Authorization") String authorization,
            @FieldMap Map<String, Object> m
    );

    @GET("items")
    Call<List<Barang>> listBarang(
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("item/search")
    Call<Barang> cariBarang(
            @Header("Authorization") String authorization,
            @FieldMap Map<String, Object> m
    );
}

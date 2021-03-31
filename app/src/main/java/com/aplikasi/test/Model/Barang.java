package com.aplikasi.test.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Barang{
    @SerializedName("success")
    public Boolean success;
    @SerializedName("kode_barang")
    public String kode_barang;
    @SerializedName("nama_barang")
    public String nama_barang;
    @SerializedName("jumlah_barang")
    public int jumlah_barang;
    @SerializedName("harga_barang")
    public int harga_barang;
    @SerializedName("satuan_barang")
    public String satuan_barang;
    @SerializedName("status_barang")
    public int status_barang;
    public List<Barang> barangList;

    public void setKode_barang(String kode_barang) {
        this.kode_barang = kode_barang;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setJumlah_barang(int jumlah_barang) {
        this.jumlah_barang = jumlah_barang;
    }

    public int getJumlah_barang() {
        return jumlah_barang;
    }

    public void setHarga_barang(int harga_barang) {
        this.harga_barang = harga_barang;
    }

    public int getHarga_barang() {
        return harga_barang;
    }

    public void setSatuan_barang(String satuan_barang) {
        this.satuan_barang = satuan_barang;
    }

    public String getSatuan_barang() {
        return satuan_barang;
    }

    public void setStatus_barang(int status_barang) {
        this.status_barang = status_barang;
    }

    public int getStatus_barang() {
        return status_barang;
    }

    public void setBarangList(List<Barang> barangList) {
        this.barangList = barangList;
    }

    public List<Barang> getBarangList() {
        return barangList;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }
}

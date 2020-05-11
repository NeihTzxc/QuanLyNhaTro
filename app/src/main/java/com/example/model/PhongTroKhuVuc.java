package com.example.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class PhongTroKhuVuc implements Serializable {
    private int MaPhong;
    private String TenPhong;
    private String TenKV;
    private String TinhTrang;

    public PhongTroKhuVuc() {
    }

    public PhongTroKhuVuc(int maPhong, String tenPhong, String tenKV, String tinhTrang) {
        MaPhong = maPhong;
        TenPhong = tenPhong;
        TenKV = tenKV;
        TinhTrang = tinhTrang;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(int maPhong) {
        MaPhong = maPhong;
    }

    public String getTenPhong() {
        return TenPhong;
    }

    public void setTenPhong(String tenPhong) {
        TenPhong = tenPhong;
    }

    public String getTenKV() {
        return TenKV;
    }

    public void setTenKV(String tenKV) {
        TenKV = tenKV;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    @NonNull
    @Override
    public String toString() {
        return TenPhong+"-"+TenKV+"-"+TinhTrang;
    }
}

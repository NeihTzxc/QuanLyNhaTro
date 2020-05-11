package com.example.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class KhuVucPhong implements Serializable {
    private  int MaKV;
    private String TenKV;
    private String Tang;
    private String TenDayPhong;
    private int SoLuongPhong;
    private String TinhTrang;

    public KhuVucPhong() {
    }

    public KhuVucPhong(int maKV, String tenKV, String tang, String tenDayPhong, int soLuongPhong, String tinhTrang) {
        MaKV = maKV;
        TenKV = tenKV;
        Tang = tang;
        TenDayPhong = tenDayPhong;
        SoLuongPhong = soLuongPhong;
        TinhTrang = tinhTrang;
    }

    public int getMaKV() {
        return MaKV;
    }

    public void setMaKV(int maKV) {
        MaKV = maKV;
    }

    public String getTenKV() {
        return TenKV;
    }

    public void setTenKV(String tenKV) {
        TenKV = tenKV;
    }

    public String getTang() {
        return Tang;
    }

    public void setTang(String tang) {
        Tang = tang;
    }

    public String getTenDayPhong() {
        return TenDayPhong;
    }

    public void setTenDayPhong(String tenDayPhong) {
        TenDayPhong = tenDayPhong;
    }

    public int getSoLuongPhong() {
        return SoLuongPhong;
    }

    public void setSoLuongPhong(int soLuongPhong) {
        SoLuongPhong = soLuongPhong;
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
        return TenKV+"-Tầng:"+Tang+"-:"+TenDayPhong+"-Số lượng:"+SoLuongPhong+"-:"+TinhTrang;
    }
}

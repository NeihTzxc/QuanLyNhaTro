package com.example.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ThietBi implements Serializable {
    private int MaTB;
    private int MaPhong;
    private String TenThietBi;
    private  int SoLuong;
    private  String TinhTrang;

    public ThietBi() {
    }

    public ThietBi(int maTB, int maPhong, String tenThietBi, int soLuong, String tinhTrang) {
        MaTB = maTB;
        MaPhong = maPhong;
        TenThietBi = tenThietBi;
        SoLuong = soLuong;
        TinhTrang = tinhTrang;
    }

    public int getMaTB() {
        return MaTB;
    }

    public void setMaTB(int maTB) {
        MaTB = maTB;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(int maPhong) {
        MaPhong = maPhong;
    }

    public String getTenThietBi() {
        return TenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        TenThietBi = tenThietBi;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
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
        return "STT: "+MaTB+"\n"+"Tên thiết bị: "+TenThietBi+"\n"+"Số lượng: "+SoLuong+"\n"+"Tình trạng:"+TinhTrang;
    }
}

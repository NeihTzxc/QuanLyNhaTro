package com.example.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class PhongTro implements Serializable {
private int MaPhong;
private String TenKV;
private String TenPhong;
private  int GiaPhong;
private int SoNguoiToiDa;
private String TinhTrang;
private int MaKV;

    public PhongTro() {
    }

    public PhongTro(int maPhong, String tenKV, String tenPhong, int giaPhong, int soNguoiToiDa, String tinhTrang, int maKV) {
        MaPhong = maPhong;
        TenKV = tenKV;
        TenPhong = tenPhong;
        GiaPhong = giaPhong;
        SoNguoiToiDa = soNguoiToiDa;
        TinhTrang = tinhTrang;
        MaKV = maKV;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(int maPhong) {
        MaPhong = maPhong;
    }

    public String getTenKV() {
        return TenKV;
    }

    public void setTenKV(String tenKV) {
        TenKV = tenKV;
    }

    public String getTenPhong() {
        return TenPhong;
    }

    public void setTenPhong(String tenPhong) {
        TenPhong = tenPhong;
    }

    public int getGiaPhong() {
        return GiaPhong;
    }

    public void setGiaPhong(int giaPhong) {
        GiaPhong = giaPhong;
    }

    public int getSoNguoiToiDa() {
        return SoNguoiToiDa;
    }

    public void setSoNguoiToiDa(int soNguoiToiDa) {
        SoNguoiToiDa = soNguoiToiDa;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public int getMaKV() {
        return MaKV;
    }

    public void setMaKV(int maKV) {
        MaKV = maKV;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tên phòng: "+TenPhong+"\n"+"Tên khu vực: "+TenKV+"\n"+"Giá phòng: "+GiaPhong+"\n"+"Số người tối đa: "+SoNguoiToiDa+"\n"+"Tình trạng phòng: "+TinhTrang;
    }
}

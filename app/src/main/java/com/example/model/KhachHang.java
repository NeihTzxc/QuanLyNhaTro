package com.example.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private int MaKH;
    private String HoTen;
    private String CMND;
    private String SDT;
    private String DiaChi;
    private  String NgheNghiep;
    private String Email;

    public KhachHang() {

    }

    public KhachHang(int maKH, String hoTen, String CMND, String SDT, String diaChi, String ngheNghiep, String email) {
        MaKH = maKH;
        HoTen = hoTen;
        this.CMND = CMND;
        this.SDT = SDT;
        DiaChi = diaChi;
        NgheNghiep = ngheNghiep;
        Email = email;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int maKH) {
        MaKH = maKH;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNgheNghiep() {
        return NgheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        NgheNghiep = ngheNghiep;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "Họ và tên: "+HoTen+"\n"+"Số CMND: "+CMND+"\n"+"Số điện thoại: "+SDT+"\n"+"Địa chỉ: "+DiaChi+"\n"+"Nghề nghiệp: "+NgheNghiep+"\n"+"Email:"+Email;
    }
    //"Mã Khách Hàng: "+MaKH+"\n"+
}

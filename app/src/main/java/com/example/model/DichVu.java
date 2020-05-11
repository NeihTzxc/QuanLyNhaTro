package com.example.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DichVu implements Serializable {
    private int MaDV;
    private String TenDV;
    private int DonGiaDV;
    private String GhiChu;

    public DichVu() {
    }

    public DichVu(int maDV, String tenDV, int donGiaDV, String ghiChu) {
        MaDV = maDV;
        TenDV = tenDV;
        DonGiaDV = donGiaDV;
        GhiChu = ghiChu;
    }

    public int getMaDV() {
        return MaDV;
    }

    public void setMaDV(int maDV) {
        MaDV = maDV;
    }

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String tenDV) {
        TenDV = tenDV;
    }

    public int getDonGiaDV() {
        return DonGiaDV;
    }

    public void setDonGiaDV(int donGiaDV) {
        DonGiaDV = donGiaDV;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tên dịch vụ: "+TenDV+"\n"+"Đơn giá: "+DonGiaDV+"\n"+"Ghi chú: "+GhiChu;
    }
}

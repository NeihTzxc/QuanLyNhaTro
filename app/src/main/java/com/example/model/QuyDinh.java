package com.example.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class QuyDinh implements Serializable {
    private int MaQuyDinh;
    private String TenQuyDinh;
    private String NoiDung;

    public QuyDinh() {
    }

    public QuyDinh(int maQuyDinh, String tenQuyDinh, String noiDung) {
        MaQuyDinh = maQuyDinh;
        TenQuyDinh = tenQuyDinh;
        NoiDung = noiDung;
    }

    public int getMaQuyDinh() {
        return MaQuyDinh;
    }

    public void setMaQuyDinh(int maQuyDinh) {
        MaQuyDinh = maQuyDinh;
    }

    public String getTenQuyDinh() {
        return TenQuyDinh;
    }

    public void setTenQuyDinh(String tenQuyDinh) {
        TenQuyDinh = tenQuyDinh;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    @NonNull
    @Override
    public String toString() {
        return "STT: "+MaQuyDinh+"\n"+"Tên quy định: "+TenQuyDinh+"\n"+"Nội dung: "+NoiDung;
    }
}

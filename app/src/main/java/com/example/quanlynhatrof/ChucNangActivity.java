package com.example.quanlynhatrof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChucNangActivity extends AppCompatActivity {
    ImageButton btnKhachHang;
    ImageButton btnQuyDinh;
    ImageButton btnThietBi;
    ImageButton btnPhongTro;
    ImageButton btnDichVu;
    Button btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang);
        addControls();
        addEvents();


    }

    private void addEvents() {
        btnKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ChucNangActivity.this,KhachHangActivity.class);
                startActivity(intent);
            }
        });
        btnQuyDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChucNangActivity.this,QuyDinhActivity.class);
                startActivity(intent);
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChucNangActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChucNangActivity.this,ThietBiActivity.class);
                startActivity(intent);
            }
        });
        btnPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChucNangActivity.this,PhongTroActivity.class);
                startActivity(intent);
            }
        });
        btnDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChucNangActivity.this,DichVuActivity.class);
                startActivity(intent);
            }
        });







    }

    private void addControls() {
        btnKhachHang=findViewById(R.id.btnKhachHang);
        btnQuyDinh=findViewById(R.id.btnQuyDinh);
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThietBi=findViewById(R.id.btnThietBi);
        btnPhongTro=findViewById(R.id.btnPhongTro);
        btnDichVu=findViewById(R.id.btnDichVu);



    }
}

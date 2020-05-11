package com.example.quanlynhatrof;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.KhachHang;
import com.example.model.QuyDinh;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SuaKhachHangActivity extends AppCompatActivity {
    Button btnSuaKhachHang, btnTroVe;
    Button btnXoa;
    EditText edtMaKH, edtTenKH, edtCMND, edtSDT, edtDiaChi, edtNgheNghiep, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_khach_hang);
        addControls();
        addEvents();
    }
    private void addControls() {
        btnSuaKhachHang=findViewById(R.id.btnSuaKhachHang);
        btnXoa=findViewById(R.id.btnXoa);
        btnTroVe=findViewById(R.id.btnTroVe);
        edtNgheNghiep=findViewById(R.id.edtNgheNghiep);
        edtMaKH=findViewById(R.id.edtMaKH);
        edtTenKH=findViewById(R.id.edtHoTen);
        edtCMND=findViewById(R.id.edtCmnd);
        edtDiaChi=findViewById(R.id.edtDiaChi);
        edtEmail=findViewById(R.id.edtEmail);
        edtSDT=findViewById(R.id.edtSdt);
        //get toan bo du lieu tu doi tuong khach hang
        Intent intent=getIntent();
        KhachHang kh=(KhachHang)intent.getSerializableExtra("KHACHHANG");
        edtMaKH.setText(kh.getMaKH()+"");
        edtTenKH.setText(kh.getHoTen());
        edtSDT.setText(kh.getSDT());
        edtEmail.setText(kh.getEmail());
        edtDiaChi.setText(kh.getDiaChi());
        edtCMND.setText(kh.getCMND());
        edtNgheNghiep.setText(kh.getNgheNghiep());
        edtMaKH.setEnabled(false);


    }
    private void addEvents() {

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SuaKhachHangActivity.this,KhachHangActivity.class);
                startActivity(intent);
            }
        });
        btnSuaKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySuaKhachHang();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xyLyXoaKhachHang();
            }
        });
        btnXoa.setEnabled(false);


    }



    private void xuLySuaKhachHang() {
KhachHang kh=new KhachHang();
kh.setMaKH(Integer.parseInt(edtMaKH.getText().toString()));
kh.setHoTen(edtTenKH.getText().toString());
kh.setSDT(edtSDT.getText().toString());
kh.setEmail(edtEmail.getText().toString());
kh.setNgheNghiep(edtNgheNghiep.getText().toString());
kh.setDiaChi(edtDiaChi.getText().toString());
kh.setCMND(edtCMND.getText().toString());

SuaKhachHangTask task =new SuaKhachHangTask();
task.execute(kh);
    }
    private void xyLyXoaKhachHang()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(SuaKhachHangActivity.this);
        builder.setTitle("Ban co muon xoa?");
        builder.setMessage("Ban co chac muon xoa");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(SuaKhachHangActivity.this,"Xoa thanh cong",Toast.LENGTH_LONG).show();
                    XoaKhachHangTask task=new XoaKhachHangTask();
                    task.execute(edtMaKH.getText().toString());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();

    }


    class SuaKhachHangTask extends AsyncTask<KhachHang,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true)
            {
                Toast.makeText(SuaKhachHangActivity.this,"Sua Khach Hang thanh cong",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(SuaKhachHangActivity.this,"Sua San Pham That Bai",Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(KhachHang... khachHangs) {
            try {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/SuaKhachHang");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                KhachHang kh=khachHangs[0];
                String params="makh="+kh.getMaKH()+"&hoten="+ URLEncoder.encode(kh.getHoTen()) +"&cmnd="+URLEncoder.encode(kh.getCMND())+"&sdt="+URLEncoder.encode(kh.getSDT())+"&diachi="+URLEncoder.encode(kh.getDiaChi())+"&nghenghiep="+URLEncoder.encode(kh.getNgheNghiep())+"&email="+URLEncoder.encode(kh.getEmail());
                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write(params);
                osw.flush();
                osw.close();
                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("boolean");
                boolean kq=Boolean.parseBoolean(dsNote.item(0).getTextContent());
                return kq;

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return false;
        }
    }
    class XoaKhachHangTask extends AsyncTask<String,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean.booleanValue()==true)
            {
                Toast.makeText(SuaKhachHangActivity.this,"Xoa thanh cong",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(SuaKhachHangActivity.this,"Xoa that bai",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try
            {
                URL url =new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/XoaKhachHang");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                OutputStream os=connection.getOutputStream();
                OutputStreamWriter opsw=new OutputStreamWriter(os,"UTF-8");
                opsw.write("makh="+strings[0]);
                opsw.flush();
                opsw.close();

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("boolean");

                boolean kq=Boolean.parseBoolean(dsNote.item(0).getTextContent());
                return kq;
            }
            catch (Exception ex)
            {

            }
            return false;
        }
    }
}

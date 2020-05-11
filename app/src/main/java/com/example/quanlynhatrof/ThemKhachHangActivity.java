package com.example.quanlynhatrof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.KhachHang;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ThemKhachHangActivity extends AppCompatActivity {
    Button btnTroVe;
    Button btnThemKhachHang;
    EditText edtHoTen, edtCMND, edtSDT, edtDiaChi, edtEmail, edtNgheNghiep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThemKhachHang=findViewById(R.id.btnThemKhachHang);
        edtHoTen=findViewById(R.id.edtHoTen);
        edtSDT=findViewById(R.id.edtSdt);
        edtCMND=findViewById(R.id.edtCmnd);
        edtDiaChi=findViewById(R.id.edtDiaChi);
        edtEmail=findViewById(R.id.edtEmail);
        edtNgheNghiep=findViewById(R.id.edtNgheNghiep);
    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThemKhachHangActivity.this,KhachHangActivity.class);
                startActivity(intent);
            }
        });
        btnThemKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xyLyThemKhachHang();
            }
        });

    }

    private void xyLyThemKhachHang() {

        KhachHang kh=new KhachHang();
        kh.setHoTen(edtHoTen.getText().toString());
        kh.setCMND(edtCMND.getText().toString());
        kh.setDiaChi(edtDiaChi.getText().toString());
        kh.setNgheNghiep(edtNgheNghiep.getText().toString());
        kh.setEmail(edtEmail.getText().toString());
        kh.setSDT(edtSDT.getText().toString());
    }

    class ThemKhachHangTask extends AsyncTask<KhachHang,Void,Boolean>
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
                Toast.makeText(ThemKhachHangActivity.this,"Them thanh cong",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(ThemKhachHangActivity.this,"Them that bai",Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(KhachHang... khachHangs) {
            try
            {
                URL url =new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/ThemKhachHang");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                KhachHang kh=khachHangs[0];

                String params="hoten="+ URLEncoder.encode(kh.getHoTen()) +"&cmnd="+URLEncoder.encode(kh.getCMND())+"&sdt="+URLEncoder.encode(kh.getSDT())+"&diachi="+URLEncoder.encode(kh.getSDT())+"&nghenghiep="+URLEncoder.encode(kh.getNgheNghiep())+"&email="+URLEncoder.encode(kh.getEmail());
                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw= new OutputStreamWriter(os,"UTF-8");
                osw.write(params);
                osw.flush();
                osw.close();
                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("boolean");
                boolean kq=Boolean.parseBoolean(dsNote.item(0).getTextContent());
                return  kq;



            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return false;
        }
    }

}

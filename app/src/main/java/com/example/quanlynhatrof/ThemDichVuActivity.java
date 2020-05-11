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

import com.example.model.DichVu;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ThemDichVuActivity extends AppCompatActivity {
    EditText edtTenDichVu, edtDonGia, edtGhiChu;
    Button btnThemDichVu, btnTroVe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_dich_vu);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtTenDichVu=findViewById(R.id.edtTenDichVu);
        edtDonGia=findViewById(R.id.edtDonGia);
        edtGhiChu=findViewById(R.id.edtGhiChu);
        btnThemDichVu=findViewById(R.id.btnThemDichVu);
        btnTroVe=findViewById(R.id.btnTroVe);

    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThemDichVuActivity.this,DichVuActivity.class);
                startActivity(intent);
            }
        });
        btnThemDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemDichVu();

            }
        });

    }

    private void xuLyThemDichVu() {
                DichVu dv=new DichVu();
                dv.setTenDV(edtTenDichVu.getText().toString());
                dv.setDonGiaDV(Integer.parseInt(edtDonGia.getText().toString()));
                dv.setGhiChu(edtGhiChu.getText().toString());
                ThemDichVuTask task=new ThemDichVuTask();
                task.execute(dv);
    }

    class ThemDichVuTask extends AsyncTask<DichVu,Void,Boolean>
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
                Toast.makeText(ThemDichVuActivity.this,"Them dich vu thanh cong",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(ThemDichVuActivity.this,"Them dich vu that bai",Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(DichVu... dichVus) {
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/ThemDichVu");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                DichVu dv=dichVus[0];
                String params="tendv="+ URLEncoder.encode(dv.getTenDV())+"&dongiadv="+dv.getDonGiaDV()+"&ghichu="+URLEncoder.encode(dv.getGhiChu());
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
}

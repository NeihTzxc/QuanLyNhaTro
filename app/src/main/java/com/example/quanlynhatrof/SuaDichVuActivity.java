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

public class SuaDichVuActivity extends AppCompatActivity {

    EditText edtMaDV,edtTenDV, edtDonGia, edtGhiChu;
    Button btnSuaDichVu, btnTroVe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_dich_vu);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtMaDV=findViewById(R.id.edtMaDichVu);
        edtTenDV=findViewById(R.id.edtTenDichVu);
        edtDonGia=findViewById(R.id.edtDonGia);
        edtGhiChu=findViewById(R.id.edtGhiChu);
        btnSuaDichVu=findViewById(R.id.btnSuaDichVu);
        btnTroVe=findViewById(R.id.btnTroVe);

        Intent intent=getIntent();
        DichVu dv=(DichVu)intent.getSerializableExtra("DICHVU");
        edtMaDV.setText(dv.getMaDV()+"");
        edtDonGia.setText(dv.getDonGiaDV()+"");
        edtTenDV.setText(dv.getTenDV());
        edtGhiChu.setText(dv.getGhiChu());
        edtMaDV.setEnabled(false);
    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SuaDichVuActivity.this,DichVuActivity.class);
                startActivity(intent);
            }
        });
        btnSuaDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySuaDichVu();
            }
        });

    }

    private void xuLySuaDichVu() {
            DichVu dv=new DichVu();
            dv.setMaDV(Integer.parseInt(edtMaDV.getText().toString()));
            dv.setTenDV(edtTenDV.getText().toString());
            dv.setDonGiaDV(Integer.parseInt(edtDonGia.getText().toString()));
            dv.setGhiChu(edtGhiChu.getText().toString());

            SuaDichVuTask task=new SuaDichVuTask();
            task.execute(dv);
    }

    class SuaDichVuTask extends AsyncTask<DichVu,Void,Boolean>
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
                Toast.makeText(SuaDichVuActivity.this,"Sua thanh cong",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(SuaDichVuActivity.this,"Sua that bai",Toast.LENGTH_LONG).show();
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
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/SuaDichVu");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                DichVu dv=dichVus[0];
                String params="madv="+dv.getMaDV()+"&tendv="+ URLEncoder.encode(dv.getTenDV())+"&dongiadv="+dv.getDonGiaDV()+"&ghichu="+URLEncoder.encode(dv.getGhiChu());
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

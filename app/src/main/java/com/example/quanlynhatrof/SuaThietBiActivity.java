package com.example.quanlynhatrof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.model.PhongTroKhuVuc;
import com.example.model.QuyDinh;
import com.example.model.ThietBi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SuaThietBiActivity extends AppCompatActivity {
    Button btnSuaThietBi,btnTroVe;
    EditText edtMaThietBi, edtTenThietBi, edtSoLuong,edtTinhTrang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thiet_bi);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnSuaThietBi=findViewById(R.id.btnSuaThietBi);
        btnTroVe=findViewById(R.id.btnTroVe);
        edtMaThietBi=findViewById(R.id.edtMaThietBi);
        edtTenThietBi=findViewById(R.id.edtTenThietBi);
        edtSoLuong=findViewById(R.id.edtSoLuong);
        edtTinhTrang=findViewById(R.id.edtTinhTrang);









        Intent intent=getIntent();
        ThietBi tb=(ThietBi)intent.getSerializableExtra("THIETBI");
        edtMaThietBi.setText(tb.getMaTB()+"");
        edtTenThietBi.setText(tb.getTenThietBi());
        edtTinhTrang.setText(tb.getTinhTrang());
        edtSoLuong.setText(tb.getSoLuong()+"");

    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SuaThietBiActivity.this,ThietBiActivity.class);
                startActivity(intent);
            }
        });
        btnSuaThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySuaThietBi();
            }
        });

    }

    private void xuLySuaThietBi() {
        ThietBi tb=new ThietBi();
        tb.setMaTB(Integer.parseInt(edtMaThietBi.getText().toString()));
        tb.setTenThietBi(edtTenThietBi.getText().toString());
        tb.setTinhTrang(edtTinhTrang.getText().toString());
        tb.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString()));
        SuaThietBiTask task=new SuaThietBiTask();
        task.execute(tb);
    }

    class SuaThietBiTask extends AsyncTask<ThietBi,Void,Boolean>
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
                Toast.makeText(SuaThietBiActivity.this,"Sua thiet bi thanh cong", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(SuaThietBiActivity.this,ThietBiActivity.class);
                startActivity(intent);

            }
            else
            {
                Toast.makeText(SuaThietBiActivity.this,"Sua thiet bi khong thanh cong",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(ThietBi... thietBis) {
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/SuaThietBi");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                ThietBi tb=thietBis[0];
                String params="tenthietbi="+tb.getTenThietBi()+"&soluong="+tb.getSoLuong()+"&tinhtrang="+tb.getTinhTrang()+"&mathietbi="+tb.getMaTB();

                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write(params);

                osw.flush();
                osw.close();

                DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
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

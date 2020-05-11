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

import com.example.model.PhongTro;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SuaPhongTroActivity extends AppCompatActivity {
    EditText edtTenPhong, edtSoNguoiToiDa, edtGiaPhong, edtTinhTrang,edtMaPhong;
    Button btnSuaPhongTro, btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_phong_tro);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtTenPhong=findViewById(R.id.edtTenPhong);
        edtSoNguoiToiDa=findViewById(R.id.edtSoNguoiToiDa);
        edtGiaPhong=findViewById(R.id.edtGiaPhong);
        edtTinhTrang=findViewById(R.id.edtTinhTrang);
        btnSuaPhongTro=findViewById(R.id.btnSuaPhongTro);
        btnTroVe=findViewById(R.id.btnTroVe);
        edtMaPhong=findViewById(R.id.edtMaPhong);

        Intent intent=getIntent();
        PhongTro pt=(PhongTro)intent.getSerializableExtra("PHONGTRO");
        edtMaPhong.setText(pt.getMaPhong()+"");
        edtTenPhong.setText(pt.getTenPhong());
        edtTinhTrang.setText(pt.getTinhTrang());
        edtGiaPhong.setText(pt.getGiaPhong()+"");
        edtSoNguoiToiDa.setText(pt.getSoNguoiToiDa()+"");
        edtMaPhong.setEnabled(false);

    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SuaPhongTroActivity.this,PhongTroActivity.class);
                startActivity(intent);
            }
        });
        btnSuaPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySuaPhongTro();
            }
        });

    }

    private void xuLySuaPhongTro() {
        PhongTro pt=new PhongTro();
        pt.setMaPhong(Integer.parseInt(edtMaPhong.getText().toString()));
        pt.setGiaPhong(Integer.parseInt(edtGiaPhong.getText().toString()));
        pt.setSoNguoiToiDa(Integer.parseInt(edtSoNguoiToiDa.getText().toString()));
        pt.setTinhTrang(edtTinhTrang.getText().toString());
        pt.setTenPhong(edtTenPhong.getText().toString());

        SuaPhongTroTask task=new SuaPhongTroTask();
        task.execute(pt);

    }

    class SuaPhongTroTask extends AsyncTask<PhongTro,Void,Boolean>
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
                Toast.makeText(SuaPhongTroActivity.this,"Sua thanh cong",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(SuaPhongTroActivity.this,"Sua that bai",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(PhongTro... phongTros) {
            try
            {
                URL url=new URL("http://192.168.1.128//quanlynhatro1/QUANLYNHATRO1.asmx/SuaPhongTro");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                PhongTro pt=phongTros[0];
                String params="maphong="+pt.getMaPhong()+"&tenphong="+URLEncoder.encode(pt.getTenPhong())+"&giaphong="+pt.getGiaPhong()+"&songuoitoida="+pt.getSoNguoiToiDa()+"&tinhtrang="+ URLEncoder.encode(pt.getTinhTrang());

                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write(params);
                osw.flush();
                osw.close();

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("boolean");
                boolean kq= Boolean.parseBoolean(dsNote.item(0).getTextContent());
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

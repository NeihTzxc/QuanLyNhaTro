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

import com.example.model.PhongTro;
import com.example.model.PhongTroKhuVuc;
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

public class ThemThietBiActivity extends AppCompatActivity {
    Button btnThemThietBi, btnTroVe;
    EditText edtTenThietBi, edtSoLuong, edtTinhTrang;

    ArrayAdapter<PhongTroKhuVuc> phongTroKhuVucAdapter;
    Spinner spPhongTro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thiet_bi);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnThemThietBi=findViewById(R.id.btnThemThietBi);
        btnTroVe=findViewById(R.id.btnTroVe);
        edtTenThietBi=findViewById(R.id.edtTenThietBi);
        edtSoLuong=findViewById(R.id.edtSoLuong);
        edtTinhTrang=findViewById(R.id.edtTinhTrang);
        spPhongTro=findViewById(R.id.spPhongTro);

        phongTroKhuVucAdapter=new ArrayAdapter<PhongTroKhuVuc>(ThemThietBiActivity.this,android.R.layout.simple_list_item_1);

        phongTroKhuVucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPhongTro.setAdapter(phongTroKhuVucAdapter);

       DanhSachPhongTask task=new DanhSachPhongTask();
       task.execute();


    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemThietBiActivity.this,ThietBiActivity.class);
                startActivity(intent);
            }
        });
        btnThemThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemThietBi();

            }
        });

    }

    private void xuLyThemThietBi() {
        ThietBi tb=new ThietBi();
        tb.setTenThietBi(edtTenThietBi.getText().toString());
        tb.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString()));
        tb.setTinhTrang(edtTinhTrang.getText().toString());
        PhongTroKhuVuc ptkv=(PhongTroKhuVuc)spPhongTro.getSelectedItem();
        tb.setMaPhong(ptkv.getMaPhong());
        ThemThietBiTask task=new ThemThietBiTask();
        task.execute(tb);

    }

    class DanhSachPhongTask extends AsyncTask<Void, Void, ArrayList<PhongTroKhuVuc>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<PhongTroKhuVuc> phongTroKhuVucs) {
            super.onPostExecute(phongTroKhuVucs);
            phongTroKhuVucAdapter.clear();
            phongTroKhuVucAdapter.addAll(phongTroKhuVucs);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<PhongTroKhuVuc> doInBackground(Void... voids) {
            ArrayList<PhongTroKhuVuc> dspkv=new ArrayList<>();
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/PTKVP");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("uspPTKVPResult");
                for(int i=0;i<dsNote.getLength();i++)
                {
                    Element elementPT=(Element)dsNote.item(i);
                    int ma= Integer.parseInt(elementPT.getElementsByTagName("MaPhong").item(0).getTextContent());
                    String tenphong=elementPT.getElementsByTagName("TenPhong").item(0).getTextContent();
                    String tenkv=elementPT.getElementsByTagName("TenKV").item(0).getTextContent();
                    String tinhtrang=elementPT.getElementsByTagName("TinhTrang").item(0).getTextContent();
                    PhongTroKhuVuc ptkv=new PhongTroKhuVuc();
                    ptkv.setMaPhong(ma);
                    ptkv.setTenKV(tenkv);
                    ptkv.setTenPhong(tenphong);
                    ptkv.setTinhTrang(tinhtrang);
                    dspkv.add(ptkv);
                }

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return dspkv;
        }
    }
    class ThemThietBiTask extends AsyncTask<ThietBi,Void,Boolean>
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
                Toast.makeText(ThemThietBiActivity.this,"Them thiet bi thanh cong",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(ThemThietBiActivity.this,"Them thiet bi that bai",Toast.LENGTH_LONG).show();
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
                URL url =new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/ThemThietBi");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                ThietBi tb=thietBis[0];

                String params="maphong="+tb.getMaPhong()+"&tenthietbi="+URLEncoder.encode(tb.getTenThietBi())+"&soluong="+tb.getSoLuong()+"&tinhtrang="+URLEncoder.encode(tb.getTinhTrang());


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

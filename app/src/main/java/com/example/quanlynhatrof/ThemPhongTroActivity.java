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

import com.example.model.KhuVucPhong;
import com.example.model.PhongTro;

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

public class ThemPhongTroActivity extends AppCompatActivity {
    EditText edtTenPhong,edtGiaPhong,edtSoNguoiToiDa,edtTinhTrang;
    Button btnTroVe;
    Button btnThemPhongTro;
    Spinner spKhuVuc;
    ArrayAdapter<KhuVucPhong> khuVucPhongAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phong_tro);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtTenPhong=findViewById(R.id.edtTenPhong);
        edtGiaPhong=findViewById(R.id.edtGiaPhong);
        edtSoNguoiToiDa=findViewById(R.id.edtSoNguoiToiDa);
        edtTinhTrang=findViewById(R.id.edtTinhTrang);
        spKhuVuc=findViewById(R.id.spKhuVuc);
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThemPhongTro=findViewById(R.id.btnThemPhongTro);
        khuVucPhongAdapter=new ArrayAdapter<KhuVucPhong>(ThemPhongTroActivity.this,android.R.layout.simple_spinner_item);
        khuVucPhongAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKhuVuc.setAdapter(khuVucPhongAdapter);
        DanhSachCacKhuVucPhongTask task=new DanhSachCacKhuVucPhongTask();
        task.execute();


    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThemPhongTroActivity.this,PhongTroActivity.class);
                startActivity(intent);
            }
        });
        btnThemPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemPhongTro();
            }
        });


    }

    private void xuLyThemPhongTro() {
        PhongTro pt=new PhongTro();
        pt.setTenPhong(edtTenPhong.getText().toString());
        pt.setTinhTrang(edtTinhTrang.getText().toString());
        pt.setSoNguoiToiDa(Integer.parseInt(edtSoNguoiToiDa.getText().toString()));
        pt.setGiaPhong(Integer.parseInt(edtGiaPhong.getText().toString()));
        KhuVucPhong kvp=(KhuVucPhong)spKhuVuc.getSelectedItem();
        pt.setMaKV(kvp.getMaKV());
        ThemPhongTroTask task=new ThemPhongTroTask();
        task.execute(pt);

    }

    class DanhSachCacKhuVucPhongTask extends AsyncTask<Void,Void, ArrayList<KhuVucPhong>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<KhuVucPhong> khuVucPhongs) {
            super.onPostExecute(khuVucPhongs);
            khuVucPhongAdapter.clear();
            khuVucPhongAdapter.addAll(khuVucPhongs);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<KhuVucPhong> doInBackground(Void... voids) {
            ArrayList<KhuVucPhong> dskvp =new ArrayList<>();
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/DanhSachKhuVuc");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("KhuVucPhong");
                for(int i=0;i<dsNote.getLength();i++)
                {
                    Element elementKVP=(Element)dsNote.item(i);
                    int makv= Integer.parseInt(elementKVP.getElementsByTagName("MaKV").item(0).getTextContent());
                    String tenkv=elementKVP.getElementsByTagName("TenKV").item(0).getTextContent();
                    String tang=elementKVP.getElementsByTagName("Tang").item(0).getTextContent();
                    String tendayphong=elementKVP.getElementsByTagName("TenDayPhong").item(0).getTextContent();
                    int soluongphong= Integer.parseInt(elementKVP.getElementsByTagName("SoLuongPhong").item(0).getTextContent());
                    String tinhtrang=elementKVP.getElementsByTagName("TinhTrang").item(0).getTextContent();
                    KhuVucPhong kvp=new KhuVucPhong();
                    kvp.setMaKV(makv);
                    kvp.setTenKV(tenkv);
                    kvp.setSoLuongPhong(soluongphong);
                    kvp.setTang(tang);
                    kvp.setTenDayPhong(tendayphong);
                    kvp.setTinhTrang(tinhtrang);

                    dskvp.add(kvp);
                }

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return dskvp;
        }
    }
    class ThemPhongTroTask extends AsyncTask<PhongTro,Void,Boolean>
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
                Toast.makeText(ThemPhongTroActivity.this,"Them phong tro thanh cong",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(ThemPhongTroActivity.this,"Them phong tro that bai",Toast.LENGTH_LONG).show();
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
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/ThemPhongTro");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                PhongTro pt=phongTros[0];

                String params="makv="+pt.getMaKV()+"&tenphong="+URLEncoder.encode(pt.getTenPhong())+"&giaphong="+pt.getGiaPhong()+"&songuoitoida="+pt.getSoNguoiToiDa()+"&tinhtrang="+ URLEncoder.encode(pt.getTinhTrang());
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

package com.example.quanlynhatrof;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.model.DichVu;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DichVuActivity extends AppCompatActivity {
    ImageButton btnThemDichVu;
    Button btnTroVe;
    ListView lvDichVu;
    ArrayAdapter<DichVu> dichVuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dich_vu);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnThemDichVu=findViewById(R.id.btnThemDichVu);
        btnTroVe=findViewById(R.id.btnTroVe);
        lvDichVu=findViewById(R.id.lvDichVu);
        dichVuAdapter=new ArrayAdapter<DichVu>(DichVuActivity.this,android.R.layout.simple_list_item_1);
        lvDichVu.setAdapter(dichVuAdapter);
        DanhSachDichVuTask task=new DanhSachDichVuTask();
        task.execute();

    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DichVuActivity.this,ChucNangActivity.class);
                startActivity(intent);
            }
        });
        btnThemDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DichVuActivity.this,ThemDichVuActivity.class);
                startActivity(intent);
            }
        });
        lvDichVu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DichVu dv=dichVuAdapter.getItem(position);
                moManHinhSuaDichVu(dv);
            }
        });
        lvDichVu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DichVu dv=dichVuAdapter.getItem(position);
                xuLyXoaDichVu(dv);
                return true;
            }
        });

    }

    private void xuLyXoaDichVu(final DichVu dv) {
        AlertDialog.Builder builder=new AlertDialog.Builder(DichVuActivity.this);
        builder.setTitle("Xac nhan xoa?");
        builder.setMessage("Ban co chac chan muon xoa ["+dv.getTenDV()+"]?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XoaDichVuTask task=new XoaDichVuTask();
                task.execute(dv);
                dialog.dismiss();
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

    private void moManHinhSuaDichVu(DichVu dv) {
                Intent intent=new Intent(DichVuActivity.this,SuaDichVuActivity.class);
                intent.putExtra("DICHVU",dv);
                startActivity(intent);
    }

    class DanhSachDichVuTask extends AsyncTask<Void,Void,ArrayList<DichVu>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<DichVu> dichVus) {
            super.onPostExecute(dichVus);
            dichVuAdapter.clear();
            dichVuAdapter.addAll(dichVus);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<DichVu> doInBackground(Void... voids) {
            ArrayList<DichVu> dsdv=new ArrayList<>();
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/DanhSachDichVu");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("DichVu");
                for(int i=0;i<dsNote.getLength();i++)
                {
                    Element nodeDV=(Element)dsNote.item(i);
                    int madv= Integer.parseInt(nodeDV.getElementsByTagName("MaDV").item(0).getTextContent());
                    String tendv=nodeDV.getElementsByTagName("TenDV").item(0).getTextContent();
                    int dongia= Integer.parseInt(nodeDV.getElementsByTagName("DonGiaDV").item(0).getTextContent());
                    String ghichu=nodeDV.getElementsByTagName("GhiChu").item(0).getTextContent();
                    DichVu dv=new DichVu();
                    dv.setMaDV(madv);
                    dv.setDonGiaDV(dongia);
                    dv.setTenDV(tendv);
                    dv.setGhiChu(ghichu);
                    dsdv.add(dv);

                }

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return dsdv;
        }
    }
    class XoaDichVuTask extends AsyncTask<DichVu,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean.booleanValue()==true)
            {
                Toast.makeText(DichVuActivity.this,"Xoa dich vu thanh cong",Toast.LENGTH_LONG).show();
                DanhSachDichVuTask task=new DanhSachDichVuTask();
                task.execute();
            }
            else
            {
                Toast.makeText(DichVuActivity.this,"Xoa dich vu that bai",Toast.LENGTH_LONG).show();
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
                URL url=new URL("192.168.1.128//quanlynhatro1/QUANLYNHATRO1.asmx/XoaDichVu");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write("madv="+dichVus[0].getMaDV());
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

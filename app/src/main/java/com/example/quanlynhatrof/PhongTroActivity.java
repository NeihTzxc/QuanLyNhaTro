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

import com.example.model.PhongTro;

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

public class PhongTroActivity extends AppCompatActivity {
    ImageButton btnThemPhongTro;
    Button btnTroVe;
    ListView lvPhongTro;
    ArrayAdapter<PhongTro> phongTroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_tro);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnThemPhongTro=findViewById(R.id.btnThemPhongTro);
        btnTroVe=findViewById(R.id.btnTroVe);
        lvPhongTro=findViewById(R.id.lvPhongTro);
        phongTroAdapter=new ArrayAdapter<PhongTro>(PhongTroActivity.this,android.R.layout.simple_list_item_1);
        lvPhongTro.setAdapter(phongTroAdapter);
        DanhSachToanBoPhongTroTask task =new DanhSachToanBoPhongTroTask();
        task.execute();



    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(PhongTroActivity.this,ChucNangActivity.class);
                startActivity(intent);
            }
        });
        btnThemPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(PhongTroActivity.this,ThemPhongTroActivity.class);
                startActivity(intent);
            }
        });
        lvPhongTro.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PhongTro pt=phongTroAdapter.getItem(position);
                xuLyXoaPhongTro(pt);
                return true;
            }
        });
        lvPhongTro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhongTro pt=phongTroAdapter.getItem(position);
                moManHinhSuaPhongTro(pt);
            }
        });

    }

    private void moManHinhSuaPhongTro(PhongTro pt) {
                Intent intent=new Intent(PhongTroActivity.this,SuaPhongTroActivity.class);
                intent.putExtra("PHONGTRO",pt);
                startActivity(intent);
    }

    private void xuLyXoaPhongTro(final PhongTro pt) {
        AlertDialog.Builder builder=new AlertDialog.Builder(PhongTroActivity.this);
        builder.setTitle("Xac nhan xoa");
        builder.setMessage("Ban co chac chan muon xoa phon:["+pt.getTenPhong()+"]?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XoaPhongTroTask task=new XoaPhongTroTask();
                task.execute(pt);
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

    class DanhSachToanBoPhongTroTask extends AsyncTask<Void,Void, ArrayList<PhongTro>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<PhongTro> phongTros) {
            super.onPostExecute(phongTros);
            phongTroAdapter.clear();
            phongTroAdapter.addAll(phongTros);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<PhongTro> doInBackground(Void... voids) {
            ArrayList<PhongTro> dspt=new ArrayList<>();
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/DSPT");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("uspDanhSachPhongTroResult");
                        for(int i=0;i<dsNote.getLength();i++)
                        {
                            Element nodePT=(Element)dsNote.item(i);
                            int maphong= Integer.parseInt(nodePT.getElementsByTagName("MaPhong").item(0).getTextContent());
                            String tenphong=nodePT.getElementsByTagName("TenPhong").item(0).getTextContent();
                            String tenkv=nodePT.getElementsByTagName("TenKV").item(0).getTextContent();
                            int giaphong= Integer.parseInt(nodePT.getElementsByTagName("GiaPhong").item(0).getTextContent());
                            int songuoitoida= Integer.parseInt(nodePT.getElementsByTagName("SoNguoiToiDa").item(0).getTextContent());
                            String tinhtrang=nodePT.getElementsByTagName("TinhTrang").item(0).getTextContent();
                            PhongTro pt=new PhongTro();
                            pt.setMaPhong(maphong);
                            pt.setTenPhong(tenphong);
                            pt.setTenKV(tenkv);
                            pt.setGiaPhong(giaphong);
                            pt.setSoNguoiToiDa(songuoitoida);
                            pt.setTinhTrang(tinhtrang);
                            dspt.add(pt);


                        }

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return dspt;
        }
    }
    class  XoaPhongTroTask extends AsyncTask<PhongTro,Void,Boolean>
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

                DanhSachToanBoPhongTroTask task =new DanhSachToanBoPhongTroTask();
                task.execute();
            }
            else
            {
                Toast.makeText(PhongTroActivity.this,"Xoa Phong Tro That Bai",Toast.LENGTH_LONG).show();
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
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/XoaPhongtro");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write("maphongtro="+phongTros[0].getMaPhong());
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

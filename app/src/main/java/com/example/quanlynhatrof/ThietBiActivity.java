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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.model.ThietBi;

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

public class ThietBiActivity extends AppCompatActivity {
    ArrayAdapter<ThietBi> thietBiAdapter;
    Button btnTroVe;
    ImageButton btnThemThietBi;
    ListView lvThietBi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThemThietBi=findViewById(R.id.btnThemThietBi);
        lvThietBi=findViewById(R.id.lvThietBi);
        thietBiAdapter=new ArrayAdapter<ThietBi>(ThietBiActivity.this,android.R.layout.simple_list_item_1);
        lvThietBi.setAdapter(thietBiAdapter);
        DanhSachThietBiTask task=new DanhSachThietBiTask();
        task.execute();


    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThietBiActivity.this,ChucNangActivity.class);
                startActivity(intent);
            }
        });
        btnThemThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThietBiActivity.this,ThemThietBiActivity.class);
                startActivity(intent);
            }
        });
        lvThietBi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ThietBi tb=thietBiAdapter.getItem(position);
                xuLyXoaThietBi(tb);
                return true;
            }
        });
        lvThietBi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ThietBi tb=thietBiAdapter.getItem(position);
                moManHinhSuaThietBi(tb);
            }
        });


    }

    private void moManHinhSuaThietBi(ThietBi tb) {
        Intent intent=new Intent(ThietBiActivity.this,SuaThietBiActivity.class);
        intent.putExtra("THIETBI",tb);
        startActivity(intent);

    }

    private void xuLyXoaThietBi(final ThietBi tb) {
        AlertDialog.Builder builder=new AlertDialog.Builder(ThietBiActivity.this);
        builder.setTitle("Xac nhan xoa");
        builder.setMessage("Ban co chac muon xoa ["+tb.getTenThietBi()+"]?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XoaThietBiTask task=new XoaThietBiTask();
                task.execute(tb);
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

    class DanhSachThietBiTask extends AsyncTask<Void,Void,ArrayList<ThietBi>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<ThietBi> thietBis) {
            super.onPostExecute(thietBis);
            thietBiAdapter.clear();
            thietBiAdapter.addAll(thietBis);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<ThietBi> doInBackground(Void... voids) {
            ArrayList<ThietBi> dstb=new ArrayList<>();
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/DanhSachThietBi");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("ThietBi");
                for(int i=0;i<dsNote.getLength();i++)
                {
                    Element nodeTB=(Element)dsNote.item(i);
                    int ma= Integer.parseInt(nodeTB.getElementsByTagName("MaTB").item(0).getTextContent());
                    String ten=nodeTB.getElementsByTagName("TenThietBi").item(0).getTextContent();
                    Integer soluong= Integer.valueOf(nodeTB.getElementsByTagName("SoLuong").item(0).getTextContent());
                    String tinhtrang=nodeTB.getElementsByTagName("TinhTrang").item(0).getTextContent();
                    ThietBi tb=new ThietBi();
                    tb.setMaTB(ma);
                    tb.setTenThietBi(ten);
                    tb.setSoLuong(soluong);
                    tb.setTinhTrang(tinhtrang);
                    dstb.add(tb);
                }

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());

            }
            return dstb;
        }
    }
    class XoaThietBiTask extends AsyncTask<ThietBi,Void,Boolean>
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

                Toast.makeText(ThietBiActivity.this,"Xoa thiet bi thanh cong",Toast.LENGTH_LONG).show();
                ThietBiActivity.DanhSachThietBiTask task=new ThietBiActivity.DanhSachThietBiTask();
                task.execute();

            }
            else
            {
                Toast.makeText(ThietBiActivity.this,"Xoa thiet bi that bai",Toast.LENGTH_LONG).show();

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
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/XoaThietBi");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write("mathietbi="+thietBis[0].getMaTB());
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
                Log.e("Loi", ex.toString());

            }
            return false;
        }
    }
}

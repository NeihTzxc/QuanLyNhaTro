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

import com.example.model.KhachHang;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class KhachHangActivity extends AppCompatActivity {
    ImageButton btnThemKhachHang;
    Button btnTroVe;
    ListView lvKhachHang;
    ArrayAdapter<KhachHang> khachHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThemKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KhachHangActivity.this,ThemKhachHangActivity.class);
                startActivity(intent);
            }
        });
                lvKhachHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KhachHang kh=khachHangAdapter.getItem(position);
                moManHinhSuaKhachHang(kh);
            }
        });

                lvKhachHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        KhachHang kh=khachHangAdapter.getItem(position);
                        xuLyXoaKhachHang(kh);
                        return true;
                    }
                });
                btnTroVe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(KhachHangActivity.this,ChucNangActivity.class);
                        startActivity(intent);
                    }
                });

    }

    private void xuLyXoaKhachHang(final KhachHang kh) {
        AlertDialog.Builder builder=new AlertDialog.Builder(KhachHangActivity.this);
        builder.setTitle("Xác nhận xóa?");
        builder.setMessage("Bạn có chắc chắc muốn xóa ["+kh.getHoTen()+"]?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XoaKhachHangTask task=new XoaKhachHangTask();
                task.execute(kh);
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


    private void addControls() {
        lvKhachHang=findViewById(R.id.lvKhachHang);
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThemKhachHang=findViewById(R.id.btnThemKhachHang);
        khachHangAdapter=new ArrayAdapter<KhachHang>(KhachHangActivity.this,android.R.layout.simple_list_item_1);
        lvKhachHang.setAdapter(khachHangAdapter);
        DanhSachKhachHangTask task =new DanhSachKhachHangTask();
        task.execute();
    }
    private void moManHinhSuaKhachHang(KhachHang kh) {
        Intent intent =new Intent(KhachHangActivity.this,SuaKhachHangActivity.class);
        intent.putExtra("KHACHHANG",kh);

        startActivity(intent);
    }
    class DanhSachKhachHangTask extends  AsyncTask<Void,Void,ArrayList<KhachHang>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<KhachHang> khachHangs) {
            super.onPostExecute(khachHangs);
            khachHangAdapter.clear();
            khachHangAdapter.addAll(khachHangs);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<KhachHang> doInBackground(Void... voids) {
            ArrayList<KhachHang> dskh=new ArrayList<>();
            try
            {

                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/DanhSachKhachHang");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("KhachHang");
                for(int i=0;i<dsNote.getLength();i++)
                {
                    Element noteKH= (Element) dsNote.item(i);
                    int ma= Integer.parseInt(noteKH.getElementsByTagName("MaKH").item(0).getTextContent());
                    String hoten=noteKH.getElementsByTagName("HoTen").item(0).getTextContent();
                    String cmnd=noteKH.getElementsByTagName("CMND").item(0).getTextContent();
                    String sdt=noteKH.getElementsByTagName("SDT").item(0).getTextContent();
                    String diachi=noteKH.getElementsByTagName("DiaChi").item(0).getTextContent();
                    String nghenghiep=noteKH.getElementsByTagName("NgheNghiep").item(0).getTextContent();
                    String email=noteKH.getElementsByTagName("Email").item(0).getTextContent();
                    KhachHang kh=new KhachHang();
                    kh.setMaKH(ma);
                    kh.setHoTen(hoten);
                    kh.setCMND(cmnd);
                    kh.setSDT(sdt);
                    kh.setDiaChi(diachi);
                    kh.setNgheNghiep(nghenghiep);
                    kh.setEmail(email);
                    dskh.add(kh);

                }

            }
            catch (Exception e)
            {
                Log.e("Loi",e.toString());
            }
            return dskh;
        }
    }
    class  XoaKhachHangTask extends AsyncTask<KhachHang,Void,Boolean>
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
                DanhSachKhachHangTask task=new DanhSachKhachHangTask();
                task.execute();

            }
            else
            {
                Toast.makeText(KhachHangActivity.this,"Xóa sản phẩm thất bại!",Toast.LENGTH_LONG).show();
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
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/XoaKhachHang");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write("makh="+khachHangs[0].getMaKH());
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

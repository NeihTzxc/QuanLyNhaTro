package com.example.quanlynhatrof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnThoat;
    TextView tvKq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xyLyDangNhap();
//              Intent intent = new Intent(MainActivity.this,ChucNangActivity.class);
//                startActivity(intent);
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
                System.exit(0);
            }
        });

    }

    private void xyLyDangNhap() {
            DangNhapTask task=new DangNhapTask();
            task.execute(edtTaiKhoan.getText().toString(),edtMatKhau.getText().toString());
    }

    private void addControls() {
        edtTaiKhoan=findViewById(R.id.edtTaiKhoan);
        edtMatKhau=findViewById(R.id.edtMatKhau);
        btnDangNhap=findViewById(R.id.btnDangNhap);
        btnThoat=findViewById(R.id.btnThoat);
       // tvKq=findViewById(R.id.tvKetQua);

    }
    class DangNhapTask extends AsyncTask<String,Void,Integer>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer!=null)
            {
                int kq=integer.intValue();
               // tvKq.setText(integer.intValue()+"");
                if (kq>0)
                {
                    Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,ChucNangActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Sai tai khoan hoac mat khau",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
               tvKq.setText("Loi");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                String ip1=getString(R.string.ip);
                String ip=getString(R.string.ip);
                String u=ip1+"/quanlynhatro1/QUANLYNHATRO1.asmx/DangNhap";
                URL url=new URL(u);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                OutputStream os =connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write("taikhoan="+ URLEncoder.encode(strings[0])+"&matkhau="+URLEncoder.encode(strings[0]));
                osw.flush();
                osw.close();

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());
                NodeList dsNote=document.getElementsByTagName("int");
                String s=dsNote.item(0).getTextContent();
                int kq=Integer.parseInt(s);
                return kq;



            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return null;
        }
    }
}

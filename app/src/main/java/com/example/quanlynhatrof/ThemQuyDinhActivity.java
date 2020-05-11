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

import com.example.model.QuyDinh;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ThemQuyDinhActivity extends AppCompatActivity {
    Button btnThemQuyDinh, btnTroVe;
    EditText edtTenQuyDinh;
    EditText edtNoiDung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_quy_dinh);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ThemQuyDinhActivity.this,QuyDinhActivity.class);
                startActivity(intent);
            }
        });
        btnThemQuyDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xyLyThemQuyDinh();
            }
        });

    }

    private void xyLyThemQuyDinh()
    {
QuyDinh qd=new QuyDinh();
qd.setTenQuyDinh(edtTenQuyDinh.getText().toString());
qd.setNoiDung(edtNoiDung.getText().toString());
ThemQuyDinhTask task=new ThemQuyDinhTask();
task.execute(qd);
    }

    private void addControls() {
        btnThemQuyDinh=findViewById(R.id.btnThemQuyDinh);
        btnTroVe=findViewById(R.id.btnTroVe);
        edtTenQuyDinh=findViewById(R.id.edtTenQuyDinh);
        edtNoiDung=findViewById(R.id.edtNoiDung);

    }
    class ThemQuyDinhTask extends AsyncTask<QuyDinh,Void,Boolean>
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
    Toast.makeText(ThemQuyDinhActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();

}
else
{
    Toast.makeText(ThemQuyDinhActivity.this,"Thêm không thành công",Toast.LENGTH_LONG).show();
}
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(QuyDinh... quyDinhs) {
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/ThemQuyDinh");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                QuyDinh qd=quyDinhs[0];

                String params="tenquydinh="+ URLEncoder.encode(qd.getTenQuyDinh()) +"&noidung="+URLEncoder.encode(qd.getNoiDung());
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

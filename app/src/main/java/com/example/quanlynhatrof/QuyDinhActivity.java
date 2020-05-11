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

import com.example.model.QuyDinh;

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

public class QuyDinhActivity extends AppCompatActivity {
    ArrayAdapter<QuyDinh> quyDinhAdapter;
    ImageButton btnThemQuyDinh;
    Button btnTroVe;
    ListView lvQuyDinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quy_dinh);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuyDinhActivity.this,ChucNangActivity.class);
                startActivity(intent);
            }
        });
        btnThemQuyDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuyDinhActivity.this,ThemQuyDinhActivity.class);
                startActivity(intent);
            }
        });
lvQuyDinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
QuyDinh qd=quyDinhAdapter.getItem(position);
moManHinhSuaQuyDinh(qd);
    }
});
lvQuyDinh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        QuyDinh qd=quyDinhAdapter.getItem(position);
        xuLyXoaQuyDinh(qd);
        return true;
    }
});

    }

    private void xuLyXoaQuyDinh(final QuyDinh qd) {
        AlertDialog.Builder builder=new AlertDialog.Builder(QuyDinhActivity.this);
        builder.setTitle("Xac nhan xoa?");
        builder.setMessage("Ban co chac chan muon xoa ["+qd.getTenQuyDinh()+"]?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XoaQuyDinhTask task=new XoaQuyDinhTask();
                task.execute(qd);
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


    private void moManHinhSuaQuyDinh(QuyDinh qd) {
Intent intent=new Intent(QuyDinhActivity.this,SuaQuyDinhActivity.class);
intent.putExtra("QUYDINH",qd);
startActivity(intent);
    }


    private void addControls() {
        lvQuyDinh=findViewById(R.id.lvQuyDinh);
        quyDinhAdapter =new ArrayAdapter<QuyDinh>(QuyDinhActivity.this,android.R.layout.simple_list_item_1);
        lvQuyDinh.setAdapter(quyDinhAdapter);
        DanhSachQuyDinhTask task=new DanhSachQuyDinhTask();
        task.execute();
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThemQuyDinh=findViewById(R.id.btnThemQuyDinh);


    }
    class DanhSachQuyDinhTask extends AsyncTask<Void,Void,ArrayList<QuyDinh>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<QuyDinh> quyDinhs) {
            super.onPostExecute(quyDinhs);
            quyDinhAdapter.clear();
            quyDinhAdapter.addAll(quyDinhs);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<QuyDinh> doInBackground(Void... voids) {
            ArrayList<QuyDinh> dsqd=new ArrayList<>();
            try
            {
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/DanhSachQuyDinh");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                DocumentBuilder builder=factory.newDocumentBuilder();
                Document document=builder.parse(connection.getInputStream());

                NodeList dsNote=document.getElementsByTagName("QuyDinh");
                for(int i=0;i<dsNote.getLength();i++)
                {
                    Element nodeQD=(Element)dsNote.item(i);
                    int ma= Integer.parseInt(nodeQD.getElementsByTagName("MaQuyDinh").item(0).getTextContent());
                    String ten=nodeQD.getElementsByTagName("TenQuyDinh").item(0).getTextContent();
                    String noidung=nodeQD.getElementsByTagName("NoiDung").item(0).getTextContent();
                    QuyDinh qd=new QuyDinh();
                    qd.setMaQuyDinh(ma);
                    qd.setTenQuyDinh(ten);
                    qd.setNoiDung(noidung);
                    dsqd.add(qd);

                }

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }
            return dsqd;
        }
    }
    class XoaQuyDinhTask extends AsyncTask<QuyDinh,Void,Boolean>
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
              DanhSachQuyDinhTask task=new DanhSachQuyDinhTask();
              task.execute();

            }
            else
            {
                Toast.makeText(QuyDinhActivity.this,"Xoa quy dinh that bai",Toast.LENGTH_LONG).show();

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
                URL url=new URL("http://192.168.1.128/quanlynhatro1/QUANLYNHATRO1.asmx/XoaQuyDinh");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                OutputStream os=connection.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os,"UTF-8");
                osw.write("maquydinh="+quyDinhs[0].getMaQuyDinh());
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

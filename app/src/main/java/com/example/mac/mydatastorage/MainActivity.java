package com.example.mac.mydatastorage;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private File sdroot,apport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
        }else{
            init();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==123){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                init();
            }else {
                finish();
            }
        }
    }

    public void init(){
        sp=getSharedPreferences("oneone",MODE_PRIVATE); //oneone.xml
        editor =sp.edit();

        sdroot = Environment.getExternalStorageDirectory();

        //Log.i("test",sp.getAbsolutePath());
        apport=new File(sdroot,"Android/data/" + getPackageName() +"/");
        if(!apport.exists()) {
            apport.mkdirs();
        }
    }
    public void test1(View view){
        editor.putString("username","test");
        editor.putInt("one1",5);
        editor.putBoolean("boolean",true);
        editor.commit();
        Toast.makeText(this,"Save Ok",Toast.LENGTH_SHORT).show();
    }

    public void test2(View view){
        Boolean sound = sp.getBoolean("sound",true);
        String username = sp.getString("username","guest");
        Log.i("test",""+sound);
        Log.i("test",""+username);
    }

    public void test3(View view){
        try(FileOutputStream fout = openFileOutput("data.txt",MODE_PRIVATE)){
            fout.write("Hello,World".getBytes());
            fout.flush();
            Toast.makeText(this,"Save OK",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.i("test",e.toString());
        }

    }

    public  void test4(View view){
        try(FileInputStream fin =  openFileInput("data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin))){
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null){
                sb.append(line + "\n");
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.i("test",e.toString());
        }
    }
    public void test5(View view){
        File file = new File(sdroot,"sile1.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write("ok".getBytes());
            fout.flush();
            fout.close();
        }catch (Exception e){

        }
    }
    public void test6(View view){
        File file = new File(apport,"sile1.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write("ok".getBytes());
            fout.flush();
            fout.close();
        }catch (Exception e){

        }
    }
}

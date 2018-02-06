package com.example.tmd_rahul.qrcodescannerandgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button scan, gen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan = (Button) findViewById(R.id.scan_btn);
        gen = (Button) findViewById(R.id.gen_btn);
        scan.setOnClickListener(MainActivity.this);
        gen.setOnClickListener(MainActivity.this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.scan_btn){
            Intent i = new Intent (MainActivity.this,QRCodeScanner.class);
            startActivity(i);
        } else {
            Intent i = new Intent (MainActivity.this,QRCodeGeneratorCode.class);
            startActivity(i);
        }
    }
}

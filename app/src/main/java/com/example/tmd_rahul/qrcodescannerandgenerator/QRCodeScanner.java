package com.example.tmd_rahul.qrcodescannerandgenerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRCodeScanner extends AppCompatActivity {
    final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
    Button startQrScanning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);
        startQrScanning = (Button) findViewById(R.id.button);
        startQrScanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(QRCodeScanner.this).initiateScan(); // `this` is the current Activity
            }
        });
        new IntentIntegrator(QRCodeScanner.this).initiateScan(); // `this` is the current Activity
    }
    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {

                Toast.makeText(this, "Cancelled. Please rey again ",  Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Pattern p = Pattern.compile(URL_REGEX);
                Matcher m = p.matcher(result.getContents());//replace with string to compare
                if(m.find()) {
                    String url = result.getContents();
                    if(url.contains("http://") || url.contains("https://")) {

                    } else {
                        url=  "http://"+url;
                    }
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    String url = result.getContents();
                                    if(url.contains("http://") || url.contains("https://")) {

                                    } else {
                                        url=  "http://"+url;
                                    }
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScanner.this);
                    builder.setMessage("Do you want to go to Website ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                } else {
                    Toast.makeText(QRCodeScanner.this, "Url not found. Please rey again ",  Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
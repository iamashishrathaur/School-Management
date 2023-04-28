package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class FullShowStudyMaterials extends AppCompatActivity {

    PDFView pdfView;
    String url;
    Dialog dialogbox;
    RelativeLayout back;
    TextView name;
    RelativeLayout download;
    String filename;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_show_study_materials);
        Objects.requireNonNull(getSupportActionBar()).hide();
        pdfView=findViewById(R.id.full_pdfview);
        back=findViewById(R.id.pdf_name_show_back_pressed);
        name=findViewById(R.id.pdf_name_show);
        download=findViewById(R.id.student_pdf_download);
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        Intent intent=getIntent();
        url=intent.getStringExtra("pdfUri");
        new PdfView().execute(url);
        dialogbox.show();
        filename=intent.getStringExtra("pdfName");
        name.setText(filename);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,1000);
                    }else {
                        dialogbox.show();
                         new Handler().postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                 dialogbox.dismiss();
                                 Toast.makeText(FullShowStudyMaterials.this, "Downloading...", Toast.LENGTH_SHORT).show();
                                 startDownloading();
                             }
                         },1000);
                    }
                }
            }
        });

    }

    private class PdfView extends AsyncTask<String ,Void,InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
           InputStream inputStream=null;
            try {
                URL Url=new URL(strings[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) Url.openConnection();
                if (httpURLConnection.getResponseCode() ==200){
                    inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
                }

            } catch (ConnectException exception){
                Toast.makeText(FullShowStudyMaterials.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                  dialogbox.dismiss();
                }
            }).defaultPage(0).scrollHandle(new DefaultScrollHandle(getApplicationContext())).spacing(10).load();

        }
    }
    private void startDownloading() {
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI );
        request.setTitle("Download");
        request.setDescription("Downloading file...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+filename+".pdf");
        DownloadManager manager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
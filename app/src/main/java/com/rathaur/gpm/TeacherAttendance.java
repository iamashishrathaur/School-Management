package com.rathaur.gpm;

import static java.lang.Math.min;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.WriterException;
import java.util.Objects;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class TeacherAttendance extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ImageView qrCodeIv = findViewById(R.id.iv_generate_gr_code);
        TextView subjectTv=findViewById(R.id.attendance_qr_subject);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        WindowManager manager= (WindowManager) getSystemService(WINDOW_SERVICE);
        Intent intent=getIntent();
        String subject=intent.getStringExtra("subject");
        Display display=manager.getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        int width=point.x;
        int height =point.y;
        int dimen= min(width, height);
        dimen=dimen*3/4;
        subjectTv.setText(subject);
        QRGEncoder qrgEncoder = new QRGEncoder(subject, null, QRGContents.Type.TEXT, dimen);
        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            qrCodeIv.setImageBitmap(bitmap);

        }catch (WriterException e){
            e.printStackTrace();
        }

    }

}
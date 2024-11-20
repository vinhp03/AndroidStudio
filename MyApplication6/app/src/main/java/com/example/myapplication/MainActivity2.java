package com.example.myapplication;

import static com.example.myapplication.R.id.btnSave;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    Button btnSave, btnCancel;
    EditText editName, editPhone;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        editName=(EditText)findViewById(R.id.editHoTen);
        editPhone=(EditText)findViewById(R.id.editSdt);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        getContact();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });
    }
    public void getContact()
    {
        Intent intent=getIntent();
        Contact ct=(Contact) intent.getSerializableExtra("CONTACT");
        assert ct != null;
        editName.setText(ct.getHoTen());
        editPhone.setText(ct.getSoDT());
    }


    //hàm lưu thông tin
    public void saveContact() {
        // Lấy dữ liệu từ các EditText
        String hoTen = editName.getText().toString();
        String soDT = editPhone.getText().toString();
        Contact updatedContact = new Contact(hoTen, soDT);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("CONTACT", updatedContact);
        // Gửi kết quả về Activity trước đó
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
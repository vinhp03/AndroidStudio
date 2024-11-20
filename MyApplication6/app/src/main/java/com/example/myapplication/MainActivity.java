package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Các khai báo biến tương tự như trước
    EditText editHoTen, editSdt, editSearch;
    Button btnAdd, btnSearch,btnReset;
    ListView lstContact;
    Contact contact;
    int vitri;
    ArrayList<Contact> arrayList1;
    ArrayAdapter<Contact> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        anhXa();

        arrayList1 = new ArrayList<>();
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList1);
        lstContact.setAdapter(adapter1);

        registerForContextMenu(lstContact);

        // Xử lý sự kiện thêm và tìm kiếm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContact();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSearch();
            }
        });

        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vitri = position;
                contact = (Contact) parent.getItemAtPosition(position);
                return false;
            }
        });
    }

    public void anhXa() {
        editHoTen = findViewById(R.id.editHoten);
        editSdt = findViewById(R.id.editSdt);
        editSearch = findViewById(R.id.editSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnSearch = findViewById(R.id.btnSearch);
        btnReset = findViewById(R.id.btnReset);
        lstContact = findViewById(R.id.lstDanhBa);
    }

    public void saveContact() {
        String ht = editHoTen.getText().toString();
        String sdt = editSdt.getText().toString();
        Contact ct = new Contact(ht, sdt);

        if (!arrayList1.contains(ct)) {
            arrayList1.add(ct);
            adapter1.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Trùng thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchContact() {
        String query = editSearch.getText().toString().toLowerCase();
        ArrayList<Contact> filteredList = new ArrayList<>();

        for (Contact ct : arrayList1) {
            if (ct.getHoTen().toLowerCase().contains(query) || ct.getSoDT().contains(query)) {
                filteredList.add(ct);
            }
        }

        ArrayAdapter<Contact> searchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
        lstContact.setAdapter(searchAdapter);
    }
    public void resetSearch() {
        editHoTen.setText("");
        editSdt.setText("");
        editSearch.setText("");// Reset EditText
        lstContact.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    // Gửi thông tin liên hệ sang MainActivity2 để chỉnh sửa
    public void sendContact() {
        Contact ct = arrayList1.get(vitri);  // Lấy đối tượng Contact tại vị trí đã chọn
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("CONTACT", ct);  // Truyền đối tượng Contact qua Intent
        startActivityForResult(intent, 1);  // Khởi chạy MainActivity2 và chờ kết quả
    }

    // Xóa liên hệ
    public void deleteContact() {
        arrayList1.remove(contact);
        adapter1.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            sendContact();
        } else if (item.getItemId() == R.id.delete) {
            deleteContact();
        } else if (item.getItemId() == R.id.call) {
            call(contact.getSoDT());
        }
        return super.onContextItemSelected(item);
    }

    // Phương thức gọi điện
    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    // Nhận kết quả chỉnh sửa từ MainActivity2
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Contact updatedContact = (Contact) data.getSerializableExtra("CONTACT");
            if (updatedContact != null) {
                // Cập nhật thông tin liên hệ tại vị trí vitri
                arrayList1.set(vitri, updatedContact);
                // Cập nhật lại danh sách hiển thị
                adapter1.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

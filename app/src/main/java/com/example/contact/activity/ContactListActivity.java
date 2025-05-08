package com.example.contact.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contact.R;
import com.example.contact.adapter.ContactAdapter;
import com.example.contact.db.ContactDatabaseHelper;
import com.example.contact.model.Contact;
import com.example.contact.ThemeUtils.ThemeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContactAdapter adapter;
    ContactDatabaseHelper dbHelper;
    FloatingActionButton fab;
    Button profileBtn, themeToggleBtn;

    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        profileBtn = findViewById(R.id.profileBtn);
        themeToggleBtn = findViewById(R.id.themeToggleBtn);

        userEmail = getIntent().getStringExtra("email");

        dbHelper = new ContactDatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Contact> contacts = dbHelper.getAllContacts();
        adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v ->
                startActivity(new Intent(this, AddContactActivity.class)));

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("email", userEmail); // ✅ передаём email пользователя
            startActivity(intent);
        });

        themeToggleBtn.setOnClickListener(v ->
                ThemeUtils.toggleTheme(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateContacts(dbHelper.getAllContacts());
    }
}

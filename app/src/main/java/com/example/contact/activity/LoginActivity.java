package com.example.contact.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.contact.R;
import com.example.contact.db.ContactDatabaseHelper;
import com.example.contact.ThemeUtils.ThemeUtils;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login, register;
    ContactDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.applyTheme(this); // ✅ Подключаем тему
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new ContactDatabaseHelper(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(v -> {
            if (dbHelper.loginUser(email.getText().toString(), password.getText().toString())) {
                Intent intent = new Intent(this, ContactListActivity.class);
                intent.putExtra("email", email.getText().toString()); // передаём email
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Ошибка входа", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}

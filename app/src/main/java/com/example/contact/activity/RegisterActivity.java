package com.example.contact.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.contact.R;
import com.example.contact.db.ContactDatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText email, password;
    Button register;
    ContactDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new ContactDatabaseHelper(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        register.setOnClickListener(v -> {
            if (dbHelper.registerUser(email.getText().toString(), password.getText().toString())) {
                Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

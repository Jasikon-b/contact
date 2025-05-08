package com.example.contact.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.contact.R;
import com.example.contact.ThemeUtils.ThemeUtils;

public class ProfileActivity extends AppCompatActivity {

    ImageView profileImage;
    TextView profileName, profileEmail, profileDescription;
    Button logoutBtn, backBtn, themeToggleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profileImage);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileDescription = findViewById(R.id.profileDescription);
        logoutBtn = findViewById(R.id.logoutBtn);
        backBtn = findViewById(R.id.backBtn);
        themeToggleBtn = findViewById(R.id.themeToggleBtn);

        String email = getIntent().getStringExtra("email");
        profileName.setText("Jasulan");
        profileEmail.setText(email != null ? email : "no-email@example.com");
        profileDescription.setText("Привет! Это мой контакт-профиль в приложении.");

        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactListActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        });

        themeToggleBtn.setOnClickListener(v -> ThemeUtils.toggleTheme(this));
    }
}

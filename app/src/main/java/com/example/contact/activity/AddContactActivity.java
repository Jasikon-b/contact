package com.example.contact.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.example.contact.R;
import com.example.contact.db.ContactDatabaseHelper;
import com.example.contact.model.Contact;
import com.example.contact.ThemeUtils.ThemeUtils;

public class AddContactActivity extends AppCompatActivity {

    EditText idEdit, nameEdit, phoneEdit, emailEdit;
    Button addBtn, updateBtn, deleteBtn;
    ContactDatabaseHelper dbHelper;

    private static final String CHANNEL_ID = "contact_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        dbHelper = new ContactDatabaseHelper(this);

        idEdit = findViewById(R.id.contactId);
        nameEdit = findViewById(R.id.name);
        phoneEdit = findViewById(R.id.phone);
        emailEdit = findViewById(R.id.email);

        addBtn = findViewById(R.id.addBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        addBtn.setOnClickListener(v -> {
            String name = nameEdit.getText().toString().trim();
            String phone = phoneEdit.getText().toString().trim();
            String email = emailEdit.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Заполни все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            Contact contact = new Contact(0, name, phone, email);
            dbHelper.addContact(contact);

            showNotification();

            Toast.makeText(this, "Контакт добавлен", Toast.LENGTH_SHORT).show();
            finish();
        });

        updateBtn.setOnClickListener(v -> {
            String idStr = idEdit.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Введите ID", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(idStr);
            Contact contact = new Contact(id,
                    nameEdit.getText().toString(),
                    phoneEdit.getText().toString(),
                    emailEdit.getText().toString());

            dbHelper.updateContact(contact);
            Toast.makeText(this, "Контакт обновлён", Toast.LENGTH_SHORT).show();
            finish();
        });

        deleteBtn.setOnClickListener(v -> {
            String idStr = idEdit.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Введите ID", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(idStr);
            dbHelper.deleteContact(id);
            Toast.makeText(this, "Контакт удалён", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Contact Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Уведомления о контактах");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Контакт добавлен")
                .setContentText("Контакт успешно сохранён.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(1001, notification);
        }
    }
}

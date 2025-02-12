package com.shop.smartshop.ui.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shop.smartshop.R;
import com.shop.smartshop.ui.cart.CartActivity;
import com.shop.smartshop.ui.home.HomeActivity;
import com.shop.smartshop.ui.login.LoginActivity;

public class AccountActivity extends AppCompatActivity {

    private TextView emailTextView;
    private Button signOutButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Инициализируем FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Находим элементы UI
        emailTextView = findViewById(R.id.emailTextView);
        signOutButton = findViewById(R.id.signOutButton);

        // Получаем текущего пользователя
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Отображаем email пользователя, если он залогинен
            emailTextView.setText(user.getEmail());
        }

        // Обработчик нажатия на кнопку "Выход"
        signOutButton.setOnClickListener(v -> {
            // Выход из аккаунта
            mAuth.signOut();
            // Перенаправляем на экран входа или главную страницу
            finish();
            startActivity(new Intent(AccountActivity.this, LoginActivity.class)); // или на другую активность
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    startActivity(new Intent(AccountActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.account) {
                    return true;
                } else if (itemId == R.id.cart) {
                    startActivity(new Intent(AccountActivity.this, CartActivity.class));
                    return true;
                }
                return false;

            }
        });
    }
}

package com.shop.smartshop.ui.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.shop.smartshop.R;
import com.shop.smartshop.adapter.CartAdapter;
import com.shop.smartshop.model.CartItem;
import com.shop.smartshop.ui.account.AccountActivity;
import com.shop.smartshop.ui.home.HomeActivity;
import com.shop.smartshop.ui.checkout.ConfirmOrderDialog;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private List<CartItem> cartItems = new ArrayList<>();
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPrice);
        checkoutButton = findViewById(R.id.checkoutButton);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadCartFromPreferences();

        CartAdapter.OnRemoveItemListener onRemoveItemListener = position -> {
            cartItems.remove(position);
            cartAdapter.notifyItemRemoved(position);
            updateTotalPrice();
            saveCartToPreferences();
        };

        cartAdapter = new CartAdapter(cartItems, onRemoveItemListener);
        cartRecyclerView.setAdapter(cartAdapter);

        updateTotalPrice();

        CartItem cartItem = (CartItem) getIntent().getSerializableExtra("cart_item");
        if (cartItem != null) {
            addToCart(cartItem);
        }

        checkoutButton.setOnClickListener(v -> {
            ConfirmOrderDialog.show(CartActivity.this, this::confirmOrder, this::cancelOrder);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.account) {
                startActivity(new Intent(CartActivity.this, AccountActivity.class));
                return true;
            } else if (itemId == R.id.cart) {
                return true;
            }
            return false;
        });
    }

    @SuppressLint("DefaultLocale")
    private void updateTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProductPrice() * item.getQuantity();
        }
        totalPriceTextView.setText(String.format("%.2f ₽", total));
    }

    private void loadCartFromPreferences() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "guest"; // Или используйте свой способ идентификации

        SharedPreferences preferences = getSharedPreferences("cart_" + userId, MODE_PRIVATE);
        String cartData = preferences.getString("cart_items", "");

        if (!cartData.isEmpty()) {
            String[] items = cartData.split(";");

            for (String itemData : items) {
                String[] itemDetails = itemData.split("\\|");
                if (itemDetails.length == 4) {
                    String productName = itemDetails[0];
                    double productPrice = Double.parseDouble(itemDetails[1]);
                    int quantity = Integer.parseInt(itemDetails[2]);
                    int productImage = Integer.parseInt(itemDetails[3]);

                    CartItem cartItem = new CartItem(productName, productImage, productPrice, quantity);
                    cartItems.add(cartItem);
                }
            }
        }
    }

    private void saveCartToPreferences() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "guest";

        SharedPreferences preferences = getSharedPreferences("cart_" + userId, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder cartData = new StringBuilder();

        for (CartItem item : cartItems) {
            cartData.append(item.getProductName())
                    .append("|")
                    .append(item.getProductPrice())
                    .append("|")
                    .append(item.getQuantity())
                    .append("|")
                    .append(item.getProductImageResId())
                    .append(";");
        }

        editor.putString("cart_items", cartData.toString());
        editor.apply();
    }

    public void addToCart(CartItem cartItem) {
        boolean itemExists = false;
        for (CartItem existingItem : cartItems) {
            if (existingItem.getProductName().equals(cartItem.getProductName())) {
                existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartItems.add(cartItem);
        }

        saveCartToPreferences();
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }

    private void confirmOrder() {
        cartItems.clear();
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
        saveCartToPreferences();
    }

    private void cancelOrder() {
    }
}

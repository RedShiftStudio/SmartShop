package com.shop.smartshop.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shop.smartshop.R;
import com.shop.smartshop.adapter.ProductAdapter;
import com.shop.smartshop.model.Product;
import com.shop.smartshop.ui.account.AccountActivity;
import com.shop.smartshop.ui.cart.CartActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private List<Product> filteredProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        int numberOfColumns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));


        productList = new ArrayList<>();
        productList.add(new Product("17.3\" Ноутбук MSI Katana GF76 B12UCR-821XRU черный", "Ноутбук MSI Katana GF76 B12UCR-821XRU предлагает компьютерным энтузиастам высокую скорость и производительность при работе в ресурсоемких программах, запуске игр и графического контента. В нем задействована конфигурация с процессором Intel Core i5-12450H и видеокартой GeForce RTX 3050, которые обеспечивают мощность и плавность графики.", 95999, R.drawable.laptop));
        productList.add(new Product("Мышь проводная MSI Clutch GM11 черный", "Мышь проводная MSI Clutch GM11 в изящном черном корпусе ничем не выдает своего «игрового» предназначения. Это возможно только до тех пор, пока пользователь не увидит ее многоцветную подсветку по контуру. Оптический светодиодный сенсор Pixart PMW3325 работает в режимах разрешений 400, 800, 1600, 3200, 5000 dpi.", 2299, R.drawable.mouse));
        productList.add(new Product("Руль ARDOR GAMING Silverstone черный", "Руль ARDOR GAMING Silverstone поддерживает популярные игровые платформы и позволяет полностью окунуться в игровой процесс и почувствовать себя настоящим гонщиком. Модель с обратной связью и эффектом виброотдачи улучшает ощущения от езды и приближает их к естественному процессу. ", 19999, R.drawable.roule));
        productList.add(new Product("Клавиатура проводная ARDOR GAMING Blade [AG-FL-B104Red-B]", "Клавиатура проводная ARDOR GAMING Blade обладает интегрированной RGB-подсветкой, которая делает заметнее клавиши при слабом внешнем освещении. Конфигурацией предусмотрен цифровой блок, незаменимый для обработки числовых данных. Подключите 1.6-метровый кабель к USB-разъему и приступайте к использованию ассистентки.", 5199, R.drawable.keyboard));
        productList.add(new Product("6.78\" Смартфон POCO C61 64 ГБ зеленый", "6.78\" Смартфон POCO C61 64 ГБ зеленого цвета предлагает дисплей 6.78\", который подходит для комфортного просмотра видео и работы с приложениями. 8-ядерный процессор мгновенно выполняет любые задачи.", 6799, R.drawable.poco));
        productList.add(new Product("12.1\" Планшет POCO Pad Wi-Fi 256 ГБ серый", "Планшет POCO Pad Wi-Fi 256 ГБ представлен в металлическом корпусе серого цвета. Аппаратная платформа с процессором Qualcomm Snapdragon 7s Gen 2 и 8 ГБ оперативной памяти обеспечивает плавную работу приложений без зависаний.", 31799, R.drawable.table));
        productList.add(new Product("Портативная колонка JBL Charge 5, черный", "Портативная колонка JBL Charge 5 в корпусе черного цвета станет отличным спутником для меломана и позволит слушать любимую музыку дома или на открытом воздухе. Подключаемая по Bluetooth к смартфону колонка хорошо защищена от попадания внутрь корпуса влаги и пыли – ее пыле- и влагозащита соответствует стандарту IP67, поэтому даже полное погружение в воду на некоторое время не выведет колонку из строя.", 16499, R.drawable.jbl));
        productList.add(new Product("6.6\" Смартфон Samsung Galaxy A55 256 ГБ синий", "Смартфон Samsung Galaxy A55 5G 256 ГБ в синем корпусе оснащен дисплеем 6.6” с защитным покрытием Corning Gorilla Glass Victus+ от царапин. Матрица Super AMOLED сохраняет яркость и четкость изображений при любом освещении. Разрешение 2340x1080 dpi с плотностью пикселей 388 ppi устраняет эффект зернистости.", 41999, R.drawable.samsung));
        productList.add(new Product("Кольцевая лампа FinePower RL-7 RGB", "Кольцевая лампа FinePower со светодиодными элементами LED RGB обеспечивает направленное освещение. Она предназначена для создания дополнительного света при съемке фотографий и видео. Цветовая температура в пределах 3000-6000K позволяет получать детализированные и реалистичные снимки. Стойка с трехсекционной конструкцией и устойчивым основанием гарантирует удобное размещение на различных покрытиях.", 2399, R.drawable.lampa));
        productList.add(new Product("Наушники TWS Samsung Buds 2 Pro белый", "Наушники TWS Samsung Buds 2 Pro внутриканального типа – удобное и прочное устройство, которое обеспечивает надежную и комфортную посадку. Пластиковый корпус белого цвета имеет высокую прочность и небольшой вес, благодаря чему модель надолго сохраняет первоначальный вид и подходит для активных тренировок.", 14999, R.drawable.buds));

        filteredProductList = new ArrayList<>(productList);

        productAdapter = new ProductAdapter(HomeActivity.this, filteredProductList);
        recyclerView.setAdapter(productAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    return true;
                } else if (itemId == R.id.account) {
                    startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                    return true;
                } else if (itemId == R.id.cart) {
                    startActivity(new Intent(HomeActivity.this, CartActivity.class));
                    return true;
                }
                return false;

            }
        });


    }


    @SuppressLint("NotifyDataSetChanged")
    private void filterProducts(String query) {
        filteredProductList.clear();
        if (query.isEmpty()) {
            filteredProductList.addAll(productList);
        } else {
            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredProductList.add(product);
                }
            }
        }
        productAdapter.notifyDataSetChanged();
    }

}

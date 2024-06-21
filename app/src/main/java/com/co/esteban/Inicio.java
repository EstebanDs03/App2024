package com.co.esteban;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.com.carrito.global_context.GlobalContext;
import co.com.carrito.models.Product;

import androidx.appcompat.app.AppCompatActivity;

public class Inicio extends AppCompatActivity {
    GlobalContext carShoping = GlobalContext.getInstance();
    public static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(0, "NIKE SB DUNK", "$600.000 COP", R.drawable.imgtenis, "Nike"));
        products.add(new Product(1, "Adidas tenerife", "$190.000 COP", R.drawable.adidas, "Adidas"));
        products.add(new Product(2, "Under Armour yellow", "$223.000 COP", R.drawable.under_amarillo, "Under Armour"));
        products.add(new Product(3, "New Balance 527", "$547.000 COP", R.drawable.new_balance, "New Balance"));
        products.add(new Product(4, "Lacoste Pure", "$430.000 COP", R.drawable.lacoste, "Lacoste"));
        products.add(new Product(5, "Asics 235", "$250.000 COP", R.drawable.asics, "Asics"));
    }

    LinearLayout productsLayout;
    Button carButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        productsLayout = findViewById(R.id.products_section);
        carButton = findViewById(R.id.car_button);
        GenerateProductViews();
        carButton.setOnClickListener(view -> {
            irCarrito();
        });
    }

    private void GenerateProductViews() {
        productsLayout.removeAllViews();
        float density = getResources().getDisplayMetrics().density;
        int heigthImage = (int) (250 * density);

        for (Product producto : products) {
            LinearLayout productoLayout = new LinearLayout(this);
            productoLayout.setOrientation(LinearLayout.VERTICAL);
            productoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Agregar una imagen (ImageView)
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    heigthImage
            ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(producto.Image);

            // Agregar marca (TextViews)
            TextView marcaTextView = new TextView(this);
            marcaTextView.setText(producto.Branch);
            marcaTextView.setTextSize(12);

            TextView modeloTextView = new TextView(this);
            modeloTextView.setText(producto.Name);
            modeloTextView.setTextSize(18);

            TextView precioTextView = new TextView(this);
            precioTextView.setText(producto.Price);
            precioTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
            precioTextView.setTextSize(16);

            // Agregar vistas al LinearLayout del producto
            productoLayout.addView(imageView);
            productoLayout.addView(marcaTextView);
            productoLayout.addView(modeloTextView);
            productoLayout.addView(precioTextView);

            Button comprarButton = new Button(this);
            comprarButton.setText("Comprar");
            comprarButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CB1E1E1E")));
            comprarButton.setOnClickListener(view -> {
                Optional<Product> productoEncontrado = products.stream()
                        .filter(x -> x.Id == producto.Id)
                        .findFirst();
                carShoping.addProduct(productoEncontrado.get());
                irCarrito();
            });

            Button carritoButton = new Button(this);
            carritoButton.setText("Añadir al Carrito");
            carritoButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CB1E1E1E")));
            carritoButton.setOnClickListener(view -> {
                Optional<Product> productoEncontrado = products.stream()
                        .filter(x -> x.Id == producto.Id)
                        .findFirst();
                carShoping.addProduct(productoEncontrado.get());
            });

            // Agregar botones al LinearLayout del producto
            LinearLayout botonesLayout = new LinearLayout(this);
            botonesLayout.setOrientation(LinearLayout.HORIZONTAL);
            botonesLayout.setGravity(Gravity.END);
            botonesLayout.addView(comprarButton);
            botonesLayout.addView(carritoButton);
            productoLayout.addView(botonesLayout);

            // Agregar el productoLayout al LinearLayout principal
            productsLayout.addView(productoLayout);
        }
    }

    public void irCarrito() {
        Intent intent = new Intent(this, carrito.class);
        startActivity(intent);
    }
}

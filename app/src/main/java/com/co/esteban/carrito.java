package com.co.esteban;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Optional;

import co.com.carrito.global_context.GlobalContext;
import co.com.carrito.models.Product;

public class carrito extends AppCompatActivity {
    GlobalContext carShoping = GlobalContext.getInstance();
    LinearLayout productsLayout ;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        productsLayout = findViewById(R.id.products_section);
        homeButton = findViewById(R.id.home_button);
        GenerateProductViews();
        homeButton.setOnClickListener(view->{
            irInicio();
        });

    }
    private void  GenerateProductViews(){
        productsLayout.removeAllViews();
        float density = getResources().getDisplayMetrics().density;
        // Altura deseada en dp
        int heigthImage = (int) (250 * density);
        List<Product> productos=carShoping.getProducts();
        LinearLayout productoLayout = new LinearLayout(this);
        double totalPrice = 0.0;
        if (productos.size() == 0){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    heigthImage // Altura deseada en píxeles
            ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(R.drawable.carrito_vacio);
            productoLayout.addView(imageView);
        }else {
            for (Product producto : productos){

                productoLayout.setOrientation(LinearLayout.VERTICAL);
                productoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // Agregar una imagen (ImageView)
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        heigthImage // Altura deseada en píxeles
                ));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(producto.Image); // Cambia esto por la imagen real

                // Agregar marca(TextViews)
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

                totalPrice += Double.parseDouble(producto.Price.replace("$", "").replace(",", "").replace("COP",""));


                Button carritoButton = new Button(this);
                carritoButton.setText("Eliminar");
                carritoButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                carritoButton.setOnClickListener(view -> {
                    Optional<Product> productoEncontrado = productos.stream()
                            .filter(x -> x.Id == producto.Id)
                            .findFirst();
                    carShoping.removeProduct(productoEncontrado.get());
                    GenerateProductViews();

                });

                // Agregar botones al LinearLayout del producto
                LinearLayout botonesLayout = new LinearLayout(this);
                botonesLayout.setOrientation(LinearLayout.HORIZONTAL);
                botonesLayout.setGravity(Gravity.END);
                botonesLayout.addView(carritoButton);
                productoLayout.addView(botonesLayout);

                // Agregar el productoLayout al LinearLayout principal


            }
            TextView totalTextView = new TextView(this);
            totalTextView.setText("Total: $" + String.format("%,.2f", totalPrice) + " COP");
            totalTextView.setTextSize(20);
            totalTextView.setGravity(Gravity.END);
            totalTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));
            productoLayout.addView(totalTextView);
        }
        productsLayout.addView(productoLayout);

    }
    public void irInicio(){
        Intent intent = new Intent(this, Inicio.class);
        startActivity(intent);
    }
}
package co.com.carrito.global_context;

import java.util.ArrayList;
import java.util.List;
import co.com.carrito.models.Product;

public class GlobalContext {
    private static GlobalContext instance;
    private List<Product> carrito = new ArrayList<>();

    private GlobalContext() {}

    public static synchronized GlobalContext getInstance() {
        if (instance == null) {
            instance = new GlobalContext();
        }
        return instance;
    }

    public void addProduct(Product product) {
        carrito.add(product);
    }

    public void removeProduct(Product product) {
        carrito.remove(product);
    }

    public List<Product> getProducts() {
        return carrito;
    }
}

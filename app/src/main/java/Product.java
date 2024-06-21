package co.com.carrito.models;

public class Product {
    public int Id;
    public String Name;
    public String Price;
    public int Image;
    public String Branch;

    public Product(int id, String name, String price, int image, String branch) {
        this.Id = id;
        this.Name = name;
        this.Price = price;
        this.Image = image;
        this.Branch = branch;
    }
}


import java.util.*; 
import java.sql.*;

// Custom Exception
class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
}

// Abstract class
abstract class Product {
    private String name;
    private double price;
    private int quantity;

    // Constructor
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Encapsulation: getters and setters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Abstract method
    public abstract double calculateDiscount();
}

// Inheritance & Polymorphism
class Electronics extends Product {
    private static final double DISCOUNT_RATE = 0.10; // final and static

    public Electronics(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public double calculateDiscount() {
        return getPrice() * getQuantity() * DISCOUNT_RATE;
    }
}

class Clothing extends Product {
    private static final double DISCOUNT_RATE = 0.20;

    public Clothing(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public double calculateDiscount() {
        return getPrice() * getQuantity() * DISCOUNT_RATE;
    }
}

public class ECommerceApp {
    private static List<Product> products = new ArrayList<>(); // Collections Framework

    public static void main(String[] args) {
        try {
            addProduct(new Electronics("Laptop", 50000, 2));
            addProduct(new Clothing("T-Shirt", 500, 6));
            addProduct(new Electronics("Headphones", 2000, 1));

            // Display products and discounts
            for(Product p : products) {
                System.out.println("Product: " + p.getName() +
                                   ", Quantity: " + p.getQuantity() +
                                   ", Discount: " + p.calculateDiscount());
            }

            // Optional: JDBC Stub (actual connection requires MySQL setup)
            connectToDatabase();

        } catch (InvalidProductException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.gc(); // Garbage Collector
        }
    }

    public static void addProduct(Product p) throws InvalidProductException {
        if(p.getQuantity() <= 0 || p.getPrice() <= 0) {
            throw new InvalidProductException("Invalid price or quantity for product: " + p.getName());
        }
        products.add(p);
    }

    public static void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/ecommerce";
            String user = "root";
            String password = "password";
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}

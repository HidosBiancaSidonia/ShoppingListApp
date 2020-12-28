package model;

public class Product {
    private Integer id_product;
    private String product;

    public Product(Integer id_product, String product) {
        this.id_product = id_product;
        this.product = product;
    }

    public Product(String product) {
        this.product = product;
    }

    public Integer getId_product() {
        return id_product;
    }

    public void setId_product(Integer id_product) {
        this.id_product = id_product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}

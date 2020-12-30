package model;

public class ListProduct {
    private Integer id_list_product;
    private Integer id_list;
    private Integer id_product;
    private Integer number;

    public ListProduct(Integer id_list_product,Integer number, Integer id_list, Integer id_product) {
        this.id_list_product = id_list_product;
        this.id_list = id_list;
        this.id_product = id_product;
        this.number = number;
    }

    public ListProduct(Integer number,Integer id_list, Integer id_product) {
        this.number = number;
        this.id_list = id_list;
        this.id_product = id_product;
    }

    public Integer getId_list_product() {
        return id_list_product;
    }

    public void setId_list_product(Integer id_list_product) {
        this.id_list_product = id_list_product;
    }

    public Integer getId_list() {
        return id_list;
    }

    public void setId_list(Integer id_list) {
        this.id_list = id_list;
    }

    public Integer getId_product() {
        return id_product;
    }

    public void setId_product(Integer id_product) {
        this.id_product = id_product;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}

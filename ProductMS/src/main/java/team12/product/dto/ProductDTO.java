package team12.product.dto;

import java.math.BigDecimal;

import team12.product.entity.Product;

public class ProductDTO {

    private Integer prodId;
    private String productname;
    private String brand;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private String image;
    private Integer sellerid;
    private String category;
    private String subcategory;
    private Integer rating;

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productName) {
        this.productname = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSellerid() {
        return sellerid;
    }

    public void setSellerId(Integer sellerid) {
        this.sellerid = sellerid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubCategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Product convertToEntity() {
        Product product = new Product();

        product.setProdId(this.getProdId());
        product.setBrand(this.getBrand());
        product.setCategory(this.getCategory());
        product.setDescription(this.getDescription());
        product.setImage(this.getImage());
        product.setPrice(this.getPrice());
        product.setProductname(this.getProductname());
        product.setRating(this.getRating());
        product.setSellerId(this.getSellerid());
        product.setStock(this.getStock());
        product.setSubCategory(this.getSubcategory());

        return product;
    }
}
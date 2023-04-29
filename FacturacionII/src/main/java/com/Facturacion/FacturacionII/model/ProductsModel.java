package com.Facturacion.FacturacionII.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private String code;
    private Integer stock;
    private Double price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "products", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvoiceDetailsModel> invoiceDetails = new ArrayList<>();

    public ProductsModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<InvoiceDetailsModel> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetailsModel> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }


}

package com.Facturacion.FacturacionII.model;

import javax.persistence.*;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_detail_id")
    private Integer invoiceDetailId;
    private Integer amount;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsModel products;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private InvoiceModel invoice;

    public InvoiceDetailsModel() {
    }

    public Integer getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(Integer invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductsModel getProducts() {
        return (ProductsModel) products;
    }

    public void setProducts(ProductsModel products) {
        this.products = products;
    }

    public InvoiceModel getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceModel invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "InvoiceDetailsModel{" +
                "invoiceDetailId=" + invoiceDetailId +
                ", amount=" + amount +
                ", price=" + price +
                ", products=" + products +
                ", invoice=" + invoice +
                '}';
    }
}

package com.Facturacion.FacturacionII.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoice")
public class InvoiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime created_at;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonDeserialize
    private ClientModel client;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvoiceDetailsModel> invoiceDetails = new ArrayList<>();

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public InvoiceModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public InvoiceModel setTotal(Double total) {
        this.total = total;
        return null;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }


}

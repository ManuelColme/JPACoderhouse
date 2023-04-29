package com.Facturacion.FacturacionII.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;


@Data
@Entity
@Table(name = "clients")
public class ClientModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastname;
    private String docnumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<InvoiceModel> invoice = new ArrayList<>();

    public ClientModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

    public List<InvoiceModel> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<InvoiceModel> invoice) {
        this.invoice = invoice;
    }



}

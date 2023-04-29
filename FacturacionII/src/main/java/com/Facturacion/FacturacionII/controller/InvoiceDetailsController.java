package com.Facturacion.FacturacionII.controller;


import com.Facturacion.FacturacionII.model.*;
import com.Facturacion.FacturacionII.service.InvoiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "facturacion/invoicedetails")
public class InvoiceDetailsController {

    @Autowired
    private InvoiceDetailsService invoiceDetailsService;


    @PostMapping(path = {"/add"})
    public ResponseEntity<InvoiceDetailsModel> create(@RequestBody InvoiceDetailsModel invoiceDetails, InvoiceModel invoice, ClientModel client ) throws Exception {
        return new ResponseEntity(this.invoiceDetailsService.create(invoiceDetails, invoice, client), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<InvoiceDetailsModel> update(@RequestBody InvoiceDetailsModel invoiceDetails, @PathVariable Integer id, InvoiceModel invoice) throws Exception {
        return new ResponseEntity<>(this.invoiceDetailsService.update(invoiceDetails,id,invoice), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceDetailsModel> findById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.invoiceDetailsService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<InvoiceDetailsModel>> findAll(){
        return new ResponseEntity<>(this.invoiceDetailsService.findAll(), HttpStatus.OK);
    }
}


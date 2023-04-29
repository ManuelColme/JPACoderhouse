package com.Facturacion.FacturacionII.controller;

import com.Facturacion.FacturacionII.model.InvoiceModel;
import com.Facturacion.FacturacionII.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "facturacion/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceModel> findById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.invoiceService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<InvoiceModel>> findAll(){
        return new ResponseEntity<>(this.invoiceService.findAll(), HttpStatus.OK);
    }
}


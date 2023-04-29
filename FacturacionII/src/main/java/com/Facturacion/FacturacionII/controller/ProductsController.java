package com.Facturacion.FacturacionII.controller;

import com.Facturacion.FacturacionII.exception.AlreadyExistsException;
import com.Facturacion.FacturacionII.model.ProductsModel;
import com.Facturacion.FacturacionII.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "facturacion/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @PostMapping(path = "/add")
    public ResponseEntity<List<ProductsModel>> create(@RequestBody List<ProductsModel> products) throws AlreadyExistsException {
        List<ProductsModel> createdProducts = this.productsService.create(products);
        return new ResponseEntity<>(createdProducts, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductsModel> update(@RequestBody ProductsModel products, @PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.productsService.update(products,id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductsModel> findById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.productsService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<ProductsModel>> findAll(){
        return new ResponseEntity<>(this.productsService.findAll(), HttpStatus.OK);
    }
}


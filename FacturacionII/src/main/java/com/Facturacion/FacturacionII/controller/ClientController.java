package com.Facturacion.FacturacionII.controller;


import com.Facturacion.FacturacionII.exception.AlreadyExistsException;
import com.Facturacion.FacturacionII.model.ClientModel;
import com.Facturacion.FacturacionII.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "facturacion/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(path = "/add")
    public ResponseEntity<List<ClientModel>> create(@RequestBody List<ClientModel> clients) throws AlreadyExistsException {
        List<ClientModel> createdClients = this.clientService.create(clients);
        return new ResponseEntity<>(createdClients, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ClientModel> update(@RequestBody ClientModel client, @PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.clientService.update(client,id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientModel> findById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.clientService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<ClientModel>> findAll(){
        return new ResponseEntity<>(this.clientService.findAll(), HttpStatus.OK);
    }
}


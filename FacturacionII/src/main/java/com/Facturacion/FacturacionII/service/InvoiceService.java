package com.Facturacion.FacturacionII.service;

import com.Facturacion.FacturacionII.exception.NotFoundException;
import com.Facturacion.FacturacionII.model.InvoiceModel;
import com.Facturacion.FacturacionII.repository.ClientRepository;
import com.Facturacion.FacturacionII.repository.InvoiceRepository;
import com.Facturacion.FacturacionII.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductsRepository productsRepository;


    public InvoiceModel findById(Integer id) throws Exception {
        if (id <= 0){
            throw new Exception("El id brindado no es valido.");
        }

        Optional<InvoiceModel> invoiceOp = this.invoiceRepository.findById(id);

        if (invoiceOp.isEmpty()){
            log.info("La Factura con el id brindado no existe en la base de datos : " + id);
            throw new NotFoundException("La Factura que intenta solicitar no existe");
        }else {
            return invoiceOp.get();
        }
    }


    public List<InvoiceModel> findAll(){
        return this.invoiceRepository.findAll();
    }
}

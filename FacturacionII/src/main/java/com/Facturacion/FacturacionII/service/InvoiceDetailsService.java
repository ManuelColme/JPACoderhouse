package com.Facturacion.FacturacionII.service;

import com.Facturacion.FacturacionII.exception.AlreadyExistsException;
import com.Facturacion.FacturacionII.exception.NotFoundException;
import com.Facturacion.FacturacionII.model.*;
import com.Facturacion.FacturacionII.repository.ClientRepository;
import com.Facturacion.FacturacionII.repository.InvoiceDetailsRepository;
import com.Facturacion.FacturacionII.repository.InvoiceRepository;
import com.Facturacion.FacturacionII.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    ///////////////////////CREATE//////////////////////////////////////////////////////////////////////////
    public InvoiceDetailsModel create(InvoiceDetailsModel newInvoiceDetails, InvoiceModel newInvoice, ClientModel newClient) throws Exception {

        Double total = 0.0;
        if (newInvoiceDetails.getInvoice().getClient() == null ||  newInvoiceDetails.getInvoice().getClient().getDocnumber() == null || newInvoiceDetails.getInvoice().getClient().getDocnumber().isEmpty() ) {
            log.info("numero de documento cliente");
            throw new Exception("El Documento del cliente es obligatorio");
        }

        String code = newInvoiceDetails.getProducts().getCode();
        Integer amount =  newInvoiceDetails.getAmount();

        if (code == null || code.isEmpty() || code.length() < 4 || code.length() > 20) {
            throw new AlreadyExistsException("El código del producto no es válido, debe tener entre 6 y 20 caracteres");
        }

        if (amount == null || amount <= 0){
            throw new AlreadyExistsException("El stock del producto no es válido, debe ser mayor que cero");
        }

        Optional<ProductsModel> productsOp = this.productsRepository.findByCode(code);

        if (productsOp.isEmpty()){
            log.info("El producto con el codigo brindado no existe en la base de datos : " + code);
            throw new NotFoundException("El producto que intenta solicitar no existe. Codigo: " + code);
        } else {
            String description = productsOp.get().getDescription();
            Integer productId = productsOp.get().getId();
            Double price = productsOp.get().getPrice();
            Integer stockBD = productsOp.get().getStock();
            if (stockBD == null || stockBD <= 0 ){
                throw new AlreadyExistsException("Ya no queda Stock del producto");

            }else{
                if (amount > stockBD) {
                    throw new AlreadyExistsException("La cantidad solicitada supera el stock disponible del producto");
                }
                total += price*amount;
                // Actualizar el stock del producto
                int updatedStock = stockBD - amount;
                ProductsModel productToUpdate = productsOp.get();
                productToUpdate.setStock(updatedStock);
                productsRepository.save(productToUpdate);
            }

        }
        newInvoice.setTotal(total.doubleValue());
        if (newInvoice.getTotal() == null || newInvoice.getTotal() <= 0) {
            throw new Exception("El total de la factura debe ser mayor a cero");
        }

        Optional<ClientModel> clientOp = this.clientRepository.findByDocnumber(newInvoiceDetails.getInvoice().getClient().getDocnumber());

        if (clientOp.isEmpty()){
            log.info("El cliente '"+ newClient.getDocnumber()+ "' debe existir en la base de datos, por favor registrelo antes de continuar." );
            throw new AlreadyExistsException("Cliente No existe.");
        }
        else {
            newInvoiceDetails.setPrice(productsOp.get().getPrice());
            newInvoice.setClient(clientOp.get());
            newInvoice.setCreated_at(LocalDateTime.now());

            newInvoiceDetails.setInvoice(newInvoice);
            InvoiceModel savedInvoice = this.invoiceRepository.save(newInvoice);
            newInvoiceDetails.setInvoice(savedInvoice);

            ProductsModel persistedProduct = productsOp.get();
            newInvoiceDetails.setProducts(persistedProduct);

            return this.invoiceDetailsRepository.saveAndFlush(newInvoiceDetails);

        }


    }



///////////////////////UPDATE//////////////////////////////////////////////////////////////////////////

    public InvoiceDetailsModel update(InvoiceDetailsModel newInvoiceDetails, Integer id, InvoiceModel newInvoice) throws Exception {
        log.info("ID INGRESANDO : " + id);
        Double total = 0.0;
        if (id <= 0){
            throw new Exception("El id brindado no es valido");
        }

        Optional<InvoiceDetailsModel> invoiceDetailsOp = this.invoiceDetailsRepository.findById(id);

        if (invoiceDetailsOp.toString().isEmpty()){
            log.info("El Detalle que intenta modificar no existe en la base de datos : " + newInvoiceDetails);
            throw new NotFoundException("El detalle que intenta modificar no existe en la base de datos");
        }else {
            log.info("el detalle fue encontrado");

            InvoiceDetailsModel invoiceDetailsBd = invoiceDetailsOp.get();
            invoiceDetailsBd.setInvoiceDetailId(invoiceDetailsBd.getInvoiceDetailId());
            invoiceDetailsBd.setAmount(Objects.requireNonNullElse(newInvoiceDetails.getAmount(), invoiceDetailsBd.getAmount()));
            invoiceDetailsBd.setPrice(Objects.requireNonNullElse(newInvoiceDetails.getPrice(), invoiceDetailsBd.getPrice()));

            Double price = invoiceDetailsBd.getPrice();
            Integer amount =  invoiceDetailsBd.getAmount();
            total += price*amount;
            newInvoice.setTotal(total.doubleValue());

            Optional<ClientModel> clientOp = this.clientRepository.findByDocnumber(newInvoiceDetails.getInvoice().getClient().getDocnumber());
            newInvoice.setClient((ClientModel) Objects.requireNonNullElse(clientOp.get(),invoiceDetailsBd.getInvoice().getClient()));

            InvoiceModel savedInvoice = this.invoiceRepository.save(newInvoice);
            invoiceDetailsBd.setInvoice((InvoiceModel) Objects.requireNonNullElse(savedInvoice, invoiceDetailsBd.getInvoice()));

            // Actualizar el stock del producto
            int updatedStock = invoiceDetailsBd.getProducts().getStock() + invoiceDetailsBd.getAmount();
            updatedStock = updatedStock -  invoiceDetailsBd.getAmount() - 1;


            ProductsModel productToUpdate = invoiceDetailsBd.getProducts();
            productToUpdate.setStock(updatedStock);
            productsRepository.save(productToUpdate);

            log.info("detalle actualizado : " + invoiceDetailsBd);

            return this.invoiceDetailsRepository.save(invoiceDetailsBd);


        }
    }
    ///////////////////////FIND_BY_ID//////////////////////////////////////////////////////////////////////////
    public InvoiceDetailsModel findById(Integer id) throws Exception {
        if (id <= 0){
            throw new Exception("El id brindado no es valido.");
        }

        Optional<InvoiceDetailsModel> invoiceDetailsOp = this.invoiceDetailsRepository.findById(id);

        if (invoiceDetailsOp.isEmpty()){
            log.info("El detalle con el id brindado no existe en la base de datos : " + id);
            throw new NotFoundException("El detalle que intenta solicitar no existe");
        }else {
            return invoiceDetailsOp.get();
        }
    }


    ///////////////////////FIND_ALL//////////////////////////////////////////////////////////////////////////
    public List<InvoiceDetailsModel> findAll(){
        return this.invoiceDetailsRepository.findAll();
    }

}

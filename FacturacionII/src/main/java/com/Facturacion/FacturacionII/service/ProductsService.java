package com.Facturacion.FacturacionII.service;

import com.Facturacion.FacturacionII.exception.AlreadyExistsException;
import com.Facturacion.FacturacionII.exception.NotFoundException;
import com.Facturacion.FacturacionII.model.ProductsModel;
import com.Facturacion.FacturacionII.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ProductsService {


    @Autowired
    private ProductsRepository productsRepository;

    public List<ProductsModel> create(List<ProductsModel> newProductsList) throws AlreadyExistsException {
        List<ProductsModel> savedProductsList = new ArrayList<>();
        List<String> existingCodes = new ArrayList<>();

        for (ProductsModel newProducts : newProductsList) {
            String description = newProducts.getDescription();
            String code = newProducts.getCode();
            Integer stock = newProducts.getStock();
            Double price = newProducts.getPrice();

            if (description == null || description.isEmpty()) {
                throw new AlreadyExistsException("La descripción del producto no puede estar vacía");
            }

            if (code == null || code.isEmpty() || code.length() < 4 || code.length() > 20) {
                throw new AlreadyExistsException("El código del producto no es válido, debe tener entre 6 y 20 caracteres");
            }

            if (stock == null || stock <= 0){
                throw new AlreadyExistsException("El stock del producto no es válido, debe ser mayor que cero");
            }

            if (price == null || price <= 0){
                throw new AlreadyExistsException("El precio del producto no es válido, debe ser mayor que cero");
            }

            Optional<ProductsModel> productsOp = this.productsRepository.findByCode(code);

            if (productsOp.isPresent()){
                existingCodes.add(code);
            } else {
                savedProductsList.add(newProducts);
            }
        }

        if(!existingCodes.isEmpty()) {
            String existingCodesMsg = String.join(",", existingCodes);
            log.info("Los siguientes códigos ya existen en la base de datos : " + existingCodesMsg);
            throw new AlreadyExistsException("Los siguientes códigos ya existen en la base de datos : " + existingCodesMsg);
        } else {
            return this.productsRepository.saveAll(savedProductsList);
        }
    }


    public ProductsModel update(ProductsModel newProducts, Integer id) throws Exception {
        log.info("ID INGRESANDO : " + id);
        if (id <= 0){
            throw new Exception("El id brindado no es valido");
        }

        if (newProducts.getDescription() == null || newProducts.getDescription().trim().isEmpty()){
            throw new Exception("La descripción del producto no puede estar vacía.");
        }

        if (newProducts.getCode() == null ||newProducts.getCode().trim().isEmpty()|| newProducts.getCode().length() < 4|| newProducts.getCode().length() > 20){
            throw new Exception("El código del producto debe contener sólo caracteres alfanuméricos mayúsculos y debe tener entre 4 y 20 caracteres");
        }

        if (newProducts.getStock() == null || newProducts.getStock() <= 0){
            throw new Exception("El stock del producto debe ser un número positivo.");
        }


        if (newProducts.getPrice() == null || newProducts.getPrice() <= 0){
            throw new Exception("El precio del producto debe ser un número positivo.");
        }

        Optional<ProductsModel> productsOp = this.productsRepository.findById(id);

        if (productsOp.isEmpty()){
            log.info("El producto que intenta modificar no existe en la base de datos : " + newProducts);
            throw new NotFoundException("El producto que intenta modificar no existe en la base de datos");
        }else {
            log.info("el producto fue encontrado");
            ProductsModel productsBd = productsOp.get();

            productsBd.setDescription(newProducts.getDescription());
            productsBd.setCode(newProducts.getCode());
            productsBd.setStock(newProducts.getStock());
            productsBd.setPrice(newProducts.getPrice());

            log.info("producto actualizado : " + productsBd);

            return this.productsRepository.save(productsBd);
        }
    }

    public ProductsModel findById(Integer id) throws Exception {
        if (id <= 0){
            throw new Exception("El id brindado no es valido.");
        }

        Optional<ProductsModel> productsOp = this.productsRepository.findById(id);

        if (productsOp.isEmpty()){
            log.info("El producto con el id brindado no existe en la base de datos : " + id);
            throw new NotFoundException("El producto que intenta solicitar no existe");
        } else {
            return productsOp.get();
        }
    }

    public List<ProductsModel> findAll(){
        return this.productsRepository.findAll();
    }


}

package com.Facturacion.FacturacionII.repository;


import com.Facturacion.FacturacionII.model.ProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository  extends JpaRepository <ProductsModel,Long> {

    Optional<ProductsModel> findByCode(String code);
    Optional<ProductsModel> findById(Integer id);


}

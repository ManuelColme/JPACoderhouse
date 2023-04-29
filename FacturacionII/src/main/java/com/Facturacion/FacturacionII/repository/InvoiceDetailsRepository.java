package com.Facturacion.FacturacionII.repository;


import com.Facturacion.FacturacionII.model.InvoiceDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface InvoiceDetailsRepository  extends JpaRepository <InvoiceDetailsModel,Integer> {

    Optional<InvoiceDetailsModel> findById(Integer id);

}

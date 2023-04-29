package com.Facturacion.FacturacionII.repository;


import com.Facturacion.FacturacionII.model.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface InvoiceRepository  extends JpaRepository <InvoiceModel,Long> {

    Optional<InvoiceModel> findById(Integer id);



}

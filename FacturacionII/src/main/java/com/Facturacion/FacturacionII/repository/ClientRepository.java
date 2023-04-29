package com.Facturacion.FacturacionII.repository;

import com.Facturacion.FacturacionII.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ClientRepository extends JpaRepository <ClientModel,Long> {

    Optional<ClientModel> findByDocnumber(String DocNumber);
    Optional<ClientModel> findById(Integer Id);

}
